package com.atom.builder;

import com.atom.obstacles.Capacitor;
import com.atom.obstacles.ElectricPlate;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector3;

public class AtomGame extends ApplicationAdapter {
	public static final float SCENE_WIDTH = 1600;
	public static final float SCENE_HEIGHT = 900;

	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Texture img;
	private Texture atomImage;
	private Texture capacitorImage;
//	private Music backgroundMusic;

	private Capacitor cap;
	private ElectricPlate plate;
	private Circle atom;
	
	@Override
	public void create () {
		camera = new OrthographicCamera();
		camera.setToOrtho(false,1600,900);
		batch = new SpriteBatch();

		img = new Texture("badlogic.jpg");
		atomImage = new Texture("circle.png");
		capacitorImage = new Texture("capacitorImage.png");

		atom = new Circle();
		atom.setRadius(100);
		atom.x = atom.radius;
		atom.y = SCENE_HEIGHT/2 - atom.radius;

		cap = new Capacitor(500,100,50);

		plate = new ElectricPlate(1100, 500, 50,"down");

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
		cap.draw(batch, capacitorImage);
		plate.draw(batch, capacitorImage);
		batch.draw(atomImage, atom.x, atom.y);
		batch.end();


		// updates
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
		atomImage.dispose();
		capacitorImage.dispose();
	}

	public float electricPlateForce(float atom, float plate, String dir, float ypos) {
		int sign = (Math.signum(atom) == Math.signum(plate))? 1 : -1;
		float dist;
		if (dir.equals("up")){
			sign *= -1;
			dist = 450-100-ypos;
		} else {
			dist = 450-100+ypos;
		}
		atom = Math.abs(atom);
		plate = Math.abs(atom);
		long k = 9 * (long)Math.pow(10,9);
		return (float) k*atom*plate/(dist*dist);
	}

	// plus on top is up
	public float capacitorForce(float atom, float capacitor, String dir, float ypos) {
		float pos,neg;
		if (dir.equals("up")){
			pos = electricPlateForce(atom,capacitor,dir,ypos);
			neg = electricPlateForce(atom,-1*capacitor,"down",ypos);
		} else {
			pos = electricPlateForce(atom,capacitor,"down",ypos);
			neg = electricPlateForce(atom,-1*capacitor,dir,ypos);
		}
		return pos+neg;
	}
}
