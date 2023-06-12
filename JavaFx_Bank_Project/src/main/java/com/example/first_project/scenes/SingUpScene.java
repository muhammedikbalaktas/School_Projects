package com.example._150719652_first_project.scenes;

import com.example._150719652_first_project.MainScene;
import com.example._150719652_first_project.classes.FileIO;
import com.example._150719652_first_project.classes.User;
import com.example._150719652_first_project.classes.UserManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class SingUpScene implements SceneManager{

    @Override
    public Scene startScene(Stage mainStage) {
        mainStage.setTitle("Welcome to Mbank!");
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setAlignment(Pos.CENTER);

        Label label1 = new Label("Name:");
        TextField inputName=new TextField();
        gridPane.add(label1, 0, 0);
        gridPane.add(inputName, 1, 0);
        Label label2 = new Label("Surname:");
        TextField inputSurname=new TextField();
        gridPane.add(label2, 0, 1);
        gridPane.add(inputSurname, 1, 1);


        Label label4=new Label("email");
        TextField inputEmail=new TextField();
        gridPane.add(label4,0,2);
        gridPane.add(inputEmail,1,2);

        Label label3 = new Label("Password:");
        PasswordField inputPassword=new PasswordField();
        gridPane.add(label3, 0, 3);
        gridPane.add(inputPassword, 1, 3);

        ToggleGroup toggleGroup = new ToggleGroup();
        RadioButton radioButton1 = new RadioButton("Male");
        radioButton1.setToggleGroup(toggleGroup);

        RadioButton radioButton2 = new RadioButton("Female");
        radioButton2.setToggleGroup(toggleGroup);
        gridPane.add(radioButton1,0,4);
        gridPane.add(radioButton2,1,4);
        Button btnSignUp=new Button("Submit");
        Button btnExit=new Button("Exit");
        gridPane.add(btnSignUp,0,5);
        gridPane.add(btnExit,1,5);
        Label message=new Label("You should fill all!");
        gridPane.add(message,0,6);
        btnExit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                MainScene mainScene=new MainScene();
                try {
                    mainScene.start(mainStage);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        String gender;
        btnSignUp.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String name=inputName.getText();
                String surname=inputSurname.getText();
                String email=inputEmail.getText();
                String password=inputPassword.getText();
                if (toggleGroup.getSelectedToggle() != null) {
                    RadioButton selectedRadioButton = (RadioButton) toggleGroup.getSelectedToggle();
                    String gender = selectedRadioButton.getText();
                    if (name.isBlank()||surname.isBlank()||email.isBlank()||password.isBlank()){
                        System.out.println("please fill all necessary input");
                    }
                    else {
                        User user=new User(name,surname,email,password,gender);
                        UserManager userManager=new UserManager();
                        if (userManager.checkIfUserExist(email)){
                            System.out.println("user exist!");

                        }
                        else {
                            FileIO fileIO=new FileIO();
                            fileIO.updateCurrentUser(email);
                            userManager.createUser(user);
                            System.out.println("user created");
                            UserMainScene userMainScene=new UserMainScene();
                            Scene scene=userMainScene.startScene(mainStage);


                            mainStage.setScene(scene);
                            mainStage.show();

                        }
                    }

                } else {
                    System.out.println("No gender selected.");
                }


            }
        });

        return new Scene(gridPane, 500, 500);
    }

}
