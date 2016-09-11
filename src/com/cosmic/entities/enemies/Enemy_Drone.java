package com.cosmic.entities.enemies;

import java.util.ArrayList;
import java.util.List;

import com.cosmic.Framework;
import com.cosmic.Globals;
import com.cosmic.behaviors.MovementBehavior;
import com.cosmic.behaviors.WeaponBehavior;
import com.cosmic.entities.Enemy;
import com.cosmic.entities.Projectile;
import com.cosmic.utils.IDGenerator;
import com.cosmic.utils.Pair;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;

public class Enemy_Drone extends Enemy {
	public static final double FIRING_DIST = 150.0;
	
	public Enemy_Drone(Pair<Double> pos) {
		super(IDGenerator.createID(), pos, MovementBehavior.SHIP_DRONE, WeaponBehavior.BASIC_FIRE);
		image = Enemy.SHIP_DRONE;
	}

	@Override
	public List<Projectile> update(long currentTime, double deltaTime, Pair<Double> playerPos) {
		position = getMovementBehavior().move(getPosition(), playerPos, deltaTime);
		theta = Framework.getHypotenuse(getPosition(), playerPos);
		List<Projectile> shots = new ArrayList<Projectile>();
		if(getWeaponBehavior().canFire(currentTime) &&
		   Framework.inRange(position, playerPos, Enemy_Drone.FIRING_DIST)) {
			shots = getWeaponBehavior().fire(id, position, theta, currentTime);
		}
		return shots;
	}

	@Override
	public void render(GraphicsContext gc) {
		if(image != null) {
			Rotate r = new Rotate(Math.toDegrees(theta + (Math.PI / 2)), getPosition().x, getPosition().y);
			
			gc.save();
			gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
			
			double x = getPosition().x - (image.getWidth() / 2);
			double y = getPosition().y - (image.getHeight() / 2);
			gc.drawImage(image, x, y);
			
			gc.restore();
		}
		
		if(Globals.SHOW_COLLIDERS) {
			gc.setStroke(Color.GREEN);
			gc.strokeOval((position.x - (getShipSize() / 2)), 
						  (position.y - (getShipSize() / 2)), 
						   getShipSize(), getShipSize());
		}
	}
}
