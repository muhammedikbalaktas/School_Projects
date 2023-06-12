module com.example._150719652_first_project {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example._150719652_first_project to javafx.fxml;
    exports com.example._150719652_first_project;
    exports com.example._150719652_first_project.scenes;
    opens com.example._150719652_first_project.scenes to javafx.fxml;
}