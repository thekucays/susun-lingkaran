package com.mygdx.hanoi.screens;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class TokoTbl {
	public Table generateContainer(Skin skin, String[] listData, Pixmap pix){
		/*
		 * listData format : (drawableName, jenis, nama, harga, isPurchased, scaleFactor)
		 */
		
		final String isPurchased = listData[4];  // 1 udah kebeli, 0 belum kebeli
		
		Image imgThumbnail;
		Label lblJenis = new Label(listData[1] + " - ", skin);
		Label lblNama = new Label(listData[2] + " ", skin);
		Label lblHarga = new Label("@ " + listData[3], skin);
		Button btnAksi = new Button(skin, (isPurchased.equals("1"))?"btnPasang":"btnBeli");  
		btnAksi.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Gdx.app.log("aksi", "button clicked!");
				if(isPurchased.equals("1")){
					Gdx.app.log("aksi", "item purchased");
				}
				else{
					Gdx.app.log("aksi", "item not purchased yet");
				}
			}
		});
		
		// bikin tabel dan set warna nya
		Table table = new Table(skin);
		table.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(pix))));
		
		// image buat thumbnail.. karena gabisa setScale() ke anonymous object Image nya
		imgThumbnail = new Image(skin, listData[0]);
		//imgThumbnail.scaleBy(Float.valueOf(listData[5])); 
		
		// isi data ke tabel
		table.add(imgThumbnail).expand().fill().align(Align.left);   
		table.add(lblJenis).expand().fill().align(Align.left);
		table.add(lblNama).expand().fill().align(Align.left);
		table.add(lblHarga).expand().fill().align(Align.left);
		table.add(btnAksi).expand().fill().align(Align.right);
		
		return table;
	}
}
