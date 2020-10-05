package com.cambio.cds.domain;

import java.util.ArrayList;
import java.util.List;

public class TestJava {
    public static void main(String[] args) {
        List<String> a = new ArrayList<>() ;
        a.add("1");a.add("2");a.add("3");

        String[] myArray = a.toArray(new String[0]);

        System.out.println(myArray[0]);
        System.out.println(myArray[1]);
        System.out.println(myArray[2]);


    }
}
