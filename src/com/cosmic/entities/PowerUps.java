package com.cosmic.entities;

import java.util.Map;

public class PowerUps {
	// Variables
	// Private
	
	// The key of the map of power ups
	public enum Type {
		PROT_SHIELD(5000),
		REFL_SHIELD(3000);
		
		private long duration, startTime;
		private Type(long d) {
			this.duration = d;
		}
		
		public void start(long currentTime){
			this.startTime = currentTime;
		}
		
		public long getDuration() { return duration; }
	}
	
	// Type references the powerUp name and duration
	// Booleans determine if the player has the power up
	private Map<Type, Boolean> powerUps;
	
	// Functions
	// Constructor
	public PowerUps() {
		// TODO: create powerUps map
	}
	
	public void addPowerUp(Type t){
		// TODO
	}
}