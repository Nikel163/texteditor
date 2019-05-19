package ru.gerasimov.components;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PopupListener extends MouseAdapter {

    JPopupMenu popup;

    public PopupListener(JPopupMenu popup){
        this.popup = popup;
    }


    public void mousePressed(MouseEvent e){
        showPopup(e);
    }

    public void mouseReleased(MouseEvent e){
        showPopup(e);
    }

    private void showPopup(MouseEvent e){
        if(e.isPopupTrigger()){
            popup.show(e.getComponent(), e.getX(), e.getY());
        }
    }
}
