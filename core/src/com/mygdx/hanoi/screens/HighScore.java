package com.mygdx.hanoi.screens;

import java.util.ArrayList;
import java.util.Map;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.mygdx.hanoi.util.DataPersister2;

public class HighScore implements Screen{
	
	private Stage stage;
	private Table table, container;
	private TextureAtlas atlas;
	private Skin skin;
	private TextButton btnLeft, btnRight, btnBack;
	
	private List list;
	private ScrollPane scrollPane;
	
	
	public HighScore(){
		init();
	}
	
	public void init(){
		// karena ini akan jadi satu file untuk ketiga mode high score nya..jadi gaperlu selalu manggil constructor
		// benarkah caranya?
	}
	public void init(Map data, String mode){
		
	}
	
	@Override
	public void show() {
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		
		atlas = new TextureAtlas("ui/atlas.pack");  // ini nanti dibuat dulu..dibuat kaya bikin button.pack.. semua asset yang di stage ini dijadiin satu .pack
		//atlas = new TextureAtlas("ui/button.pack");
		skin = new Skin(Gdx.files.internal("ui/menuSkin.json"), atlas);
		
		table = new Table(skin);
		table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); // otherwise, the table will look terrible :P
		
		
		// ambil highscore preferences
		DataPersister2 dp2 = new DataPersister2();
		Preferences hsPref = dp2.getOrCreatePreferences("highScores");  //TODO replace with Constant values
		Map data = dp2.getPreferencesData(hsPref);
		
		// bikin Tabel lagi untuk naruh Tabel loop highScore nya
		Table innerContainer = new Table();
		
		// loop the highscore data - free mode
		ArrayList<String[]> loop = new Json().fromJson(ArrayList.class, String[].class, (String)data.get("freeMode"));
		Object[] loopTable = new Object[loop.size()];
		for(int i=0; i<loop.size(); i++){
			//loopTable[i] = new HighScoreTbl().generateHSContainer(skin, loop.get(i)[0], loop.get(i)[1]);
			innerContainer.add(new HighScoreTbl().generateHSContainer(skin, loop.get(i)[0], loop.get(i)[1])).expand().fill();
			innerContainer.row();
		}
		
		//list = new List(skin); // ini agak beda sama yang di video tutorial (constructor List() nya udah beda..
		//list.setItems(loopTable);
		//scrollPane = new ScrollPane(list, skin);
		scrollPane = new ScrollPane(innerContainer, skin);
		container = new Table();
		container.add(scrollPane);
		
		
		btnLeft = new TextButton("<", skin);
		btnLeft.pad(15);
		btnRight = new TextButton(">", skin);
		btnRight.pad(15);
		btnBack = new TextButton("back", skin);
		btnBack.pad(10);
		
		// button listeners
		btnBack.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				//((Game)Gdx.app.getApplicationListener()).setScreen(new MainMenu());
			}
		});
		btnLeft.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				
			}
		});
		btnRight.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				
			}
		});
		
		// putting stuffs
		// bikin supaya title nya ditengah - bikin 3 kolom lalu di-span
		table.add("High Score - Free Mode").colspan(3).row();
		
		table.add(btnLeft);
		table.add(container);//table.add(scrollPane);
		table.add(btnRight).row();
		
		table.add().width(table.getWidth()/3);
		table.add(btnBack);
		table.add().width(table.getWidth()/3).row();
		table.getCell(btnBack).spaceTop(25);
		
		table.debug();
		stage.addActor(table);
		
		// last watched video duration : 36:20
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		stage.getViewport().update(width, height);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		stage.dispose();
		atlas.dispose();
		skin.dispose();
	}

}
