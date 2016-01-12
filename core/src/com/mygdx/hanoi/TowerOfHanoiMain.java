package com.mygdx.hanoi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//import sun.java2d.pipe.hw.ExtendedBufferCapabilities.VSyncType;











import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.Json;
import com.mygdx.hanoi.game.Assets;
import com.mygdx.hanoi.game.WorldController;
import com.mygdx.hanoi.game.WorldRenderer;
import com.mygdx.hanoi.screens.MainMenu;
import com.mygdx.hanoi.screens.SplashScreen;
import com.mygdx.hanoi.util.Constants;
import com.mygdx.hanoi.util.DataPersister2;

public class TowerOfHanoiMain extends Game{  //extends ApplicationAdapter
	public static final String TAG = TowerOfHanoiMain.class.getName();
	
	private WorldController worldController;
	private WorldRenderer worldRenderer;
	
	private boolean paused; //apakah game lagi di-pause?
	
	@Override
	public void create(){
		// setting up database
		DataPersister2 persister = new DataPersister2();
		Preferences hScore = persister.getOrCreatePreferences(Constants.pref_highscore);
		Preferences userpref = persister.getOrCreatePreferences(Constants.pref_userpref);
		Preferences toko = persister.getOrCreatePreferences(Constants.pref_toko);
		
		int init = 0;
		int initUang = 10000;
		int initHint = 5;
		
		
		// kalo belum ada preferensi nya, bikin dulu
		if(persister.getPreferencesData(hScore).isEmpty()){
			Map hsmap = new HashMap();
			hsmap.put(Constants.MODE_MOVE, init);
			hsmap.put(Constants.MODE_SURVIVAL, init);
			hsmap.put(Constants.MODE_TIMED, init);
			
			persister.insertPreferences(hScore, hsmap);
		}
		
		if(persister.getPreferencesData(userpref).isEmpty()){
			Map usermap = new HashMap();
			usermap.put(Constants.pref_userpref_background, Constants.pref_userpref_background_def);
			usermap.put(Constants.pref_userpref_ring, Constants.pref_userpref_ring_def);
			usermap.put(Constants.pref_userpref_poin, initUang);
			usermap.put(Constants.pref_userpref_hint, initHint); // awal permainan dikasih 5 hint
			
			Gdx.app.log("userpref", "tes");
			
			persister.insertPreferences(userpref, usermap);
		}
		
		// nanti nge-looping nya refer kesini http://stackoverflow.com/questions/1066589/iterate-through-a-hashmap
		if(persister.getPreferencesData(toko).isEmpty()){
			//Map tokomap = new HashMap();
			//Map<String, String[]> tokomap = new HashMap<String, String[]>();
			//tokomap.put(Constants.pref_toko_item + " 1", new String[]{"bg-default", Constants.pref_toko_background, "Background default", "500", "0", "70"});
			
			// taruh pakai arraylist, lalu di convert ke json
			ArrayList<String[]> itemList = new ArrayList<String[]>();
			itemList.add(new String[] {"bg-default", "Background", "Background default", "0", "1", "70"});
			itemList.add(new String[] {"bg-clouds", "Background", "Background clouds", "1500", "0", "70"});
			itemList.add(new String[] {"ring-default", "Ring", "Ring default", "0", "1", "70"});
			itemList.add(new String[] {"ring-pie-coklat", "Ring", "Ring pie coklat", "500", "1", "70"});
			itemList.add(new String[] {"ring-pie-greentea", "Ring", "Ring pie green tea", "500", "0", "70"});
			itemList.add(new String[] {"hint-5", "Hint", "Hint +5", "500", "1", "5"});
			itemList.add(new String[] {"hint-10", "Hint", "Hint +10", "500", "1", "10"});
			
			Map tokomap = new HashMap();
			tokomap.put(Constants.pref_toko_item, new Json().toJson(itemList));
			
			//Gdx.app.log("map toh main", String.valueOf(tokomap.size()));
			Gdx.app.log("new map length", String.valueOf(new Json().fromJson(ArrayList.class, String[].class, (String)tokomap.get(Constants.pref_toko_item)).size()));
			persister.insertPreferences(toko, tokomap);
		}
		
		setScreen(new SplashScreen(this)); //pake this supaya this.game di AbstractGameScreen keisi..jadi bisa dipanggil semua
		/*Assets.instance.init(new AssetManager(), "bg-default", "ring-default");
		
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		Gdx.app.log("Greetings", "Hello, greetings from tge main class");
		
		
		//coba bikin preferences buat nyimpen data.. refer to ebook pg.86
		Preferences prefs = Gdx.app.getPreferences("foo.prefs");
		prefs.clear();
		Map map_gamemiscs = new HashMap();
		map_gamemiscs.put("isMute", 0);
		map_gamemiscs.put("soundvolume", 50);
		
		prefs.put(map_gamemiscs);
		prefs.flush();
		Gdx.app.log("Map print", "the value of map is " + prefs.get());
		Gdx.app.log("Map print", "the sound mute status is " + prefs.get().containsKey("isMute")); //cek availability
		Gdx.app.log("Map print", "the sound volume is " + prefs.get().get("soundvolume")); //cek value
		
		//coba rubah preferensinya
		Map temp = prefs.get(); 
		temp.put("soundvolume", 75);
		prefs.put(temp);
		prefs.flush();
		Gdx.app.log("Map print", "the sound volume was changed to " + prefs.get().get("soundvolume")); 
		
		worldController = new WorldController();
		worldRenderer = new WorldRenderer(worldController);
		
		//pastinya pertama-tama game ga di-pause
		paused = false; */  
	}
	
	@Override
	public void render(){
		super.render();
		
		/*if(!paused){
			//update game nya continously
			worldController.update(Gdx.graphics.getDeltaTime());
		}
			
		//bikin clear screen nya, di-set ke : CornFlower Blue (liat ebook hal 118), lalu clear screen nya
		Gdx.gl.glClearColor(0x64/255.0f, 0x95/255.0f, 0xed/255.0f, 0xff/255.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		//render game nya ke layar
		worldRenderer.render(); */ 
	}
	
	@Override 
	public void resize(int width, int height){
		super.resize(width, height);
		//worldRenderer.resize(width, height);
	}
	
	@Override
	public void pause(){
		super.pause();
		//paused = true;
	}
	
	@Override
	public void resume(){
		super.resume();
		//paused = false;
	}
	
	@Override
	public void dispose(){
		super.dispose();
		//Assets.instance.dispose();
		//worldRenderer.dispose();
	}
}
