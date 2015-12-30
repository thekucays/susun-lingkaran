package com.mygdx.hanoi.screens;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.hanoi.game.objects.RingB;
import com.mygdx.hanoi.game.objects.TiangB;
import com.mygdx.hanoi.util.Constants;
import com.mygdx.hanoi.util.GameTimer;

public class GamePlayB extends AbstractGameScreen{

	private Stage stage;
	private Skin skin_object, skin_decorations, skin_window, skin_ui;
	private TextureAtlas atlas_object, atlas_decorations, atlas_window, atlas_ui;
	private Table table;
	
	// GUI Labels
	private Label lblMode, lblWaktu, lblHint, lblMode_, lblWaktu_, lblHint_;
	private Button btnPause, btnHint, btnMainLagi, btnUlangi, btnKeluar, btnYa, btnTidak, btnLanjut;
	private Window windowPause, windowConfirm, windowWin, windowLose;
	
	private Image imgBackground;
	private List<RingB> rings = new ArrayList<RingB>(); //private RingB[] rings;
	private List<TiangB> tiangs = new ArrayList<TiangB>(); //private TiangB[] tiangs;
	private String gameMode;
	private String confirmOption;  // isinya bisa main lagi, ulangi, keluar.. buat nentuin event di window confirm nya
	private int hint, waktu, jmlRing, jmlTiang;
	
	// listener buat tiang nya
	private boolean isFirstClick, pause;
	
	// dummy game settings.. nanti ambil dari database
	private String resRing, resBg, resTiang;
	private Timer timer;
	
	public GamePlayB(Game game, String gMode, int hint, int waktu, int jmlRing, int jmlTiang) {
		super(game);
		this.isFirstClick = false;
		this.gameMode = gMode;
		this.hint = hint;
		this.waktu = waktu;
		this.pause = false;
		
		this.resBg = "bg-default";
		this.resRing = "ring-default";
		this.resTiang = "tiang-default";
		
		this.jmlRing = jmlRing;
		this.jmlTiang = jmlTiang;
	}
	
	private void rebuildStage(){
		atlas_object = new TextureAtlas("ui/gameplay/objects/objects.pack");
		atlas_decorations = new TextureAtlas("ui/gameplay/decorations/decorations.pack");
		atlas_ui = new TextureAtlas("ui/gameplay/ui-game/ui-game.pack");
		atlas_window = new TextureAtlas("ui/uiskin/uiskin.atlas");
		
		skin_object = new Skin(Gdx.files.internal("ui/gameplay/objects/objects.json"), atlas_object);
		skin_decorations = new Skin(Gdx.files.internal("ui/gameplay/decorations/decorations.json"), atlas_decorations);
		skin_ui = new Skin(Gdx.files.internal("ui/gameplay/ui-game/ui-game.json"), atlas_ui);
		skin_window = new Skin(Gdx.files.internal("ui/uiskin/uiskin.json"), atlas_window);
		
		// buat bikin ring dan tiang
		buildTiangs();
		buildRings();
		
		
		Table layerGuiRight = buildLayerGuiRight(); 
		Table layerGuiLeft = buildLayerGuiLeft();
		Table layerBackground = buildLayerBackground();
		Table layerGamePlay = buildLayerGamePlay();
		
		// window untuk pause menu, menang, dan kalah
		Table layerPause = buildLayerPause();
		Table layerConfirm = buildLayerConfirm();
		Table layerWin = buildLayerWin();
		Table layerLose = buildLayerLose();
		
		stage.clear();
		Stack stack = new Stack();
		stage.addActor(stack);
		stack.setSize(Constants.VIEWPORT_GUI_WIDTH, Constants.VIEWPORT_GUI_HEIGHT);
		
		stack.add(layerBackground);
		stack.add(layerGuiRight);	
		stack.add(layerGuiLeft);
		stack.add(layerGamePlay);
		
		stage.addActor(layerPause);
		stage.addActor(layerConfirm);
		stage.addActor(layerWin);
		stage.addActor(layerLose);
		
		if(gameMode.equals(Constants.MODE_TIMED)){
			executeTimer();
		}
	}

	private void buildRings(){
		for(int i=0; i<this.jmlRing; i++){
			RingB ring = new RingB(skin_object, this.resRing, "jenis", 5);
			ring.setPosition(tiangs.get(0).getX(), tiangs.get(0).getY());  // tes taruh di tiang
			ring.addListener(new ClickListener(){
				@Override
				public void clicked(InputEvent event, float x, float y) {
					//super.clicked(event, x, y);
					Gdx.app.log("RING", "RING clicked");
				}
			});
			rings.add(ring);
		}
	}
	private void buildTiangs(){
		for(int i=0; i<this.jmlTiang; i++){
			final TiangB tiang = new TiangB(skin_object, this.resTiang, this.jmlRing);
			tiang.setPosition(Constants.VIEWPORT_GUI_WIDTH/2, Constants.VIEWPORT_GUI_HEIGHT/2);
			tiang.addListener(new ClickListener(){
				@Override
				public void clicked(InputEvent event, float x, float y) {
					//super.clicked(event, x, y);
					Gdx.app.log("TIANG", "tiang clicked");
					RingB ringPeek = tiang.peek();
					ringPeek.setPosition(ringPeek.getX(), ringPeek.getY()+Constants.GAP_MEDIUM);
				}
			});
			tiangs.add(tiang);
		}
	}
	
	private void executeTimer(){
		timer = new Timer();
		TimerTask taskWaktu = new GameTimer(this);
		
		timer.scheduleAtFixedRate(taskWaktu, 1000, 1000);
		
		/*try{
			Thread.sleep(10000);
		} catch(InterruptedException ie){ Gdx.app.log("TIMER", "timer error"); }
		
		Gdx.app.log("TIMER", "Cancelling timer");
		timer.cancel(); */ 
	}
	
	private void stopTimer(){
		timer.cancel();
	}
	
	public void hitungWaktu(){
		this.waktu--;
	}
	
	private Table buildLayerGuiRight(){
		Table layer = new Table();
		layer.top().right();
		
		btnPause = new Button(skin_ui, "btnPause");
		btnPause.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				pause = true;
				windowPause.setVisible(true);
				
				stopTimer();
			}
		});
		btnHint = new Button(skin_ui, "btnHint");
		btnHint.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Gdx.app.log("GuiRight", "btnHint pressed");
				waktu--;
			}
		});
		
		layer.add(btnHint).spaceRight(Constants.GAP_SMALL);
		layer.add(btnPause);
		
		return layer;
	}
	private Table buildLayerGuiLeft(){		
		Table layer = new Table();
		layer.top().left();
		
		this.lblMode_ = new Label("Mode : ", skin_ui);
		this.lblMode = new Label(this.gameMode, skin_ui);
		this.lblWaktu_ = new Label("Waktu : ", skin_ui);
		this.lblWaktu = new Label(String.valueOf(this.waktu), skin_ui);
		this.lblHint_ = new Label("Hint : ", skin_ui);
		this.lblHint = new Label(String.valueOf(this.hint), skin_ui);
		
		
		layer.add(this.lblMode_).align(Align.left);
		layer.add(this.lblMode).align(Align.left).row();
		layer.add(this.lblWaktu_).align(Align.left);
		layer.add(this.lblWaktu).align(Align.left).row();
		layer.add(this.lblHint_).align(Align.left);
		layer.add(this.lblHint).align(Align.left).row();
		
		return layer;
	}
	private Table buildLayerBackground(){		
		Table layer = new Table();
		imgBackground = new Image(skin_decorations, this.resBg);
		layer.add(imgBackground);
		
		return layer;
	}
	private Table buildLayerGamePlay(){		
		Table layer = new Table();
		layer.center();
		layer.debug();
		
		// add tiangs
		for(int i=0; i<tiangs.size(); i++){
			layer.addActor(tiangs.get(i));
		}
		
		// add rings
		for(int i=0; i<rings.size(); i++){
			layer.addActor(rings.get(i));
		}
		
		// test pushing
		tiangs.get(0).push(rings.get(0));
		
		return layer;
	}
	private Table buildLayerPause(){		
		windowPause = new Window(Constants.PAUSE_TITLE, skin_window);
		windowPause.setVisible(false);
		
		btnKeluar = new TextButton(Constants.PAUSE_KELUAR, skin_window);
		btnKeluar.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				confirmOption = Constants.PAUSE_OPTION_KELUAR;
				windowPause.setVisible(false);
				windowConfirm.setVisible(true);
			}
		});
		btnUlangi = new TextButton(Constants.PAUSE_ULANGI, skin_window);
		btnUlangi.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				confirmOption = Constants.PAUSE_OPTION_ULANGI;
				windowPause.setVisible(false);
				windowConfirm.setVisible(true);
			}
		});
		btnMainLagi = new TextButton(Constants.PAUSE_MAIN_LAGI, skin_window);
		btnMainLagi.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				pause = false;
				windowPause.setVisible(false);
				
				executeTimer();
			}
		});
		
		Table isi = new Table(skin_window);
		isi.center();
		isi.add(btnMainLagi);
		isi.add(btnUlangi).space(Constants.GAP_MEDIUM);
		isi.add(btnKeluar);
		isi.pad(Constants.GAP_BIG);
		
		windowPause.add(isi);
		windowPause.pack();
		windowPause.setPosition((Constants.VIEWPORT_GUI_WIDTH/2)-(windowPause.getWidth()/2), (Constants.VIEWPORT_GUI_HEIGHT/2)-(windowPause.getHeight()/2));  // taruh di tengah
		
		return windowPause;
	}
	private Table buildLayerConfirm(){
		windowConfirm = new Window(Constants.CONFIRM_TITLE, skin_window);
		windowConfirm.setVisible(false);
		
		btnYa = new TextButton(Constants.CONFIRM_YES, skin_window);
		btnYa.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if(confirmOption.equals(Constants.PAUSE_OPTION_KELUAR)){
					game.setScreen(new MainMenu(game));
					Gdx.app.log("Confirm", "option keluar");
				}
				else if(confirmOption.equals(Constants.PAUSE_OPTION_ULANGI)){
					pause = false;
					Gdx.app.log("Confirm", "option ulangi");
					rebuildStage();
				}
			}
		});
		btnTidak = new TextButton(Constants.CONFIRM_NO, skin_window);
		btnTidak.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				//pause = false;
				windowConfirm.setVisible(false);
				windowPause.setVisible(true);
			}
		});
		
		Table isi = new Table(skin_window);
		isi.center();
		isi.add(btnYa).spaceRight(Constants.GAP_MEDIUM);
		isi.add(btnTidak);
		isi.pad(Constants.GAP_BIG);
		windowConfirm.add(isi);
		windowConfirm.pack();
		windowConfirm.setPosition((Constants.VIEWPORT_GUI_WIDTH/2)-(windowConfirm.getWidth()/2), (Constants.VIEWPORT_GUI_HEIGHT/2)-(windowConfirm.getHeight()/2));  // taruh di tengah
		
		return windowConfirm;
	}
	private Table buildLayerWin(){		
		windowWin = new Window(Constants.WIN_TITLE, skin_window);
		windowWin.setVisible(false);
		
		
		return windowWin;
	}
	private Table buildLayerLose(){		
		windowLose = new Window(Constants.LOSE_TITLE, skin_window);
		windowLose.setVisible(false);
		
		btnKeluar = new TextButton(Constants.LOSE_BTN_KELUAR, skin_window);
		btnKeluar.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				
			}
		});
		btnUlangi = new TextButton(Constants.LOSE_BTN_ULANGI, skin_window);
		btnUlangi.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				windowLose.setVisible(false);
			}
		});
		
		Table isi = new Table(skin_window);
		isi.center();
		isi.add(Constants.LOSE_NOTE).row();
		isi.add(btnUlangi).spaceRight(Constants.GAP_MEDIUM);
		isi.add(btnKeluar);
		isi.pad(Constants.GAP_BIG);
		isi.debug();
		
		windowLose.add(isi);
		windowLose.pack();
		windowLose.setPosition((Constants.VIEWPORT_GUI_WIDTH/2)-(windowLose.getWidth()/2), (Constants.VIEWPORT_GUI_HEIGHT/2)-(windowLose.getHeight()/2));  // taruh di tengah
		
		return windowLose;
	}
	
	@Override
	public void show() {
		stage = new Stage(new StretchViewport(Constants.VIEWPORT_GUI_WIDTH, Constants.VIEWPORT_GUI_HEIGHT));
		Gdx.input.setInputProcessor(stage);
		
		rebuildStage();
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void resume() {
		pause = false;
	}
	
	@Override
	public void render(float deltaTime) {
		stage.act(deltaTime);
		stage.draw();
		
		if(this.gameMode.equals(Constants.MODE_TIMED) && this.waktu <= 0){
			timer.cancel();
			windowLose.setVisible(true);
		}
		this.lblWaktu.setText(String.valueOf(this.waktu));
		this.lblHint.setText(String.valueOf(this.hint));
		
		
	}

	@Override
	public void resize(int width, int height) {
		
	}
	
	@Override
	public void pause() {
		pause = true;
	}
	
	@Override
	public void dispose(){
		
	}
}
