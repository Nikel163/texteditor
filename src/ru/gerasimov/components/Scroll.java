package ru.gerasimov.components;

import javax.swing.*;

public class Scroll extends JScrollPane {

    private final JTextArea pane;
    private final String path;
    private final boolean isOpened;

    public Scroll(JTextArea pane, String path, boolean isOpened){
        super(pane);
        this.pane = pane;
        this.path = path;
        this.isOpened = isOpened;
    }

    public JTextArea getTextArea(){
        return pane;
    }

    public boolean isOpened(){
        return this.isOpened;
    }

    public String getPath(){
        return path;
    }
}
