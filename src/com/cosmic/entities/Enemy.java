package com.cosmic.entities;

import com.cosmic.Framework;
import com.cosmic.behaviors.MovementBehavior;
import com.cosmic.behaviors.WeaponBehavior;
import com.cosmic.entities.enemies.Enemy_Drone;
import com.cosmic.utils.Pair;
import com.cosmic.utils.Tools;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class Enemy {
	public static final Image SHIP_DRONE = Tools.LoadImage("enemy_ship1.png");
	
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
	
	private Image image;
	public Image getImage() { return image; }

	public Enemy(Pair<Double> pos, MovementBehavior mb, WeaponBehavior wb) { // Number One
		this.position.x = pos.x;
		this.position.y = pos.y;
		this.mb = mb;
		this.wb = wb;
	}
	
	public abstract void update(long currentTime, Pair<Double> playerPos);
	public abstract void render(GraphicsContext gc);
	
	public enum Type {
		DRONE;
		
		public Enemy createInstance(Type t, Pair<Double> pos) {
			if(t == Type.DRONE) return new Enemy_Drone(pos);
			else return null;
		}
		
		public Image getImage(Type t) {
			if(t == Type.DRONE) return Enemy.SHIP_DRONE;
			else return null;
		}
	}
}
