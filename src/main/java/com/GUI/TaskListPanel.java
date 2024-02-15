package com.GUI;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class TaskListPanel extends JPanel {
    private ImageIcon background;
    private ArrayList<Image> waitingList;
    public TaskListPanel() {
        waitingList = new ArrayList<Image>();
        background =  new ImageIcon("src/main/java/com/image/grass.jpg");
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background.getImage(),0,0,background.getImage().getWidth(null),background.getImage().getHeight(null),this);

        int index = 1;
        for(Image i : waitingList){
            g.drawImage(i,10,index*64,i.getWidth(null),i.getHeight(null),this);
            index++;
        }
    }

    public void addIcon(Image image){
        waitingList.add(image);
        repaint();
    }
    public void removeIcon(int index){
        if(waitingList!=null)
        waitingList.remove(index);
        repaint();
    }
}
