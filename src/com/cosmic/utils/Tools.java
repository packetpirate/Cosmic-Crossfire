package com.cosmic.utils;

import java.net.URISyntaxException;

import javafx.scene.image.Image;

public class Tools {
	public static Image LoadImage(String filename) {
		Image img = null;
		try {
			img = new Image(Tools.class.getResource("/resources/" + filename).toURI().toString());
		} catch(URISyntaxException e) {
			System.err.println("ERROR: Invalid URI when loading image!");
		} catch(NullPointerException npe) {
			System.err.println("ERROR: Invalid filename when loading image!");
		}
		
		return img;
	}
}