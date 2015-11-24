package com.mygdx.hanoi.screens;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.hanoi.util.SpriteAccessor;

public class SplashScreen extends AbstractGameScreen{ //implements Screen{ //
	private Sprite splash;
	private SpriteBatch batch;
	//private Game game;
	
	// ini buat bikin animasi nya
	private TweenManager tweenManager;
	
	public SplashScreen(Game game){
		super(game);
	} 
	
	@Override
	public void show() {
		batch = new SpriteBatch();
		tweenManager = new TweenManager();
		Tween.registerAccessor(Sprite.class, new SpriteAccessor());  // ngasih tau library Tween nya class accessor nya yang mana
		
		Texture splashTexture = new Texture("badlogic.jpg");
		splash = new Sprite(splashTexture);
		splash.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		

		// mulai bikin animasinya
		// target 0 berarti mulai dari invisible
		Tween.set(splash, SpriteAccessor.ALPHA).target(0).start(tweenManager);  
		 
		// target 1 berarti dimunculin..dengan waktu 2 detik
		// repeatYoyo() berarti ngulang animasi yang udah ada, tapi dibalik (jadi nge-fade out otomatis)
		// setCallback() dipanggil pas animasinya selesai
		Tween.to(splash, SpriteAccessor.ALPHA, 2).target(1).repeatYoyo(1, 2).setCallback(new TweenCallback() {
			@Override
			public void onEvent(int type, BaseTween<?> source) {
				//((Game)Gdx.app.getApplicationListener()).setScreen(new MainMenu(game));  // karena ini bukan turunan class "Game"
				game.setScreen(new MainMenu(game));
			}
		}).start(tweenManager); 
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);  //warna item
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		tweenManager.update(delta);
		
		batch.begin();
		splash.draw(batch);
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void dispose() {
		batch.dispose();
		splash.getTexture().dispose();
	}

}
