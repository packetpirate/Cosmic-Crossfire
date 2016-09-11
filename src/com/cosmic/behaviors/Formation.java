package com.cosmic.behaviors;

import java.util.ArrayList;
import java.util.List;

import com.cosmic.entities.Enemy;
import com.cosmic.utils.Pair;

public class Formation {
	private Enemy.Type enemyType;
	private Pair<Double> spawnLocation;
	private MBGenerator mb;
	private WBGenerator wb;
	private long lastUpdate;
	private long cooldown;
	
	public Formation(Enemy.Type enemyType, Pair<Double> spawnLocation,
					 MBGenerator mb, WBGenerator wb,
					 long currentTime, long cooldown) {
		this.enemyType = enemyType;
		this.spawnLocation = spawnLocation;
		this.mb = mb;
		this.wb = wb;
		this.lastUpdate = currentTime;
		this.cooldown = cooldown;
	}
	
	public boolean onCooldown(long currentTime) {
		return ((currentTime - lastUpdate) < cooldown);
	}
	
	public List<Enemy> produce(long currentTime) {
		List<Enemy> enemies = new ArrayList<Enemy>();
		enemies.add(Formation.GenerateEnemy(enemyType, spawnLocation, 
										 (mb == null)?null:mb.create(), 
										 (wb == null)?null:wb.create()));
		lastUpdate = currentTime;
		return enemies;
	}
	
	public static Enemy GenerateEnemy(Enemy.Type type, Pair<Double> spawn, 
									  MovementBehavior mbOverride, WeaponBehavior wbOverride) {
		Enemy e = Enemy.Type.createInstance(type, spawn);
		
		if(mbOverride != null) e.overrideMovementBehavior(mbOverride);
		if(wbOverride != null) e.overrideWeaponBehavior(wbOverride);
		
		return e;
	}
}
