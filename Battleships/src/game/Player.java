package game;

public class Player {
	private GameBoard gb;
	private String name;

	public Player(String name) {
		gb = new GameBoard();
		this.setName(name);
	}
	// TODO: Update so that it checks if a coordinate will be within the range of an already placed ship
	public boolean addShip(int len, int x, int y, String dir) {
		
		try {
			gb.addShip(x, y, len, dir);
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Ship Placement Out of Bounds, Try Again.");
			return false;
		}
		return true;

	}



	public void fireAt(Player p, int x , int y) {

		if(p.getGameBoard().getBoard()[x][y] != null) {
			// HIT OR SINK
			Ship s = p.getGameBoard().getShipAt(x, y);
			p.getGameBoard().getBoard()[x][y] = null;
			if(p.getGameBoard().contains(s.getId())) {
				//HIT
				System.out.println("HIT");
			} else  {
				// SINK
				System.out.println("SINK");
			}
		} else {
			//MISS
			System.out.println("MISS");
		}
	}

	public boolean hasShips() {

		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if (gb.getBoard()[i][j] != null) {
					return true;
				}
			}
		}

		return false;
	}
	
	public GameBoard getGameBoard() {
		return gb;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
