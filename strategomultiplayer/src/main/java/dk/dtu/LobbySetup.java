package dk.dtu;

import java.io.FileNotFoundException;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class LobbySetup {
	
	public static Pane getStage() throws FileNotFoundException {
		Pane menu = new Pane();
		VBox list = new VBox();
		HBox buttonText = new HBox();
		Button b = new Button("Join room 1");
		Text t = new Text("uwu");
		buttonText.getChildren().addAll(b,t);
		list.getChildren().addAll(buttonText);
		menu.getChildren().add(buttonText);
		return menu;
		
	}

}
