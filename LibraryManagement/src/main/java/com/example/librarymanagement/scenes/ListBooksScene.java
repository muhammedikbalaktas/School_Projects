package com.example.librarymanagement.scenes;


import com.example.librarymanagement.classes.BookManager;
import com.example.librarymanagement.scenes.HomePage;
import com.example.librarymanagement.scenes.SceneManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ListBooksScene implements SceneManager {
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

        // Create a layout container and add the ListView and exit button to it
        VBox root = new VBox(listView, exitButton);

        // Create a Scene and set it on the Stage
        Scene scene = new Scene(root, 500, 500);
        mainStage.setScene(scene);
        mainStage.setTitle("Welcome to MLibrary");
        mainStage.show();



        return scene;
    }
}