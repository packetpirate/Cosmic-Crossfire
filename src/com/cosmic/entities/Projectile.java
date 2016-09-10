package com.cosmic.entities;

import com.cosmic.utils.Pair;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Projectile {
	private Pair<Double> position;
	public Pair<Double> getPosition() { return position; }
	private double theta;
	private double speed;
	private double size;
	public double getSize() { return size; }
	
	public Projectile(double x, double y, double theta, double speed, double size) {
		this(new Pair<Double>(x, y), theta, speed, size);
	}
	
	public Projectile(Pair<Double> pos, double theta, double speed, double size) {
		this.position = new Pair<Double>(pos.x, pos.y);
		this.theta = theta;
		this.speed = speed;
		this.size = size;
	}
	
	public void update() {
		position.x += speed * Math.cos(theta);
		position.y += speed * Math.sin(theta);
	}
	
	public void render(GraphicsContext gc) {
		gc.setStroke(Color.GRAY);
		gc.setFill(Color.ORANGE);
		gc.strokeOval((position.x - (size / 2)), 
				(position.y - (size / 2)), 
				size, size);
		gc.fillOval((position.x - (size / 2)), 
					(position.y - (size / 2)), 
					size, size);
	}
}
