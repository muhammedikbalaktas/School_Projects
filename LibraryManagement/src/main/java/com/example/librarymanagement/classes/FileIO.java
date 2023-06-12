package com.example.librarymanagement.classes;

import java.io.*;
import java.util.ArrayList;

public class FileIO {


    public void appendFile(String data,String fileName){
        String path="src/data/"+fileName;
        try {
            BufferedWriter bufferedWriter=new BufferedWriter(new FileWriter(path,true));
            bufferedWriter.write(data);
            bufferedWriter.write(System.lineSeparator());
            bufferedWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public ArrayList<String> getData(String fileName){
        String path="src/data/"+fileName;
        ArrayList <String> result=new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                result.add(line);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
    public void writeToFile(ArrayList<String> data,String fileName){
        String path="src/data/"+fileName;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            for (String item : data) {
                writer.write(item);
                writer.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}