package com.cosmic.behaviors;

import com.cosmic.entities.Projectile;
import com.cosmic.utils.Pair;

public class WeaponBehavior {
	private long cooldown;
	private long lastFired;
	
	public WeaponBehavior(long cooldown) {
		this.cooldown = cooldown;
		this.lastFired = Long.MAX_VALUE;
	}
	
	/**
	 * This method must be overridden. This method is used to define the weapon behavior of the enemy.
	 * @param pos The starting position of the projectile to be fired.
	 * @param theta The direction for the projectile to move in.
	 * @return A projectile representing the one fired from the enemy weapon.
	 */
	public Projectile fire(Pair<Double> pos, double theta, long currentTime) {
		return null;
	}
	
	public boolean canFire(long currentTime) {
		return ((currentTime - lastFired) >= cooldown);
	}
	
	public void recharge(long currentTime) {
		lastFired = currentTime;
	}
	
	// PRE-DEFINED WEAPON BEHAVIORS START
	
	public static final WeaponBehavior RAPID_FIRE = new WeaponBehavior(500) {
		@Override
		public Projectile fire(Pair<Double> pos, double theta, long currentTime) {
			recharge(currentTime);
			return new Projectile(pos, theta, 5.0, 4.0);
		}
	};
	
	// PRE-DEFINED WEAPON BEHAVIORS END
}
