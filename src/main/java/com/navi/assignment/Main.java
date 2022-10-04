package com.navi.assignment;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.URL;
import java.util.Objects;

public class Main {
    public static void main(String[] args) throws Exception {
        URL resource = Main.class.getClassLoader().getResource("test.txt");
        assert resource!=null;
        String filePath = resource.getFile();
        if(args.length > 1 && Objects.equals(args[0], "-f")){
            filePath = args[1];
        }

        try(BufferedReader reader = new BufferedReader(new FileReader(filePath))){
            InputProcessor inputProcessor = new InputProcessor();
            inputProcessor.process(reader);
        }
    }

}