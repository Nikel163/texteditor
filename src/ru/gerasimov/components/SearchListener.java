package ru.gerasimov.components;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SearchListener implements ActionListener {
    private JTabbedPane tabs;

    public SearchListener(JTabbedPane tabs){
        this.tabs = tabs;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        if(action.equals("Поиск")){
            search();
        }
    }

    public void search(){
        Scroll scroll = (Scroll)tabs.getSelectedComponent();
        JTextArea textArea = scroll.getTextArea();
        String allText = textArea.getText();

        String searchingWord = JOptionPane.showInputDialog(null, "Поиск");
        if(searchingWord.isEmpty()){
            return;
        }

        int index = allText.indexOf(searchingWord);
        if(index == -1){
            JOptionPane.showMessageDialog(null, "Не найдено!", "Результат", JOptionPane.INFORMATION_MESSAGE);
        } else {
            textArea.requestFocus();
            textArea.select(index, index+searchingWord.length());
        }
    }
}
