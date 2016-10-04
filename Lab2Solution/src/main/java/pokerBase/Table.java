package pokerBase;

import java.util.ArrayList;
import java.util.UUID;

public class Table {

	private UUID TableID;
	private ArrayList<Player> players = new ArrayList<Player>();

	public Table() {
		TableID = UUID.randomUUID();
	}

	public Table AddPlayerToTable(Player p) {
		players.add(p);
		return this;
	}

	public UUID getTableID() {
		return TableID;
	}

	public void setTableID(UUID tableID) {
		TableID = tableID;
	}
}
