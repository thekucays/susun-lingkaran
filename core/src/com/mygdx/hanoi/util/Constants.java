package com.mygdx.hanoi.util;

import java.util.HashMap;
import java.util.Map;

public class Constants {
	// app id related
	public static final String app_name = "Susun Lingkaran";
	public static final String app_version = "0.0.1a";
	public static final String app_creator = "Luki Ramadon - 4512212044";
	public static final String app_university = "Universitas Pancasila";
	
	// general gap needs
	public static final float GAP_SMALL = 5.0f;
	public static final float GAP_MEDIUM = 10.0f;
	public static final float GAP_BIG = 20.0f;
	
	// thumbnail and lit toko related
	public static final String thumbnail_prefix = "-thumb";
	public static final float table_width = 700.0f;
	public static final float table_height = 260.0f;
	public static final float TABLE_TOP_GAP = 50.0f;
	public static final float COIN_SIZE = 30.0f;
	
	// ini panjang dan lebar viewport nya (yang ditampilin di layar) sebesar 5 meter 
	public static final float VIEWPORT_WIDTH = 5.0f;
	public static final float VIEWPORT_HEIGHT = 5.0f;
	
	// GUI related
	public static final float VIEWPORT_GUI_WIDTH = 800.0f;
	public static final int VIEWPORT_GUI_WIDTH_INT = 800;
	public static final float VIEWPORT_GUI_HEIGHT = 480.0f;
	public static final int VIEWPORT_GUI_HEIGHT_INT = 480;
	public static final float HEADER_TOP_GAP = 80.0f;
	
	// preferences related
	public static final String pref_highscore = "highScores";
	public static final String pref_poin = "poinUser";
	public static final String pref_background = "backgroundUser";
	public static final String pref_hint = "hintUser";
	
	// gameplay related
	public static final String TEXTURE_ATLAS_OBJECTS = "ui/gameplay/objects/objects.pack";
	public static final String TEXTURE_ATLAS_DECORATION = "ui/gameplay/decorations/decorations.pack";
	public static final String TEXTURE_ATLAS_GUI = "ui/gameplay/ui-game/ui-game.pack";
}
