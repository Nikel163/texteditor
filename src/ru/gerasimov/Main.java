package ru.gerasimov;

import ru.gerasimov.components.TextEditor;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TextEditor();
            }
        });

    }
}
