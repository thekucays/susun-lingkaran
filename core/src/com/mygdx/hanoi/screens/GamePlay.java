package com.mygdx.hanoi.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.mygdx.hanoi.game.Assets;
import com.mygdx.hanoi.game.GameController;
import com.mygdx.hanoi.game.GameRenderer;
import com.mygdx.hanoi.game.WorldController;
import com.mygdx.hanoi.util.Constants;

public class GamePlay extends AbstractGameScreen implements InputProcessor{
	
	private GameController gameController;
	private GameRenderer gameRenderer;
	private String dummyBg, dummyRing;
	private boolean paused;
	
	// for touch purposes
	private static final int appWidth = Constants.VIEWPORT_GUI_WIDTH_INT;
    private static final int appHeight = Constants.VIEWPORT_GUI_HEIGHT_INT;
	
	public GamePlay(Game game) {
		super(game);
		
		// still dummy.. nantinya ngambil dari database nya
		this.dummyBg = "bg-default";
		this.dummyRing = "ring-default";
		
		Gdx.input.setInputProcessor(this);  
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
		gameRenderer.render(deltaTime);
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

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		float pointerX = getCursorToModelX(Gdx.graphics.getWidth(), screenX);
		float pointerY = getCursorToModelY(Gdx.graphics.getHeight(), screenY);
		
		for(int i=0; i<gameController.rings.size; i++){
		}
		
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		
		return true;
	}

	// for touch purposes
	private float getCursorToModelX(int screenX, int cursorX) {
        return (((float)cursorX) * appWidth) / ((float)screenX); 
    }

    private float getCursorToModelY(int screenY, int cursorY) {
        return ((float)(screenY - cursorY)) * appHeight / ((float)screenY) ; 
    }
	
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}
