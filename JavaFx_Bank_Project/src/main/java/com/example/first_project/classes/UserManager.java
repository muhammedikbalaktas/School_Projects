package com.example._150719652_first_project.classes;

import java.io.*;
import java.util.ArrayList;

public class UserManager {

    public void createUser(User user){
        FileIO fileIO=new FileIO();
        fileIO.appendFile(user.getName(),"usernames.txt");
        fileIO.appendFile(user.getSurname(),"usersurnames.txt");
        fileIO.appendFile(user.getPassword(),"userpasswords.txt");
        fileIO.appendFile(user.getEmail(),"usermails.txt");
        fileIO.appendFile(user.getGender(),"usergenders.txt");
        fileIO.appendFile("0.0","userbalances.txt");

    }
    public boolean checkIfUserExist(String mail){

        try {
            BufferedReader bufferedReader=new BufferedReader(new FileReader("src/data/usermails.txt"));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (mail.equals(line)) return true;
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
    public int getUserIndex(String mail){

        try (BufferedReader reader = new BufferedReader(new FileReader("src/data/usermails.txt"))) {

            String line;
            int index = 0;

            while ((line = reader.readLine()) != null) {

                if (line.equals(mail)) {
                    return index;
                }
                index++;
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return -1;

    }

    public  double getBalance(int index){
        try {


            BufferedReader bufferedReader=new BufferedReader(new FileReader("src/data/userbalances.txt"));
            String line;

            int i=0;
            do {
                line=bufferedReader.readLine();
                i++;
            }while (i<=index);
            return Double.parseDouble(line);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public String getCurrentUserMail(){
        try {
            BufferedReader bufferedReader=new BufferedReader(new FileReader("src/data/currentuser.txt"));
            String result=bufferedReader.readLine();
            return result;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public int checkUserMailPassword(String mail,String password){
        int index=this.getUserIndex(mail);
        if (index==-1) return -1;
        else {
            String pass=this.getUserPassword(index);
            if (password.equals(pass)) return 1;

        }


        return -1;
    }
    public String getUserPassword(int index){
        try {

            BufferedReader bufferedReader=new BufferedReader(new FileReader("src/data/userpasswords.txt"));
            String line;
            int i=0;
            while ((line = bufferedReader.readLine()) != null) {
                if(i==index) return line;
                i++;
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return null;
    }
    public void depositMoney(double amount,int index){
        ArrayList<String> balances=new ArrayList<>();
        try {
            BufferedReader bufferedReader=new BufferedReader(new FileReader("src/data/userbalances.txt"));
            String line;
            while ((line = bufferedReader.readLine()) != null){
                balances.add(line);
            }
            bufferedReader.close();
            double base=Double.parseDouble(balances.get(index));
            base+=amount;
            balances.set(index, String.valueOf(base));
            updateUserBalance(balances);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public int withdrawMoney(double amount,int index){
        ArrayList<String> balances=new ArrayList<>();
        try {
            BufferedReader bufferedReader=new BufferedReader(new FileReader("src/data/userbalances.txt"));
            String line;
            while ((line = bufferedReader.readLine()) != null){
                balances.add(line);
            }
            bufferedReader.close();
            double base=Double.parseDouble(balances.get(index));
            base-=amount;
            if (base<0){
                return -1;
            }
            else {
                balances.set(index, String.valueOf(base));
                updateUserBalance(balances);
                return 1;
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public int sendMoney(int from, int to, double amount){
        ArrayList<String> balances=new ArrayList<>();
        try {
            BufferedReader bufferedReader=new BufferedReader(new FileReader("src/data/userbalances.txt"));
            String line;
            while ((line = bufferedReader.readLine()) != null){
                balances.add(line);
            }
            bufferedReader.close();
            double base=Double.parseDouble(balances.get(from));
            double reciever=Double.parseDouble(balances.get(to));
            base-=amount;
            if (base<0){
                return -1;
            }
            else {
                reciever+=amount;
                balances.set(from, String.valueOf(base));
                balances.set(to,String.valueOf(reciever));
                updateUserBalance(balances);
                return 1;
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    private void updateUserBalance(ArrayList<String> data){
        try {
            BufferedWriter bufferedWriter=new BufferedWriter(new FileWriter("src/data/userbalances.txt"));
            for (String s:data) {
                bufferedWriter.write(s);
                bufferedWriter.write(System.lineSeparator());

            }
            bufferedWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
