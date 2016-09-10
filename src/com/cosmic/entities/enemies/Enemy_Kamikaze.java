package com.cosmic.entities.enemies;

import com.cosmic.behaviors.MovementBehavior;
import com.cosmic.behaviors.WeaponBehavior;
import com.cosmic.entities.Enemy;
import com.cosmic.utils.Pair;

import javafx.scene.canvas.GraphicsContext;

public class Enemy_Kamikaze extends Enemy{

	public Enemy_Kamikaze(Pair<Double> pos) {
		super(pos, mb, wb);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update(long currentTime, Pair<Double> playerPos) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(GraphicsContext gc) {
		// TODO Auto-generated method stub
		
	}

}
