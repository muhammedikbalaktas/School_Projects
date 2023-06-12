package com.example._150719652_first_project;

import com.example._150719652_first_project.classes.FileIO;
import com.example._150719652_first_project.classes.UserManager;
import com.example._150719652_first_project.scenes.SingUpScene;
import com.example._150719652_first_project.scenes.UserMainScene;
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
        Label label=new Label("Don't have account?");
        label.setFont(new Font(20));


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
                    UserManager userManager=new UserManager();
                    if(userManager.checkUserMailPassword(email,password)==-1){
                        System.out.println("email or password is wrong or user does not exist");
                    }
                    else {
                        FileIO fileIO=new FileIO();
                        fileIO.updateCurrentUser(email);
                        UserMainScene userMainScene=new UserMainScene();
                        Scene scene=userMainScene.startScene(primaryStage);
                        primaryStage.setScene(scene);
                        primaryStage.show();
                    }
                }
            }
        });

        btnSignIn.setAlignment(Pos.CENTER);
        //btnSignIn.setPadding(new Insets(20));

        //*********************************
        Button btnSignUp=new Button("Sign Up");
        btnSignIn.setAlignment(Pos.CENTER);
        btnSignUp.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                signUpPage();
            }
        });

        //**************************************
        VBox mainColumn=new VBox();
        mainColumn.setAlignment(Pos.CENTER);
        mainColumn.getChildren().addAll(row1,row2,btnSignIn,label,btnSignUp);
        Scene scene = new Scene(mainColumn, 500, 500);
        VBox.setMargin(label,new Insets(50, 0, 0, 0));
        primaryStage.setTitle("Welcome to Mbank!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public  void signInPage(){
        UserMainScene scene=new UserMainScene();

        primaryStage.setTitle("Welcome to Mbank!");
        primaryStage.setScene(scene.startScene(primaryStage));
        primaryStage.show();
    }
    public  void signUpPage(){
        SingUpScene scene=new SingUpScene();
        primaryStage.setTitle("deneme");
        primaryStage.setScene(scene.startScene(primaryStage));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}