package pokerBase;

import java.util.ArrayList;
import java.util.UUID;

public class Game {
	
	private UUID GameID;
	private ArrayList<Player> GamePlayers = new ArrayList<Player>();
	private UUID TableID;

	public Game() {
		GameID = UUID.randomUUID();
	}
	
	public void AddPlayerToGame(Table t, Player p){
		t.AddPlayerToTable(p);
	}
	

}
