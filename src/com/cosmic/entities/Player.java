package com.cosmic.entities;

import com.cosmic.utils.Pair;

public class Player {
	public static final double SHIP_SIZE = 32;
	
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
	
	public Player() {
		
	}
	
	public void reset(boolean gameOver) {
		
	}
	
	public void update(long currentTime) {
		
	}
}
