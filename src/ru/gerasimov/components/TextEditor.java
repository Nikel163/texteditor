package ru.gerasimov.components;

import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.DefaultEditorKit;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TextEditor extends JFrame {

    private static final String NEW_TAB = "Безымянный";
    private static Font defaultFont = new Font("Ubuntu", Font.PLAIN,18);
    private JFileChooser fc = new JFileChooser();
    private JTabbedPane tabs = new JTabbedPane();
    private JPopupMenu popup = new JPopupMenu();
    private JMenuBar menuBar = new JMenuBar();
    private JTextField status = new JTextField();
    private CaretListener statusListener;

    public TextEditor() {
        setTitle("EXTREME TEXT EDITOR");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        setVisible(true);

        //FILTER FOR TXT FILES
        fc.addChoosableFileFilter(new FileNameExtensionFilter(".txt", "txt"));

        //CREATE STATUS LISTENER
        statusListener = new CaretListener() {
            @Override
            public void caretUpdate(CaretEvent e) {
                JTextArea text = (JTextArea)e.getSource();
                int line = 1;
                int column = 1;
                int caretPosition = text.getCaretPosition();
                try {
                    line = text.getLineOfOffset(caretPosition);
                    column = caretPosition - text.getLineStartOffset(line);
                    line++; column++;
                    updateStatus(line, column);
                } catch (Exception ex){ex.printStackTrace();}
            }
        };

        setJMenuBar(menuBar);
        add(tabs);

        //INITIALIZE FIRST TAB
        JTextArea text = new JTextArea();
        text.addMouseListener(new PopupListener(popup));
        text.addCaretListener(statusListener);
        text.setFont(defaultFont);
        tabs.add(NEW_TAB, new Scroll(text, "", false));

        JMenu file = new JMenu("Файл");
        menuBar.add(file);

        //ADDING ACTIONS TO FILE MENU ITEMS
        file.add(Create);
        file.add(Open);
        file.add(Save);
        file.addSeparator();

        JMenu edit = new JMenu("Правка");

        JMenuItem cut = new JMenuItem(new DefaultEditorKit.CutAction());
        cut.setText("Вырезать");
        JMenuItem copy = new JMenuItem(new DefaultEditorKit.CopyAction());
        copy.setText("Копировать");
        JMenuItem paste = new JMenuItem(new DefaultEditorKit.PasteAction());
        paste.setText("Вставить");

        edit.add(cut);
        edit.add(copy);
        edit.add(paste);

        popup.add(cut);
        popup.add(copy);
        popup.add(paste);

        menuBar.add(edit);

        //FONTS LIST
        FontsComboBox fontList = new FontsComboBox();
        fontList.addActionListener(new ChangeFontAction(tabs));
        menuBar.add(fontList);

        //FONT SIZE
        JSpinner fontSize = new JSpinner(new SpinnerNumberModel(20,1,72,1));
        fontSize.addChangeListener(new ChangeFontSizeListener((Scroll)tabs.getSelectedComponent()));
        menuBar.add(fontSize);
        menuBar.add(new JSeparator());

        //FONT STYLE
        JToggleButton bold = new JToggleButton(new ImageIcon("src/res/bold16.png"));
        bold.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AbstractButton button = (AbstractButton)e.getSource();
                Scroll scroll = (Scroll)tabs.getSelectedComponent();
                Font oldFont = scroll.getTextArea().getFont();
                Font newFont = null;
                boolean selected = button.getModel().isSelected();
                if (selected) {
                    if(oldFont.getStyle() == Font.ITALIC){
                        newFont = new Font(oldFont.getName(), Font.BOLD|Font.ITALIC, oldFont.getSize());
                    } else {
                        newFont = new Font(oldFont.getName(), Font.BOLD, oldFont.getSize());
                    }
                } else {
                    if(oldFont.getStyle() == (Font.BOLD|Font.ITALIC)){
                        newFont = new Font(oldFont.getName(), Font.ITALIC, oldFont.getSize());
                    } else {
                        newFont = new Font(oldFont.getName(), Font.PLAIN, oldFont.getSize());
                    }
                }
                scroll.getTextArea().setFont(newFont);
            }
        });
        JToggleButton italic = new JToggleButton(new ImageIcon("src/res/italic16.png"));
        italic.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AbstractButton button = (AbstractButton)e.getSource();
                Scroll scroll = (Scroll)tabs.getSelectedComponent();
                Font oldFont = scroll.getTextArea().getFont();
                Font newFont = null;
                boolean selected = button.getModel().isSelected();
                if (selected) {
                    if(oldFont.getStyle() == Font.BOLD){
                        newFont = new Font(oldFont.getName(), Font.BOLD|Font.ITALIC, oldFont.getSize());
                    } else {
                        newFont = new Font(oldFont.getName(), Font.ITALIC, oldFont.getSize());
                    }
                } else {
                    if(oldFont.getStyle() == (Font.BOLD|Font.ITALIC)){
                        newFont = new Font(oldFont.getName(), Font.BOLD, oldFont.getSize());
                    } else {
                        newFont = new Font(oldFont.getName(), Font.PLAIN, oldFont.getSize());
                    }
                }
                scroll.getTextArea().setFont(newFont);
            }
        });

        menuBar.add(bold);
        menuBar.add(new JSeparator());
        menuBar.add(italic);
        menuBar.add(new JSeparator());
        //BUTTONS FOR CHANGING COLORS
        MenuButton changeTextColor = new MenuButton(new ImageIcon("src/res/color.png"));
        changeTextColor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color color = JColorChooser.showDialog(null, "Color", Color.black);
                Scroll scroll = (Scroll)tabs.getSelectedComponent();
                scroll.getTextArea().setForeground(color);
            }
        });
        changeTextColor.setText("Цвет текста");

        MenuButton changeBackgroundColor = new MenuButton(new ImageIcon("src/res/color.png"));
        changeBackgroundColor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color color = JColorChooser.showDialog(null, "Color", Color.white);
                Scroll scroll = (Scroll)tabs.getSelectedComponent();
                scroll.getTextArea().setBackground(color);
            }
        });
        changeBackgroundColor.setText("Цвет фона");

        menuBar.add(changeTextColor);
        menuBar.add(changeBackgroundColor);
        menuBar.add(new JSeparator());

        add(status, BorderLayout.SOUTH);
        updateStatus(1,1);

    }

    //ACTIONS START HERE
    Action Create = new AbstractAction("Создать файл") {
        @Override
        public void actionPerformed(ActionEvent e) {
            JTextArea text = new JTextArea();
            text.addCaretListener(statusListener);
            text.addMouseListener(new PopupListener(popup));
            text.setFont(defaultFont);
            tabs.add(NEW_TAB, new Scroll(text, "", false));
        }
    };

    Action Open = new AbstractAction("Открыть файл") {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
                openFile(fc.getSelectedFile().getAbsolutePath());
            }
        }
    };

    Action Save = new AbstractAction("Сохранить файл") {
        @Override
        public void actionPerformed(ActionEvent e) {
            saveFile();
        }
    };
    //ACTIONS STOP HERE

    //METHODS START HERE
    public void openFile(String fileName){
        try {
            File file = fc.getSelectedFile();
            String input = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
            JTextArea text = new JTextArea(input);
            text.setFont(defaultFont);
            text.addCaretListener(statusListener);
            text.addMouseListener(new PopupListener(popup));
            Scroll scroll = new Scroll(text, file.getAbsolutePath(), true);
            tabs.addTab(file.getName(), scroll);
            setTitle(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveFile() {
        Scroll scroll = (Scroll)tabs.getSelectedComponent();
        String output = scroll.getTextArea().getText();

        if(scroll.isOpened()){
            try {
                FileOutputStream writer = new FileOutputStream(scroll.getPath());
                writer.write(output.getBytes());
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            fc.showSaveDialog(null);
            File file =  fc.getSelectedFile();
            try {
                FileOutputStream writer = new FileOutputStream(file);
                writer.write(output.getBytes());
                writer.close();
                tabs.setTitleAt(tabs.getSelectedIndex(), file.getName());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateStatus(int line, int column){
        status.setText("Строка: " + line + " Столбец: " + column);
    }

}
