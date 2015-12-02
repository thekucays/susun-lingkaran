package com.mygdx.hanoi.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.utils.Array;
import com.mygdx.hanoi.game.objects.AbstractGameObject;
import com.mygdx.hanoi.game.objects.Background;
import com.mygdx.hanoi.game.objects.Ring;
import com.mygdx.hanoi.game.objects.Tiang;
import com.mygdx.hanoi.screens.AbstractGameScreen;


public class GameController extends InputAdapter{
	// game objects
	public Array<Tiang> tiangs;
	public Array<Ring> rings;
	//private Game game;
	
	// game decorations
	public Background background;     public Sprite[] testSprite;
	private Game game;
	
	public GameController(Game game){
		this.game = game;
		init();
	}
	
	private void init(){
		// preparing variables
		rings = new Array<Ring>();
		tiangs = new Array<Tiang>();
		
		initObjects();
		initDecorations();
		initGui();
	}
	
	private void initObjects(){
		AbstractGameObject obj = null;
		obj = new Ring(1, "default");
		/*obj.addListener(new ChangeListener() {
			
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Gdx.app.log("tag", "test");
			}
		}); */
		obj.position.set(0, 2);
		
		rings.add((Ring)obj);
	}
	
	private void initDecorations(){
		
	}
	
	private void initGui(){
		
	}
	
	private void handleInput(float deltatime){
		
	}
	
	public void update(float deltaTime){
		
	}
}
