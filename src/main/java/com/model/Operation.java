package com.model;

public class Operation {
    public static int manipulateInputFromUI(String s){
        String regex = "\\d+";
        if(s.matches(regex)==false) {
            return -1;
        }
        else{
            int nb = Integer.parseInt(s);
            return nb;
        }
    }
}
