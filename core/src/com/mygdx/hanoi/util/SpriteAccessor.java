package com.mygdx.hanoi.util;

import com.badlogic.gdx.graphics.g2d.Sprite;
import aurelienribon.tweenengine.TweenAccessor;


public class SpriteAccessor implements TweenAccessor<Sprite>{
	
	public static final int ALPHA = 0;  // ini nilai Aplha buat fade in dan fade out nya
	
	@Override
	public int getValues(Sprite target, int tweenType, float[] returnValues) {
		switch(tweenType){
		case ALPHA:
			returnValues[0] = target.getColor().a; // ambil nilai aplha nya
			return 1; // 1 ini berarti jumlah index returnValues
		default:
			assert false;
			return -1;
		}
			
	}

	@Override
	public void setValues(Sprite target, int tweenType, float[] newValues) {
		switch(tweenType){
		case ALPHA:
			target.setColor(target.getColor().r, target.getColor().g, target.getColor().b, newValues[0]); // newValues ini diambil dari method get nya..jadi ngambil nilai aplha nya
			break;
		default:
			assert false;
		}
	}

}
