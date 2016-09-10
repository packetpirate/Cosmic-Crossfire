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
	protected Pair<Double> position;
	public Pair<Double> getPosition() { return position; }
	protected double theta;
	
	private MovementBehavior mb;
	public MovementBehavior getMovementBehavior() { return mb; }
	public void move(Pair<Double> playerPos) { mb.move(position, playerPos); }
	
	private WeaponBehavior wb;
	public WeaponBehavior getWeaponBehavior() { return wb; }
	public void fire(Pair<Double> playerPos, long currentTime) { 
		wb.fire(position, Framework.getHypotenuse(position, playerPos), currentTime); 
	}
	
	protected Image image;

	public Enemy(Pair<Double> pos, MovementBehavior mb, WeaponBehavior wb) { // Number One
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
