package com.mygdx.hanoi.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.hanoi.game.objects.AbstractGameObject;
import com.mygdx.hanoi.game.objects.Background;
import com.mygdx.hanoi.game.objects.Ring;
import com.mygdx.hanoi.game.objects.Tiang;
import com.mygdx.hanoi.screens.AbstractGameScreen;
import com.mygdx.hanoi.util.Constants;


public class GameController extends InputAdapter{
	// game objects
	public Array<Tiang> tiangs;
	public Array<Ring> rings;
	
	// stage related
	public Stage stage;
	public Table table;
	public Array<TextButton> buttons;
	public TextureAtlas atlas;
	public Skin skin;
	//private Game game;
	
	// test ibjects
	public Button testButton;
	
	// game decorations
	public Background background;     public Sprite[] testSprite;
	private Game game;
	
	public GameController(Game game){
		this.game = game;
		init();
	}
	
	private void init(){
		// preparing variables
		rings = new Array<Ring>();
		tiangs = new Array<Tiang>();
		
		// init stage nya disini
		stage = new Stage(new StretchViewport(Constants.VIEWPORT_GUI_WIDTH, Constants.VIEWPORT_GUI_HEIGHT));
		
		initObjects();
		initDecorations();
		initGui();
		
		initButtons();
		buildStage();
	}
	
	private void initObjects(){
		AbstractGameObject obj = null;
		obj = new Ring(1, "default");
		obj.addListener(new ChangeListener() {  // ini blum jalan..pake nya mungkin bukan change listener
			
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Gdx.app.log("tag", "test");
			}
		});
		obj.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				//super.clicked(event, x, y);
				Gdx.app.log("tag", "test clisk");
			}
		});
		/*obj.addListener(new InputListener(){
			private void touch() {
				Gdx.app.log("update", "obj touched");
			}
		});*/
		
		//obj.setTouchable(Touchable.enabled);
		//obj.hit(obj.position.x, obj.position.y, true);
		//obj.position.set(Constants.VIEWPORT_GUI_WIDTH-150.0f, Constants.VIEWPORT_GUI_HEIGHT-150.0f);
		//obj.position.set(0f,-3.75f);
		
		rings.add((Ring)obj);
	}
	
	private void buildStage(){
		atlas = new TextureAtlas("ui/menu.pack");
		skin = new Skin(Gdx.files.internal("ui/menu.json"), atlas);
		
		// test button
		testButton = new Button(skin, "btnHscore");
		testButton.setPosition(Constants.VIEWPORT_GUI_WIDTH-100.0f, Constants.VIEWPORT_GUI_HEIGHT-100.0f);
		
		// pas di klik, coba pindah posisinya
		testButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				testButton.setPosition(Constants.VIEWPORT_GUI_WIDTH/2, Constants.VIEWPORT_GUI_HEIGHT/2);
			}
		});
		
		Table btnTable = new Table();
		btnTable.top();
		btnTable.addActor(testButton);
		
		stage.clear();
		Gdx.input.setInputProcessor(stage);
		
		Stack stack = new Stack();
		stack.setSize(Constants.VIEWPORT_GUI_WIDTH, Constants.VIEWPORT_GUI_HEIGHT);
		stage.addActor(stack);
		stack.add(btnTable);
	}
	
	private void initButtons(){
		/*TextButton tb1 = new TextButton("test", skin);
		buttons.add(tb1); */
	}
	
	private void initDecorations(){
		
	}
	
	private void initGui(){
		
	}
	
	private void handleInput(float deltatime){
		
	}
	
	public void update(float deltaTime){
		//if(Gdx.input.isTouched()){
		//	Gdx.app.log("update", "screen touched");
		//}
	}
}
