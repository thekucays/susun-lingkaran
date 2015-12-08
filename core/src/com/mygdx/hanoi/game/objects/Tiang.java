package com.mygdx.hanoi.game.objects;

import java.util.Stack;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.mygdx.hanoi.game.Assets;


public class Tiang extends AbstractGameObject{
	// pilihan ada 2 untuk ngatur ukuran ring..
	// bisa mainin dimensi  - refer ke water overlay page 173 (190)
	// bisa main 3 region : kiri, tengah, kanan (butuh 3 image)  - refer ke rocks page 167 (184)
	
	private TextureRegion ringOverLay;
	private float length;
	private int maxLoad, currLoad;
	private Stack<Ring> tumpukan;  // tumpukan isinya ring doang.. jangan pake raw
	
	// jenis ring nya
	public String jenis;
	
	public Tiang(float length, int maxLoad, int currLoad){
		this.length = length;
		this.maxLoad = maxLoad;
		this.currLoad = currLoad; 
		init();
	}
	
	private void init(){
		dimension.set(length, 1); // dalam satuan meter dalam screen game nya
		ringOverLay = Assets.instance.ring.ring;  // Assets.instance.namaobjek.atlasregion
		
		origin.x = -dimension.x/2;
	}
	
	// unimplemented (yet) methods, Stack (java.util) related
	public void push(){
		
	}
	
	public void pop(){
		
	}
	
	public void peek(){
		
	}
	
	@Override
	public void render(SpriteBatch batch) {
		TextureRegion reg = null;
		reg = ringOverLay;
		
		// perlu ditinjau ulang bagian ini.. ga semua game object parameter draw nya sama
		batch.draw(reg.getTexture(), position.x + origin.x, position.y + origin.y, origin.x, origin.y, dimension.x, dimension.y, scale.x, scale.y, rotation, 
				reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(), reg.getRegionHeight(), 
				false, false);
	}

	/*@Override
	public boolean handle(Event event) {
		// TODO Auto-generated method stub
		return false;
	}*/
}
