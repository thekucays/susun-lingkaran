package com.mygdx.hanoi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Json;
import com.mygdx.hanoi.screens.SplashScreen;
import com.mygdx.hanoi.util.DataPersister;
import com.mygdx.hanoi.util.DataPersister2;
//import com.sun.xml.internal.bind.v2.runtime.reflect.ListIterator;

public class Main extends Game{

	@Override
	public void create() {
		//setScreen(new SplashScreen());  // langsung jalanin splash screen nya
		
		DataPersister2 hs = new DataPersister2();
		Preferences hScore = hs.getOrCreatePreferences("highScores");
		hs.clearPreferences(hScore);
		
		// dummy test score data
		ArrayList<String[]> hsFreeMode = new ArrayList<String[]>();  // declare apa yang mau ditaruh sini, biar ga error di kemudian method
		hsFreeMode.add(new String[] {"luki", "5000"});
		hsFreeMode.add(new String[] {"laras", "3900"});
		hsFreeMode.add(new String[] {"ajul", "3800"});
		hsFreeMode.add(new String[] {"koko", "3700"});
		hsFreeMode.add(new String[] {"antar", "3600"});
		hsFreeMode.add(new String[] {"bangkit", "3500"});
		
		
		ArrayList hsMoveMode = new ArrayList();
		hsMoveMode.add(new String[] {"cika", "6000"});
		hsMoveMode.add(new String[] {"cikoo", "1000"});
		
		Map hsMap = new HashMap();
		hsMap.put("freeMode", new Json().toJson(hsFreeMode));
		hsMap.put("moveMode", hsMoveMode);
		
		//hs.insertPreferences("highScores", hsMap);
		hs.insertPreferences(hScore, hsMap);
		
		// print out the data
		Map data = hs.getPreferencesData(hScore);
		//Gdx.app.log("data print", "the free mode value is " + data.get("freeMode"));
		//Gdx.app.log("data print", "the free mode value is " + (String[]) new Json().fromJson(ArrayList.class, String[].class, (String)data.get("freeMode")).get(0));
	    Gdx.app.log("data print", "the free mode value is " + ((String[])new Json().fromJson(ArrayList.class, String[].class, (String)data.get("freeMode") ).get(1)) [0]);
	    
	    
	    // loop the json file
	    ArrayList<String[]> loop = new Json().fromJson(ArrayList.class, String[].class, (String)data.get("freeMode"));
	    /*Iterator itr =  loop.iterator();
	    while(itr.hasNext()){
	    	Object element = itr.next();
	    	Gdx.app.log("loop print", "the element value is : " + element.toString());
	    }*/
	    
	    // loop using for loop
	    for(int i=0; i<loop.size(); i++){
	    	Gdx.app.log("for loop", "the name value is" + loop.get(i)[0]);
	    	Gdx.app.log("for loop", "the score value is" + loop.get(i)[1]);
	    }
		
		setScreen(new SplashScreen(this));  //karena semua screen akan dibuat extends AbstractGameScreen
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}

	@Override
	public void dispose() {
		super.dispose();
	}

}
