/* author: Tim Larocque
 * colaborator: Darin Beaudreau
 * date: 9/9/2016
 */

package com.cosmic.entities;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class PowerUps {
	public enum Type {
		PROT_SHIELD(5000),
		REFL_SHIELD(3000),
		PHAS_SHIELD(5000),
		SPEED_BOOST(5000),
		SHIP_REPAIR(0),
		SHOT_DISPLC(0),
		INVINCIBLE(2000);
		
		private long duration, startTime;
		
		private Type(long d) {
			this.duration = d;
			this.startTime = 0;
		}
		
		public void start(long currentTime){
			this.startTime = currentTime;
		}
		
		public long getDuration() { return duration; }
	}
	
	private Map<Type, Boolean> powerUps;
	
	public PowerUps() {
		powerUps = new HashMap<>();
		for(Type t : Type.values()) {
			powerUps.put(t, false);
		}
	}
	
	public void addPowerUp(Type t, long currentTime){
		System.out.println("Player gained: " + t.toString());
		powerUps.put(t, true);
		t.start(currentTime);
	}
	
	public void update(long currentTime) {
		// Check for expired power-ups.
		Iterator<Entry<Type, Boolean>> it = powerUps.entrySet().iterator();
		while(it.hasNext()) {
			Entry<Type, Boolean> entry = it.next();
			if(entry.getValue()) {
				System.out.println(entry.getKey().toString() + " set to true!");
				if(!isActive(entry.getKey(), currentTime)) {
					System.out.println("Expired: " + entry.getKey().toString());
					remPowerUp(entry.getKey());
				}
			}
		}
	}
	
	public boolean isActive(Type t, long currentTime) {
		if(powerUps.get(t) == Boolean.FALSE) return false;
		System.out.println(t.toString() + " set to true!");
		final long timeDiff = currentTime - t.startTime;
		
		if(timeDiff >= t.duration) {
			System.out.println(t.toString() + "\'s time is up!");
			return false;
		} else {
			System.out.println(t.toString() + " is still active!");
			return true;
		}
	}
	
	public void remPowerUp(Type t) {
		powerUps.put(t, false);
	}
}
