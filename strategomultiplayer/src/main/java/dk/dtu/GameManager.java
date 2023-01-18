package dk.dtu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import org.jspace.ActualField;
import org.jspace.FormalField;
import org.jspace.RemoteSpace;
import org.jspace.SequentialSpace;
import org.jspace.SpaceRepository;

import javafx.application.Platform;
import javafx.scene.Scene;

public class GameManager implements Runnable {
	
	private static SpaceRepository repo = new SpaceRepository();
	private static SequentialSpace moveSpace = new SequentialSpace();
	private static SequentialSpace tokenSpace = new SequentialSpace();
	private static SequentialSpace pieceSpace = new SequentialSpace();
	private static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	private static String s = "";
	private static RemoteSpace opponentMoveSpace,opponentTokenSpace,opponentPieceSpace,rooms,roomsLock;
	public static Boolean hasTurn = false;
	private static String myIp = "";
	private static String lobbyIp = "";
	private static String opponentIp = "";
	public static Boolean hasWon = false;
	public static Boolean gameFinished;
	private static int roomId;
	
	
	@Override
	public void run() {
		boolean gameConnected = false;
		gameFinished =false;
		hasWon = false;
		//while(!gameConnected) {
			try {
				startHostClient();
				joinRoom();
				//hasTurn = handshake();
				//System.out.println("hasTurn:" + hasTurn);
				Board board = new Board(hasTurn);
				Scene game = new Scene(GameSetup.getStage());
				Platform.runLater(new Runnable() {
					@Override 
					public void run() {
					App.changeScene(game);
					}
				});
				gameConnected = true;
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				System.out.println("Something went wrong in connection try again");
				e1.printStackTrace();
			}
		//}
		//SetupManager.boardSetup();
		while(!gameFinished) {
			try {
			if(hasTurn) {
				System.out.println("I will send a move");
				sendMove();
				sendTurnToken();
				System.out.println("sent token");
				
			} else {
				System.out.println("I will wait for opponent move");
				handleEnemyMove();
				waitForTurnToken();
			}
			} catch (InterruptedException e) {
				System.out.println("Connection lost to other player");
				e.printStackTrace();
			}
		}
		repo.closeGates();
		Platform.runLater(new Runnable() {
			@Override 
			public void run() {
			Platform.exit();
			}
		});
	}
	public void startHostClient() throws UnknownHostException {
		repo.add("moveSpace", moveSpace);
		repo.add("tokenSpace", tokenSpace);
		repo.add("pieceSpace", pieceSpace);
		myIp = InetAddress.getLocalHost().getHostAddress();
		System.out.println("my ip is: " + myIp);
		repo.addGate("tcp://" + myIp + ":9001/?keep");
	}
	//Not in use for now
//	public void joinHostClient(){
//		boolean connected = false;
//		while(!connected) {
//			try {
//				System.out.println("Provide ip of opponent");
//				s = input.readLine();
//				System.out.println("Establishing connection");
//				opponentMove = new RemoteSpace("tcp://" + s + ":9001/move?keep");
//				connected = true;
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//					System.out.println("No connection found try again");
//				}
//		}
//	}
	
	public void joinRoom() throws InterruptedException {
		boolean joined = false;
		while(!joined) {
			try {
				System.out.println("Provide ip of lobby:");
				lobbyIp = input.readLine();
				System.out.println("Connecting to lobby");
				rooms = new RemoteSpace("tcp://" + lobbyIp + ":9002/rooms?keep");
				roomsLock = new RemoteSpace("tcp://" + lobbyIp + ":9002/roomsLock?keep");
				//Get a list of available rooms
				List<Object[]> availableRooms = rooms.queryAll(new FormalField(Integer.class), new FormalField(String.class), new FormalField(String.class), new FormalField(Integer.class));
				System.out.println("TEST: " + availableRooms.size());
				String lobbyString = "The available rooms are:\n\n";
				for(int i = 0; i < availableRooms.size(); i++) {
					lobbyString += "Room " + (int) availableRooms.get(i)[0] + "\t Current active players: " + (int) availableRooms.get(i)[3] + "\n";
				}
				System.out.println(lobbyString);
				//The protocol is handled here
				while(!joined) {
					while(true){
						System.out.print("Enter the number of the room you wish to join");
						String lobbycheck = input.readLine();
						if(lobbycheck.matches("([1-9]){1}+$")|| lobbycheck.matches("10")){//Number between 1-10
							roomId = Integer.valueOf(lobbycheck);
							break;
						}else{
							System.out.println("Invalid id, try a number between 1-10");
						}
					}	
					//Locks the critical region
					roomsLock.get(new ActualField(roomId));
					Object[] room = rooms.get(new ActualField(roomId), new FormalField(String.class), new FormalField(String.class), new FormalField(Integer.class));
					if((int)room[3] == 2) {
						System.out.println("The room is full. Try another.");
						//Updates the rooms
						rooms.put(room);
						roomsLock.put(roomId);
						availableRooms = rooms.queryAll(new FormalField(Integer.class), new FormalField(String.class), new FormalField(String.class), new FormalField(Integer.class));
						lobbyString = "The available rooms are:\n\n";
						for(int i = 0; i < availableRooms.size(); i++) {
							lobbyString += "Room " + (int) availableRooms.get(i)[0] + "\t Current active players: " + (int) availableRooms.get(i)[3] + "\n";
						}
						System.out.println(lobbyString);
					}
					//Room is empty
					else if(room[1].equals("")) {
						rooms.put(roomId, myIp, "", 1);
						//Unlocks critical region
						roomsLock.put(roomId);
						//The player is player 1
						hasTurn = true;
						joined = true;
						System.out.println("You are blue. Awaiting player...");
						connectPlayers(roomId,1);
					}
					else {
						rooms.put(roomId, (String)room[1], myIp, 2);
						//unlocks critical region
						roomsLock.put(roomId);
						//The player is player 2
						hasTurn = false;
						joined = true;
						System.out.println("You are red. Starting game...");
						connectPlayers(roomId,2);
					}
					
				}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("No connection found try again");
				}
		}
	}
	
	public void connectPlayers(int roomId, int numberOfPlayersInRoom) throws InterruptedException, UnknownHostException, IOException {
		if(numberOfPlayersInRoom == 1) {
			Object[] room = rooms.query(new ActualField(roomId), new FormalField(String.class), new FormalField(String.class), new ActualField(2));
			opponentIp = (String)room[2];
			opponentMoveSpace = new RemoteSpace("tcp://" + opponentIp + ":9001/moveSpace?keep");
			opponentTokenSpace = new RemoteSpace("tcp://" + opponentIp + ":9001/tokenSpace?keep");
			opponentPieceSpace = new RemoteSpace("tcp://" + opponentIp + ":9001/pieceSpace?keep");
		}
		else if(numberOfPlayersInRoom == 2) {
			Object[] room = rooms.query(new ActualField(roomId), new FormalField(String.class), new FormalField(String.class), new ActualField(2));
			opponentIp = (String)room[1];
			opponentMoveSpace = new RemoteSpace("tcp://" + opponentIp + ":9001/moveSpace?keep");
			opponentTokenSpace = new RemoteSpace("tcp://" + opponentIp + ":9001/tokenSpace?keep");
			opponentPieceSpace = new RemoteSpace("tcp://" + opponentIp + ":9001/pieceSpace?keep");
		}
		else {
			throw new IllegalArgumentException("Illegal player count");
		}
	}
	
//	public Boolean handshake() throws IOException, InterruptedException{
//		System.out.println("Who starts? input \"me\" or \"not me\"");
//		s = input.readLine();
//		if(s.equals("me")){
//			opponentMove.get(new ActualField("start"));
//			return true;
//		} else{
//			move.put("start");
//			return false;
//		}
//	}

	public void sendMove()throws InterruptedException{
		//Do a move, make sure it  be correct.
		PieceMoves pieceMove = TurnManager.move();
		int x = pieceMove.getX();
		int y = pieceMove.getY();
		int z = pieceMove.getZ();
		int w = pieceMove.getW();
		Move playerMove = pieceMove.getOutcomeMove();
		System.out.println("received move");
		if(playerMove == Move.BATTLE) {
			moveSpace.put(pieceMove);
			Piece piece = Board.tiles[x][y].getPiece();
			pieceSpace.put(piece.getPieceType());
			PieceType opponentType = (PieceType) opponentPieceSpace.get(new FormalField(PieceType.class))[0];
			System.out.println("The Opponent Type is " + opponentType);
			TurnManager.revealPiece(z,w,opponentType);
			Piece opponentPiece = Board.tiles[z][w].getPiece();
			System.out.println("Resolving battle");
			Thread.sleep(1000);
			TurnManager.battle(piece, opponentPiece, x, y, z, w);
		}else {
			System.out.println("Sending move");
			TurnManager.movePiece(x,y,z,w);
			moveSpace.put(pieceMove);
			System.out.println("Sent move");
		}
	}
	
	public void sendTurnToken() throws InterruptedException{
		tokenSpace.put("token");
		hasTurn = false;
	}
	
	public void handleEnemyMove() throws InterruptedException{
		PieceMoves pieceMove = (PieceMoves)opponentMoveSpace.get(new FormalField(PieceMoves.class))[0];
		System.out.println("I have received Move");
		int x = pieceMove.getX();
		int y = pieceMove.getY();
		int z = pieceMove.getZ();
		int w = pieceMove.getW();
		Move opponentMove = pieceMove.getOutcomeMove();
		Thread.sleep(1000);
		if(opponentMove == Move.BATTLE) {
			System.out.println("Move is battle");
			Piece piece = Board.tiles[pieceMove.getZ()][pieceMove.getW()].getPiece();
			System.out.println("Getting opponentPiece");
			PieceType opponentType = (PieceType)opponentPieceSpace.get(new FormalField(PieceType.class))[0];
			System.out.println("Got opponentPiece");
			System.out.println("Sending piece");
			pieceSpace.put(piece.getPieceType());
			System.out.println("Sent piece");
			TurnManager.revealPiece(x,y,opponentType);
			Piece opponentPiece = Board.tiles[pieceMove.getX()][pieceMove.getY()].getPiece();
			System.out.println("Resolving battle");
			Thread.sleep(1000);
			TurnManager.battle(opponentPiece, piece, x, y, z, w);
		} else {
			System.out.println("Move is Legal just moving piece");
			TurnManager.movePiece(x,y,z,w);
			System.out.println("Piece moved");
		}
	}
	
	public void waitForTurnToken() throws InterruptedException{
			opponentTokenSpace.get(new ActualField("token"));
			hasTurn = true;
	}

}
