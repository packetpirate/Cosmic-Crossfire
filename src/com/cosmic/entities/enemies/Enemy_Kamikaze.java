package com.cosmic.entities.enemies;

import java.util.ArrayList;
import java.util.List;

import com.cosmic.behaviors.MovementBehavior;
import com.cosmic.behaviors.WeaponBehavior;
import com.cosmic.entities.Enemy;
import com.cosmic.entities.Projectile;
import com.cosmic.utils.Pair;

import javafx.scene.canvas.GraphicsContext;

public class Enemy_Kamikaze extends Enemy{

	public Enemy_Kamikaze(Pair<Double> pos) {
		super(pos, MovementBehavior.SHIP_KAMIK, WeaponBehavior.BASIC_FIRE);
	}

	@Override
	public List<Projectile> update(long currentTime, Pair<Double> playerPos) {
		List<Projectile> shots = new ArrayList<Projectile>();
		
		getMovementBehavior().move(getPosition(), playerPos);
		
		return shots;
	}

	@Override
	public void render(GraphicsContext gc) {
		if(getImage() != null) {
			double x = getPosition().x - (getImage().getWidth() / 2);
			double y = getPosition().y - (getImage().getHeight() / 2);
			gc.drawImage(getImage(), x, y);
		}
	}

}
