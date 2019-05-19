package ru.gerasimov.components;

import javax.swing.*;

public class FontsComboBox extends JComboBox {
    private static final String[] fonts = {"Ubuntu", "Liberation Serif", "Lato Black", "Times New Roman"};
    private static int width = 40;
    private static int height = 16;

    public FontsComboBox(){
        super(fonts);
        setSize(width, height);
        setEditable(true);
    }

}
