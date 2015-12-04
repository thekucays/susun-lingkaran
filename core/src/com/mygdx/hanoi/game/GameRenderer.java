package com.mygdx.hanoi.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.hanoi.game.objects.Ring;
import com.mygdx.hanoi.util.Constants;

public class GameRenderer implements Disposable{

	private OrthographicCamera camera;
	private GameController controller;
	private SpriteBatch batch;
	
	public GameRenderer(GameController controller){
		this.controller = controller;
		init();
	}
	
	private void init(){
		batch = new SpriteBatch();
		camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);  //diambil dari class "Constants" (di package util)
		camera.position.set(0, 0, 0);
		camera.update();
	}
	
	public void resize(int width, int height){
		camera.viewportWidth = (Constants.VIEWPORT_HEIGHT / height) * width;
		camera.update();
	}
	
	public void render(){
		renderGui();
		renderDecorations();
		renderObjects();
	}
	
	private void renderGui(){
		
	}
	
	private void renderDecorations(){
		
	}
	
	private void renderObjects(){
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		
		for(Ring rings : controller.rings){
			rings.render(batch);
		}
		
		batch.end();
	}
	
	@Override
	public void dispose() {
		
	}

}
