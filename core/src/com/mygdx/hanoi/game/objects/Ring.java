package com.mygdx.hanoi.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.hanoi.game.Assets;


public class Ring extends AbstractGameObject{
	// pilihan ada 2 untuk ngatur ukuran ring..
	// bisa mainin dimensi  - refer ke water overlay page 173 (190)
	// bisa main 3 region : kiri, tengah, kanan (butuh 3 image)  - refer ke rocks page 167 (184)
	
	private TextureRegion ringOverLay;
	private float length;
	
	// jenis ring nya
	public String jenis;
	
	public Ring(float length, String jenis){
		init();
		
		setLength(length);
		setJenis(jenis);
	}
	
	// getters
	public float getLength(){
		return this.length;
	}
	
	public String getJenis(){
		return this.jenis;
	}
	
	public Vector2 getPosition(){
		return position;
	}
	
	// setters
	public void setLength(float length){
		this.length = length;
		dimension.set(5.0f, 1.0f);
	}
	
	public void setJenis(String jenis){
		this.jenis = jenis;
	}
	
	public void setPosition(float x, float y){
		position.set(x, y);
	}
	
	private void init(){
		//dimension.set(length, 1); // dalam satuan meter dalam screen game nya
		ringOverLay = Assets.instance.ring.ring;  // Assets.instance.namaobjek.atlasregion

		origin.x = dimension.x/2;  // -dimension.x/2;
		origin.y = dimension.y/2;
		
		position.x = -5.0f;
		position.y = -2.5f;
	}
	
	@Override
	public void render(SpriteBatch batch) {
		TextureRegion reg = null;
		reg = ringOverLay;

		// perlu ditinjau ulang bagian ini.. ga semua game object parameter draw nya sama
		batch.draw(reg.getTexture(), position.x, position.y, origin.x, origin.y, dimension.x, dimension.y, scale.x, scale.y, rotation, reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(), reg.getRegionHeight(), false, false);
		//batch.draw(region, x, y, originX, originY, width, height, scaleX, scaleY, length);
		//batch.draw(reg.getTexture(), position.x, position.y);
	}

}
