package com.mygdx.hanoi.game.objects;

import java.util.EmptyStackException;
import java.util.Stack;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/*
 * PLAN B CLASS
 * author @thekucays
 */

/*
 * Operasi dalam java.util.Stack : 
 * 	1. Push()	- masukin ke dalam Stack
 * 	2. Pop() 	- keluarin dari Stack
 * 	3. Peek()	- mirip Pop(), tapi hanya nge-view item yang paling atas..ga di keluarin
 */

public class TiangB extends Image{
	
	private int maxLoad, currLoad;
	private Stack<RingB> tumpukan;
	
	// buat nyimpen koordinat ring paling atas
	private float topRingY;
	
	public TiangB(Skin skin, String drawName, int maxLoad){
		super(skin, drawName);
		
		this.maxLoad = maxLoad;
		this.currLoad = 0;
		this.tumpukan = new Stack<RingB>();
	}
	
	public int getIsi(){
		return this.currLoad;
	}
	
	// Modified existing java.util.Stack features
	public RingB pop(){
		RingB popped = (RingB)this.tumpukan.pop();
		this.currLoad--;
		return popped;
	}
	public RingB peek(){
		RingB peeked = null;
		
		try{
			peeked = (RingB)this.tumpukan.peek();
			return peeked;
		}
		catch(EmptyStackException ese){
			return null;
		}
	}
	public boolean push(RingB ring){
		boolean hasil = true;
		
		// Tiang udah penuh
		if(this.currLoad >= this.maxLoad){
			hasil = false;
		}
		else{
			//cek dulu apakah yang mau di-push lebih besar / kecil dari Ring yang paling atas
 			if(this.tumpukan.empty() || peek().getLength() > ring.getLength()){
 				this.tumpukan.push(ring);
		 		this.currLoad++;
 			}
 			else if((peek().getLength() < ring.getLength()) || (peek().getLength() == ring.getLength())){
 				hasil = false;
 			}
		}
		
		return hasil;
	}
	
	// extended features
	public boolean cekIfComplete(){
		boolean hasil = false;
		
		if(this.currLoad == this.maxLoad){  // udah penuh
			hasil = true;
		}
		else{}
		
		return hasil;
	}
	
	public void setTopY(float y){
		this.topRingY = y;
	}
	
	public float getTopY(){
		return this.topRingY;
	}
}
