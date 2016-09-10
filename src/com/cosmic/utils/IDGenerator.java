package com.cosmic.utils;

public class IDGenerator {
	public static int currentID = -1;
	
	public static int createID() {
		IDGenerator.currentID++;
		return IDGenerator.currentID;
	}
	
	public static void resetID() {
		IDGenerator.currentID = -1;
	}
}
