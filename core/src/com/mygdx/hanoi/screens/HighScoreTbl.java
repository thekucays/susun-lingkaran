package com.mygdx.hanoi.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class HighScoreTbl {
	/*
	 * class ini dipakai sebagai container kecil untuk nampilin list highscore supaya lebih rapih
	 * 
	 * jadi nanti objek class ini akan dimasukkan ke dalam list pada class HighScore.java
	 */
	
	public Table generateHSContainer(Skin skin, String nama, String skor){
		// bikin label biar bisa di-align text nya
		Label lblSkor = new Label(skor, skin);
		lblSkor.setAlignment(Align.right);
		Label lblNama = new Label(nama, skin);
		lblNama.setAlignment(Align.left);
		
		Table table = new Table(skin);
		table.add(lblNama).expand().fill().align(Align.left);
		
		// buat spacer aja
		table.add(new Label("", skin)).width(10f).expand().fill().align(Align.center);
		
		table.add(lblSkor).expand().fill().align(Align.right);
		
		//table.debug();
		return table;
	}
}
