package ru.gerasimov.components;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TextEditor extends JFrame {

    private static final String NEW_TAB = "Безымянный";
    private JFileChooser fc = new JFileChooser();
    private JTabbedPane tabs = new JTabbedPane();

    public TextEditor() {
        setTitle("EXTREME TEXT EDITOR");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        setVisible(true);


        //FILTER FOR TXT FILES
        fc.addChoosableFileFilter(new FileNameExtensionFilter(".txt", "txt"));

        //MENU AND MENU ITEMS
        add(tabs);
        Box box = Box.createHorizontalBox();
        JMenuBar menuBar = new JMenuBar();
        menuBar.setLayout(null);
        menuBar.setSize(800,20);
//        add(box);
        setJMenuBar(menuBar);
        JMenu file = new JMenu("Файл");
        menuBar.add(file);

        //ADDING ACTIONS TO FILE MENU ITEMS
        file.add(Create);
        file.add(Open);
        file.add(Save);
        file.addSeparator();
        JButton button = new ImageButton("src/res/color.png");
        button.setPreferredSize(new Dimension(16,16));
        menuBar.add(button);

//        button.setIcon(new ImageIcon("src/res/color.png"));
//        box.add(button);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color color = JColorChooser.showDialog(null, "Color", Color.black);
                Scroll scroll = (Scroll)tabs.getSelectedComponent();
                scroll.getTextArea().setForeground(color);
            }
        });

    }

    //ACTIONS START HERE
    Action Create = new AbstractAction("Создать файл") {
        @Override
        public void actionPerformed(ActionEvent e) {
            JEditorPane text = new JEditorPane();
            text.setFont(new Font("Ubuntu Mono", Font.BOLD, 25));
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
            JEditorPane text = new JEditorPane(input);
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
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
