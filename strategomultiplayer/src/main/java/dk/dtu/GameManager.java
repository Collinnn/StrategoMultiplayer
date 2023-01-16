package dk.dtu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.jspace.ActualField;
import org.jspace.FormalField;
import org.jspace.RemoteSpace;
import org.jspace.SequentialSpace;
import org.jspace.SpaceRepository;

import javafx.scene.Scene;
import javafx.application.Platform;

public class GameManager implements Runnable {
	
	private static SpaceRepository repo = new SpaceRepository();
	private static SequentialSpace move = new SequentialSpace();
	private static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	private static String s = "";
	private static RemoteSpace opponentMove;
	private static Boolean hasTurn = false;
	private static String myIp = "";
	@Override
	public void run() {
		boolean gameConnected = false;
		
		//while(!gameConnected) {
			try {
				startHostClient();
				joinHostClient();
				hasTurn = handshake();
				System.out.println("hasTurn:" + hasTurn);
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
		while(true) {
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
				while(true) System.out.println("waiting");
			}
			} catch (InterruptedException e) {
				System.out.println("Connection lost to other player");
				e.printStackTrace();
			}
		}
	}
	public void startHostClient() throws UnknownHostException {
		repo.add("move", move);
		myIp = InetAddress.getLocalHost().getHostAddress();
		System.out.println(myIp);
		repo.addGate("tcp://" + myIp + ":9001/?keep");
		
		
	}
	//Not in use for now
	public void joinHostClient(){
		boolean connected = false;
		while(!connected) {
			try {
				System.out.println("Provide ip of opponent");
				s = input.readLine();
				System.out.println("Establishing connection");
				opponentMove = new RemoteSpace("tcp://" + s + ":9001/move?keep");
				connected = true;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("No connection found try again");
				}
		}
	}
	
	public Boolean handshake() throws IOException, InterruptedException{
		System.out.println("Who starts? input \"me\" or \"not me\"");
		s = input.readLine();
		if(s.equals("me")){
			opponentMove.get(new ActualField("start"));
			return true;
		} else{
			move.put("start");
			return false;
		}
	}

	public void sendMove()throws InterruptedException{
		//Do a move, make sure it  be correct.
		PieceMoves pieceMove = TurnManager.move();
		System.out.println("received move");
		if(pieceMove.getOutcomeMove() == Move.BATTLE) {
			move.put(pieceMove);
			Piece piece = (Piece) opponentMove.get(new FormalField(Piece.class))[0];
			TurnManager.revealPiece(pieceMove.getZ(),pieceMove.getW(),piece);
			System.out.println("Resolving battle");
			Thread.sleep(400);
			TurnManager.battle(pieceMove.getPiece(), piece, pieceMove.getX(), pieceMove.getY(), pieceMove.getZ(), pieceMove.getW());
		}
		System.out.println("Sending move");
		TurnManager.movePiece(pieceMove.getX(),pieceMove.getY(),pieceMove.getZ(),pieceMove.getW(), pieceMove.getPiece());
		move.put(new PieceMoves(pieceMove.getX(),pieceMove.getY(),pieceMove.getZ(),pieceMove.getW(),pieceMove.getOutcomeMove()));
		System.out.println("Sent move");
	}
	
	public void sendTurnToken() throws InterruptedException{
		move.put("turn");
		hasTurn = false;
	}
	
	public void handleEnemyMove() throws InterruptedException{
		PieceMoves pieceMove = (PieceMoves)opponentMove.get(new FormalField(PieceMoves.class))[0];
		if(pieceMove.getOutcomeMove() == Move.BATTLE) {
			Piece piece = Board.tiles[pieceMove.getZ()][pieceMove.getW()].getPiece();
			move.put(piece);
			TurnManager.revealPiece(pieceMove.getX(),pieceMove.getY(),pieceMove.getPiece());
			System.out.println("Resolving battle");
			Thread.sleep(400);
			TurnManager.battle(pieceMove.getPiece(), piece, pieceMove.getX(), pieceMove.getY(), pieceMove.getZ(), pieceMove.getW());
		}
		TurnManager.movePiece(pieceMove.getX(),pieceMove.getY(),pieceMove.getZ(),pieceMove.getW(), pieceMove.getPiece());
	}
	
	public void waitForTurnToken() throws InterruptedException{
		opponentMove.get(new ActualField("turn"));
		hasTurn = true;
	}

}
