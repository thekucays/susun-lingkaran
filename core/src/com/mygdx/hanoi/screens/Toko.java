package com.mygdx.hanoi.screens;

import java.util.ArrayList;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.hanoi.util.Constants;
import com.sun.java.swing.plaf.windows.WindowsTreeUI.ExpandedIcon;

public class Toko extends AbstractGameScreen {

	private Stage stage;
	private TextureAtlas atlas;
	private Skin skin;
	private Table table;
	private Button btnBack;
	private Image imgHeading, imgCoin, imgBackground;
	
	// ini buat keperluan list nya
	private Table container;
	private ScrollPane scrollPane;
	
	public Toko(Game game) {
		super(game);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void show() {
		stage = new Stage(new StretchViewport(Constants.VIEWPORT_GUI_WIDTH, Constants.VIEWPORT_GUI_HEIGHT));
		Gdx.input.setInputProcessor(stage);
		
		rebuildStage();
	}
	
	private void rebuildStage(){
		atlas = new TextureAtlas("ui/toko/toko.pack");
		skin = new Skin(Gdx.files.internal("ui/toko/toko.json"), atlas);
		
		// table untuk layer tampilan nya
		Table layerBackground = buildLayerBackground();
		Table layerHeader = buildLayerHeader();
		Table layerListItem = buildLayerListItem();
		Table layerBack = buildLayerBack(); // sepertinya perlu dipisah karena posisinya di pojok kanan atas sendiri
		
		stage.clear();
		Stack stack = new Stack();
		stage.addActor(stack);
		stack.setSize(Constants.VIEWPORT_GUI_WIDTH, Constants.VIEWPORT_GUI_HEIGHT);
		
		stack.add(layerBackground);
		stack.add(layerHeader);
		stack.add(layerListItem);
		stack.add(layerBack);
	}
	private Table buildLayerBackground(){
		Table layer = new Table();
		imgBackground= new Image(skin, "background");
		layer.add(imgBackground);
		
		return layer;
	}
	private Table buildLayerHeader(){
		Table layer = new Table();
		layer.top();
		//imgHeading = new Image(skin, "header");
		//layer.addActor(imgHeading);
		//imgHeading.setPosition((Constants.VIEWPORT_GUI_WIDTH/2)-(imgHeading.getWidth()/2), Constants.VIEWPORT_GUI_HEIGHT-Constants.HEADER_TOP_GAP);
		
		return layer;
	}
	private Table buildLayerListItem(){
		// bikin pixmap color buat background row nya
		Pixmap pix = new Pixmap(1, 1, Format.RGB565);
		pix.setColor(Color.valueOf("ff6600"));  //warna sama kaya warna button pix.setColor(Color.CYAN); 
		pix.fill();
		
		// dummy list data.. real nya nge-query dari preferences nya
		ArrayList<String[]> dummyToko = new ArrayList<String[]>();  // declare apa yang mau ditaruh sini, biar ga error di kemudian method
		dummyToko.add(new String[] {"ring-default", "Ring", "Pie Greentea", "500", "1", "50"});   // yang paling belakang "isPurchased", "scaleFactor"
		dummyToko.add(new String[] {"bg-default", "Ring", "Background default", "500", "1", "70"});
		
		// inner table buat atur list nya
		Table innerContainer = new Table();
		for(int i=0; i<dummyToko.size(); i++){
			innerContainer.add(new TokoTbl().generateContainer(skin, dummyToko.get(i), pix)).expand().fill();
			innerContainer.row();
		}
		
		// data yang udah jadi masukin scrollPane biar bisa di-scroll
		scrollPane = new ScrollPane(innerContainer, skin);
		
		// terakhir, tambahin ke layer aslinya
		Table layer = new Table(); 
		layer.center();
		layer.add(scrollPane);
		
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
