package com.mygdx.hanoi.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.hanoi.game.objects.RingB;
import com.mygdx.hanoi.game.objects.TiangB;
import com.mygdx.hanoi.util.Constants;

public class GamePlayB extends AbstractGameScreen{

	private Stage stage;
	private Skin skin_object, skin_decorations, skin_window, skin_ui;
	private TextureAtlas atlas_object, atlas_decorations, atlas_window, atlas_ui;
	private Table table;
	
	// GUI Labels
	private Label lblMode, lblWaktu, lblHint, lblMode_, lblWaktu_, lblHint_;
	private Button btnPause, btnHint, btnMainLagi, btnUlangi, btnKeluar, btnYa, btnTidak;
	private Window windowPause, windowConfirm;
	
	private Image imgBackground;
	private RingB[] rings;
	private TiangB[] tiangs;
	private String gameMode;
	private String confirmOption;  // isinya bisa main lagi, ulangi, keluar.. buat nentuin event di window confirm nya
	private int hint, waktu;
	
	// listener buat tiang nya
	private boolean isFirstClick, pause;
	
	// dummy game settings.. nanti ambil dari database
	private String resRing, resBg, resTiang;
	
	public GamePlayB(Game game, String gMode, int hint, int waktu) {
		super(game);
		this.isFirstClick = false;
		this.gameMode = gMode;
		this.hint = hint;
		this.waktu = waktu;
		this.pause = false;
		
		this.resBg = "bg-default";
		this.resRing = "ring-default";
		this.resTiang = "tiang-default";
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
		
		stack.addActor(layerPause);
		stack.addActor(layerWin);
		stack.addActor(layerLose);
	}

	private Table buildLayerGuiRight(){
		Table layer = new Table();
		layer.top().right();
		
		return layer;
	}
	private Table buildLayerGuiLeft(){		
		Table layer = new Table();
		layer.top().left();
		
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
				windowConfirm.setVisible(true);
			}
		});
		btnUlangi = new TextButton(Constants.PAUSE_ULANGI, skin_window);
		btnUlangi.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				confirmOption = Constants.PAUSE_OPTION_ULANGI;
				windowConfirm.setVisible(true);
			}
		});
		btnMainLagi = new TextButton(Constants.PAUSE_MAIN_LAGI, skin_window);
		btnMainLagi.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				pause = false;
				windowPause.setVisible(false);
			}
		});
		
		Table isi = new Table(skin_window);
		isi.center();
		isi.add(btnMainLagi);
		isi.add(btnUlangi);
		isi.add(btnKeluar);
		isi.row();
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
				pause = false;
				windowConfirm.setVisible(false);
				windowPause.setVisible(false);
			}
		});
		
		Table isi = new Table(skin_window);
		isi.center();
		isi.add(btnYa);
		isi.add(btnTidak);
		isi.row();
		windowConfirm.add(isi);
		windowConfirm.pack();
		windowConfirm.setPosition((Constants.VIEWPORT_GUI_WIDTH/2)-(windowConfirm.getWidth()/2), (Constants.VIEWPORT_GUI_HEIGHT/2)-(windowConfirm.getHeight()/2));  // taruh di tengah
		
		return windowConfirm;
	}
	private Table buildLayerWin(){		
		Table layer = new Table();
		
		return layer;
	}
	private Table buildLayerLose(){		
		Table layer = new Table();
		
		return layer;
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
