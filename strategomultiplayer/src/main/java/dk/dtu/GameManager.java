package dk.dtu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;

import org.jspace.ActualField;
import org.jspace.RemoteSpace;
import org.jspace.SequentialSpace;
import org.jspace.SpaceRepository;

public class GameManager implements Runnable {
	
	@Override
	public void run() {
		try {
			SpaceRepository repo = new SpaceRepository();
			SequentialSpace move = new SequentialSpace();
			repo.add("move", move);
			String myIp = InetAddress.getLocalHost().getAddress().toString();
			repo.addGate("tcp://" + myIp + ":9001/?keep");
			System.out.println(myIp);
			System.out.println("Provide ip of opponent");
			BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
			String s = input.readLine();
			System.out.println("Establishing connection");
			RemoteSpace opponentMove = new RemoteSpace("tcp://" + s + ":9001/move?keep");
			System.out.println("Who starts? input \"me\" or \"not me\"");
			s = input.readLine();
			if(s == "me"){
				opponentMove.get(new ActualField("start"));
			} else{
				move.put("start");
			} 
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}
	
	

}
