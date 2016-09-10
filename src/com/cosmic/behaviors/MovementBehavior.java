package com.cosmic.behaviors;

import com.cosmic.Framework;
import com.cosmic.utils.Pair;

public class MovementBehavior {
	private double speed;
	public double getSpeed() { return speed; }
	
	public MovementBehavior(double speed) {
		this.speed = speed;
	}
	
	/**
	 * This method must be overridden. This method is used to define the movement behavior of the enemy.
	 * @param currPos The current position of the enemy ship.
	 * @return The new position of the enemy ship after applying the movement behavior.
	 */
	public Pair<Double> move(Pair<Double> currPos, Pair<Double> playerPos) {
		// Must be overridden.
		return null;
	}
	
	// PRE-DEFINED MOVEMENT BEHAVIORS START HERE
	
	public static final MovementBehavior SHIP_DRONE = new MovementBehavior(5.0) {
		@Override
		public Pair<Double> move(Pair<Double> currPos, Pair<Double> playerPos) {
			Pair<Double> newPos = new Pair<>(currPos.x, currPos.y);
			
			if(!Framework.inRange(currPos, playerPos, 150.0)) {
				// Move closer to the player.
				double theta = Framework.getHypotenuse(currPos, playerPos);
				newPos.x += getSpeed() * Math.cos(theta);
				newPos.y += getSpeed() * Math.sin(theta);
			}
			
			return newPos;
		}
	};
	
	// TODO: More movement behaviors!
	
	// PRE-DEFINED MOVEMENT BEHAVIORS END HERE
}
