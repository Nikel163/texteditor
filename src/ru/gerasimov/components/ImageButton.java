package ru.gerasimov.components;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageButton extends JButton {
    private BufferedImage image;

    public ImageButton(String fileName){
        super();
        try {
            image = ImageIO.read(new File(fileName));
        } catch (IOException e){
            e.printStackTrace();
        }
        setSize(16,16);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image,0,0,null);
    }
}
