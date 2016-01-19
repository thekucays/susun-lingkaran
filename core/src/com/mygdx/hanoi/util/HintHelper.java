package com.mygdx.hanoi.util;

import com.badlogic.gdx.Gdx;
import com.mygdx.hanoi.game.objects.RingB;
import com.mygdx.hanoi.screens.GamePlayB;


public class HintHelper {
	GamePlayB gpb = null;
	private int lowIndex, hiIndex, tempLength, tempIndex;
	private boolean firstFlag;
	
	
	public HintHelper(GamePlayB gpb){
		this.gpb = gpb;
		tempLength = 0;
		tempIndex = 0;
		firstFlag = true;
	}
	
	public boolean hint(){
		boolean hasil = false;
		Gdx.app.log("hint helper", "hint exec");
		
		// sort tiang untuk cari ring yang paling kecil
		for(int i=0; i<this.gpb.tiangs.size(); i++){
			RingB tempRing = this.gpb.tiangs.get(i).peek();
			// kalo null, berarti tiang nya kosong, skip, karena ini nyari ring paling kecil
			if(tempRing != null){
				if(firstFlag || tempRing.getLength()<tempLength){
					tempLength = tempRing.getLength();
					tempIndex = i;
					firstFlag = false;
				}
			}
		}
		
		// udah dapet ring paling kecil, catet
		lowIndex = tempIndex;
		tempIndex = 0;
		
		// sort tiang untuk cari target tempat ring mau dipindahin
		for(int i=0; i<this.gpb.tiangs.size(); i++){
			RingB tempRing = this.gpb.tiangs.get(i).peek();
			
			// kalo null, ada tiang kosong, langsung set kesitu
			if(tempRing == null){
				hiIndex = i;
				break;
			}
			else{
				if(hiIndex != 0){
					// cari yang lebih besar length nya dari ring yang dipilih pertama 
					if(tempRing.getLength() > tempLength){
						hiIndex = i;
					}
				}
			}
		}
		
		// kedua target udah ketemu, pindahin ring nya
		// .. dipakein if jaga2 aja supaya kalo ternyata ga ketemu target nya
		if(lowIndex != hiIndex){
			this.gpb.clickTiangs(this.gpb.tiangs.get(lowIndex));
			this.gpb.clickTiangs(this.gpb.tiangs.get(hiIndex));
			
			tempIndex = 0;
			tempLength = 0;
			lowIndex = 0;
			hiIndex = 0;
			firstFlag = true;
			
			hasil = true;
		}
		
		return hasil;
	}
}
