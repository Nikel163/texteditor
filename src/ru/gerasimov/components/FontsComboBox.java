package ru.gerasimov.components;

import javax.swing.*;
import java.awt.*;

public class FontsComboBox extends JComboBox {
    private static final String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
    private static int width = 40;
    private static int height = 16;

    public FontsComboBox(){
        super(fonts);
        setSize(width, height);
        setEditable(false);
    }

}
