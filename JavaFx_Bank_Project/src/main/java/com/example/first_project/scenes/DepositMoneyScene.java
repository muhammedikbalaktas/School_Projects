package com.example._150719652_first_project.scenes;

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

public class DepositMoneyScene implements SceneManager{
    @Override
    public Scene startScene(Stage mainStage) {

        GridPane gridPane=new GridPane();
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setAlignment(Pos.CENTER);
        Label label=new Label("Amount");
        TextField textField=new TextField();
        gridPane.add(label,0,0);
        gridPane.add(textField,1,0);
        Button btnDeposit=new Button("Deposit");
        UserManager userManager=new UserManager();
        int index=userManager.getUserIndex(userManager.getCurrentUserMail());
        btnDeposit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    String input=textField.getText();
                    double amount = Double.parseDouble(input);
                    if(amount<=0){
                        System.out.println("please provide a valid amount");
                    }
                    else {
                        UserManager userManager=new UserManager();
                        userManager.depositMoney(amount,index);
                        UserMainScene userMainScene=new UserMainScene();
                        Scene scene=userMainScene.startScene(mainStage);
                        mainStage.setScene(scene);
                        mainStage.show();
                    }

                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }



            }
        });
        Button btnExit=new Button("Exit");
        gridPane.add(btnDeposit,0,1);
        gridPane.add(btnExit,1,1);
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
