package com.example.librarymanagement.scenes;


import com.example.librarymanagement.classes.Book;
import com.example.librarymanagement.classes.BookManager;
import com.example.librarymanagement.scenes.HomePage;
import com.example.librarymanagement.scenes.SceneManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class AddBookScene implements SceneManager {
    @Override
    public Scene startScene(Stage mainStage) {
        GridPane gridPane=new GridPane();
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setAlignment(Pos.CENTER);
        Label label1=new Label("Book Name:");
        TextField bookName=new TextField();
        gridPane.add(label1,0,0);
        gridPane.add(bookName,1,0);

        Label label=new Label("Book Kind:");
        TextField bookKind=new TextField();
        gridPane.add(label,0,1);
        gridPane.add(bookKind,1,1);

        Label label2=new Label("Book Author:");
        TextField bookAuthor=new TextField();
        gridPane.add(label2,0,2);
        gridPane.add(bookAuthor,1,2);

        Label label3=new Label("Page Count:");
        TextField pageCount=new TextField();
        gridPane.add(label3,0,3);
        gridPane.add(pageCount,1,3);

        Label label4=new Label("Publish Date:");
        TextField publishDate=new TextField();
        gridPane.add(label4,0,4);
        gridPane.add(publishDate,1,4);

        Button btnCreate=new Button("Create");
        Button btnExit=new Button("Exit");
        gridPane.add(btnCreate,0,5);
        gridPane.add(btnExit,1,5);

        btnCreate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String name=bookName.getText();
                String kind=bookKind.getText();
                String author=bookAuthor.getText();
                String pCount=pageCount.getText();
                String pDate=publishDate.getText();
                Book book=new Book(name,kind,author,pCount,pDate);
                BookManager bookManager=new BookManager();
                bookManager.addBook(book);
                HomePage homePage=new HomePage();
                Scene scene=homePage.startScene(mainStage);
                mainStage.setScene(scene);
                mainStage.show();


            }
        });
        btnExit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                HomePage homePage=new HomePage();
                Scene scene=homePage.startScene(mainStage);
                mainStage.setScene(scene);
                mainStage.show();
            }
        });
        return new Scene(gridPane,500,500);
    }
}