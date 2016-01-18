package com.mygdx.hanoi.screens;

import java.util.ArrayList;
import java.util.Map;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.hanoi.util.Constants;
//import com.sun.java.swing.plaf.windows.WindowsTreeUI.ExpandedIcon;
import com.mygdx.hanoi.util.DataPersister2;

public class Toko extends AbstractGameScreen {

	private Stage stage;
	private TextureAtlas atlas, atlas_ui;
	private Skin skin, skin_ui;
	private Table table;
	private Button btnBack, btnOK;
	private Image imgHeading, imgCoin, imgBackground;
	private Label lblUang, lblNotif;
	private DataPersister2 persister;
	private Preferences toko, userpref;
	
	// ini buat keperluan list nya
	private Table container;
	private ScrollPane scrollPane;
	
	// TODO add to diagram
	// ini buat window sukses/gagal setelah melakukan pembelian
	private Window windowNotif;
	private String currNotif;
	
	
	public Toko(Game game) {
		super(game);
		
		persister = new DataPersister2();
		toko = persister.getOrCreatePreferences(Constants.pref_toko);
		userpref = persister.getOrCreatePreferences(Constants.pref_userpref);
	}

	@Override
	public void show() {
		stage = new Stage(new StretchViewport(Constants.VIEWPORT_GUI_WIDTH, Constants.VIEWPORT_GUI_HEIGHT));
		Gdx.input.setInputProcessor(stage);
		
		int dummyUang = 9999;
		
		rebuildStage();
		windowNotif.setVisible(true);
	}
	
	// TODO add to diagram (public biar bisa di akses sama TokoTbl)
	public void showNotifAfterTran(int status){
		// tampilin notifikasi udah berhasil beli apa engga disini
		// terus refresh screen nya
		if(status == 1){  // berhasil beli
			this.currNotif = Constants.TOKO_NOTIF_SUKSES;
			// windowNotifOK.setVisible(true);
		}
		else{
			this.currNotif = Constants.TOKO_NOTIF_GAGAL;
			// windowNotifNO.setVisible(true);
		}
		
		rebuildStage();
		windowNotif.setVisible(true);
	}
	
	private void rebuildStage(){
		atlas = new TextureAtlas("ui/toko/toko.pack");
		atlas_ui = new TextureAtlas("ui/uiskin/uiskin.atlas");
		skin = new Skin(Gdx.files.internal("ui/toko/toko.json"), atlas);
		skin_ui = new Skin(Gdx.files.internal("ui/uiskin/uiskin.json"), atlas_ui);
		//toko = persister.getOrCreatePreferences(Constants.pref_toko);
		
		int uang = 0;
		
		switch(Gdx.app.getType()){
			case Desktop : 
				uang = Integer.parseInt((String)persister.getPreferencesData(userpref).get(Constants.pref_userpref_poin));
				break;
			case Android : 
				uang = (int)persister.getPreferencesData(userpref).get(Constants.pref_userpref_poin);
				break;
			default : 
				Gdx.app.log("error", "app type not implemented yet");
				break;
		}
		
		// table untuk layer tampilan nya
		Table layerBackground = buildLayerBackground();
		Table layerUang = buildLayerUang(uang);
		Table layerHeader = buildLayerHeader();
		Table layerListItem = buildLayerListItem();
		Table layerNotif = buildLayerNotif();
		Table layerBack = buildLayerBack(); // sepertinya perlu dipisah karena posisinya di pojok kanan atas sendiri
		
		stage.clear();
		Stack stack = new Stack();
		stage.addActor(stack);
		stack.setSize(Constants.VIEWPORT_GUI_WIDTH, Constants.VIEWPORT_GUI_HEIGHT);
		
		stack.add(layerBackground);
		stack.add(layerUang);
		stack.add(layerHeader);
		stack.add(layerListItem);
		stack.add(layerBack);
		stack.add(layerNotif);
	}
	private Table buildLayerBackground(){
		Table layer = new Table();
		imgBackground= new Image(skin, "background");
		layer.add(imgBackground);
		
		return layer;
	}
	private Table buildLayerNotif(){
		windowNotif = new Window(Constants.TOKO_NOTIF_TITLE, skin_ui);
		windowNotif.setVisible(false);
		
		btnOK = new TextButton(Constants.TOKO_NOTIF_BTN, skin_ui);
		btnOK.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				windowNotif.setVisible(false);
			}
		});
		
		Table isi = new Table(skin_ui);
		isi.center();
		isi.add(currNotif).row();
		isi.add(btnOK);
		
		windowNotif.add(isi);
		windowNotif.pack();
		windowNotif.setPosition((Constants.VIEWPORT_GUI_WIDTH/2)-(windowNotif.getWidth()/2), (Constants.VIEWPORT_GUI_HEIGHT/2)-(windowNotif.getHeight()/2));  // taruh di tengah
		
		
		return windowNotif;
		
	}
	private Table buildLayerHeader(){
		Table layer = new Table();
		layer.top();
		imgHeading = new Image(skin, "header");
		layer.addActor(imgHeading);
		imgHeading.setPosition((Constants.VIEWPORT_GUI_WIDTH/2)-(imgHeading.getWidth()/2), Constants.VIEWPORT_GUI_HEIGHT-Constants.HEADER_TOP_GAP);
		
		return layer;
	}
	private Table buildLayerUang(int dummyUang){
		Table layer = new Table();
		layer.left().top();
		imgCoin = new Image(skin, "coin");
		lblUang = new Label(String.valueOf(dummyUang), skin);
		
		layer.add(imgCoin).width(Constants.COIN_SIZE).height(Constants.COIN_SIZE).padLeft(Constants.GAP_SMALL);
		layer.add(lblUang).spaceLeft(Constants.GAP_SMALL);
		
		return layer;
	}
	private Table buildLayerListItem(){
		// bikin pixmap color buat background row nya
		Pixmap pix = new Pixmap(1, 1, Format.RGB565);
		pix.setColor(Color.valueOf("ff6600"));  //warna sama kaya warna button //pix.setColor(Color.CYAN); 
		pix.fill();
		
		// dummy list data.. real nya nge-query dari preferences nya
		ArrayList<String[]> dummyToko = new ArrayList<String[]>();  // declare apa yang mau ditaruh sini, biar ga error di kemudian method
		dummyToko.add(new String[] {"ring-default", "Ring", "Pie Greentea", "500", "1", "50"});   // yang paling belakang "isPurchased", "scaleFactor"
		dummyToko.add(new String[] {"bg-default", "Background", "Background default", "500", "0", "70"});
		dummyToko.add(new String[] {"bg-default", "Background", "Background default", "500", "1", "70"});
		dummyToko.add(new String[] {"bg-default", "Background", "Background default", "500", "1", "70"});
		dummyToko.add(new String[] {"bg-default", "Background", "Background default", "500", "1", "70"});
		dummyToko.add(new String[] {"bg-default", "Background", "Background default", "500", "1", "70"});
		dummyToko.add(new String[] {"bg-default", "Background", "Background default", "500", "1", "70"});
		
		// real toko data
		Map itemList = persister.getPreferencesData(toko);
		int length = new Json().fromJson(ArrayList.class, String[].class, (String)itemList.get(Constants.pref_toko_item)).size();
		
		// inner table buat atur list nya
		Table innerContainer = new Table();
		for(int i=0; i<length; i++){
			// tes output data nya
			Gdx.app.log("1", ((String[])new Json().fromJson(ArrayList.class, String[].class, (String)itemList.get(Constants.pref_toko_item) ).get(i)) [0]);
			
			Table temp = new TokoTbl().generateContainer(skin, ((String[])new Json().fromJson(ArrayList.class, String[].class, (String)itemList.get(Constants.pref_toko_item) ).get(i)), pix, this, i);
			
			innerContainer.add(temp).expand().fill();
			innerContainer.getCell(temp).spaceTop(10).spaceBottom(10);
			innerContainer.row();
		}

		// data yang udah jadi masukin scrollPane biar bisa di-scroll
		scrollPane = new ScrollPane(innerContainer, skin);
		//scrollPane.setFillParent(true);
		
		// terakhir, tambahin ke layer aslinya
		Table layer = new Table(); 
		layer.center(); //layer.setPosition(Constants.VIEWPORT_GUI_WIDTH/2), y);   
		
		// addActor(), supaya dia keluar dari parent, jadi bisa di-det koordinat nya sendiri 
		layer.addActor(scrollPane);
		scrollPane.setWidth(Constants.table_width);
		scrollPane.setHeight(Constants.table_height);
		scrollPane.setPosition((Constants.VIEWPORT_GUI_WIDTH/2)-(scrollPane.getWidth()/2), (Constants.VIEWPORT_GUI_HEIGHT/2)-(scrollPane.getHeight()/2)-Constants.TABLE_TOP_GAP);
		
		layer.debug();
		
		return layer;
	}
	private Table buildLayerBack(){
		Table layer = new Table();
		layer.right().top();
		
		btnBack = new Button(skin, "btnBack");
		btnBack.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Gdx.app.log("Control", "btnBack pressed");
				game.setScreen(new MainMenu(game));
			}
		});
		
		layer.add(btnBack);
		layer.row().expandY();
		
		return layer;
	}
	
	@Override
	public void render(float deltaTime) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.act(deltaTime);
		stage.draw();
		
		//Gdx.app.log("log", "delta time");
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true); 
	}

	@Override
	public void hide() {
		stage.dispose();
		skin.dispose();
	}

	@Override
	public void pause() {
		
	}
	
	@Override
	public void resume() {

	}
	
	@Override
	public void dispose() {  // for the sake of efficiency.. i don't know whether this is required or not lol :P
		skin.dispose();
		stage.dispose();
		atlas.dispose();
	}
}
