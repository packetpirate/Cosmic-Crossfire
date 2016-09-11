package com.cosmic.entities;

import java.util.List;

import com.cosmic.Framework;
import com.cosmic.Globals;
import com.cosmic.utils.Pair;
import com.cosmic.utils.Tools;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;

public class Player {
	public static final double SHIP_SIZE = 32;
	public static final Image SHIP_IMAGE = Tools.LoadImage("player_phoenix.png");
	public static final Image THRUST_IMAGE = Tools.LoadImage("player_phoenix_thrust.png");
	public static final int MAX_HEALTH = 4;
	public static final int MAX_LIVES = 3;
	public static final double SPEED = 2.5;
	
	private int state;
	public int getState() { return state; }
	public void setState(int state) { 
		this.state = state;
		if(this.state == 0) image = Player.SHIP_IMAGE;
		else if(this.state == 1) image = Player.THRUST_IMAGE;
	}
	
	private int health;
	public int getHealth() { return health; }
	public void takeDamage() { health--; }
	public boolean isAlive() { return (health >= 0); }
	
	private int lives;
	public int getLives() { return lives; }
	public void die() {
		if(!isInvincible()) {
			lives--;
			reset(gameOver());
		}
	}
	public boolean gameOver() { return (lives == 0); }
	public boolean isInvincible() {
		return false;
	}
	
	private Pair<Double> position;
	public Pair<Double> getPosition() { return position; }
	private double theta;
	public void move() {
		position.x += Player.SPEED * Math.cos(theta);
		position.y += Player.SPEED * Math.sin(theta);
	}
	public boolean collision(Pair<Double> pos) {
		return Framework.inRange(pos, position, Player.SHIP_SIZE);
	}
	
	public boolean collision(Enemy e) {
		double dist = Framework.distance(getPosition(), e.getPosition());
		return (dist <= ((e.getShipSize() / 2) + (getShipSize() / 2)));
	}
	
	private PowerUps pwrUps;
	public PowerUps getPowerUps() { return pwrUps; }
	
	private Image image;
	public Image getImage() { return image; }
	public double getShipSize() { return image.getWidth(); }
	
	public Player() {
		state = 0;
		reset(true);
		image = Player.SHIP_IMAGE;
	}
	
	public void reset(boolean gameOver) {
		position = new Pair<Double>((double)(Framework.CANVAS_WIDTH / 2), 
									(double)(Framework.CANVAS_HEIGHT / 2));
		theta = -(Math.PI / 2);
		pwrUps = new PowerUps();
		
		if(this.state == 0) image = Player.SHIP_IMAGE;
		else if(this.state == 1) image = Player.THRUST_IMAGE;
		
		if(gameOver) {
			health = Player.MAX_HEALTH;
			lives = Player.MAX_LIVES;
		}
	}
	
	public void update(long currentTime, List<String> input) {
		if(input.contains("W")) move();
		if(input.contains("A")) theta -= (Math.PI / 45);
		if(input.contains("D")) theta += (Math.PI / 45);
	}
	
	public void render(GraphicsContext gc) {
		double x = 0;
		double y = 0;
		
		Rotate r = new Rotate(Math.toDegrees(theta + (Math.PI / 2)), position.x, position.y);
		gc.save();
		gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
		
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
		
		if(Globals.SHOW_COLLIDERS) {
			gc.setStroke(Color.GREEN);
			gc.strokeOval((position.x - (getShipSize() / 2)), 
					  	  (position.y - (getShipSize() / 2)), 
					  	   getShipSize(), getShipSize());
		}
		
		gc.restore();
	}
}
