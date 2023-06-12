package com.example._150719652_first_project.scenes;

import com.example._150719652_first_project.MainScene;
import com.example._150719652_first_project.classes.UserManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class UserMainScene implements SceneManager{
    @Override
    public Scene startScene(Stage mainStage) {
        mainStage.setTitle("Welcome to Mbank!");
        VBox vBox=new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(50);
        UserManager userManager=new UserManager();
        int index=userManager.getUserIndex(userManager.getCurrentUserMail());
        double balance=userManager.getBalance(index);
        Label label=new Label("Balance:"+balance);
        Button btnSendMoney=new Button("Send Money");
        Button btnWithdrawMoney=new Button("Withdraw Money");
        Button btnDeposit=new Button("Deposit Money");
        btnDeposit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                DepositMoneyScene depositMoneyScene=new DepositMoneyScene();
                Scene scene=depositMoneyScene.startScene(mainStage);
                mainStage.setScene(scene);
                mainStage.show();
            }
        });
        btnSendMoney.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                SendMoneyScene withDrawMoneyScene=new SendMoneyScene();
                Scene scene=withDrawMoneyScene.startScene(mainStage);
                mainStage.setScene(scene);
                mainStage.show();

            }
        });
        btnWithdrawMoney.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                WithDrawMoneyScene withDrawMoneyScene=new WithDrawMoneyScene();
                Scene scene=withDrawMoneyScene.startScene(mainStage);
                mainStage.setScene(scene);
                mainStage.show();
            }
        });
        Button btnExit=new Button("Exit");
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
        vBox.getChildren().addAll(label,btnSendMoney,btnWithdrawMoney,btnDeposit,btnExit);


        return new Scene(vBox,500,500);
    }
}
