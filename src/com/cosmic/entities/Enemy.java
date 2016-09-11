package com.cosmic.entities;

import java.util.List;

import com.cosmic.Framework;
import com.cosmic.behaviors.MovementBehavior;
import com.cosmic.behaviors.WeaponBehavior;
import com.cosmic.entities.enemies.Enemy_Drone;
import com.cosmic.entities.enemies.Enemy_Kamikaze;
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
	public double getTheta() { return theta; }
	public void setTheta(double theta) { this.theta = theta; }
	
	private MovementBehavior mb;
	public MovementBehavior getMovementBehavior() { return mb; }
	public void overrideMovementBehavior(MovementBehavior mb) { this.mb = mb; }
	public void move(Pair<Double> playerPos, long currentTime) { mb.move(position, playerPos, currentTime); }
	
	private WeaponBehavior wb;
	public WeaponBehavior getWeaponBehavior() { return wb; }
	public void overrideWeaponBehavior(WeaponBehavior wb) { this.wb = wb; }
	public void fire(Pair<Double> playerPos, long currentTime) { 
		wb.fire(id, position, playerPos, Framework.getHypotenuse(position, playerPos), currentTime); 
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
	
	public enum Type {
		DRONE, KAMIKAZE;
		
		public static Enemy createInstance(Type t, Pair<Double> pos) {
			Enemy e = null;
			
			if(t == Type.DRONE) return new Enemy_Drone(pos);
			else if(t == Type.KAMIKAZE) return new Enemy_Kamikaze(pos);
			
			return e;
		}
		
		public static Type randomType(Pair<Double> pos) {
			int r = Framework.rand.nextInt(2);
			if(r == 0) return DRONE;
			else if(r == 1) return KAMIKAZE;
			else return DRONE;
		}
	}
}
