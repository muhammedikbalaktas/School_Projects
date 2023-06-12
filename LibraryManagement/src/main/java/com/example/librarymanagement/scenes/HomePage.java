package com.example.librarymanagement.scenes;


import com.example.librarymanagement.MainScene;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class HomePage implements SceneManager{
    @Override
    public Scene startScene(Stage mainStage) {
        mainStage.setTitle("Welcome to MLibrary!");
        VBox vBox=new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(50);


        Button listBooks=new Button("List Books");
        Button addBook=new Button("Add Book");
        Button deleteBook=new Button("Delete Book");
        deleteBook.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                DeleteBookScene deleteBookScene=new DeleteBookScene();
                Scene scene=deleteBookScene.startScene(mainStage);
                mainStage.setTitle("Welcome to MLibrary!");
                mainStage.setScene(scene);
                mainStage.show();

            }
        });
        listBooks.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ListBooksScene listBooksScene=new ListBooksScene();
                Scene scene=listBooksScene.startScene(mainStage);
                mainStage.setTitle("Welcome to MLibrary!");
                mainStage.setScene(scene);
                mainStage.show();

            }
        });
        addBook.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                AddBookScene addBookScene=new AddBookScene();
                Scene scene=addBookScene.startScene(mainStage);
                mainStage.setTitle("Welcome to MLibrary!");
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
        vBox.getChildren().addAll(listBooks,addBook,deleteBook,btnExit);


        return new Scene(vBox,500,500);
    }
}