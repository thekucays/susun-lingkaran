package com.mygdx.hanoi.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.hanoi.util.Constants;

public class Assets implements Disposable, AssetErrorListener{
	public static final String TAG = Assets.class.getName();
	public static final Assets instance = new Assets();
	
	private AssetManager assetManager;
	
	// inner class objects
	public AssetTiang tiang;
	public AssetBackgroud bg;
	public AssetTombol tombol;
	public AssetTombolBg tombolBg;
	public AssetRing ring;
	
	//singleton pattern, buat mencegah instansiasi dari class yang lain
	private Assets(){}
	
	//public void init(AssetManager assetManager){
	public void init(AssetManager assetManager, String jenisBg, String jenisRing){
		this.assetManager = assetManager;
		assetManager.setErrorListener(this);
		
		//load texture atlas yang udah dibikin pake TexturePacker nya (liat ebook page 167) 
		assetManager.load(Constants.TEXTURE_ATLAS_OBJECTS, TextureAtlas.class);
		assetManager.load(Constants.TEXTURE_ATLAS_DECORATION, TextureAtlas.class);
		assetManager.load(Constants.TEXTURE_ATLAS_GUI, TextureAtlas.class);
		
		//load asset nya sampai selesai
		assetManager.finishLoading();
		
		Gdx.app.log(TAG, "# of assets loaded: " + assetManager.getAssetNames().size);
		for (String a : assetManager.getAssetNames()){
			Gdx.app.log(TAG, "asset: " + a);
		}
		
		
		// biar lebih smooth, gambar nya dikasih filter
		TextureAtlas atlasObject = assetManager.get(Constants.TEXTURE_ATLAS_OBJECTS);
		TextureAtlas atlasDecoration= assetManager.get(Constants.TEXTURE_ATLAS_DECORATION);
		TextureAtlas atlasGui= assetManager.get(Constants.TEXTURE_ATLAS_GUI);
		for(Texture t : atlasObject.getTextures()){
			t.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		}
		for(Texture t : atlasDecoration.getTextures()){
			t.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		}
		for(Texture t : atlasGui.getTextures()){
			t.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		}
		
		// inner class objects
		tiang = new AssetTiang(atlasObject);
		bg = new AssetBackgroud(atlasDecoration, jenisBg);
		tombol = new AssetTombol(atlasGui);
		tombolBg = new AssetTombolBg(atlasDecoration);
		ring = new AssetRing(atlasObject, jenisRing);
	}
	
	@Override
	public void error(AssetDescriptor asset, Throwable throwable) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose(){
		assetManager.dispose();
	}

	/*
	 * Nanti disini ada inner class, buat nge-load objek apa aj yang mau ditampilin..
	 * satu objek satu inner class
	 * (liat ebook page 170)
	 */
	/*public class AssetGoldCoin{
		public final AtlasRegion goldCoin;
		
		public AssetGoldCoin(TextureAtlas atlas){
			goldCoin = atlas.findRegion("item_gold");
		}
	}*/
	
	public class AssetTiang{
		public final AtlasRegion tiang;
		
		public AssetTiang(TextureAtlas atlas){
			tiang = atlas.findRegion("tiang");
		}
	}
	
	public class AssetRing{
		public final AtlasRegion ring;
		
		// jenis ring dimasukin disini, karena jenis ring bisa diganti-ganti sesuai yang dipilih
		public AssetRing(TextureAtlas atlas, String jenisRing){
			if(!jenisRing.equals("")){
				ring = atlas.findRegion(jenisRing);
			}
			else{
				ring = atlas.findRegion("ring-default");
			}
		}
	}
	
	public class AssetBackgroud{
		public final AtlasRegion bg;
		
		public AssetBackgroud(TextureAtlas atlas, String jenisBg){
			if(jenisBg.equals("")){
				bg = atlas.findRegion(jenisBg);
			}
			else{
				bg = atlas.findRegion("bg_default");
			}
		}
	}
	
	//bener pake ini ga yah?
	public class AssetTombol{
		public final AtlasRegion tombol;
		
		public AssetTombol(TextureAtlas atlas){
			tombol = atlas.findRegion("tombol");
		}
	}
	
	public class AssetTombolBg{  // maksud nya ini adalah layer yang dipake untuk tempat naruh semua tombol..bukan background di tombolnya
		public final AtlasRegion tombolBg;
		
		public AssetTombolBg(TextureAtlas atlas){
			tombolBg = atlas.findRegion("tombolBg");
		}
	}
}
