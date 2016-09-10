package com.cosmic.behaviors;

import java.util.ArrayList;
import java.util.List;

import com.cosmic.Framework;
import com.cosmic.entities.Projectile;
import com.cosmic.utils.Pair;

public class WeaponBehavior {
	private long cooldown;
	private long lastFired;
	
	public WeaponBehavior(long cooldown) {
		this.cooldown = cooldown;
		this.lastFired = 0;
	}
	
	/**
	 * This method must be overridden. This method is used to define the weapon behavior of the enemy.
	 * @param pos The starting position of the projectile to be fired.
	 * @param theta The direction for the projectile to move in.
	 * @return A projectile representing the one fired from the enemy weapon.
	 */
	public List<Projectile> fire(Pair<Double> pos, double theta, long currentTime) {
		return null;
	}
	
	public boolean canFire(long currentTime) {
		return ((currentTime - lastFired) >= cooldown);
	}
	
	public void recharge(long currentTime) {
		lastFired = currentTime;
	}
	
	// PRE-DEFINED WEAPON BEHAVIORS START
	
	public static final WeaponBehavior BASIC_FIRE = new WeaponBehavior(1000) {
		@Override
		public List<Projectile> fire(Pair<Double> pos, double theta, long currentTime) {
			recharge(currentTime);
			Projectile p = new Projectile(pos, theta, 5.0, 4.0);
			List<Projectile> shots = new ArrayList<Projectile>();
			shots.add(p);
			return shots;
		}
	};
	
	public static final WeaponBehavior RAPID_FIRE = new WeaponBehavior(500) {
		@Override
		public List<Projectile> fire(Pair<Double> pos, double theta, long currentTime) {
			recharge(currentTime);
			Projectile p = new Projectile(pos, theta, 5.0, 4.0);
			List<Projectile> shots = new ArrayList<Projectile>();
			shots.add(p);
			return shots;
		}
	};
	
	public static final WeaponBehavior SHOTG_FIRE = new WeaponBehavior(2000) {
		@Override
		public List<Projectile> fire(Pair<Double> pos, double theta, long currentTime) {
			recharge(currentTime);
			List<Projectile> shots = new ArrayList<Projectile>();
			for(int i = 0; i < 5; i++) {
				double th = (theta - 20.0) + (Framework.rand.nextDouble() * 40.0);
				Projectile p = new Projectile(pos, th, 5.0, 5.0);
			}
			
			return shots;
		}
	};
	
	// PRE-DEFINED WEAPON BEHAVIORS END
}
