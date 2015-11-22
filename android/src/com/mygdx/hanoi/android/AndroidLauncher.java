package com.mygdx.hanoi.android;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.mygdx.hanoi.MyGdxGame;
import com.mygdx.hanoi.TowerOfHanoiMain;
import com.mygdx.hanoi.Main;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		//initialize(new MyGdxGame(), config);
		//initialize(new TowerOfHanoiMain(), config);
		initialize(new Main(), config);
	}
}

