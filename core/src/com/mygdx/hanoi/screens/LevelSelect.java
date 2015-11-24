package com.mygdx.hanoi.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
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
import com.mygdx.hanoi.util.Constants;

public class LevelSelect extends AbstractGameScreen {
	
	private Stage stage;
	private TextureAtlas atlas;
	private Skin skin;
	private Table table;
	private Button btnFree, btnTimed, btnSurvival, btnMove, btnBack;
	private Image imgHeading, imgBackground;
	
	public LevelSelect(Game game) {
		super(game);
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
			}
		});
		
		btnSurvival = new Button(skin, "btnSurvival");
		btnSurvival.pad(20);
		btnSurvival.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Gdx.app.log("Control", "btnSurvival pressed");
			}
		});
		
		btnTimed = new Button(skin, "btnTimed");
		btnTimed.pad(20);
		btnTimed.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Gdx.app.log("Control", "btnTimed pressed");
			}
		});
		
		btnMove = new Button(skin, "btnMove");
		btnMove.pad(20);
		btnMove.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Gdx.app.log("Control", "btnMove pressed");
			}
		});
		
		layer.add(btnFree);
		layer.add(btnSurvival).row();
		layer.add(btnTimed);
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
	public void dispose() {  // for the sake of efficiency.. i don't know whether this is required or not lol :P
		skin.dispose();
		stage.dispose();
		atlas.dispose();
	}
}
