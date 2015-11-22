package com.mygdx.hanoi.util;

/*
 * Class yang dipake buat nyimpen preferensi awal dari game ini
 * last update 02 September 2015 by @thekucays
 * 
 *  How to use : 
 *  -> langsung panggil aja GamePrefs.instance.nama-method-init-nya
 *  -> contoh : Gameprefs.instance.miscInit();
 */

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.utils.Array;

public class GamePrefs {
	public static final GamePrefs instance = new GamePrefs();
	
	//singleton pattern
	private GamePrefs(){}
	
	public Map miscInit(){
		Map map = new HashMap();
		map.put("soundVolume", 50);
		map.put("isMute", 0);
		
		return map;
	}
	public Map userInit(){
		Map map = new HashMap();
		map.put("live", 5);
		map.put("level", 1);
		map.put("coin", 0);
		
		return map;
	}
	public Map highscoreInit(){
		Map map = new HashMap();
		map.put("move_mode", new int[5]); //ini array
		map.put("free_mode", new int[5]); //dan tambahan mode lain nya di bawah
		
		return map;
	}
}
