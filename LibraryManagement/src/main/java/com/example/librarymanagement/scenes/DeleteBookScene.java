package com.example.librarymanagement.scenes;


import com.example.librarymanagement.classes.BookManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.ListView;

import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DeleteBookScene implements SceneManager {
    @Override
    public Scene startScene(Stage mainStage) {



        BookManager bookManager=new BookManager();

        ListView<String> listView = new ListView<>();
        listView.getItems().addAll(bookManager.getAllBooks());

        // Create an exit button
        Button exitButton = new Button("Exit");
        exitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                HomePage homePage=new HomePage();
                Scene scene=homePage.startScene(mainStage);
                mainStage.setTitle("Welcome to MLibrary!");
                mainStage.setScene(scene);
                mainStage.show();

            }
        });

        TextField bookName=new TextField();
        bookName.setPromptText("Please enter a book name");
        Button btnDelete=new Button("Delete");
        btnDelete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String bName=bookName.getText();
                if (bName.isBlank()) System.out.println("book name should be filled");
                else {
                    bookManager.deleteBook(bName);
                    DeleteBookScene deleteBookScene=new DeleteBookScene();
                    Scene scene=deleteBookScene.startScene(mainStage);
                    mainStage.setTitle("Welcome to MLibrary!");
                    mainStage.setScene(scene);
                    mainStage.show();
                }
            }
        });

        VBox root = new VBox(listView,bookName,btnDelete, exitButton);



        return new Scene(root, 500, 500);
    }
}