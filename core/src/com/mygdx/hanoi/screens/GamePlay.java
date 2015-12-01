package com.mygdx.hanoi.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.mygdx.hanoi.game.Assets;
import com.mygdx.hanoi.game.GameController;
import com.mygdx.hanoi.game.GameRenderer;

public class GamePlay extends AbstractGameScreen{
	
	private GameController gameController;
	private GameRenderer gameRenderer;
	private String dummyBg, dummyRing;
	private boolean paused;
	
	public GamePlay(Game game) {
		super(game);
	}

	@Override
	public void render(float deltaTime) {
		
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void show() {
		// still dummy.. nantinya ngambil dari database nya
		this.dummyBg = "default-bg";
		this.dummyRing = "default-ring";
		
		Assets.instance.init(new AssetManager(), dummyBg, dummyRing);
		
		gameController = new GameController();
		gameRenderer = new GameRenderer();
	}

	@Override
	public void hide() {
		
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
		Assets.instance.init(new AssetManager(), this.dummyBg, this.dummyRing);
		paused = false;
	}
}
