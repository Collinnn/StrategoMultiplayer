package dk.dtu;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;

public class GameSetup {
    public static BorderPane getStage() throws FileNotFoundException{
        BorderPane map = new BorderPane();
        map.setCenter(Board.grid);
        Image image = new Image(new FileInputStream("./assets/Background.png"));
        ImageView imageView = new ImageView(image);

        BackgroundSize size = new BackgroundSize(
            514, 514, false, false, false, false);
        Background background = new Background(new BackgroundImage(image,
        BackgroundRepeat.NO_REPEAT,
        BackgroundRepeat.NO_REPEAT,
        BackgroundPosition.CENTER,
        size));
        map.setBackground(background);
        //imageView.setX(3);
        //imageView.setY(3);
        //imageView.setFitHeight(511);
        //imageView.setFitWidth(511);
        //imageView.setPreserveRatio(true);
        
        //Group roots = new Group(imageView,Board.grid);
        return map;
    }
}
