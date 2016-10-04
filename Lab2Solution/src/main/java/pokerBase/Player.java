package pokerBase;

import java.util.UUID;

public class Player {

	private UUID PlayerID;
	private String PlayerName;
	
	public Player(String strPlayerName)
	{
		PlayerID = UUID.randomUUID();
		this.PlayerName = strPlayerName;
		
	}
	
	public UUID getPlayerID() {
		return PlayerID;
	}

	public void setPlayerID(UUID playerID) {
		PlayerID = playerID;
	}
	
	
}
