import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

public class GUI2048Panel extends JPanel implements KeyListener{
	//constants
	private static final Font FONT = new Font("Arial", Font.BOLD, 100);
	private static final Color[] COLORS = { new Color(210,180,140),
			new Color(255, 190, 0), new Color(255, 140, 0), new Color(253, 116, 0),
			new Color(255, 87, 34), new Color(210, 54, 0), new Color(213, 0, 0), 
			new Color(185, 18, 27), new Color(10, 3, 200), new Color(0, 90, 0), 
			new Color(100, 1, 243), new Color(4, 40, 250)};

	
	//fields
	private int tileWidth;
	private int tileHeight;
	
	private Console2048 game;
	
	//constructor
	public GUI2048Panel() {
		game = new Console2048();
	}
	
	//methods
	private void setTileSize() {
		tileWidth = this.getWidth() / Console2048.ROWS;
		tileHeight = this.getHeight() / Console2048.COLS; 
	}
	
	private void drawNumberedTile(Graphics g, int row, int col) {
		int[][] numbers = game.getBoard();
		int tileValue = numbers[row][col];
		Color tileColor = getColor(tileValue);
		drawTile(g, row, col, tileColor);	
		g.setColor(Color.WHITE);
		g.setFont(FONT);
		int textX = row * tileWidth;
		int textY = col * tileHeight + tileHeight;
		if(tileValue != 0) {
			g.drawString(Integer.toString(tileValue), textX, textY);
		}

	}
	
	private void drawTile(Graphics g, int row, int col, Color color) {
		int x = row * tileWidth;
		//System.out.println(x);
		int y = col * tileHeight;
		g.setColor(color);
		g.fillRect(x, y, tileWidth, tileHeight);
		//System.out.println(color);
		
	}
	
	public void paintComponent(Graphics g) {
		setTileSize();
		for(int r = 0; r < Console2048.ROWS; r++) {
			for(int c = 0; c < Console2048.COLS; c++) {
				/*
				double Rd = Math.random() * 256;
				double Gd = Math.random() * 256;
				double Bd = Math.random() * 256;
				int R = (int) Rd;
				int G = (int) Gd;
				int B = (int) Bd;
				Color color = new Color(R, G, B);
				drawTile(g, r, c, color);
				*/
				drawNumberedTile(g, r, c);
				
			}
		}
		
	}
	//i dont think this code works yet
	public Color getColor(int value) { //untested code
		if(value == 0) {
			return COLORS[0];
			
		} else {
			int counter = 1;
			for(int i = 1; i < COLORS.length; i++) {
				if(value == (int)Math.pow(2, counter)) {
					//System.out.print(value);
					return COLORS[i];
				} else {
					counter++;
				}
			}
		}
		return null;
		
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		game.storeBoard();
		int keyCode = e.getKeyCode();
		if(keyCode == KeyEvent.VK_W) {
			game.processMove(Console2048.LEFT);
		}
		if(keyCode == KeyEvent.VK_A) {
			game.processMove(Console2048.UP);
		}
		if(keyCode == KeyEvent.VK_S) {
			game.processMove(Console2048.RIGHT);
		}
		if(keyCode == KeyEvent.VK_D) {
			game.processMove(Console2048.DOWN);
		}
		if(game.hasBoardChanged()) {
			game.addNewTile();
			if(game.checkWin()) {
				System.out.println("Congrats, you won!");
			}
			if(game.checkLoss()) {
				System.out.println("You lost");
			}
		}
		this.repaint();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
