package com.mygdx.hanoi.util;

import java.util.TimerTask;

import com.mygdx.hanoi.screens.GamePlayB;

public class GameTimer extends TimerTask {
	public int waktu = 0;
	GamePlayB gpb = null;
	
	public GameTimer(GamePlayB gpb){
		this.gpb = gpb;
	}
	
	@Override
	public void run() {
		/*this.waktu--;
		if(this.waktu <= 0){
			this.cancel();
		} */
		gpb.hitungWaktu();
	}
	
}
