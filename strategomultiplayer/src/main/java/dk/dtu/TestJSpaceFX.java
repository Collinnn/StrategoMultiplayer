package dk.dtu;

import org.jspace.FormalField;
import org.jspace.SequentialSpace;
import org.jspace.Space;

import javafx.application.Application;
import javafx.event.*;
import javafx.scene.control.Button;
import javafx.scene.input.*;
import javafx.scene.layout.StackPane;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TestJSpaceFX extends Application {

    public static void main(String[] args) throws InterruptedException {
        Space inbox = new SequentialSpace();
        inbox.put("Hello World!");
        Object[] tuple = inbox.get(new FormalField(String.class));
        System.out.println(tuple[0]);
        
        launch(args);
    }

    private int counter = 0;
    private Button button = new Button();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setWidth(800);
        primaryStage.setHeight(600);
        primaryStage.setTitle("Hello World!");
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}