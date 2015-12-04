package com.mygdx.hanoi.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.mygdx.hanoi.game.Assets;
import com.mygdx.hanoi.game.GameController;
import com.mygdx.hanoi.game.GameRenderer;
import com.mygdx.hanoi.game.WorldController;

public class GamePlay extends AbstractGameScreen{
	
	private GameController gameController;
	private GameRenderer gameRenderer;
	private String dummyBg, dummyRing;
	private boolean paused;
	
	public GamePlay(Game game) {
		super(game);
		
		// still dummy.. nantinya ngambil dari database nya
		this.dummyBg = "bg-default";
		this.dummyRing = "ring-default";
	}

	@Override
	public void render(float deltaTime) {
		if(!paused){
			gameController.update(deltaTime);
		}
		
		//Gdx.gl.glClearColor(0x64 / 255.0f, 0x95 / 255.0f,0xed / 255.0f, 0xff / 255.0f);
		//Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// render game nya
		gameRenderer.render();
	}

	@Override
	public void resize(int width, int height) {
		gameRenderer.resize(width, height);
	}

	@Override
	public void show() {
		Assets.instance.init(new AssetManager(), "bg-default", "ring-default");
		Gdx.app.log("GamePlay", "After show() method");
		
		gameController = new GameController(game);
		gameRenderer = new GameRenderer(gameController);
		Gdx.input.setCatchBackKey(true);
	}

	@Override
	public void hide() {
		gameRenderer.dispose();
		Gdx.input.setCatchBackKey(false);
	}

	@Override
	public void dispose(){
		gameRenderer.dispose();
		Assets.instance.dispose();
	}
	
	@Override
	public void pause() {
		paused = true;
	}
	
	@Override
	public void resume() {
		super.resume(this.dummyBg, this.dummyRing);
		//Assets.instance.init(new AssetManager(), this.dummyBg, this.dummyRing);
		
		paused = false;
	}
}
