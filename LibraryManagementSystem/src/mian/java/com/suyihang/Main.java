package com.suyihang;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Main.primaryStage = primaryStage;
        primaryStage.setTitle("图书管理系统");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/suyihang/login.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/com/suyihang/css/loginview.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setResizable(false); // 禁止改变界面大小
        primaryStage.show();
    }

    public static void switchScene(String fxmlFile) throws Exception {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource(fxmlFile));
        Parent root = loader.load();
        Scene scene = new Scene(root);
//        scene.getStylesheets().add(Main.class.getResource("/com/suyihang/css/style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setResizable(false); // 禁止改变界面大小
    }

    public static void addScene(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource(fxmlFile));
            Pane popupLayout = loader.load(); // Use Pane as the root

            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL); // Set as modal window
            popupStage.initOwner(Main.getMainStage()); // Set owner as main Stage

            Scene popupScene = new Scene(popupLayout);
            popupStage.setScene(popupScene);

            popupStage.showAndWait(); // Show modal window and wait for it to close
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Stage getMainStage(){
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
