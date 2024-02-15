package com.GUI;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.Random;

public class View extends JFrame{
    private static final int TEXT_SIZE = 10;
    private static int arraySize = 8;
    private JTabbedPane panelTabs;
    private Background panelTab1;
    private JPanel insideTab1;
    private JPanel panelTab2;
    private JPanel rightPanel;
    private JPanel leftPanel;
    private JPanel auxPanel;
    private JPanel [] inputPanels;
    private TaskListPanel waitingListItems;
    private JTextField [] textFields;
    private JLabel [] labels;
    private JComboBox<String> comboStrategy;
    private String [] defaultMessagesForTextFields;
    private String [] defaultMessagesForLabels;
    private JButton submitButton;
    private JTextArea queueInfo;
    private Background[] panelItems;
    private ArrayList<ImageIcon> icons;
    private ArrayList<ImageIcon> waitingListIcons;
    private int nbQueues=0;
    private int nbIcons = 9;
    private int nbClients = 0;

    public View(){
        initElements();
        setLayouts();
        setIcons();
        createTabs();
        this.setContentPane(panelTabs);
        this.setPreferredSize(new Dimension(1200,700));
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Queue Simulator");
        this.setVisible(true);
    }

    public void initElements(){
        panelTabs = new JTabbedPane();
        panelTab1 = new Background(1);
        insideTab1 = new JPanel();
        panelTab2 = new JPanel();
        rightPanel = new JPanel();
        waitingListItems = new TaskListPanel();
        leftPanel = new JPanel();
        textFields = new JTextField[arraySize];
        labels = new JLabel[arraySize+1];
        inputPanels = new JPanel[arraySize+1];
        queueInfo = new JTextArea(45, 20);
        defaultMessagesForTextFields = new String[arraySize];
        defaultMessagesForLabels = new String[arraySize];
        setDefaultTextForTextFields();
        for(int i = 0; i < textFields.length; i++){
            labels[i]=new JLabel();
            inputPanels[i] = new JPanel();
            textFields[i] = new JTextField();
            textFields[i].setMaximumSize(new Dimension(500,40));
        }
        auxPanel = new JPanel();
        String[] choices={"Shortest Queue","Shortest Time"};
        comboStrategy = new JComboBox<String>(choices);
        comboStrategy.setPreferredSize(new Dimension(200,20));
        submitButton = new JButton("Submit");
        for(int i = 0 ; i < textFields.length; i++) {
            textFieldFocusListener(textFields[i], defaultMessagesForTextFields[i]);
            labels[i].setText(defaultMessagesForLabels[i]);
        }
        //labels[textFields.length].setText("Strategy");
        panelItems = new Background[nbQueues];
        icons = new ArrayList<ImageIcon>();
        waitingListIcons = new ArrayList<ImageIcon>();
    }

    private void setLayouts(){
        panelTab1.setLayout(new GridBagLayout());
        insideTab1.setLayout(new BoxLayout(insideTab1, BoxLayout.Y_AXIS));
        panelTab2.setLayout(new BoxLayout(panelTab2, BoxLayout.Y_AXIS));
        rightPanel.setLayout(new BoxLayout(rightPanel,BoxLayout.Y_AXIS));
        for(int i = 0 ; i < nbQueues; i++){
            panelItems[i].setLayout(new FlowLayout());
            inputPanels[i].setLayout(new FlowLayout());
        }
        auxPanel.setLayout(new FlowLayout());
        insideTab1.setPreferredSize(new Dimension(500,500));
        panelTab1.add(insideTab1);
    }
    private void setIcons(){
        icons.add(new ImageIcon("src/main/java/com/image/cat.png"));
        icons.add(new ImageIcon("src/main/java/com/image/dog.png"));
        icons.add(new ImageIcon("src/main/java/com/image/penguin.png"));
        icons.add(new ImageIcon("src/main/java/com/image/duck.png"));
        icons.add(new ImageIcon("src/main/java/com/image/hen.png"));
        icons.add(new ImageIcon("src/main/java/com/image/fox.png"));
        icons.add(new ImageIcon("src/main/java/com/image/turtle.png"));
        icons.add(new ImageIcon("src/main/java/com/image/rabbit.png"));
        icons.add(new ImageIcon("src/main/java/com/image/pig.png"));

    }
    private void setDefaultTextForTextFields(){
        defaultMessagesForTextFields[0] = new String("Add Maximum Simulation Time");
        defaultMessagesForTextFields[1] = new String("Add Number Of Clients");
        defaultMessagesForTextFields[2] = new String("Add Number Of Queues");
        defaultMessagesForTextFields[3] = new String("Add Maximum Tasks Per Server");
        defaultMessagesForTextFields[4] = new String("Add Maximum Arival");
        defaultMessagesForTextFields[5] = new String("Add Minimum Arrival");
        defaultMessagesForTextFields[6] = new String("Add Maximum Service");
        defaultMessagesForTextFields[7] = new String("Add Minimum Service");

        defaultMessagesForLabels[0] = new String("Simulation Time");
        defaultMessagesForLabels[1] = new String("Number Clients ");
        defaultMessagesForLabels[2] = new String("Number Queues  ");
        defaultMessagesForLabels[3] = new String("Tasks / Service");
        defaultMessagesForLabels[4] = new String("Maximum Arrival");
        defaultMessagesForLabels[5] = new String("Minimum Arrival");
        defaultMessagesForLabels[6] = new String("Maximum Service");
        defaultMessagesForLabels[7] = new String("Minimum Service");
    }
    private void textFieldFocusListener(JTextField t, String outOfFocus){
        t.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                t.setText("");
                t.setForeground(Color.BLACK);
                t.setFont(new Font("Open Sans",Font.BOLD,TEXT_SIZE));            }

            @Override
            public void focusLost(FocusEvent e) {
                if(t.getText().compareTo("")==0) {
                    t.setText(outOfFocus);
                    t.setForeground(Color.GRAY);
                    t.setFont(new Font("Open Sans", Font.ITALIC, TEXT_SIZE));
                }
            }
        });
        if(t.getText().compareTo("")==0) {
            t.setText(outOfFocus);
            t.setForeground(Color.GRAY);
            t.setFont(new Font("Open Sans", Font.ITALIC, TEXT_SIZE));
        }
    }
    private void createTabs(){
        panelTabs.addTab("Tab1",panelTab1);
        panelTabs.addTab("Tab2",panelTab2);
        tab1();
        tab2();
    }
    private void tab1(){
        for(int i = 0 ; i < textFields.length; i++){
            textFields[i].setPreferredSize(new Dimension(300,30));
            inputPanels[i].add(labels[i]);
            inputPanels[i].add(textFields[i]);
            inputPanels[i].setBackground(new Color(250,218,221));
            insideTab1.add(inputPanels[i]);
        }
        auxPanel.setBackground(new Color(250,218,221));
       // inputPanels[textFields.length].add(labels[0]);
       // inputPanels[textFields.length].add(comboStrategy);
        JLabel aux = new JLabel("Strategy");
        aux.setText("Strategy");
        auxPanel.add(aux);
        auxPanel.add(comboStrategy);
        insideTab1.add(auxPanel);
        insideTab1.add(submitButton);
        Border margin =  BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        insideTab1.setBackground(new Color(250,218,221));
        insideTab1.setBorder(margin);
        panelTab1.add(insideTab1);

    }
    private void tab2(){
        leftPanel.setBorder(new TitledBorder(new BevelBorder(BevelBorder.RAISED), "Display Area"));
        queueInfo.setEditable(false);
        JScrollPane scroll = new JScrollPane(queueInfo);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        leftPanel.add(scroll);
        JSplitPane splitted = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,leftPanel,rightPanel);
        JSplitPane rightSplitted = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,splitted,waitingListItems);
        //waitingListItems.setPreferredSize(new Dimension(100,1000));
        JPanel panel1 = (JPanel) splitted.getComponent(1); // obtineti referinta la primul panel
        panel1.setPreferredSize(new Dimension(300, 200));
        panelTab2.add(rightSplitted);
    }
    public void removeAllItems(){
       for(int i = 0 ; i < nbQueues; i++){
           //todo fa-l sa stearge tot din storeIconItem
          // panelItems[i].remove(storeIconItem.get(i));
            rightPanel.remove(panelItems[i]);
        }
       while(waitingListIcons.isEmpty()==false){
           waitingListItems.removeIcon(0);
           waitingListIcons.remove(0);
       }
    }
    public void putIcons(){
        panelItems = new Background[nbQueues];
        for(int i = 0 ; i < nbQueues; i++){
            panelItems[i] = new Background(2);
        }
        //panelItems[0].addImage(store.getImage());

        for(int i = 0 ; i < nbQueues; i++){
            //panelItems[i].add(storeIconItem.get(i));
            rightPanel.add(panelItems[i]);
        }
        for(int i = 0 ; i < nbClients; i++){
            waitingListItems.addIcon(waitingListIcons.get(i).getImage());
        }
    }
    public void generateWaitingListIcons(){
        Random random;
        for(int i = 0 ; i < nbClients; i++){
            random = new Random();
            int randomIndex = random.nextInt(0,nbIcons);
            waitingListIcons.add(icons.get(randomIndex));
        }

        JScrollPane scroll = new JScrollPane(queueInfo);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        leftPanel.add(scroll);
    }
    public void addIcon(int index){
        int i = 0;
        panelItems[index].addIcon(waitingListIcons.get(i).getImage());
        waitingListIcons.remove(i);
        waitingListItems.removeIcon(i);
    }
    public void removeIcon(int index){
        panelItems[index].removeIcon();
    }
    public void setAreaText(String s){
        queueInfo.setText(s);
    }
    public JTextField[] getTextFields() {
        return textFields;
    }
    public void addSubmitButtonListener(ActionListener listener){
        submitButton.addActionListener(listener);
    }
    public void setNbQueues(int nbQueues) {
        this.nbQueues = nbQueues;
    }
    public void setNbClients(int nbClients) {
        this.nbClients = nbClients;
    }

    public JComboBox<String> getComboStrategy() {
        return comboStrategy;
    }
}
