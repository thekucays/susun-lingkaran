package com.mygdx.hanoi.screens;

import java.util.Map;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.hanoi.TowerOfHanoiMain;
import com.mygdx.hanoi.util.Constants;
import com.mygdx.hanoi.util.DataPersister2;

public class ModeSelect extends AbstractGameScreen {
	
	private Stage stage;
	private TextureAtlas atlas;
	private Skin skin;
	private Table table;
	private Button btnFree, btnTimed, btnSurvival, btnMove, btnBack;
	private Image imgHeading, imgBackground;
	private int hint;
	private Preferences userpref;
	private DataPersister2 persister;
	
	public ModeSelect(Game game) {
		super(game);
		
		// ambil preferences untuk ambil hint nya (mode timed dan move)
		persister = new DataPersister2();
		userpref = persister.getOrCreatePreferences(Constants.pref_userpref);
		switch(Gdx.app.getType()){
			case Desktop: 
				hint = Integer.parseInt((String)persister.getPreferencesData(userpref).get(Constants.pref_userpref_hint));
				break;
			case Android:
				hint = (int)persister.getPreferencesData(userpref).get(Constants.pref_userpref_hint);
				break;
			default: 
				Gdx.app.log("HINT", "app type not implemented yet");
				break;
		}
		
	}
	
	@Override
	public void show() {
		stage = new Stage(new StretchViewport(Constants.VIEWPORT_GUI_WIDTH, Constants.VIEWPORT_GUI_HEIGHT));
		Gdx.input.setInputProcessor(stage);
		
		rebuildStage();
	}
	
	private void rebuildStage(){
		atlas = new TextureAtlas("ui/level-select/level-select.pack");
		skin = new Skin(Gdx.files.internal("ui/level-select/level-select.json"), atlas);
		
		// table untuk layer tampilan nya
		Table layerBackground = buildLayerBackground();
		Table layerHeader = buildLayerHeader();
		Table layerControls = buildLayerControls();
		Table layerBack = buildLayerBack(); // sepertinya perlu dipisah karena posisinya di pojok kanan atas sendiri
		
		stage.clear();
		Stack stack = new Stack();
		stage.addActor(stack);
		stack.setSize(Constants.VIEWPORT_GUI_WIDTH, Constants.VIEWPORT_GUI_HEIGHT);
		
		stack.add(layerBackground);
		stack.add(layerHeader);
		stack.add(layerControls);
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
		imgHeading = new Image(skin, "header");
		layer.addActor(imgHeading);
		imgHeading.setPosition((Constants.VIEWPORT_GUI_WIDTH/2)-(imgHeading.getWidth()/2), Constants.VIEWPORT_GUI_HEIGHT-Constants.HEADER_TOP_GAP);
		
		return layer;
	}
	private Table buildLayerControls(){
		Table layer = new Table();
		layer.center();
		
		btnFree = new Button(skin, "btnFree");
		btnFree.pad(20);
		btnFree.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Gdx.app.log("Control", "btnFree pressed");
				//game.setScreen(new GamePlay(game));
				game.setScreen(new GamePlayB(game, Constants.MODE_FREE, 5));
			}
		});
		
		btnSurvival = new Button(skin, "btnSurvival");
		btnSurvival.pad(20);
		btnSurvival.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Gdx.app.log("Control", "btnSurvival pressed");
				//game.setScreen((Screen) new TowerOfHanoiMain());
				game.setScreen(new GamePlayB(game, Constants.MODE_SURVIVAL, Constants.SURVIVAL_MODE_HINT));
			}
		});
		
		btnTimed = new Button(skin, "btnTimed");
		btnTimed.pad(20);
		btnTimed.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Gdx.app.log("Control", "btnTimed pressed");
				game.setScreen(new GamePlayB(game, Constants.MODE_TIMED, hint));   //  jmlRing, jmlTiang));
			}
		});
		
		btnMove = new Button(skin, "btnMove");
		btnMove.pad(20);
		btnMove.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Gdx.app.log("Control", "btnMove pressed");
				game.setScreen(new GamePlayB(game, Constants.MODE_MOVE, hint));
			}
		});
		
		layer.add(btnFree).spaceRight(Constants.GAP_MEDIUM).spaceBottom(Constants.GAP_MEDIUM);
		layer.add(btnSurvival).spaceBottom(Constants.GAP_MEDIUM).row();
		layer.add(btnTimed).spaceRight(Constants.GAP_MEDIUM);
		layer.add(btnMove).row();
		layer.setTransform(true);
		//layer.debug();
		
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
