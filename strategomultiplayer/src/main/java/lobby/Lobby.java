package lobby;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.jspace.FormalField;
import org.jspace.SequentialSpace;
import org.jspace.SpaceRepository;

public class Lobby {
	
	static final int NUMBER_OF_ROOMS = 10;
	
	public static void main(String[] args) {
		
		try {
			SpaceRepository repository = new SpaceRepository();
			
			SequentialSpace rooms = new SequentialSpace();
			
			//Used to lock the rooms when being joined by player
			SequentialSpace roomsLock = new SequentialSpace();
			
			repository.add("rooms", rooms);
			
			repository.add("roomsLock", roomsLock);
			
			for(int i = 1; i <= NUMBER_OF_ROOMS; i++) {
				//Creates NUMBER_OF_ROOMS empty rooms.
				//The tuples have the following information:
				//(room id, ip of player 1, ip of player 2, number of players) 
				rooms.put(i, "", "", 0);
				//System.out.println((int)rooms.get(new FormalField(Integer.class), new FormalField(String.class), new FormalField(String.class), new FormalField(Integer.class))[0]);
				roomsLock.put(i);
			}
			
			//Lobby host should be connected to DTU's network
			String ip = InetAddress.getLocalHost().getHostAddress();
			System.out.println(ip);
			
			repository.addGate("tcp://" + ip + ":9002/?keep");
			
		} catch(InterruptedException e) {
			e.printStackTrace();
		} catch(UnknownHostException e1) {
			e1.printStackTrace();
		}
		
		
	}

}
