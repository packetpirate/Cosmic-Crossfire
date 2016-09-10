package com.cosmic.entities;

import com.cosmic.Framework;
import com.cosmic.utils.Pair;
import com.cosmic.utils.Tools;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Player {
	public static final double SHIP_SIZE = 32;
	public static final int MAX_HEALTH = 4;
	public static final int MAX_LIVES = 3;
	private static final boolean SHOW_COLLIDER = false;
	
	private int health;
	public int getHealth() { return health; }
	public void takeDamage() { health--; }
	public boolean isAlive() { return (health >= 0); }
	
	private int lives;
	public int getLives() { return lives; }
	public void die() { lives--; }
	public boolean gameOver() { return (lives == 0); }
	
	private Pair<Double> position;
	public Pair<Double> getPosition() { return position; }
	public void move() {
		
	}
	
	private PowerUps pwrUps;
	public PowerUps getPowerUps() { return pwrUps; }
	
	private Image image;
	public Image getImage() { return image; }
	
	public Player() {
		reset(true);
		
		image = Tools.LoadImage("player_ship.png");
	}
	
	public void reset(boolean gameOver) {
		position = new Pair<Double>((double)(Framework.CANVAS_WIDTH / 2), 
									(double)(Framework.CANVAS_HEIGHT / 2));
		pwrUps = new PowerUps();
		if(gameOver) {
			health = Player.MAX_HEALTH;
			lives = Player.MAX_LIVES;
		}
	}
	
	public void update(long currentTime) {
		
	}
	
	public void render(GraphicsContext gc) {
		double x = 0;
		double y = 0;
		
		if(image != null) {
			x = position.x - (image.getWidth() / 2);
			y = position.y - (image.getHeight() / 2);
			gc.drawImage(image, x, y);
		} else {
			x = position.x - (Player.SHIP_SIZE / 2);
			y = position.y - (Player.SHIP_SIZE / 2);
			
			gc.setFill(Color.RED);
			gc.setStroke(Color.BLACK);
			gc.fillRect(x, y, Player.SHIP_SIZE, Player.SHIP_SIZE);
			gc.strokeRect(x, y, Player.SHIP_SIZE, Player.SHIP_SIZE);
		}
		
		if(Player.SHOW_COLLIDER) {
			gc.setStroke(Color.GREEN);
			gc.strokeOval((position.x - (Player.SHIP_SIZE / 2)), 
						  (position.y - (Player.SHIP_SIZE / 2)), 
						   Player.SHIP_SIZE, Player.SHIP_SIZE);
		}
	}
}
