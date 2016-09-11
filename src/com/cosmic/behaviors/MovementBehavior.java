package com.cosmic.behaviors;

import com.cosmic.Framework;
import com.cosmic.entities.Player;
import com.cosmic.entities.enemies.Enemy_Drone;
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
	public Pair<Double> move(Pair<Double> currPos, Pair<Double> playerPos, double deltaTime) {
		// Must be overridden.
		return null;
	}
	
	// =========================================
	// PRE-DEFINED MOVEMENT BEHAVIORS START HERE
	
	public static final MovementBehavior SHIP_DRONE = new MovementBehavior(2.0) {
		@Override
		public Pair<Double> move(Pair<Double> currPos, Pair<Double> playerPos, double deltaTime) {
			Pair<Double> newPos = new Pair<>(currPos.x, currPos.y);
			
			if(!Framework.inRange(currPos, playerPos, Enemy_Drone.FIRING_DIST)) {
				// Move closer to the player.
				double theta = Framework.getHypotenuse(currPos, playerPos);
				newPos.x += getSpeed() * Math.cos(theta);
				newPos.y += getSpeed() * Math.sin(theta);
			}
			
			return newPos;
		}
	};
	
	public static final MovementBehavior SHIP_KAMIK = new MovementBehavior(2.0) {

		@Override
		public Pair<Double> move(Pair<Double> currPos, Pair<Double> playerPos, double deltaTime) {
			Pair<Double> newPos = new Pair<>(currPos.x, currPos.y);
			if(currPos != playerPos) {
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
	// =======================================
	// PRE-DEFINED FORMATION BEHAVIORS START
	
	public static final MovementBehavior FORM_ORBIT = new MovementBehavior(2.5) {
		@Override
		public Pair<Double> move(Pair<Double> currPos, Pair<Double> playerPos, double deltaTime) {
			Pair<Double> newPos = new Pair<>(currPos.x, currPos.y);
			
			double r = (Framework.CANVAS_WIDTH / 2) - Player.SHIP_SIZE;
			double cX = (Framework.CANVAS_WIDTH / 2);
			double cY = (Framework.CANVAS_HEIGHT / 2);
			newPos.x = cX + (r * Math.cos(Framework.getHypotenuse(currPos, new Pair<Double>(cX, cY)) + (Math.PI / 90)));
			newPos.y = cY + (r * Math.sin(deltaTime * Framework.getHypotenuse(currPos, new Pair<Double>(cX, cY)) + (Math.PI / 90)));
			
			return newPos;
		}
	};
	
	// MOVEMENT BEHAVIOR SKELETON HERE
	/*
	 * public static final MovementBehavior MOVE_NAME = new MovementBehavior(double moveSpeed) {
		   @Override
		   public Pair<Double> move(Pair<Double> currPos, Pair<Double> playerPos) {
			   Pair<Double> newPos = new Pair<>(currPos.x, currPos.y);
			
			   return newPos;
		   }
	   };
	 */
	
	// PRE-DEFINED FORMATION BEHAVIORS END
}
