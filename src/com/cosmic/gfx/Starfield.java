package com.cosmic.gfx;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.cosmic.Framework;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Starfield {
	public static final double SPEED = 2.0;
	public static final long COOLDOWN = 150;
	
	private List<Rectangle<Double>> stars;
	public List<Rectangle<Double>> getStars() { return stars; }
	private long lastStar;
	private long cooldown;
	public boolean canCreate(long currentTime) {
		return ((currentTime - lastStar) >= cooldown);
	}
	
	public Starfield() {
		stars = new ArrayList<Rectangle<Double>>();
		for(int i = 0; i < 30; i++) {
			double x = Framework.rand.nextDouble() * Framework.CANVAS_WIDTH;
			double y = Framework.rand.nextDouble() * Framework.CANVAS_HEIGHT;
			double size = (Framework.rand.nextDouble() * 2.0) + 1.0;
			Rectangle<Double> star = new Rectangle<Double>(x, y, size, size);
			star.setFill(Color.WHITE);
			stars.add(star);
		}
		
		lastStar = 0;
		cooldown = Starfield.COOLDOWN;
	}
	
	public void update(long currentTime) {
		Iterator<Rectangle<Double>> it = stars.iterator();
		while(it.hasNext()) {
			Rectangle<Double> star = it.next();
			if(star.y >= (Framework.CANVAS_HEIGHT + star.h)) {
				it.remove();
				break;
			} else {
				star.y += Starfield.SPEED;
			}
		}
		
		if(canCreate(currentTime)) {
			double x = Framework.rand.nextDouble() * Framework.CANVAS_WIDTH;
			double size = (Framework.rand.nextDouble() * 2.0) + 1.0;
			double y = -size;
			Rectangle<Double> star = new Rectangle<Double>(x, y, size, size);
			star.setFill(Color.WHITE);
			stars.add(star);
			
			lastStar = currentTime;
		}
	}
	
	public void render(GraphicsContext gc) {
		for(Rectangle<Double> star : stars) {
			star.draw(gc);
		}
	}
}
