package dk.dtu;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.util.Scanner;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Hello world!
 *
 */
public class App extends Application
{
    private static Board board = new Board();
    private static Stage root = new Stage();
    public static void main( String[] args )throws IOException
    {
    	System.out.println("Please provide the ip address of lobby");
    	//Should be reader here
        Scanner myIp = new Scanner(System.in);
        System.out.println("Enter IP");
        
    	Connection.setLobbyIp(myIp.nextLine());
        launch(args);
        
    }
    
    @Override
    public void start(Stage stage) throws FileNotFoundException {
        //System.out.println(System.getProperty("user.dir"));
        
        root = stage;
        root.setWidth(800);
        root.setHeight(600);
        root.setTitle("StrategoMultiplayer");
        
        
        //root.getIcons().add(new Image("stratego.png")); //Adds logo for the program.
        Scene scene = new Scene(GameSetup.getStage());
        root.setScene(scene);
        root.centerOnScreen();
        root.setOnCloseRequest(e->closeProgram());
		root.setResizable(false);
		root.show();
		Thread game1 = new Thread(new TurnManager(),"game1");
		game1.start();
		

    }
    
    public static void changeScene(Scene scene) {
    	root.setScene(scene);
    }

    public static void closeProgram(){
        Platform.exit();
    }


}
