package com.example.zoo.component;

import java.io.FileWriter;
import java.io.IOException;
//import com.example.servingwebcontent.File;
import java.util.ArrayList;

import com.example.zoo.model.DongVat;

public class WriteToFile {
    public void ToFile(ArrayList<DongVat> u) {
        try {
            
            int i = u.size() - 1;
            FileWriter writer = new FileWriter("./complete/File/Login.txt", true);
            writer.append("\n");

            writer.write(u.get(i).getId());
            writer.write(u.get(i).getTen());

            writer.close();
        } catch (IOException e) {
            System.out.println("Error at write to File!");
            e.printStackTrace();
        }

    }
}