package com.mygdx.hanoi.game.objects;

import java.lang.invoke.ConstantCallSite;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.hanoi.game.Assets;
import com.mygdx.hanoi.util.Constants;

public class Background extends AbstractGameObject{
	private TextureRegion backgroundOverlay;
	private float length;
	
	private String backgroundName;
	
	public Background(float length, String bgname){
		this.length = length;
		this.backgroundName = bgname;
		
		init();
	}
	
	private void init(){
		dimension.set(Constants.VIEWPORT_GUI_WIDTH, Constants.VIEWPORT_GUI_HEIGHT);
		backgroundOverlay = Assets.instance.bg.bg;
	}
	
	@Override
	public void render(SpriteBatch batch) {
		TextureRegion reg = null;
		reg = backgroundOverlay;
		
		// perlu ditinjau ulang bagian ini.. ga semua game object parameter draw nya sama
		batch.draw(reg.getTexture(), position.x + origin.x, position.y + origin.y, origin.x, origin.y, dimension.x, dimension.y, scale.x, scale.y, rotation, 
				reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(), reg.getRegionHeight(), 
				false, false);
	}

}
