package ru.gerasimov.components;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class ChangeFontSizeListener implements ChangeListener {
    Scroll scroll;
    public ChangeFontSizeListener(Scroll scroll){
        this.scroll = scroll;
    }
    @Override
    public void stateChanged(ChangeEvent e) {
        JSpinner spinner = (JSpinner)e.getSource();
        Font oldFont = scroll.getTextArea().getFont();
        int size = (int)spinner.getValue();
        Font newFont = new Font(oldFont.getName(), oldFont.getStyle(), size);
        scroll.getTextArea().setFont(newFont);
    }
}
