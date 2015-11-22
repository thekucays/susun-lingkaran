package com.mygdx.hanoi.util;

import java.util.HashMap;
import java.util.Map;

public class Constants {
	// ini panjang dan lebar viewport nya (yang ditampilin di layar) sebesar 5 meter 
	public static final float VIEWPORT_WIDTH = 5.0f;
	public static final float VIEWPORT_HEIGHT = 5.0f;
	
	// GUI related
	public static final float VIEWPORT_GUI_WIDTH = 800.0f;
	public static final float VIEWPORT_GUI_HEIGHT = 480.0f;
	
	// preferences related
	public static final String pref_highscore = "highScores";
	public static final String pref_poin = "poinUser";
	public static final String pref_background = "backgroundUser";
	public static final String pref_hint = "hintUser";
	
	// gameplay related
	public static final String TEXTURE_ATLAS_OBJECTS = "ui/gameplay.pack";  // belom dibikin..cari resource nya dulu
}
