package lobby;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.jspace.SequentialSpace;
import org.jspace.SpaceRepository;

public class Lobby {
	
	static final int NUMBER_OF_ROOMS = 1;
	
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
				rooms.put(i, null, null, 0);
				roomsLock.put("LOCK" + i);
			}
			
			//Lobby host should be connected to DTU's network
			String ip = InetAddress.getLocalHost().getHostAddress().toString();
			
			repository.addGate("tcp://" + ip + ":9001/?keep");
			
		} catch(InterruptedException e) {
			e.printStackTrace();
		} catch(UnknownHostException e1) {
			e1.printStackTrace();
		}
		
		
	}

}
