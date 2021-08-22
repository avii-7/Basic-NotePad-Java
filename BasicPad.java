import java.awt.Frame;
import java.awt.TextArea;
import java.awt.MenuBar;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.FileDialog;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

class BasicPad extends Frame implements ActionListener {
    TextArea text;
    MenuItem cut, copy, paste, delete;
    String dir = "";
    static String title = "Untiled - BasicPad";
    static BasicPad f;

    BasicPad(String msg) {
        super(msg);

        /* ----------------------------- MenuBar Section ---------------------------- */
        MenuBar mbar = new MenuBar();
        setMenuBar(mbar);

        Menu file = new Menu("File");
        MenuItem neww, open, save, saveAs, exit;
        file.add(neww = new MenuItem("New"));
        file.add(open = new MenuItem("Open..."));
        file.add(save = new MenuItem("Save"));
        file.add(saveAs = new MenuItem("Save As..."));
        file.add(new MenuItem("-"));
        file.add(exit = new MenuItem("Exit"));
        mbar.add(file);

        Menu edit = new Menu("Edit");
        MenuItem selectAll, timeDate;
        edit.add(new MenuItem("-"));
        edit.add(delete = new MenuItem("Delete"));
        edit.add(new MenuItem("-"));
        edit.add(new MenuItem("-"));
        edit.add(selectAll = new MenuItem("Select All"));
        edit.add(timeDate = new MenuItem("Time/Date"));
        mbar.add(edit);
        

        /* -------------------------------- Text Area ------------------------------- */

        text = new TextArea(null, 0, 0, TextArea.SCROLLBARS_BOTH);
        add(text);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        selectAll.addActionListener(this);
        open.addActionListener(this);
        saveAs.addActionListener(this);
        delete.addActionListener(this);
        timeDate.addActionListener(this);
        neww.addActionListener(this);
        save.addActionListener(this);
        exit.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        String msg = "";
        if (e.getActionCommand().equals("Select All"))
            text.selectAll();

        else if (e.getActionCommand().equals("New")) {
            f.setTitle(title);
            text.setText(" ");
        } else if (e.getActionCommand().equals("Save")) {
            if (f.getTitle().equals(title)) {
                FileDialog saveAs = new FileDialog(this, "Save As", FileDialog.SAVE);
                saveAs.setVisible(true);
                dir = saveAs.getDirectory() + saveAs.getFile();
                try {
                    FileWriter obj = new FileWriter(dir);
                    obj.write(text.getText());
                    obj.close();
                } catch (IOException q) {
                    System.out.println(q);
                }
                String fileName = saveAs.getFile();
                if (fileName.equals(null))
                    fileName = title;
                f.setTitle(fileName + " - BasicPad");
            } else {
                try {
                    FileWriter obj = new FileWriter(dir);
                    obj.write(text.getText());
                    obj.close();
                } catch (IOException q) {
                    System.out.println(q);
                }
            }
        }

        else if (e.getActionCommand().equals("Delete")) {
            try {
                text.replaceRange("", text.getSelectionStart(), text.getSelectionEnd());
            } catch (StringIndexOutOfBoundsException q) {
            }

        }

        else if (e.getActionCommand().equals("Time/Date")) {
            String date = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
            if (text.getSelectedText().equals(""))
                text.append(date);
            else
                text.replaceRange(date, text.getSelectionStart(), text.getSelectionEnd());
        }

        else if (e.getActionCommand().equals("Open...")) {
            try {
                FileDialog load = new FileDialog(this, "Open", FileDialog.LOAD);
                load.setVisible(true);
                dir = load.getDirectory() + load.getFile();
                FileReader in = new FileReader(dir);
                f.setTitle(load.getFile() + " - BasicPad");
                int c = in.read();
                while (c != -1) {
                    msg += (char) c;
                    c = in.read();
                }
                in.close();
            } catch (IOException q) {
                System.out.println(q);
            }
            text.setText(msg);
        }

        else if (e.getActionCommand().equals("Save As...")) {
            FileDialog saveAs = new FileDialog(this, "Save As", FileDialog.SAVE);
            saveAs.setVisible(true);
            String fileName = saveAs.getFile();
            dir = saveAs.getDirectory() + fileName;
            FileWriter obj = null; // not run without null;
            try {
                obj = new FileWriter(dir);
                obj.write(text.getText());
                f.setTitle(fileName + " - BasicPad");
            } catch (IOException q) {
                // Never Runnn
            } catch (NullPointerException n) {
                f.setTitle(fileName + " - BasicPad");
            }
        } else if (e.getActionCommand().equals("Exit")) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        f = new BasicPad(title);
        f.setSize(500, 500);
        f.setVisible(true);
    }
}
