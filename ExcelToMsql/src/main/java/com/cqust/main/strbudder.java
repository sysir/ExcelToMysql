package com.cqust.main;

import java.util.ArrayList;

public class strbudder {
    public static void main(String[] args) {
        ArrayList<String> objects = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        int kk=0;
        StringBuilder append = new StringBuilder();
        boolean flag=true;
        for (int i=0;i<10;i++){
            objects.add(String.valueOf(i));
            append = stringBuilder.append(objects.get(i)).append("!!!");
         if (flag) {
             kk = append.length();
             flag=false;
         }

        }
        //System.out.println(append.length());
        //System.out.println(objects);

        System.out.println(kk);
        System.out.println(append);
    }
}
