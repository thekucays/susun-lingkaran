package com.mygdx.hanoi.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.hanoi.util.Constants;

public class WorldRenderer implements Disposable{
	//segala macem render dibuat disini
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private WorldController worldController;
	
	public WorldRenderer(WorldController worldController){
		this.worldController = worldController;
		init();
	}
	
	private void init(){
		batch = new SpriteBatch();
		camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);  //diambil dari class "Constants" (di package util)
		camera.position.set(0, 0, 0);
		camera.update();
	}
	
	//dijalanin dari TowerOfHanoiMain.java nya
	public void render(){
		renderTestObjects();
	}
	
	private void renderTestObjects(){
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		/*for(TextButton tbt : worldController.buttons){
			//tbt.draw(batch, 1.0f);
		} */
		for(Sprite sprite : worldController.testSprite){   //ambil testSprite yang dibikin di WorldController, bisa langsung akses karena public
			sprite.draw(batch);
		}
		batch.end();
	}
	
	public void resize(int width, int height){
		//di-kalkulasi lagi size nya, di sesuain dengan device yang dipake
		camera.viewportWidth = (Constants.VIEWPORT_HEIGHT / height) * width;
		camera.update();
	}
	
	@Override
	public void dispose(){
		batch.dispose();
	}
}
