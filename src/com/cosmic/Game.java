package com.cosmic;

import javafx.application.Application;
import javafx.stage.Stage;

public class Game extends Application {
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage stage) {
		stage.setTitle("Cosmic Crossfire");
		new Framework(stage);
	}
}
