package ru.gerasimov.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChangeFontAction implements ActionListener {

    private JTabbedPane tabs;

    public ChangeFontAction(JTabbedPane tabs){
        this.tabs = tabs;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JComboBox box = (JComboBox)e.getSource();
        String fontName = (String)box.getSelectedItem();

        Scroll scroll = (Scroll)tabs.getSelectedComponent();
        Font oldFont = scroll.getTextArea().getFont();
        Font newFont = new Font(fontName, oldFont.getStyle(), oldFont.getSize());
        scroll.getTextArea().setFont(newFont);
    }
}
