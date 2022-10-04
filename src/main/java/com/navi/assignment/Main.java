package com.navi.assignment;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Objects;

public class Main {
    public static void main(String[] args) throws Exception {
        String filePath = "src/main/resources/test.txt";
        if(args.length > 1 && Objects.equals(args[0], "-f")){
            filePath = args[1];
        }

        try(BufferedReader reader = new BufferedReader(new FileReader(filePath))){
            InputProcessor inputProcessor = new InputProcessor();
            inputProcessor.process(reader);
        }
    }

}