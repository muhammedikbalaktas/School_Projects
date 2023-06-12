package com.example.librarymanagement;


import com.example.librarymanagement.scenes.HomePage;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class MainScene extends Application {
    private Stage primaryStage;
    @Override
    public void start(Stage stage) throws IOException {
        this.primaryStage=stage;
        mainPage();
    }
    public  void mainPage(){
        HBox row1=new HBox();
        HBox row2=new HBox();

        TextField textFieldEmail=new TextField();
        textFieldEmail.setPromptText("Email");

        textFieldEmail.setFont(new Font(20));
        row1.getChildren().addAll(textFieldEmail);
        row1.setAlignment(Pos.CENTER);
        row1.setPadding(new Insets(20));
        //*********************************
        PasswordField passwordField=new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setFont(new Font(20));
        row2.getChildren().addAll(passwordField);
        row2.setAlignment(Pos.CENTER);
        row2.setPadding(new Insets(20));
        //*********************************




        Button btnSignIn=new Button("Sign In");
        btnSignIn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                String email=textFieldEmail.getText();
                String password=passwordField.getText();

                if (password.isBlank()||email.isBlank()){
                    System.out.println("please provide email and password");
                }
                else {
                    if (email.equals("admin")&&password.equals("123456")){
                        HomePage scene=new HomePage();

                        primaryStage.setTitle("Welcome to MLibrary!");
                        primaryStage.setScene(scene.startScene(primaryStage));
                        primaryStage.show();
                    }
                }

            }
        });

        btnSignIn.setAlignment(Pos.CENTER);
        //btnSignIn.setPadding(new Insets(20));

        //*********************************


        //**************************************
        VBox mainColumn=new VBox();
        mainColumn.setAlignment(Pos.CENTER);
        mainColumn.getChildren().addAll(row1,row2,btnSignIn);
        Scene scene = new Scene(mainColumn, 500, 500);
        primaryStage.setTitle("Welcome to MLibrary!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }



    public static void main(String[] args) {
        launch();
    }

}