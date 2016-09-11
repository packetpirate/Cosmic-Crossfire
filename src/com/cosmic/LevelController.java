package com.cosmic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.cosmic.behaviors.Formation;
import com.cosmic.behaviors.MovementBehavior;
import com.cosmic.behaviors.WeaponBehavior;
import com.cosmic.entities.Enemy;
import com.cosmic.entities.Player;
import com.cosmic.entities.Projectile;
import com.cosmic.gfx.Starfield;
import com.cosmic.utils.IDGenerator;
import com.cosmic.utils.Pair;

import javafx.scene.canvas.GraphicsContext;

public class LevelController {
	private int level;
	public int getLevel() { return level; }
	
	private Starfield starfield;
	public Starfield getStarfield() { return starfield; }
	
	private List<Enemy> enemies;
	public List<Enemy> getEnemies() { return enemies; }
	
	private List<Projectile> projectiles;
	public List<Projectile> getProjectiles() { return projectiles; }
	
	private List<Formation> formations;
	public List<Formation> getFormations() { return formations; }
	public void executeFormation(Formation f) {
		formations.add(f);
	}
	
	public LevelController(long startTime) {
		starfield = new Starfield();
		enemies = new ArrayList<Enemy>();
		projectiles = new ArrayList<Projectile>();
		formations = new ArrayList<Formation>();
		
		formations.add(new Formation(Enemy.Type.DRONE, new Pair<Double>((Framework.CANVAS_WIDTH + 32.0), 32.0), 
									() -> MovementBehavior.FORM_ORBIT(), () -> WeaponBehavior.BASIC_FIRE(true), 
									(startTime / Framework.NANO_TO_MS), 1500L, 4));
	}
	
	private void reset() {
		enemies.clear();
		projectiles.clear();
		formations.clear();
		IDGenerator.resetID();
	}
	
	public void update(long currentTime, Player player) {
		// Update the starfield animation.
		starfield.update(currentTime);
		
		// Check collisions between game objects.
		checkProjectileCollisions(player);
		checkShipCollisions(player);
		
		// Update enemies and add their projectiles to the projectile list.
		Iterator<Enemy> eit = enemies.iterator();
		while(eit.hasNext()) {
			Enemy e = eit.next();
			List<Projectile> shots = e.update(currentTime, player.getPosition());
			projectiles.addAll(shots);
		}
				
		// Handle projectile movement.
		Iterator<Projectile> pit = projectiles.iterator();
		while(pit.hasNext()) {
			Projectile p = pit.next();
			p.update();
		}
		
		// Produce enemies from formations and remove formations that have finished.
		Iterator<Formation> fit = formations.iterator();
		while(fit.hasNext()) {
			Formation f = fit.next();
			if(!f.onCooldown(currentTime)) enemies.addAll(f.produce(currentTime));
			if(f.isFinished()) fit.remove();
		}
	}
	
	private void checkProjectileCollisions(Player player) {
		// Check projectiles for collisions.
		Iterator<Projectile> pit = projectiles.iterator();
		Iterator<Enemy> eit = enemies.iterator();
		while(pit.hasNext()) {
			Projectile p = pit.next();
			if(!Framework.inBounds(p.getPosition().x, p.getPosition().y)) {
				// The projectile has gone off-screen. Delete it.
				pit.remove();
				continue;
			} else if(player.collision(p.getPosition())) {
				// The projectile has collided with the player. Destroy it and kill the player.
				pit.remove();
				player.die();
				if(player.gameOver()) reset();
				continue;
			}
			
			// Check for collisions between ships and projectiles.
			while(eit.hasNext()) {
				Enemy e = eit.next();
				// Make sure the ship we're checking isn't the one that created the projectile.
				if(e.collision(p.getPosition()) && !e.isParent(p.getID())) {
					// Collision detected! Destroy the ship and the projectile.
					eit.remove();
					pit.remove();
					break;
				}
			}
		}
	}
	
	private void checkShipCollisions(Player player) {
		// Check ship-to-ship collisions.
		for(int i = 0; i < enemies.size(); i++) {
			Enemy ei = enemies.get(i);
			for(int j = (i + 1); j < enemies.size(); j++) {
				Enemy ej = enemies.get(j);
				double dist = Framework.distance(ei.getPosition(), ej.getPosition());
				if(!(enemies.get(i).equals(enemies.get(j))) && 
					(dist <= ((ei.getShipSize() / 2) + (ej.getShipSize() / 2)))) {
					// Collision detected between these two enemies. Destroy both.
					ei.collide();
					ej.collide();
				}
			}
			
			// Also check for collision with the player.
			if(player.isAlive() && player.collision(ei)) {
				ei.collide();
				player.die();
				if(player.gameOver()) reset();
			}
		}
		
		// Destroy enemies who have collided with something.
		Iterator<Enemy> it = enemies.iterator();
		while(it.hasNext()) {
			Enemy e = it.next();
			if(e.isColliding()) it.remove();
		}
	}
	
	public void renderObjects(GraphicsContext gc) {
		starfield.render(gc);
		
		// Draw projectiles to the screen.
		Iterator<Projectile> pit = projectiles.iterator();
		while(pit.hasNext()) {
			Projectile p = pit.next();
			p.render(gc);
		}
		
		// Draw enemies to the screen.
		Iterator<Enemy> eit = enemies.iterator();
		while(eit.hasNext()) {
			Enemy e = eit.next();
			e.render(gc);
		}
	}
}
