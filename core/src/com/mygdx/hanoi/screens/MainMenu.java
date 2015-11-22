package com.mygdx.hanoi.screens;

import java.util.ArrayList;
import java.util.Map;

import javax.swing.text.TabExpander;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.hanoi.util.Constants;
import com.mygdx.hanoi.util.DataPersister2;

public class MainMenu extends AbstractGameScreen{  //implements Screen { //

	private Stage stage;  // di dalam Stage nanti ada "actor" (Table, sprite, label, tombol, dsb)
	private Skin skin;  // buat template tampilan aktor yang akan ditaruh di stage
	private TextureAtlas atlas;
	private Table table;
	private TextButton btnPlay, btnSound, btnExit; //, btnInfo;
	private Label heading;
	//private BitmapFont white, black;  // font yang dipake.. format nya true type (.ttf)
	
	// variabel buat bikin efek heading (judul game) nya
	private float rotation, shake;
	private final float degreesPerSecond = 5.0f;
	private Container con;
	
	// start to following ebook
	private Image imgLogo;
	private Button btnHscore, btnInfo, btnToko;
	private Window windowInfo, windowHscore;
	private Label lblStart, lblNull;
	
	public MainMenu(Game game) {
		super(game);
		Gdx.app.log("Main Server", "Hello, MainMenu class was just started.. Have Fun! ");
		// buat inputan nya di-init di show() 
		// jadi urutan nya show() dulu, baru render()
		// semua styling udah dipindah ke json file
	}
	
	// start to following ebook
	private void rebuildStage(){
		atlas = new TextureAtlas("ui/menu.pack");
		skin = new Skin(Gdx.files.internal("ui/menu.json"), atlas);
		
		// bikin layer buat tampilannya.. layer pake tabel
		Table layerBackground = buildLayerBackground();
		Table layerLogo = bulidLayerLogo();
		Table layerControls = buildLayerControls();
		Table layerOptions = buildLayerOptions();
		
		// taruh table nya ke dalem stack (Stack nya libgdx)
		stage.clear();
		Stack stack = new Stack();
		stage.addActor(stack);
		stack.setSize(Constants.VIEWPORT_GUI_WIDTH, Constants.VIEWPORT_GUI_HEIGHT);
		
		stack.add(layerBackground);
		stack.add(layerLogo);
		stack.add(layerControls);
		
		stage.addActor(layerOptions);
	}
	
	private Table buildLayerBackground(){
		Table layer = new Table();
		//layer.setColor(0x64/255.0f, 0x95/255.0f, 0xed/255.0f, 0xff/255.0f);  // cornflower blue..nyomot dari TowerOfHanoiMain.java..for testing purpose
		//layer.setBackground(background);
		return layer;
	}
	private Table bulidLayerLogo(){
		Table layer = new Table();
		layer.center();
		
		imgLogo = new Image(skin, "logo");
		layer.add(imgLogo);
		layer.row();
		imgLogo.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.log("Image", "Finallyyyy");
			}
		});
		
		lblNull = new Label("", skin);
		layer.add(lblNull);
		layer.row();
		
		lblStart = new Label("Tekan logo untuk mulai", skin);
		layer.add(lblStart).bottom();
		
		//layer.debug();
		return layer;
	}
	private Table buildLayerControls(){
		Table layer = new Table();
		layer.bottom();
		
		btnHscore = new Button(skin, "btnHscore");
		layer.add(btnHscore);
		btnHscore.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Gdx.app.log("Button : ", "btnHscore pressed");
			}
		});
		
		btnToko = new Button(skin, "btnToko");
		layer.add(btnToko);
		btnToko.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Gdx.app.log("Button : ", "btnToko pressed");
			}
		});
		
		btnInfo= new Button(skin, "btnInfo");
		layer.add(btnInfo);
		btnInfo.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Gdx.app.log("Button : ", "btnInfo pressed");
			}
		});
		
		// set spacing antar button nya
		layer.getCell(btnHscore).spaceRight(5);
		layer.getCell(btnToko).spaceLeft(5).spaceRight(5);
		layer.getCell(btnInfo).spaceLeft(5);
		
		return layer;
	}
	private Table buildLayerOptions(){
		Table layer = new Table();
		return layer;
	}
	
	@Override
	public void show() {
		// coba print preferences yang udah dibikin di Main.java disini (oke dah bisaa!!)
		DataPersister2 hs = new DataPersister2();
		Preferences hScore = hs.getOrCreatePreferences("highScores");
		Map data = hs.getPreferencesData(hScore);
		ArrayList<String[]> loop = new Json().fromJson(ArrayList.class, String[].class, (String)data.get("freeMode"));
		for(int i=0; i<loop.size(); i++){
	    	Gdx.app.log("for loop main menu", "the name value is" + loop.get(i)[0]);
	    	Gdx.app.log("for loop main menu", "the score value is" + loop.get(i)[1]);
	    }
		
		// start to following ebook
		stage = new Stage(new StretchViewport(Constants.VIEWPORT_GUI_WIDTH, Constants.VIEWPORT_GUI_HEIGHT));
		Gdx.input.setInputProcessor(stage);
		rebuildStage();
		
		/*
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		
		atlas = new TextureAtlas("ui/menu.pack");//atlas = new TextureAtlas("ui/atlas.pack");  // bisa pake .xml ga ya? apa mesti .pack? (gabisa)
		skin = new Skin(Gdx.files.internal("ui/menu.json"), atlas);//skin = new Skin(Gdx.files.internal("ui/menuSkin.json"), atlas);
		
		table = new Table(skin);
		table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());  //koordinat 0,0 dan sebesar layar
		
		
		// bikin button
		btnPlay = new TextButton("Mulai", skin);
		btnPlay.pad(20);
		btnPlay.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.log("Main Server", "button play clicked");
				((Game)Gdx.app.getApplicationListener()).setScreen(new HighScore());  // TODO change to level select screen
			}
		});
		
		btnExit = new TextButton("Keluar", skin);
		btnExit.pad(20);
		btnExit.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				//super.clicked(event, x, y);
				Gdx.app.log("Main Server", "Shutting down gracefully..");
				Gdx.app.exit();
			}
		});
		
		
		// bikin heading.. coba taruh di dalem Container biar bisa di rotate
		heading = new Label("Susunan Ring", skin);
		heading.setFontScale(1.5f);
		con = new Container(heading);
		con.setTransform(true);
		
		
		// masukin item ke tabel, note : tabel termasuk ke dalam "aktor" juga
		table.add(con); // heading);
		table.getCell(con).spaceBottom(50);  // ambil cell yang ada heading nya, dan modifikasi disini
		table.row();
		table.add(btnPlay);
		table.getCell(btnPlay).spaceBottom(7);
		table.row();
		table.add(btnExit);
		table.setTransform(true);  // TODO remove	
		//table.debug();
		
		// tambahin para aktor ke stage.. setelah ini baru di-render.. pay attention to render() :)
		stage.addActor(table);
		*/
	}

	@Override
	public void render(float delta) {
		// start to following ebook
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		rotation = (rotation + Gdx.graphics.getDeltaTime() * degreesPerSecond) % 360;
		shake = MathUtils.sin(rotation) * 5.0f;
		imgLogo.setOrigin(imgLogo.getX()/2, imgLogo.getY()/2);
		imgLogo.setRotation(shake);
		
		stage.act(delta);
		stage.draw();
		
		/*
		//ini cuman buat nge-itemin layar..testing aja
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// coba bikin animasi buat heading nya.. not working (yet) 
		rotation = (rotation + Gdx.graphics.getDeltaTime() * degreesPerSecond) % 360;
		shake = MathUtils.sin(rotation) * 5.0f;
		con.setOrigin(con.getX()/2, con.getY()/2);
		con.setRotation(shake);
		
		stage.act(delta);
		stage.draw();
		*/
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);  // refer to http://stackoverflow.com/questions/23091823/incorrect-click-event-when-resize-on-libgdx
		//table.setClip(true);  // ini buat nge-override table nya supaya jalanin setTransform()
		//table.setSize(width, height);
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {
		stage.dispose();
		skin.dispose();
	}

	@Override
	public void dispose() {
		skin.dispose();
		stage.dispose();
		atlas.dispose();
	}

}
