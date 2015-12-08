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
import com.mygdx.hanoi.game.Assets;
import com.mygdx.hanoi.game.WorldController;
import com.mygdx.hanoi.game.WorldRenderer;
import com.mygdx.hanoi.screens.MainMenu;
import com.mygdx.hanoi.screens.SplashScreen;

public class TowerOfHanoiMain extends Game{  //extends ApplicationAdapter
	public static final String TAG = TowerOfHanoiMain.class.getName();
	
	private WorldController worldController;
	private WorldRenderer worldRenderer;
	
	private boolean paused; //apakah game lagi di-pause?
	
	@Override
	public void create(){
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
