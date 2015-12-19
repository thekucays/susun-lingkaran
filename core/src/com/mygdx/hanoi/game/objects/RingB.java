package com.mygdx.hanoi.game.objects;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/*
 * PLAN B CLASS
 * author @thekucays
 */

public class RingB extends Image{
	private String jenis;
	private int length;
	
	public RingB(Skin skin, String drawName, String jenis, int length){
		super(skin, drawName);
		
		this.length = length;
		this.jenis = jenis;
	} 
	
	// setters
	public void setJenis(String j){
		this.jenis = j;
	}
	public void setLength(int l){
		this.length = l;
	}
	
	//setters
	public String getJenis(){
		return this.jenis;
	}
	public int getLength(){
		return this.length;
	}
}
