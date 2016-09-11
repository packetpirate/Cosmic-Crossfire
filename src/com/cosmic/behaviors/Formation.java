package com.cosmic.behaviors;

import com.cosmic.entities.Enemy;
import com.cosmic.utils.Pair;

public class Formation {
	public static Enemy GenerateEnemy(Enemy.Type type, Pair<Double> spawn, 
									  MovementBehavior mbOverride, WeaponBehavior wbOverride) {
		Enemy e = Enemy.Type.createInstance(type, spawn);
		
		if(mbOverride != null) e.overrideMovementBehavior(mbOverride);
		if(wbOverride != null) e.overrideWeaponBehavior(wbOverride);
		
		return e;
	}
}
