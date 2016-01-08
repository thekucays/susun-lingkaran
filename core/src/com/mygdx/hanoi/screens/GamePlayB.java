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
	private Button btnPause, btnHint, btnMainLagi, btnUlangi, btnKeluar, btnYa, btnTidak;
	private Button btnLanjut, btnUlangi_, btnMenu; // untuk window menang (masih ada level selanjutnya)
	private Button btnOK; // untuk window menang (level udah abis)
	private Window windowPause, windowConfirm, windowWinA, windowWinB, windowLose;
	
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
	
	// variabel temporary untuk mindahin ring/tiang
	private List<Object> firstObj, secondObj;
	private TiangB firstTiang, secondTiang;
	private RingB firstRing, secondRing;
	
	// variabel unttuk nentuin apakah window notif (win, lose) sudah tampil apa belum, di-render apa engga
	private boolean notifShown;
	
	// variabel untuk nyimpen skor permainan
	private int skor;
	
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
		// set variabel temporary ke null
		/*this.firstRing = null;
		this.firstTiang = null;
		this.secondRing = null;
		this.secondTiang = null; */
		this.firstObj = new ArrayList<Object>();
		this.secondObj = new ArrayList<Object>();
		this.notifShown = false;
		this.skor = 0;
		
		
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
		Table layerWinA = buildLayerWinA();
		Table layerWinB = buildLayerWinB();
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
		stage.addActor(layerWinA);
		stage.addActor(layerWinB);
		stage.addActor(layerLose);
		
		if(gameMode.equals(Constants.MODE_TIMED)){
			executeTimer();
		}
	}
	
	private void setFirst(TiangB t, RingB r){
		this.firstObj.add(t);
		this.firstObj.add(r);
	}
	private List getFirst(){
		return this.firstObj;
	}
	private void clearTemp(){
		this.secondObj.clear();
		this.firstObj.clear();
	}
	private void setSecond(TiangB t, RingB r){
		this.secondObj.add(t);
		this.secondObj.add(r);
	}
	private List getSecond(){
		return this.secondObj;
	}
	

	private void buildRings(){
		int count = this.jmlRing;
		
		for(int i=this.jmlRing; i>0; i--){  // reverse for loop.. supaya bisa di-push ke tiang nya
			RingB ring = new RingB(skin_object, this.resRing, "jenis", i);
			count--;
			
			ring.setScale(ring.getLength()*Constants.RING_SCALE_FACTOR, 1);
			ring.setOriginX(ring.getX() + ring.getWidth()/2);       // Math.round(ring.getWidth()/2));
			
			ring.setPosition(tiangs.get(0).getX(), tiangs.get(0).getTopY());  // tiangs.get(0).getX()+ring.getOriginX()
			tiangs.get(0).setTopY(tiangs.get(0).getTopY() + Constants.GAP_RING);
			
			ring.addListener(new ClickListener(){
				@Override
				public void clicked(InputEvent event, float x, float y) {
					//super.clicked(event, x, y);
					Gdx.app.log("RING", "RING clicked");
				}
			});
			rings.add(ring);
			
			Gdx.app.log("RING", "1. " + tiangs.get(0).getTopY());
			Gdx.app.log("RING", "2. " + tiangs.get(0).getY()+Constants.GAP_MEDIUM);
			Gdx.app.log("RING", "3. " + tiangs.get(0).getY());
			Gdx.app.log("RING", "4. " + ring.getHeight());
			Gdx.app.log("RING", "5. " + ring.getOriginX());
			
			Gdx.app.log("RING", "6. " + ring.getX());
			Gdx.app.log("RING", "7. " + tiangs.get(0).getX());
		}
	}
	private void buildTiangs(){
		int counter = 0;
		
		for(int i=0; i<this.jmlTiang; i++){
			final TiangB tiang = new TiangB(skin_object, this.resTiang, this.jmlRing);
			
			// set top Y pertama kali
			tiang.setTopY(Constants.GAP_TIANG_BOTTOM_DEFAULT);

			// posisi tiang pertama selalu sama 
			if(counter == 0){
				tiang.setPosition(Constants.GAP_BIG, Constants.GAP_BIG);
			}
			
			if(this.jmlTiang == 3){
				if(counter == 1){
					tiang.setPosition(Constants.VIEWPORT_GUI_WIDTH/2 - tiang.getWidth()/2, Constants.GAP_BIG);
				}
				else if(counter == 2){
					tiang.setPosition(Constants.VIEWPORT_GUI_WIDTH - (Constants.GAP_BIG + tiang.getWidth()), Constants.GAP_BIG);
				}
			}
			else if(this.jmlTiang == 4){
				if(counter == 1){
					tiang.setPosition((Constants.VIEWPORT_GUI_WIDTH/2) - Constants.GAP_TIANG_4_LEFT, Constants.GAP_BIG);
				}
				else if(counter == 2){
					tiang.setPosition((Constants.VIEWPORT_GUI_WIDTH/2) + Constants.GAP_TIANG_4_RIGHT, Constants.GAP_BIG);
				}
				else if(counter == 3){
					tiang.setPosition(Constants.VIEWPORT_GUI_WIDTH - (Constants.GAP_BIG + tiang.getWidth()), Constants.GAP_BIG);
				}
			}
			
			tiang.addListener(new ClickListener(){
				@Override
				public void clicked(InputEvent event, float x, float y) {
					//super.clicked(event, x, y);
					Gdx.app.log("TIANG", "tiang clicked");
					RingB ringPeek = tiang.peek();
					//ringPeek.setPosition(ringPeek.getX(), ringPeek.getY()+Constants.GAP_MEDIUM);
					//Gdx.app.log("tiang click", String.valueOf(ringPeek.getY()));
					Gdx.app.log("currLoad", String.valueOf(tiang.getIsi()));
					
					// klik 1, tiang 1 ada isinya
					if(ringPeek!=null && firstObj.isEmpty()){
						setFirst(tiang, ringPeek);
						//Gdx.app.log("tiang klik", "klik 1, tiang 1 ada isinya");
					}

					// klik 1, tiang 1 kosong
					else if(ringPeek==null && firstObj.isEmpty()){
						// do nothing
						//Gdx.app.log("tiang klik", "klik 1, tiang 1 kosong");
					}
					
					// klik 2, tiang 2 ada isinya (compare)
					else if(!firstObj.isEmpty()){
						Gdx.app.log("tiang klik", "klik 2, tiang 2 ada isinya (compare)");
						boolean isPushed = tiang.push((RingB)getFirst().get(1));
						
						if(isPushed){   
							// kalo berhasil di push ke stack nya, baru dipindahin posisi ring di screen nya
							// ..logika berhasil push atau enggak di tiang nya, bukan disini
							RingB ring1 = (RingB)getFirst().get(1);
							ring1.setPosition(tiang.getX(), tiang.getTopY());
							TiangB tiang1 = (TiangB)getFirst().get(0);
							tiang1.pop();
							
							// re-set the tiang's topY coordinates
							tiang1.setTopY(tiang1.getTopY() - Constants.GAP_RING);
							tiang.setTopY(tiang.getTopY() + Constants.GAP_RING);
							
							Gdx.app.log("isPushed", "pushed");
							
							// cek apakah udah selesai semua.. cek tiang terakhir nya
							boolean isOver = tiangs.get(tiangs.size()-1).cekIfComplete();
							if(isOver){
								Gdx.app.log("isOver", "complete!");
								windowWinB.setVisible(true);
							}
						}
						
						clearTemp();
					}
					
					/*
					// klik 2, tiang 2 kosong (langsung pindahin)
					else if(ringPeek==null && !firstObj.isEmpty()){
						Gdx.app.log("tiang klik", "klik 2, tiang 2 kosong (langsung pindahin)");
						
						setSecond(tiang, ringPeek);
						
						TiangB tiang1 = (TiangB)getFirst().get(0);
						RingB ring1 = (RingB)getFirst().get(1);
						TiangB tiang2 = (TiangB)getFirst().get(0);
						RingB ring2 = (RingB)getSecond().get(1);

						ring1 = tiang1.pop();
						tiang1.setTopY(tiang1.getTopY() - Constants.GAP_RING);
						
						ring1.setPosition(tiang2.getX(), tiang2.getTopY());
						tiang2.push(ring1);
						tiang2.setTopY(tiang2.getTopY() + Constants.GAP_RING);
						
						clearTemp();
					}
					*/
					
					
					
					// pertama kali klik
					/*if(firstObj.isEmpty()){
						setFirst(tiang, ringPeek);
					}
					else{
						setSecond(tiang, ringPeek);
						
						TiangB tiang1 = (TiangB)getFirst().get(0);
						RingB ring1 = (RingB)getFirst().get(1);
						TiangB tiang2 = (TiangB)getFirst().get(0);
						RingB ring2 = (RingB)getSecond().get(1);
						
						// compare length nya
						if(ring1.getLength() < ring2.getLength()){
							ring1 = tiang1.pop();
							tiang1.setTopY(tiang1.getTopY() - Constants.GAP_RING);
							
							ring1.setPosition(tiang2.getX(), tiang2.getTopY());
							tiang2.push(ring1);
							tiang2.setTopY(tiang2.getTopY() + Constants.GAP_RING);
						}
						
						clearTemp();
					} */
				}
			});
			tiangs.add(tiang);  Gdx.app.log("TIANG", String.valueOf(tiang.getWidth()));
			counter++;
		}
		
		counter = 0;
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
		for(int i=0; i<rings.size(); i++){
			tiangs.get(0).push(rings.get(i));
			Gdx.app.log("test push", String.valueOf(rings.get(i).getY()));
		}
		
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
	private Table buildLayerWinA(){		
		windowWinA = new Window(Constants.WIN_TITLE, skin_window);
		windowWinA.setVisible(false);
		
		btnMenu = new TextButton(Constants.WIN_BTN_MENU, skin_window);
		btnMenu.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				
			}
		});
		btnUlangi_ = new TextButton(Constants.WIN_BTN_ULANGI, skin_window);
		btnUlangi_.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				
			}
		});
		btnLanjut = new TextButton(Constants.WIN_BTN_LANJUT, skin_window);
		btnLanjut.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				
			}
		});
		
		Table isi = new Table(skin_window);
		isi.center();
		isi.add(Constants.WIN_NOTE).row();
		isi.add(Constants.WIN_NOTE_POIN + this.skor).row();
		isi.add(btnMenu);
		isi.add(btnUlangi_);
		isi.add(btnLanjut);
		
		windowWinA.add(isi);
		windowWinA.pack();
		windowWinA.setPosition((Constants.VIEWPORT_GUI_WIDTH/2)-(windowWinA.getWidth()/2), (Constants.VIEWPORT_GUI_HEIGHT/2)-(windowWinA.getHeight()/2));
		
		return windowWinA;
	}
	private Table buildLayerWinB(){		
		windowWinB = new Window(Constants.WIN_TITLE, skin_window);
		windowWinB.setVisible(false);
		
		btnOK = new TextButton(Constants.WIN_BTN_OK, skin_window);
		btnOK.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				// just for tes
				windowWinB.setVisible(false);
			}
		});
		
		Table isi = new Table(skin_window);
		isi.center();
		isi.add(Constants.WIN_NOTE).row();
		isi.add(Constants.WIN_NOTE_POIN + this.skor).row();
		isi.add(btnOK);
		
		windowWinB.add(isi);
		windowWinB.pack();
		windowWinB.setPosition((Constants.VIEWPORT_GUI_WIDTH/2)-(windowWinB.getWidth()/2), (Constants.VIEWPORT_GUI_HEIGHT/2)-(windowWinB.getHeight()/2));
		
		return windowWinB;
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
			if(!this.notifShown){
				windowLose.setVisible(true);
				timer.cancel();
			}
			this.notifShown = true;
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
