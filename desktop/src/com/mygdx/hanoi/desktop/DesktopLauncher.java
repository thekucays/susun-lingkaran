package com.mygdx.hanoi.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.hanoi.Main;
import com.mygdx.hanoi.MyGdxGame;
import com.mygdx.hanoi.TowerOfHanoiMain;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "demo";
		config.width = 800;  //800
		config.height = 480; //400
		config.vSyncEnabled = true;  // ini buat syncing frame dengan update game nya 
		
		//new LwjglApplication(new MyGdxGame(), config);
		new LwjglApplication(new TowerOfHanoiMain(), config);
		//new LwjglApplication(new Main(), config);
	}
}
