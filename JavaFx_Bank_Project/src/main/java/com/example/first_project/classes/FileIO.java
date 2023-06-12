package com.example._150719652_first_project.classes;

import java.io.*;

public class FileIO {

    public void updateCurrentUser(String mail){
        try {

            BufferedWriter bufferedWriter=new BufferedWriter(new FileWriter("src/data/currentuser.txt"));
            bufferedWriter.write(mail);
            bufferedWriter.close();
            System.out.println(mail);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }
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

}
