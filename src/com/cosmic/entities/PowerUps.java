/* author: Tim Larocque
 * colaborator: Darin Beaudreau
 * date: 9/9/2016
 */

package com.cosmic.entities;

import java.util.HashMap;
import java.util.Map;

public class PowerUps {
	/*********************/
	/***** Variables *****/
	/*********************/
	// The key of the map of power ups
	public enum Type {
		// TODO: add times for powerUps
		PROT_SHIELD(5000),
		REFL_SHIELD(3000),
		TRAC_SHIELD(0),
		PHAS_SHIELD(0),
		SPEED_BOOST(0),
		SHIP_REPAIR(0),
		SHOT_DISPLC(0);
		
		private long duration, startTime;
		
		// when each "Type" is created, makes the duration equal
		// to how long the powerUp should last
		private Type(long d) {
			this.duration = d;
		}
		
		// If the powerUp has been activated, startTime will be
		// initiated at the currentTime of the program
		public void start(long currentTime){
			this.startTime = currentTime;
		}
		// duration is private so this is needed so that other
		// functions can retrieve the duration if needed
		public long getDuration() { return duration; }
	}
	
	// Type references the powerUp name and duration
	// Booleans determine if the player has the power up
	private Map<Type, Boolean> powerUps;
	
	/*********************/
	/***** Functions *****/
	/*********************/
	
	// Constructor
	// Fills the map with the powerUps and sets the values to false
	// because the player does not initially have any powerUps
	public PowerUps() {
		powerUps = new HashMap<>();
		for(Type t : Type.values()) {
			powerUps.put(t, false);
		}
	}
	
	// Turns the powerUp "on"
	public void addPowerUp(Type t, long currentTime){
		powerUps.put(t, true);
		t.start(currentTime);
	}
	
	// Checks to see if the player currently has 
	// the powerUp in question
	public boolean isActive(Type t, long currentTime) {
		final long timeDiff = currentTime - t.startTime;
		
		if(timeDiff >= t.duration)
			return false;
		else
			return true;
	}
	
	// Turns the powerUp "off"
	public void remPowerUp(Type t) {
		powerUps.put(t, false);
	}
}
