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
	
	
	
}
