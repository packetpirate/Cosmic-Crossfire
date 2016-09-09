package com.cosmic;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Framework {
	public static final int CANVAS_WIDTH = 600;
	public static final int CANVAS_HEIGHT = 600;
	public static final long NANO_TO_SECS = 1000000000L;
	
	private Stage mainStage;
	private Scene mainScene;
	
	private GraphicsContext gc;
	
	public Framework(Stage stage) {
		mainStage = stage;
		mainStage.setResizable(false);
		
		Group root = new Group();
		mainScene = new Scene(root);
		mainStage.setScene(mainScene);
		
		Canvas canvas = new Canvas(Framework.CANVAS_WIDTH, Framework.CANVAS_HEIGHT);
		root.getChildren().add(canvas);
		
		gc = canvas.getGraphicsContext2D();
		
		final long startTime = System.nanoTime();
		
		new AnimationTimer() {
			@Override
			public void handle(long currentTime) {
				update(currentTime);
				render();
			}
		}.start();
		
		mainStage.show();
	}
	
	private void update(long currentTime) {
		
	}
	
	private void render() {
		gc.setFill(Color.WHITE);
		gc.fillRect(0, 0, Framework.CANVAS_WIDTH, Framework.CANVAS_HEIGHT);
	}
}
