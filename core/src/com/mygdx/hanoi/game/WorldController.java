package com.mygdx.hanoi.game;

//import javafx.scene.text.Text;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Input.Keys;
import com.mygdx.hanoi.util.Constants;

public class WorldController extends InputAdapter{
	//kelas ini mengandung segala logic game yang harus di-inisialisasi dan memodifikasi game world nya
	
	private static final String TAG = WorldController.class.getName();
	public Sprite[] testSprite;
	public int selectedSprite; //ini buat event nya nanti
	public TextButton[] buttons;
	
	//button styling variables
	private TextureAtlas atlas;
	private Skin skin;
	
	public WorldController(){
		init();
	}
	
	private void init(){
		initTestObjects();
		initTestButton();  //testing bikin button disini..buat implementasi worldcontroller sama worldrenderer buat TA
		
		//class ini sebagai listener input nya, karena udah extends InputAdapter
		Gdx.input.setInputProcessor(this);
	}
	
	private void initTestButton(){
		buttons = new TextButton[1];
		atlas = new TextureAtlas("ui/atlas.pack");  // bisa pake .xml ga ya? apa mesti .pack? (gabisa)
		skin = new Skin(Gdx.files.internal("ui/menuSkin.json"), atlas);
		
		TextButton btnTes = new TextButton("Test", skin);
		btnTes.setSize(1.0f,  1.0f);
		//btnTes.setPosition(MathUtils.random(-2.0f, 2.0f), MathUtils.random(-2.0f, 2.0f));
		
		buttons[0] = btnTes;
	}
	
	private void initTestObjects(){
		//bikin 5 sprite buat contoh
		testSprite = new Sprite[5];
		
		// testing assets
		Array<TextureRegion> regions = new Array<TextureRegion>();
		regions.add(Assets.instance.ring.ring);
		regions.add(Assets.instance.ring.ring);
		regions.add(Assets.instance.ring.ring);
		regions.add(Assets.instance.ring.ring);
		regions.add(Assets.instance.ring.ring);
		
		//panjang dan lebar kotaknya
		int width = 32;
		int height = 32;
		
		//buat format pixmap baru untuk buat texture nta
		Pixmap pixmap = createProceduralPixmap(width, height);
		Texture texture = new Texture(pixmap);
		
		//looping bikin random sprite nya
		for(int i = 0; i<testSprite.length; i++){
			Sprite spr = new Sprite(regions.random());//Sprite spr = new Sprite(texture);
			spr.setSize(1.0f, 1.0f); //1 meter x 1 meter
			spr.setOrigin(spr.getWidth() / 2.0f, spr.getHeight() / 2.0f);  //origin nya ditengah sprite (sumbu putar nya) 
			
			//posisi nya di random
			/*float randomX = MathUtils.random(-2.0f, 2.0f);
			float randomY = MathUtils.random(-2.0f, 2.0f);
			spr.setPosition(randomX, randomY);*/
			
			//coba bikin sprite nya numpuk, posisi sprite pertama di random dulu
			if(i == 0){
				float randomX = MathUtils.random(-2.0f, 2.0f);
				float randomY = MathUtils.random(-2.0f, 2.0f);
				spr.setPosition(randomX, randomY);
				
				//ini persis di tengah
				//spr.setPosition(0.0f - spr.getOriginX(), 0.0f - Constants.VIEWPORT_HEIGHT/2);
			}
			else{
				float currX = testSprite[i-1].getX();
				float currY = testSprite[i-1].getY() + 1;
				spr.setPosition(currX, currY);
			}
			
			testSprite[i] = spr;  //masukin ke array
		}
		
		selectedSprite = 0;
	}
	private Pixmap createProceduralPixmap(int width, int height){
		Pixmap pixmap = new Pixmap(width, height, Format.RGBA8888);
		
		//opacity nya 50%
		pixmap.setColor(1, 0, 0, 0.5f);
		pixmap.fill();
		
		//bikin silang di tengah kotak
		pixmap.setColor(1,1,0,1);
		pixmap.drawLine(0, 0, width, height);
		pixmap.drawLine(width, 0, 0, height);
		
		//bikin border kotaknya berwarna cyan
		pixmap.setColor(0,0,0,1);
		pixmap.drawRectangle(0, 0, width, height);
		
		return pixmap;
	}
	
	public void update(float deltaTime){
		updateTestObjects(deltaTime);
		handleDebugInput(deltaTime);  //dibuat disini bukan di keyUp() supaya bisa di-long press, karena di-update terus 
	}	
	private void updateTestObjects(float deltaTime){
		//ambil current rotation sprite nya
		float rotation = testSprite[selectedSprite].getRotation();
		
		//rotate 90 derajat/detik
		rotation += 90*deltaTime;
		
		//wrap 360 derajat
		rotation %= 360;
		
		//set rotasi nya
		//testSprite[selectedSprite].setRotation(rotation);
	}
	private void handleDebugInput(float deltaTime){
		//gerakan per 5 derajat
		float sprMoveSpeed = 5 * deltaTime;
		
		if(Gdx.app.getType() == ApplicationType.Desktop){
			//isKeyPressed() kepencet terus, isKeyJustPressed() hanya kepencet sekali
			if(Gdx.input.isKeyJustPressed(Keys.A))  moveSelectedSprite(-sprMoveSpeed, 0);
			if(Gdx.input.isKeyPressed(Keys.D)) moveSelectedSprite(sprMoveSpeed, 0);
			if(Gdx.input.isKeyPressed(Keys.W)) moveSelectedSprite(0, sprMoveSpeed);
			if(Gdx.input.isKeyPressed(Keys.S)) moveSelectedSprite(0, -sprMoveSpeed);
		}
		
		if(Gdx.app.getType() == ApplicationType.Android){
			
		}
	}
	private void moveSelectedSprite(float x, float y){
		//gerakin sprite ke koordinat
		testSprite[selectedSprite].translate(x, y);
	}
	
	@Override
	public boolean keyUp(int keycode){
		if(keycode == Keys.R){
			//reset game nya
			init();
			Gdx.app.debug(TAG, "Game di-reset");
		}
		else if(keycode == Keys.SPACE){
			selectedSprite = (selectedSprite + 1) % testSprite.length;
			Gdx.app.debug(TAG, "SPrite diubah");
		}
		return false;
	}
}
