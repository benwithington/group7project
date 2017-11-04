package game;

public class GameBoard {
	private Ship board[][];
	
	
	public GameBoard() {
		board  = new Ship[10][10];
	}
	
	// TODO: Collision Logic
	public void addShip(int x, int y, int length, String dir) {
		if(dir.equals("W")) {
			for(int i = 0; i < length; i++) {
				board[x][y-i] = new Ship(x, y-i, "W" + length);
			}
		} else if(dir.equals("N")) {
			for(int i = 0; i < length; i++) {
				board[x-i][y] = new Ship(x-i, y, "N" + length);
			}
		} else if(dir.equals("E")) {
			for(int i = 0; i < length; i++) {
				board[x][y+i] = new Ship(x, y+i, "E" + length);
			}
		} else if(dir.equals("S")) {
			for(int i = 0; i < length; i++) {
				board[x+i][y] = new Ship(x+i, y, "S" + length);
			}
		}
		
	}
	
	public Ship[][] getBoard() {
		return board;
	}
	
	public Ship getShipAt(int x, int y) {
		return board[x][y];
	}
	
	public void removeShipAt(int x, int y) {
		board[x][y] = null;
	}
	
	public boolean contains(String shipID) {
		
		for(int i = 0; i < 10; i++) {
			for(int j = 0 ; j < 10; j++) {
				if(board[i][j] != null) {
					if(board[i][j].getId().equals(shipID)) { return true; }
				}
			}
		}
		
		return false;
	}
}