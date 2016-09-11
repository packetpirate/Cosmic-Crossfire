package com.cosmic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.cosmic.behaviors.Formation;
import com.cosmic.entities.Enemy;
import com.cosmic.entities.Player;
import com.cosmic.entities.Projectile;
import com.cosmic.gfx.Starfield;
import com.cosmic.utils.IDGenerator;
import com.cosmic.utils.Pair;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Framework {
	public static final int CANVAS_WIDTH = 600;
	public static final int CANVAS_HEIGHT = 600;
	public static final long NANO_TO_SECS = 1000000000L;
	public static final long NANO_TO_MS = 1000000L;
	
	public static final Random rand = new Random();
	
	private Stage mainStage;
	private Scene mainScene;
	
	private GraphicsContext gc;
	
	private List<String> input;
	
	private Starfield starfield;
	private Player player;
	
	private List<Enemy> enemies;
	private List<Projectile> projectiles;
	
	private List<Formation> formations;
	
	public Framework(Stage stage) {
		mainStage = stage;
		mainStage.setResizable(false);
		mainStage.setMaxWidth(Framework.CANVAS_WIDTH);
		mainStage.setMaxHeight(Framework.CANVAS_HEIGHT);
		mainStage.requestFocus();
		mainStage.centerOnScreen();
		
		Group root = new Group();
		mainScene = new Scene(root);
		mainStage.setScene(mainScene);
		
		Canvas canvas = new Canvas(Framework.CANVAS_WIDTH, Framework.CANVAS_HEIGHT);
		root.getChildren().add(canvas);
		
		gc = canvas.getGraphicsContext2D();
		
		input = new ArrayList<String>();
		mainScene.setOnKeyPressed(keyPress);
		mainScene.setOnKeyReleased(keyRelease);
		
		starfield = new Starfield();
		player = new Player();
		
		enemies = new ArrayList<Enemy>();
		projectiles = new ArrayList<Projectile>();
		formations = new ArrayList<Formation>();
		
		final long startTime = System.nanoTime();
		
		//form1 = new Formation(Enemy.Type.KAMIKAZE, new Pair<Double>((Framework.CANVAS_WIDTH + 32.0), 32.0), () -> MovementBehavior.FORM_ORBIT(), null, (startTime / Framework.NANO_TO_MS), 1500);
		
		new AnimationTimer() {
			@Override
			public void handle(long currentTime) {
				currentTime /= Framework.NANO_TO_MS;
				update(currentTime);
				render();
			}
		}.start();
		
		mainStage.show();
	}
	
	private void reset() {
		enemies.clear();
		projectiles.clear();
		IDGenerator.resetID();
	}
	
	private void update(long currentTime) {
		starfield.update(currentTime);
		
		checkProjectileCollisions();
		checkShipCollisions();
		
		player.update(currentTime, input);
		
		Iterator<Enemy> eit = enemies.iterator();
		while(eit.hasNext()) {
			Enemy e = eit.next();
			List<Projectile> shots = e.update(currentTime, player.getPosition());
			projectiles.addAll(shots);
		}
		
		Iterator<Projectile> pit = projectiles.iterator();
		while(pit.hasNext()) {
			Projectile p = pit.next();
			p.update();
		}
		
		// 
		Iterator<Formation> fit = formations.iterator();
		while(fit.hasNext()) {
			Formation f = fit.next();
			if(!f.onCooldown(currentTime)) enemies.addAll(f.produce(currentTime));
		}
	}
	
	private void checkProjectileCollisions() {
		// Check projectiles for collisions.
		Iterator<Projectile> pit = projectiles.iterator();
		Iterator<Enemy> eit = enemies.iterator();
		while(pit.hasNext()) {
			Projectile p = pit.next();
			if(!inBounds(p.getPosition().x, p.getPosition().y)) {
				pit.remove();
				continue;
			} else if(player.collision(p.getPosition())) {
				pit.remove();
				player.die();
				if(player.gameOver()) reset();
				continue;
			}
			
			while(eit.hasNext()) {
				Enemy e = eit.next();
				if(e.collision(p.getPosition()) && !e.isParent(p.getID())) {
					eit.remove();
					pit.remove();
					break;
				}
			}
		}
	}
	
	private void checkShipCollisions() {
		// Check ship-to-ship collisions.
		for(int i = 0; i < enemies.size(); i++) {
			Enemy ei = enemies.get(i);
			for(int j = (i + 1); j < enemies.size(); j++) {
				Enemy ej = enemies.get(j);
				double dist = Framework.distance(ei.getPosition(), ej.getPosition());
				if(!(enemies.get(i).equals(enemies.get(j))) && 
					(dist <= (ei.getShipSize() + ej.getShipSize()))) {
					// Collision detected between these two enemies.
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
	}
	
	private void render() {
		gc.setFill(Color.BLACK);
		gc.fillRect(0, 0, Framework.CANVAS_WIDTH, Framework.CANVAS_HEIGHT);
		
		starfield.render(gc);
		
		Iterator<Projectile> pit = projectiles.iterator();
		while(pit.hasNext()) {
			Projectile p = pit.next();
			p.render(gc);
		}
		
		Iterator<Enemy> eit = enemies.iterator();
		while(eit.hasNext()) {
			Enemy e = eit.next();
			e.render(gc);
		}
		
		player.render(gc);
	}
	
	public static boolean inBounds(double x, double y) {
		return ((x >= 0) && 
				(x <= Framework.CANVAS_WIDTH) && 
				(y >= 0) && 
				(y <= Framework.CANVAS_HEIGHT));
	}
	
	public static double distance(Pair<Double> src, Pair<Double> target) {
		double a = (target.x - src.x);
		double b = (target.y - src.y);
		return Math.sqrt((a * a) + (b * b));
	}
	
	public static boolean inRange(Pair<Double> src, Pair<Double> target, double dist) {
		double a = (target.x - src.x);
		double b = (target.y - src.y);
		double d = Math.sqrt((a * a) + (b * b));
		return (d <= dist);
	}
	
	public static double getHypotenuse(Pair<Double> src, Pair<Double> target) {
		return Math.atan2((target.y - src.y), (target.x - src.x));
	}
	
	EventHandler<KeyEvent> keyPress = key -> {
		String code = key.getCode().toString();
		if(!input.contains(code)) {
			input.add(code);
			if(code.equals("W")) player.setState(1);
		}
	};
	
	EventHandler<KeyEvent> keyRelease = key -> {
		String code = key.getCode().toString();
		input.remove(code);
		if(code.equals("W")) player.setState(0);
	};
}
