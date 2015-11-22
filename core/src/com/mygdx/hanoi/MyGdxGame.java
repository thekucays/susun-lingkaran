package com.mygdx.hanoi;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	OrthographicCamera camera;
	Sprite sprite;
	private float rot; //ini untuk rotasinya
	
	@Override
	public void create () {
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		/* bawaan gradle nya, mau coba ganti sama koding dari ebook
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg"); */
		
		camera = new OrthographicCamera(1, h/w);
		batch = new SpriteBatch();
		
		img = new Texture(Gdx.files.internal("badlogic.jpg"));
		img.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		TextureRegion region = new TextureRegion(img, 0, 0, 512, 275);
		
		sprite = new Sprite(region);
		sprite.setSize(0.9f, 0.9f * sprite.getHeight() / sprite.getHeight()); //ukuran nya 90% ukuran aslinya
		sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
		sprite.setPosition(-sprite.getWidth()/2, -sprite.getHeight()/2);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		/* bawaan gradle nya, mau coba ganti sama koding dari ebook
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end(); */
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		
		final float degreespersecond = 5.0f;  //rotasi 10 derajat per detik
		rot = (rot + Gdx.graphics.getDeltaTime() * degreespersecond) % 360;  //nilai rotasi nya
		
		//bikin rotasi terus2an
		//sprite.setRotation(rot);
		
		//bikin efek shake
		final float shakeamplitudeindegrees = 3.0f;
		float shake = MathUtils.sin(rot) * shakeamplitudeindegrees;
		sprite.setRotation(shake);
		
		sprite.draw(batch);
		batch.end();
	}
	
	@Override
	public void dispose(){
		batch.dispose();
		img.dispose();
	}
	
	@Override
	public void pause(){
		
	}
	
	@Override
	public void resume(){
		
	}
	
	@Override
	public void resize(int width, int height){
		
	}
}
