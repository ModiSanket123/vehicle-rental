package com.navi.assignment;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Objects;

public class Main {
    public static void main(String[] args) throws Exception {
        if(args.length < 2 || !Objects.equals(args[0], "-f")){
            throw new Exception("Invalid parameters, please pass the file-path as: -f ${FILE_PATH}");
        }

        try(BufferedReader reader = new BufferedReader(new FileReader(args[1]))){
            InputProcessor inputProcessor = new InputProcessor();
            inputProcessor.process(reader);
        }
    }

}