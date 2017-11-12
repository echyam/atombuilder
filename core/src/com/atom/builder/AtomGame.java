package com.atom.builder;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class AtomGame extends ApplicationAdapter {
	public final float SCENE_WIDTH = 1600;
	public final float SCENE_HEIGHT = 900;

	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Texture img;
	private Texture atomImage;
	private Texture capacitorImage;
//	private Music backgroundMusic;

	private Rectangle capacitor;
	private Circle atom;
	
	@Override
	public void create () {
		camera = new OrthographicCamera();
		camera.setToOrtho(false,1600,900);
		batch = new SpriteBatch();

		img = new Texture("badlogic.jpg");

		atomImage = new Texture("circle.png");
		atom = new Circle();
		atom.setRadius(100);
		atom.x = atom.radius;
		atom.y = SCENE_HEIGHT/2 - atom.radius;

		capacitorImage = new Texture("capacitorImage.png");
		capacitor = new Rectangle();
		capacitor.width = 500;
		capacitor.height = 100;

//		backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("background.mp3"));
//		backgroundMusic.setLooping(true);
//		backgroundMusic.play();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.710f, 0.710f, 0.835f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		batch.setProjectionMatrix(camera.combined);

		batch.begin();
		//batch.draw(img, 0, 0);
//		batch.draw(capacitorImage,capacitor.x,capacitor.y);
//		batch.draw(atomImage,300,SCENE_HEIGHT/2 - atom.radius/2);
		batch.draw(capacitorImage,0,0);
		batch.draw(atomImage, atom.x, atom.y);
		batch.end();

		// android input
		if(Gdx.input.isTouched()) {
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			atom.x = touchPos.x - atom.radius;
			atom.y = touchPos.y - atom.radius;
		}

		// keyboard input
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) atom.x -= 200 * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) atom.x += 200 * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.UP)) atom.y += 200 * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) atom.y -= 200 * Gdx.graphics.getDeltaTime();

		// guarantee atom in bounds
		if (atom.x < (-atom.radius)) atom.x = (-atom.radius);
		if (atom.y < (-atom.radius)) atom.y = (-atom.radius);
		if (atom.x > SCENE_WIDTH-atom.radius) atom.x = SCENE_WIDTH-atom.radius;
		if (atom.y > SCENE_HEIGHT-atom.radius) atom.y = SCENE_HEIGHT-atom.radius;
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
