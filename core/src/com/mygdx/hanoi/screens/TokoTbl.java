package com.mygdx.hanoi.screens;

import java.util.ArrayList;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
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
import com.badlogic.gdx.utils.Json;
import com.mygdx.hanoi.util.Constants;
import com.mygdx.hanoi.util.DataPersister2;

public class TokoTbl {
	public Toko tokoContext;
	private DataPersister2 persister;
	private Preferences userpref, toko;
	private int index;  // buat nentuin item ini ada di list berapa di list toko (untuk update status nya setelah di beli)
	
	public void equipItem(String[] listData){
		persister = new DataPersister2();
		userpref = persister.getOrCreatePreferences(Constants.pref_userpref);
		Map insert = persister.getPreferencesData(userpref);
		
		if(listData[1].equals("Background")){
			insert.put(Constants.pref_userpref_background, listData[0]);
		}
		else if(listData[1].equals("Ring")){
			insert.put(Constants.pref_userpref_ring, listData[0]);
		}
		
		persister.insertPreferences(userpref, insert);
		this.tokoContext.showNotifAfterTran(1);
	}
	
	public void purchaseItem(String[] listData){
		persister = new DataPersister2();
		userpref = persister.getOrCreatePreferences(Constants.pref_userpref);
		toko = persister.getOrCreatePreferences(Constants.pref_toko);
		
		// cek dulu, poin nya cukup gak
		int poin = 0;
		switch(Gdx.app.getType()){
			case Desktop :
				poin = Integer.parseInt((String)persister.getPreferencesData(userpref).get(Constants.pref_userpref_poin));
				break;
			case Android : 
				poin = (int)persister.getPreferencesData(userpref).get(Constants.pref_userpref_poin);
				break;
		}
		
		// kalo poin cukup
		if(poin >= Integer.parseInt(listData[3])){  
			// kurangin uangnya
			Map insert = persister.getPreferencesData(userpref);
			insert.put(Constants.pref_userpref_poin, poin - Integer.parseInt(listData[3]));
			
			// set ke userpref nya
			if(listData[1].equals("Background")){
				insert.put(Constants.pref_userpref_background, listData[0]);
			}
			else if(listData[1].equals("Ring")){
				insert.put(Constants.pref_userpref_ring, listData[0]);
			}
			else if(listData[1].equals("hint")){
				// ambil hint yang udah ada
				int hint = 0;
				int hintPurchased = 0;
				switch(Gdx.app.getType()){
					case Desktop :
						hint = Integer.parseInt((String)persister.getPreferencesData(userpref).get(Constants.pref_userpref_hint));
						hintPurchased = Integer.parseInt((String)listData[5]); 
						break;
					case Android : 
						hint = (int)persister.getPreferencesData(userpref).get(Constants.pref_userpref_hint);
						hintPurchased = Integer.parseInt(listData[5]);//(int)listData[5];
						break;
					default :
						Gdx.app.log("hint purchasing", "app type not supported yet");
						break;
				}	
				
				// tambah hint nya (sementara aja, ngambilnya dari scale factor nya aja)
				insert.put(Constants.pref_userpref_hint, hint + hintPurchased);
			}
			
			// set item yang dibeli, isPurchased nya set ke "1"
			Map insertToko = persister.getPreferencesData(toko);  //ArrayList<String[]> itemList = (ArrayList<String[]>) persister.getPreferencesData(toko).get(Constants.pref_toko_item);
			ArrayList<String[]> itemList = new Json().fromJson(ArrayList.class, String[].class, (String)insertToko.get(Constants.pref_toko_item));
			itemList.get(this.index)[4] = "1";
			insertToko.put(Constants.pref_toko_item, new Json().toJson(itemList));
			
			// flush datanya
			persister.insertPreferences(userpref, insert);
			persister.insertPreferences(toko, insertToko);
			Gdx.app.log("ispurchase test", "purchased");
			this.tokoContext.showNotifAfterTran(1);
		}
		else{
			Gdx.app.log("ispurchase test", "poin ga cukup");
			this.tokoContext.showNotifAfterTran(0);  // gagal
		}
	}
	
	public Table generateContainer(Skin skin, final String[] listData, Pixmap pix, Toko toko, int i){
		/*
		 * listData format : (drawableName, jenis, nama, harga, isPurchased, scaleFactor)
		 */
		
		this.tokoContext = toko;
		this.index = i;
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
					equipItem(listData);
				}
				else{
					Gdx.app.log("aksi", "item not purchased yet");
					purchaseItem(listData);
				}
			}
		});
		
		// bikin tabel dan set warna nya
		Table table = new Table(skin);
		table.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(pix))));
		
		// image buat thumbnail.. karena gabisa setScale() ke anonymous object Image nya
		imgThumbnail = new Image(skin, listData[0] + Constants.thumbnail_prefix); //createThumbnail(skin, listData[0], listData[1]);  //imgThumbnail.scaleBy(Float.valueOf(listData[5])); 

		// isi data ke tabel
		table.add(imgThumbnail).align(Align.left);   
		table.add(lblJenis).fill().align(Align.left);
		table.add(lblNama).expand().fill().align(Align.left);
		table.add(lblHarga).fill().align(Align.left);
		table.add(btnAksi).align(Align.right).padLeft(7);
		
		table.padLeft(8).padRight(8).padTop(10).padBottom(10);
		//table.debug();
		
		return table;
	}
	
	private Image createThumbnail(Skin skin, String drawableName, String jenis){
		Image thumb = new Image(skin, drawableName);
		thumb.setSize(0.1f, 0.1f);
		thumb.setScale(0.1f, 0.1f);
		thumb.pack();
		
		/*switch(jenis) {
			case "Ring":
				thumb.setS
				break;
			case "Hint":
				break;
			case "Background":
				break;
			default:
				Gdx.app.log("Thumbnail", "object type unknown");
				return null; //break;
		} */
		
		return thumb;
	}
}
