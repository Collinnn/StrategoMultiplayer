package dk.dtu;

import java.io.IOError;
import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
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
        launch(args);
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        
        root = stage;
        root.setWidth(800);
        root.setHeight(600);
        root.setTitle("StrategoMultiplayer");
        //root.getIcons().add(new Image("stratego.png"));
        root.setScene(new Scene(StageSetup.getStage()));
        root.setOnCloseRequest(e->closeProgram());
		root.setResizable(false);
		root.show();


    }

    public static void closeProgram(){
        Platform.exit();
    }


}
