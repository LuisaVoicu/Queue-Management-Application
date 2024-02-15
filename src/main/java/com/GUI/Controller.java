package com.GUI;

import com.business.logic.SelectionPolicy;
import com.business.logic.SimulationManager;
import com.model.Operation;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller {
    private View view;
    private SimulationManager model;
    private static final String W_I = "WrongInput";

    public Controller(View view, SimulationManager model){
        this.view = view;
        this.model = model;
        view.addSubmitButtonListener(new SubmitButtonListener());
    }
    public void getTextFromTextField(){
        int result = Operation.manipulateInputFromUI(view.getTextFields()[0].getText());
        if( result != -1){
            model.setMaxSimulation(result);
        }
        else{
            view.getTextFields()[0].setText(W_I);
        }
        result = Operation.manipulateInputFromUI(view.getTextFields()[1].getText());
        if( result != -1){
            model.setNbOfClients(result);
        }
        else{
            view.getTextFields()[1].setText(W_I);
        }
        result = Operation.manipulateInputFromUI(view.getTextFields()[2].getText());
        if( result != -1){
            model.setMaxNoServers(result);
            //view.setNbQueues(result);

        }
        else{
            view.getTextFields()[2].setText(W_I);
        }
        result = Operation.manipulateInputFromUI(view.getTextFields()[3].getText());
        if( result != -1){
            model.setMaxTasksPerServer(result);
        }
        else{
            view.getTextFields()[3].setText(W_I);
        }
        result = Operation.manipulateInputFromUI(view.getTextFields()[4].getText());
        if( result != -1){
            model.setMaxArrival(result);
        }
        else{
            view.getTextFields()[4].setText(W_I);
        }
        int result2 = Operation.manipulateInputFromUI(view.getTextFields()[5].getText());
        if( result2 != -1 && result2 < result){
            model.setMinArrival(result2);
        }
        else{
            view.getTextFields()[5].setText(W_I);
        }
        result = Operation.manipulateInputFromUI(view.getTextFields()[6].getText());
        if(result != -1){
            model.setMaxService(result);
        }
        else{
            view.getTextFields()[6].setText(W_I);
        }
        result2 = Operation.manipulateInputFromUI(view.getTextFields()[7].getText());
        if( result2 != -1 && result2 < result){
            model.setMinService(result2);
        }
        else{
            view.getTextFields()[7].setText(W_I);
        }
    }


    class SubmitButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("--->[i]:  am apasat pe submit");
            getTextFromTextField();
            model.initScheduler();
            model.stopThread();
            view.removeAllItems();
            model.setQueueOutput("");
            view.setNbQueues(model.getMaxNoServers());
            view.setNbClients(model.getNbOfClients());
            view.generateWaitingListIcons();
            view.putIcons();
            model.startThread();
        }
    }
    class ComboBoxListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String s = (String) view.getComboStrategy().getSelectedItem();


            if(s.compareTo("Shortest Queue")==0){
                try {
                    model.setSelectionPolicy(SelectionPolicy.SHORTEST_QUEUE);
                }catch(Exception ex){
                }
            }
            else if(s.compareTo("Shortest Time")==0){
                try {
                    model.setSelectionPolicy(SelectionPolicy.SHORTEST_TIME);
                }catch(Exception ex){
                }
            }
        }
    }

}
