package com.GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Background extends JPanel {
    private ArrayList<Image> icons;
    private ImageIcon background;
    private ImageIcon shop;
    private int tab;
    public Background(int tab){
        this.tab = tab;
        if(tab==2) {
            background = new ImageIcon("src/main/java/com/image/path.jpg");
            shop = new ImageIcon("src/main/java/com/image/shops.png");
            icons = new ArrayList<Image>();
        }
        else{
            background = new ImageIcon("src/main/java/com/image/tab1background.jpg");

        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        if(tab==2) {
            int index = 1;
            g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), this);
            int h = background.getImage().getHeight(null) - shop.getImage().getHeight(null) - 30;
            g.drawImage(shop.getImage(), 10, 50, shop.getImage().getWidth(null), shop.getImage().getHeight(null), this);
            for (Image i : icons) {
                index++;
                g.drawImage(i, 50 * index + 30, 100, i.getWidth(null), i.getHeight(null), this);
            }
            try {
                BufferedImage store = ImageIO.read(new File("src/main/java/com/image/shops.png"));
                // g.drawImage(store,10,10,null);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else if(tab==1){
            g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), this);

        }
    }

    public void addIcon(Image image){
        icons.add(image);
        repaint();
    }
    public void removeIcon(){
        if(icons.isEmpty()==false) {
            icons.remove(0);
            repaint();
        }
    }
}
