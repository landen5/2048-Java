import java.util.Scanner;
import java.util.Arrays;

public class Console2048 {
	//constants
	public static final int ROWS = 4;
	public static final int COLS = 4;
	
	public static final int UP = 1;
	public static final int LEFT = 2;
	public static final int DOWN = 3;
	public static final int RIGHT = 4;
	
	//fields
	private int[][] board;
	private int[][] oldBoard;
	Scanner scan;
	
	//constructor
	public Console2048 () {
		board = new int[ROWS][COLS];
		oldBoard = new int[ROWS][COLS];
		for(int row = 0; row < ROWS; row++) {
			for(int col = 0; col < COLS; col++) {
				board[row][col] = 0;
			}
		}
		//board[0][0] = 2;
		//board[0][1] = 2;
		//board[0][3] = 4;
		//board[2][0] = 2;
		addNewTile();
		addNewTile();
		
		
		scan = new Scanner(System.in);
	}
	//methods
	public void storeBoard() {
		for(int r = 0; r < ROWS; r++) {
			for(int c = 0; c < COLS; c++) {
				oldBoard[r][c] = board[r][c];
			}
		}
	}
	
	public boolean hasBoardChanged() {
		for(int r = 0; r < ROWS; r++) {
			for(int c = 0; c< COLS; c++) {
				if(board[r][c] != oldBoard[r][c]) {
					return true;
				}
			}
		}
		return false;
	}
	
	public int[][] getBoard() {
		return board;
	}
	
	public int getInputDirection() {
		boolean flag = true;
		int value = 0;
		while(flag) {
			System.out.println("Enter move key:");
			String dir = scan.nextLine();
			if(dir.equals("w")) {
				value = UP;
				flag = false;
			}
			if(dir.equals("s")) {
				value = DOWN;
				flag = false;
			}
			if(dir.equals("a")) {
				value = LEFT;
				flag = false;
			}
			if(dir.equals("d")) {
				value = RIGHT;
				flag = false;
			}
			break;
			
		}
		
		return value;
	}
	
	public void processMove(int direction) {
		if(direction == UP) {
			for(int c = 0; c < COLS; c++) {
				int[] newCol = getCol(c);
				moveValuesToFront(newCol);
				combinePairsToFront(newCol);
				moveValuesToFront(newCol);
				updateCol(newCol, c);	
			}
		}
		if(direction == DOWN) {
			for(int c = 0; c < COLS; c++) {
				int[] newCol = getCol(c);
				moveValuesToBack(newCol);
				combinePairsToBack(newCol);
				moveValuesToBack(newCol);
				updateCol(newCol, c);	
			}
		}
		if(direction == LEFT) {
			for(int r = 0; r < ROWS; r++) {
				int[] newRow = getRow(r);
				moveValuesToFront(newRow);
				combinePairsToFront(newRow);
				moveValuesToFront(newRow);
				updateRow(newRow, r);
			}
		}
		if(direction == RIGHT) {
			for(int r = 0; r < ROWS; r++) {
				int[] newRow = getRow(r);
				moveValuesToBack(newRow);
				combinePairsToBack(newRow);
				moveValuesToBack(newRow);
				updateRow(newRow, r);
			}
		}
	}
	
	private int[] getRow(int rows) {
		int[] rowArray = new int[COLS];
		for(int c = 0; c < COLS; c++) {
			rowArray[c] = board[rows][c];
		}
		return rowArray;
	}
	private int[] getCol(int cols) {
		int[] newCol = new int[ROWS];
		for(int r = 0; r < ROWS; r++) {
			newCol[r] = board[r][cols];
		}
		return newCol;
	}
	private void moveValuesToFront(int[] array) {
		int i = 0;
		for(int j = 0; j < array.length; j++) {
			if(array[j] != 0) {
				array[i] = array[j];
				i++;
			}
		}
		for(int k = i; k < array.length; k++) {
			array[k] = 0;
		}
	}
	
	private void moveValuesToBack(int[] array) {
		int i = array.length-1;
		for(int j = array.length-1; j >= 0; j--) {
			if(array[j] != 0) {
				array[i] = array[j];
				i--;
			}
		}
		for(int k = i; k >= 0; k--) {
			array[k] = 0;
		}
	}
	
	private void combinePairsToFront(int[] array) {
		for(int i = 0; i < array.length-2; i++) {
			if(array[i] == array[i+1]) {
				array[i] += array[i+1];
				array[i+1] = 0;
			}
		}
	}
	
	private void combinePairsToBack(int[] array) {
		for(int i = array.length-1; i >= 1; i--) {
			if(array[i] == array[i-1]) {
				array[i] += array[i-1];
				array[i-1] = 0;
			}
		}
	}
	
	private void updateRow(int[] row, int index) {
		for(int c = 0; c < COLS; c++) {
			board[index][c] = row[c];
		}
	}
	
	private void updateCol(int[] col, int index) {
		for(int r = 0; r < ROWS; r++) {
			board[r][index] = col[r];
		}
	}
	
	public void printBoard() {
		for(int r = 0; r < ROWS; r++) {
			for(int c = 0; c < COLS; c++) {
				System.out.print(" ~~~~~~");
			}
			System.out.println();
			for(int c = 0; c < COLS; c++) {
				System.out.print("|      ");
			}
			System.out.println("|");
			for(int c = 0; c < COLS; c++) {
				if(board[r][c] == 0) {
					System.out.print("|      ");
				}
				else {
					System.out.format("|%5d ", board[r][c]);
				}
			}
			System.out.println("|");

		}
		for(int c = 0; c < COLS; c++) {
			System.out.print(" ~~~~~~");
		}
		System.out.println();	
	}
	
	public boolean checkLoss() {
		boolean flag = true;
		for(int r = 0; r < ROWS; r++) {
			for(int c = 0; c < COLS; c++) {
				if(board[r][c] == 0) {
					return false;
				}
			}
		}
		for(int r = 0; r < ROWS; r++) {
			for(int c = 0; c < COLS-1; c++) {
				if(board[r][c] == board[r][c+1]) {
					return false;
				}
			}
		}
		for(int c = 0; c < COLS; c++) {
			for(int r = 0; r < ROWS-1; r++) {
				if(board[r][c] == board[r+1][c]) {
					return false;
				}
			}
		}
		
		return flag;
	}
	public boolean checkWin() {
		boolean flag = false;
		for(int r = 0; r < ROWS; r++) {
			for(int c = 0; c < COLS; c++) {
				if(board[r][c] == 32) {
					flag = true;
				}
			}
		}
		return flag;
	}
	
	public void addNewTile() {
		int zeroCount = 0;
		for(int r = 0; r < ROWS; r++) {
			for(int c = 0; c < ROWS; c++) {
				if(board[r][c] == 0) {
					zeroCount++;
				}
			}
		}
		if(zeroCount == 0)
		{
			return;
		}
	
		int nthValue = (int)(Math.random() * (zeroCount - 0) + 0);// doublecheck
		int tileValue = 0;
		if(Math.random() < 0.25) {
			tileValue = 4;
		} else {
			tileValue = 2;
		}
		zeroCount = 0;
		for(int r = 0; r < ROWS; r++) {
			for(int c = 0; c < ROWS; c++) {
				if(board[r][c] == 0) {
					if(zeroCount == nthValue) {
						board[r][c] = tileValue;
					}
					zeroCount++;
				}
			}
		}
		
	}
	
	
	public static void main(String[] args) {
		Console2048 game = new Console2048();
		boolean playGame = true;
		while(playGame) {
			game.printBoard();
			//System.out.println("test");
			int direction = game.getInputDirection();
			System.out.println(direction); //issue is here
			game.storeBoard();
			game.processMove(direction);
			if(game.hasBoardChanged() == true) {
				game.addNewTile();
				if(game.checkLoss())
				{
					playGame = false;
					game.printBoard();
					System.out.println("You lost");
				}
				if(game.checkWin()) {
					playGame = false;
					game.printBoard();
					System.out.println("You won!");
				}
			}

		}

	}
}