package com.cosmic.entities;

import com.cosmic.Framework;
import com.cosmic.behaviors.MovementBehavior;
import com.cosmic.behaviors.WeaponBehavior;
import com.cosmic.utils.Pair;

import javafx.scene.canvas.GraphicsContext;

public class Enemy {
	private Pair<Double> position;
	public Pair<Double> getPosition() { return position; }
	
	private MovementBehavior mb;
	public MovementBehavior getMovementBehavior() { return mb; }
	public void move(Pair<Double> playerPos) { mb.move(position, playerPos); }
	
	private WeaponBehavior wb;
	public WeaponBehavior getWeaponBehavior() { return wb; }
	public void fire(Pair<Double> playerPos, long currentTime) { 
		wb.fire(position, Framework.getHypotenuse(position, playerPos), currentTime); 
	}
	
	public Enemy(double x, double y) { // Number One
		this(x, y, MovementBehavior.SHIP_DRONE);
	}
	
	public Enemy(double x, double y, MovementBehavior movement) { // Number One
		this.position.x = x;
		this.position.y = y;
		this.mb = movement;
	}
	
	public void update(long deltaTime) {
		// To be overridden...
	}
	
	public void render(GraphicsContext gc) {
		// To be overridden.
	}
}
