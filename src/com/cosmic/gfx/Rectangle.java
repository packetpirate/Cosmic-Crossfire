package com.cosmic.gfx;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Rectangle<T> {
	public T x, y, w, h;
	
	private Color color;
	public Color getFill() { return color; }
	public void setFill(Color color) { this.color = color; }
	private Color stroke;
	public Color getStroke() { return stroke; }
	public void setStroke(Color stroke) { this.stroke = stroke; }
	
	public Rectangle(T x, T y, T w, T h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	
	public void draw(GraphicsContext gc) {
		gc.setFill(color);
		gc.setStroke(stroke);
		gc.strokeRect((double)x, (double)y, (double)w, (double)h);
		gc.fillRect((double)x, (double)y, (double)w, (double)h);
	}
}
