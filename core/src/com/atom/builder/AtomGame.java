package com.atom.builder;

import com.atom.obstacles.Atom;
import com.atom.obstacles.Capacitor;
import com.atom.obstacles.ElectricPlate;
import com.atom.obstacles.Obstacle;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class AtomGame extends ApplicationAdapter {
	public static final float SCENE_WIDTH = 1600;
	public static final float SCENE_HEIGHT = 900;

	private OrthographicCamera camera;
	private SpriteBatch batch;

	private Texture atomImage;
	private Texture positivePlate;
	private Texture negativePlate;
//	private Music backgroundMusic;

	private Capacitor cap;
	private ElectricPlate plate;
	private Atom atom;

	private ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>();
	
	@Override
	public void create () {
		camera = new OrthographicCamera();
		camera.setToOrtho(false,1600,900);
		batch = new SpriteBatch();

		atomImage = new Texture("circle.png");
		positivePlate = new Texture("positivePlate.png");
		negativePlate = new Texture("negativePlate.png");

		atom = new Atom();

		createObstacles();

//		backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("background.mp3"));
//		backgroundMusic.setLooping(true);
//		backgroundMusic.play();
	}

	public void createObstacles() {
		cap = new Capacitor(400,500,50);
		obstacles.add(cap);
		plate = new ElectricPlate(1100, 500, 50,false);
		obstacles.add(plate);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.710f, 0.710f, 0.835f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		float yShift = calcShift();
		System.out.println(yShift);

		camera.update();
		batch.setProjectionMatrix(camera.combined);

		batch.begin();
		cap.draw(batch, positivePlate, negativePlate);
		plate.draw(batch, positivePlate, negativePlate);
		atom.draw(batch, camera, atomImage, yShift);
		batch.end();

//		System.out.println(capacitorForce(atom.getCharge(),plate.strength,plate.up,atom.yPos()));
//		System.out.println(electricPlateForce(atom.getCharge(),plate.strength,plate.up,atom.yPos()));
	}

	public float calcShift() {
		float yShift = 0;
		for(Obstacle o : obstacles) {
			if (o instanceof Capacitor) {
				Capacitor c = (Capacitor) o;
				if (atom.xPos() >= c.x && atom.xPos() <= c.x+c.width) {
					yShift = capacitorForce(atom.getCharge(),c.strength,c.up,atom.yPos());
				}
			}
			if (o instanceof ElectricPlate) {
				ElectricPlate ep = (ElectricPlate) o;
				if (atom.xPos() >= ep.x && atom.xPos() <= ep.x+ep.width)
					yShift = electricPlateForce(atom.getCharge(),ep.strength,ep.up,atom.yPos());
			}
		}
		return yShift;
	}
	
	@Override
	public void dispose () {
		batch.dispose();
//		img.dispose();
		atomImage.dispose();
		positivePlate.dispose();
		negativePlate.dispose();
	}

	public float electricPlateForce(float atom, float plate, boolean up, float ypos) {
		int sign = (Math.signum(atom) == Math.signum(plate))? 1 : -1;
		float dist;
		if (up) {
			sign *= -1;
			dist = 450-100-ypos;
		} else {
			dist = 450-100+ypos;
		}
//		System.out.println("atom:"+atom+" plate:"+plate+" dist:"+dist+" sign:"+sign+" ypos:"+ypos);
		atom = Math.abs(atom);
		plate = Math.abs(atom);

		// k is actually 9 * 10^9, but let's keep the numbers "small"
		int k = 900000/7;
		return (float) sign * k*(atom/dist)*(plate/dist);
	}

	// plus on top is up
	public float capacitorForce(float atom, float capacitor, boolean up, float ypos) {
		float pos,neg;
		if (up) {
			pos = electricPlateForce(atom,capacitor,true,ypos);
			neg = electricPlateForce(atom,-1*capacitor,false,ypos);
		} else {
			pos = electricPlateForce(atom,capacitor,false,ypos);
			neg = electricPlateForce(atom,-1*capacitor,true,ypos);
		}
		return pos+neg;
	}
}
