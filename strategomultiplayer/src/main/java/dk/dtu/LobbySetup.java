package dk.dtu;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.jspace.ActualField;
import org.jspace.FormalField;
import org.jspace.RemoteSpace;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class LobbySetup {
	
	public static Pane getStage() throws FileNotFoundException {
		HBox lobbyMenu = new HBox();
		new Thread(new GameManager(),"Game").start();
		/*
		try {
			RemoteSpace rooms = new RemoteSpace("tcp://" + Connection.lobbyIp + ":9001/rooms?keep");
			RemoteSpace roomsLock = new RemoteSpace("tcp://" + Connection.lobbyIp + "9001/roomsLock?keep");
			
			VBox buttons = new VBox();
			Button b1 = new Button("Join room 1");
			b1.setOnAction(e -> {
				try {
					roomsLock.get(new ActualField("LOCK1"));
					Object[] t = rooms.query(new ActualField(1), new FormalField(String.class), new FormalField(String.class),new FormalField(Integer.class));
					if((int)t[3] < 2) {
						rooms.get(new ActualField(1), new FormalField(String.class), new FormalField(String.class),new FormalField(Integer.class));
						if((int)t[3] == 0 && (String)t[1] == null) {
							try {
								rooms.put(1,InetAddress.getLocalHost().getAddress().toString(),"",1);
								App.changeScene(new Scene(GameSetup.getStage()));
							} catch (UnknownHostException e1) {
								e1.printStackTrace();
							} catch (FileNotFoundException e1){
								e1.printStackTrace();
							}
						} else if ((int)t[3] == 1 && (String)t[2] == null) {
							try {
								rooms.put(1,(String)t[1],InetAddress.getLocalHost().getAddress().toString(),2);
								
							} catch (UnknownHostException e1) {
								e1.printStackTrace();
							}
						}
					}
					roomsLock.put("LOCK1");
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				
				
			});
			buttons.getChildren().addAll(b1);
			lobbyMenu.getChildren().addAll(buttons);
		} catch(IOException e) {
			e.printStackTrace();
		}*/
		return lobbyMenu;
	}

}
