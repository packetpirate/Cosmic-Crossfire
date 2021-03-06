package com.cosmic.entities.enemies;

import java.util.ArrayList;
import java.util.List;

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

public class Enemy_Kamikaze extends Enemy{

	public Enemy_Kamikaze(Pair<Double> pos) {
		super(IDGenerator.createID(), pos, MovementBehavior.SHIP_KAMIK(), WeaponBehavior.BASIC_FIRE(false));
		image = Enemy.SHIP_KAMIK;
	}

	@Override
	public List<Projectile> update(long currentTime, Pair<Double> playerPos) {
		List<Projectile> shots = new ArrayList<Projectile>();
		Pair<Double> newPos = getMovementBehavior().move(getPosition(), playerPos, currentTime);
		theta = getMovementBehavior().getHeading(position, newPos, playerPos);
		position = newPos;
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
