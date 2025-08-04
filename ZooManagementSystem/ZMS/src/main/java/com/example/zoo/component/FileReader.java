package com.example.zoo.component;

import java.io.File;
import java.io.FileInputStream;

public class FileReader{
    public static void main(String[] args) {
        File f = new File("final/src/test.txt");
        try (FileInputStream fis = new FileInputStream(f)) {
            int c;
            while ((c = fis.read()) != -1) {
                System.out.print((char) c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
