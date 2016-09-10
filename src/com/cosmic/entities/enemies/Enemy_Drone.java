package com.cosmic.entities.enemies;

import com.cosmic.behaviors.MovementBehavior;
import com.cosmic.behaviors.WeaponBehavior;
import com.cosmic.entities.Enemy;
import com.cosmic.utils.Pair;

import javafx.scene.canvas.GraphicsContext;

public class Enemy_Drone extends Enemy {
	public Enemy_Drone(Pair<Double> pos) {
		super(pos, MovementBehavior.SHIP_DRONE, WeaponBehavior.BASIC_FIRE);
	}

	@Override
	public void update(long currentTime, Pair<Double> playerPos) {
		
	}

	@Override
	public void render(GraphicsContext gc) {
		
	}
}
