package com.navi.assignment;

import java.io.BufferedReader;
import java.io.StringReader;

public class Main {
    public static void main(String[] args) throws Exception {
        String input ="ADD_BRANCH B1 CAR,BIKE,VAN\n" +
                "ADD_VEHICLE B1 CAR V1 500\n" +
                "ADD_VEHICLE B1 CAR V2 1000\n" +
                "ADD_VEHICLE B1 BIKE V3 250\n" +
                "ADD_VEHICLE B1 BIKE V4 300\n" +
                "ADD_VEHICLE B1 BUS V5 2500\n" +
                "BOOK B1 VAN 1 5\n" +
                "BOOK B1 CAR 1 3\n" +
                "BOOK B1 BIKE 2 3\n" +
                "BOOK B1 BIKE 2 5\n" +
                "DISPLAY_VEHICLES B1 1 5";

        try(BufferedReader reader = new BufferedReader(new StringReader(input))){
            InputProcessor inputProcessor = new InputProcessor();
            inputProcessor.process(reader);
        }
    }

}