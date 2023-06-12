package com.example._150719652_first_project.scenes;

import com.example._150719652_first_project.classes.User;
import com.example._150719652_first_project.classes.UserManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class SendMoneyScene implements SceneManager{
    @Override
    public Scene startScene(Stage mainStage) {
        GridPane gridPane=new GridPane();
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setAlignment(Pos.CENTER);
        Label label1=new Label("reciever mail:");
        TextField inputMail=new TextField();
        gridPane.add(label1,0,0);
        gridPane.add(inputMail,1,0);
        Label label=new Label("Amount");
        TextField textField=new TextField();
        gridPane.add(label,0,1);
        gridPane.add(textField,1,1);
        Button btnSend=new Button("Send");
        Button btnExit=new Button("Exit");
        gridPane.add(btnSend,0,2);
        gridPane.add(btnExit,1,2);
        UserManager userManager=new UserManager();
        int index=userManager.getUserIndex(userManager.getCurrentUserMail());
        btnSend.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String recieverMail=inputMail.getText();
                if (!recieverMail.isBlank()||textField.getText().isBlank()){
                    if (userManager.checkIfUserExist(recieverMail)){

                        try {
                            String input=textField.getText();
                            double amount = Double.parseDouble(input);
                            if (amount<=0){
                                System.out.println("please provide a valid number");
                            }
                            else {
                                if (userManager.sendMoney(index, userManager.getUserIndex(recieverMail),amount)!=-1){
                                    System.out.println("money sent to reciever");
                                    UserMainScene userMainScene=new UserMainScene();
                                    Scene scene=userMainScene.startScene(mainStage);
                                    mainStage.setScene(scene);
                                    mainStage.show();
                                }
                                else {
                                    System.out.println("amount is not enough");
                                }
                            }
                        }catch (NumberFormatException e){
                            System.out.println("please provide a valid number");
                        }

                    }
                    else {
                        System.out.println("user does not exist");
                    }

                }
                else {
                    System.out.println("please fill all blanks");
                }

            }
        });
        btnExit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                UserMainScene userMainScene=new UserMainScene();
                Scene scene=userMainScene.startScene(mainStage);
                mainStage.setScene(scene);
                mainStage.show();
            }
        });
        return new Scene(gridPane,500,500);
    }
}
