package com.example.rospivpatent;

import java.util.ArrayList;

public class Single {
    private static Single INSTANCE = null;
    private Single(){}

    ArrayList<SearchClass> list = new ArrayList<>();
    SearchClass thisElement;
    Integer count = 10;

    public static Single getInstance(){
        if (INSTANCE == null){
            INSTANCE = new Single();
        }
        return INSTANCE;
    }
}
