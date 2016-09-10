package com.cosmic.entities.enemies;

import java.util.List;

import com.cosmic.Framework;
import com.cosmic.behaviors.MovementBehavior;
import com.cosmic.behaviors.WeaponBehavior;
import com.cosmic.entities.Enemy;
import com.cosmic.entities.Projectile;
import com.cosmic.utils.Pair;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.transform.Rotate;

public class Enemy_Drone extends Enemy {
	public Enemy_Drone(Pair<Double> pos) {
		super(pos, MovementBehavior.SHIP_DRONE, WeaponBehavior.BASIC_FIRE);
		image = Enemy.SHIP_DRONE;
	}

	@Override
	public List<Projectile> update(long currentTime, Pair<Double> playerPos) {
		position = getMovementBehavior().move(getPosition(), playerPos);
		theta = Framework.getHypotenuse(getPosition(), playerPos);
		List<Projectile> shots = this.getWeaponBehavior().fire(playerPos, theta, currentTime);
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
	}
}
