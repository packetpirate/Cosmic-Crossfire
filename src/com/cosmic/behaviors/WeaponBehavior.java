package com.cosmic.behaviors;

import java.util.ArrayList;
import java.util.List;

import com.cosmic.Framework;
import com.cosmic.entities.Projectile;
import com.cosmic.utils.Pair;

public class WeaponBehavior {
	private double range;
	public boolean inRange(double dist) { return (dist <= range); }
	private boolean targeted;
	public boolean isTargeted() { return targeted; }
	private long cooldown;
	private long lastFired;
	
	public WeaponBehavior(double range, boolean targeted, long cooldown) {
		this.range = range;
		this.targeted = targeted;
		this.cooldown = cooldown;
		this.lastFired = 0;
	}
	
	/**
	 * This method must be overridden. This method is used to define the weapon behavior of the enemy.
	 * @param pos The starting position of the projectile to be fired.
	 * @param theta The direction for the projectile to move in.
	 * @return A projectile representing the one fired from the enemy weapon.
	 */
	public List<Projectile> fire(int id, Pair<Double> pos, Pair<Double> playerPos, double theta, long currentTime) {
		return null;
	}
	
	public boolean canFire(long currentTime) {
		return ((currentTime - lastFired) >= cooldown);
	}
	
	public void recharge(long currentTime) {
		lastFired = currentTime;
	}
	
	// PRE-DEFINED WEAPON BEHAVIORS START
	
	public static final WeaponBehavior BASIC_FIRE(boolean targeted) {
		return new WeaponBehavior(250.0, targeted, 2000) {
			@Override
			public List<Projectile> fire(int id, Pair<Double> pos, Pair<Double> playerPos, double theta, long currentTime) {
				recharge(currentTime);
				if(isTargeted()) theta = Framework.getHypotenuse(pos, playerPos);
				Projectile p = new Projectile(id, pos, theta, 2.5, 4.0);
				List<Projectile> shots = new ArrayList<Projectile>();
				shots.add(p);
				return shots;
			}
		};
	}
	
	public static final WeaponBehavior RAPID_FIRE(boolean targeted) {
		return new WeaponBehavior(400.0, targeted, 1000) {
			@Override
			public List<Projectile> fire(int id, Pair<Double> pos, Pair<Double> playerPos, double theta, long currentTime) {
				recharge(currentTime);
				if(isTargeted()) theta = Framework.getHypotenuse(pos, playerPos);
				Projectile p = new Projectile(id, pos, theta, 3.0, 4.0);
				List<Projectile> shots = new ArrayList<Projectile>();
				shots.add(p);
				return shots;
			}
		};
	}
	
	public static final WeaponBehavior SHOTG_FIRE(boolean targeted) {
		return new WeaponBehavior(300.0, targeted, 3000) {
			@Override
			public List<Projectile> fire(int id, Pair<Double> pos, Pair<Double> playerPos, double theta, long currentTime) {
				recharge(currentTime);
				List<Projectile> shots = new ArrayList<Projectile>();
				if(isTargeted()) theta = Framework.getHypotenuse(pos, playerPos);
				for(int i = 0; i < 5; i++) {
					double th = (theta - (Math.PI / 9)) + (Framework.rand.nextDouble() * (Math.PI / 4.5));
					Projectile p = new Projectile(id, pos, th, 2.5, 5.0);
					shots.add(p);
				}
				
				return shots;
			}
		};
	}
	
	// PRE-DEFINED WEAPON BEHAVIORS END
}
