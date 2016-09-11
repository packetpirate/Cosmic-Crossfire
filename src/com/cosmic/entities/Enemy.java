package com.cosmic.entities;

import java.util.List;

import com.cosmic.Framework;
import com.cosmic.behaviors.MovementBehavior;
import com.cosmic.behaviors.WeaponBehavior;
import com.cosmic.utils.Pair;
import com.cosmic.utils.Tools;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class Enemy {
	protected int id;
	public int getID() { return id; }
	public boolean isParent(int projID) { return (id == projID); }
	
	protected Pair<Double> position;
	public Pair<Double> getPosition() { return position; }
	private boolean colliding;
	public boolean isColliding() { return colliding; }
	public void collide() { colliding = true; }
	public boolean collision(Pair<Double> pos) {
		return Framework.inRange(pos, position, image.getWidth());
	}
	protected double theta;
	
	private MovementBehavior mb;
	public MovementBehavior getMovementBehavior() { return mb; }
	public void move(Pair<Double> playerPos) { mb.move(position, playerPos); }
	
	private WeaponBehavior wb;
	public WeaponBehavior getWeaponBehavior() { return wb; }
	public void fire(Pair<Double> playerPos, long currentTime) { 
		wb.fire(id, position, Framework.getHypotenuse(position, playerPos), currentTime); 
	}
	
	protected Image image;
	public double getShipSize() { return image.getWidth(); }

	public Enemy(int id, Pair<Double> pos, MovementBehavior mb, WeaponBehavior wb) { // Number One
		this.id = id;
		this.position = new Pair<Double>(pos.x, pos.y);
		this.theta = 0;
		this.mb = mb;
		this.wb = wb;
	}
	
	public abstract List<Projectile> update(long currentTime, Pair<Double> playerPos);
	public abstract void render(GraphicsContext gc);
	
	public static final Image SHIP_DRONE = Tools.LoadImage("enemy_ship1.png");
	public static final Image SHIP_KAMIK = Tools.LoadImage("enemy_ship2.png");
}
