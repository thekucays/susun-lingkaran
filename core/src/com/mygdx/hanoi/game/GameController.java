package com.mygdx.hanoi.game;

import com.badlogic.gdx.InputAdapter;
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
	
	// game decorations
	public Background background;
	
	public GameController(){
		init();
	}
	
	private void init(){
		initObjects();
		initDecorations();
		initGui();
	}
	
	private void initObjects(){
		
	}
	
	private void initDecorations(){
		
	}
	
	private void initGui(){
		
	}
	
	private void handleInput(float deltatime){
		
	}
}
