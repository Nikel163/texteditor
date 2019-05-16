package ru.gerasimov.components;

import javax.swing.*;
import java.awt.*;

public class Scroll extends JScrollPane {

    private final JEditorPane pane;
    private final String path;
    private final boolean isOpened;

    public Scroll(JEditorPane pane, String path, boolean isOpened){
        super(pane);
        this.pane = pane;
        this.path = path;
        this.isOpened = isOpened;
    }

    public JEditorPane getTextArea(){
        return pane;
    }

    public boolean isOpened(){
        return this.isOpened;
    }

    public String getPath(){
        return path;
    }
}
