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
	public static final float GAP_RING = 28.0f;
	public static final float GAP_TIANG_BOTTOM_DEFAULT  = 42.0f;
	public static final float GAP_TIANG_4_LEFT = 150.0f;
	public static final float GAP_TIANG_4_RIGHT = 50.0f;
	
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
	public static final String pref_highscore_timed = "timed";
	public static final String pref_highscore_survival = "survival";
	public static final String pref_highscore_move = "move";
	public static final String pref_userpref = "userPref";
	public static final String pref_userpref_background = "background";
	public static final String pref_userpref_background_def = "bg-default";
	public static final String pref_userpref_ring = "ring";
	public static final String pref_userpref_ring_def = "ring-default";
	public static final String pref_userpref_poin = "poin";
	public static final String pref_userpref_hint = "hint";
	public static final String pref_toko = "toko";
	public static final String pref_toko_background = "Background";
	public static final String pref_toko_ring = "Ring";
	public static final String pref_toko_item = "item";
	public static final String pref_toko_nama = "nama";
	public static final String pref_toko_jenis = "jenis";
	public static final String pref_toko_harga = "harga";
	
	// toko related
	public static final String TOKO_NOTIF_TITLE = "Toko";
	public static final String TOKO_NOTIF_SUKSES = "Pembelian berhasil!";
	public static final String TOKO_NOTIF_GAGAL = "Pembelian gagal, cek jumlah poin";
	public static final String TOKO_NOTIF_BTN = "OK";
	
	// gameplay related
	public static final float RING_SCALE_FACTOR = 0.3f;
	public static final String TEXTURE_ATLAS_OBJECTS = "ui/gameplay/objects/objects.pack";
	public static final String TEXTURE_ATLAS_DECORATION = "ui/gameplay/decorations/decorations.pack";
	public static final String TEXTURE_ATLAS_GUI = "ui/gameplay/ui-game/ui-game.pack";
	public static final String PAUSE_TITLE = "Pause";
	public static final String PAUSE_MAIN_LAGI = "Main Lagi";
	public static final String PAUSE_ULANGI = "Ulangi";
	public static final String PAUSE_KELUAR = "Keluar";
	public static final String PAUSE_OPTION_ULANGI = "ulangi";
	public static final String PAUSE_OPTION_KELUAR = "keluar";
	
	public static final String CONFIRM_TITLE = "Yakin?";
	public static final String CONFIRM_YES = "Ya";
	public static final String CONFIRM_NO = "Tidak";
	
	public static final String LOSE_TITLE = "Kalah";
	public static final String LOSE_NOTE = "Waktu Habis. Kamu Kalah!";
	public static final String LOSE_OK = "Oke";
	public static final String LOSE_BTN_KELUAR = "Keluar";
	public static final String LOSE_BTN_ULANGI = "Ulangi";
	
	public static final String WIN_TITLE = "Menang";
	public static final String WIN_NOTE = "Selamat! Kamu Menang!";
	public static final String WIN_NOTE_POIN = "Poin : ";
	public static final String WIN_BTN_KELUAR = "Keluar";
	public static final String WIN_BTN_ULANGI = "Ulangi";
	public static final String WIN_BTN_LANJUT = "Lanjut";
	public static final String WIN_BTN_MENU = "Menu";
	public static final String WIN_BTN_OK = "OK";
	
	
	
	// game modes
	public static final String MODE_FREE = "free";
	public static final String MODE_TIMED = "timed";
	public static final String MODE_SURVIVAL = "survival";
	public static final String MODE_MOVE = "move";
	
	public static final int TIMED_MODE_TIME = 1800;
	public static final int SURVIVAL_MODE_MOVE_EXPAND = 5;
	public static final int SURVIVAL_MODE_HINT = 3;
	
	// poin generator multiplier constants
	public static final int MULTIPLIER_TIMED = 10;
	public static final int MULTIPLIER_SURVIVAL = 15;
	public static final int MULTIPLIER_MOVE_BIG = 5; 	// jmlRing >= 7
	public static final int MULTIPLIER_MOVE_SMALL = 10;	// jmlRing < 7
	
	//public static final String GAME_MODES [][] = {{"a"}, {"b"}};
	public static final int GAME_LEVEL_CONFIG [][] = {  // buat settingan game nya {jumlahRIng, jumlahTiang}
		{3, 4},
		{5, 4},
		{3, 3},
		{5, 3},
		{7, 3}
	};
}
