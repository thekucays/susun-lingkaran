package com.mygdx.hanoi.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;

public abstract class AbstractGameObject extends Actor{ //implements EventListener{
	public Vector2 position;
	public Vector2 dimension;
	public Vector2 origin;
	public Vector2 scale;
	public float rotation;
	
	public AbstractGameObject(){
		position = new Vector2();
		dimension = new Vector2(1, 1);
		origin = new Vector2();
		scale = new Vector2(1, 1);
		rotation = 0;
		
	}
	
	public void update (float deltaTime){
		
	}
	
	// ga pake body karena implementasi nya beda2 di tiap class yang implements ini
	public abstract void render(SpriteBatch batch);
}
