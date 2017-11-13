package com.atom.builder;

import com.atom.obstacles.Atom;
import com.atom.obstacles.Capacitor;
import com.atom.obstacles.ElectricPlate;
import com.atom.obstacles.Electron;
import com.atom.obstacles.Obstacle;
import com.atom.obstacles.Proton;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import java.awt.Color;
import java.util.ArrayList;

public class AtomGame extends ApplicationAdapter {
	public static final float SCENE_WIDTH = 1600;
	public static final float SCENE_HEIGHT = 900;

	BitmapFont font;
	BitmapFont elementFont;

	private OrthographicCamera camera;
	private SpriteBatch batch;

	private Texture atomImage;
	private Texture electronImage;
	private Texture protonImage;
	private Texture insulatorImage;
	private Texture positivePlate;
	private Texture negativePlate;
//	private Music backgroundMusic;

	private Capacitor cap;
	private ElectricPlate plate;
	private Atom atom;
	private Electron elec;
	private Proton prot;

	private ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>();
	
	@Override
	public void create () {
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Rubik-Bold.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
		param.size = 36;
		param.color = com.badlogic.gdx.graphics.Color.BLACK;
//		param.shadowColor = com.badlogic.gdx.graphics.Color.LIGHT_GRAY;
//		param.shadowOffsetX = 1;
//		param.shadowOffsetY = 1;
		font = generator.generateFont(param);

		param.size = 48;
		param.color = com.badlogic.gdx.graphics.Color.LIGHT_GRAY;
		elementFont = generator.generateFont(param);
		generator.dispose();

		font.setColor(0,0,0,1);


		camera = new OrthographicCamera();
		camera.setToOrtho(false,1600,900);
		batch = new SpriteBatch();

		atomImage = new Texture("circle.png");
		electronImage = new Texture("electron.png");
		protonImage = new Texture("proton.png");
		insulatorImage = new Texture("circle.png");
		positivePlate = new Texture("positivePlate.png");
		negativePlate = new Texture("negativePlate.png");

		atom = new Atom();

		createObstacles();

//		backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("background.mp3"));
//		backgroundMusic.setLooping(true);
//		backgroundMusic.play();
	}

	public void createObstacles() {
		cap = new Capacitor(400,300,50);
		obstacles.add(cap);
		plate = new ElectricPlate(800, 300, 50, false);
		obstacles.add(plate);
		plate = new ElectricPlate(800, 300, 50, true);
		obstacles.add(plate);
		elec = new Electron(900,300);
		obstacles.add(elec);
		prot = new Proton(200,450);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.710f, 0.710f, 0.835f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		float yShift = calcShift();

		camera.update();
		batch.setProjectionMatrix(camera.combined);

		batch.begin();
		for(Obstacle o : obstacles) {
			if (o instanceof  Capacitor) {
				Capacitor c = (Capacitor) o;
				c.draw(batch,positivePlate,negativePlate);
			}
			if (o instanceof ElectricPlate) {
				ElectricPlate ep = (ElectricPlate) o;
				ep.draw(batch,positivePlate,negativePlate);
			}
			if (o instanceof Electron) {
				Electron e = (Electron) o;
				e.draw(batch,electronImage);
			}
			if (o instanceof Proton) {
				Proton p = (Proton) o;
				p.draw(batch,protonImage);
			}
		}
		atom.draw(batch, camera, atomImage, insulatorImage, yShift, elementFont);
		font.draw(batch,"Atomic Number: "+atom.getAtomicNum(),50,50);
		batch.end();

		if(Gdx.input.isKeyPressed(Input.Keys.R)) reset();

//		System.out.println(capacitorForce(atom.getCharge(),plate.strength,plate.up,atom.yPos()));
//		System.out.println(electricPlateForce(atom.getCharge(),plate.strength,plate.up,atom.yPos()));
	}

	public float calcShift() {
		float yShift = 0;
		for(Obstacle o : obstacles) {
			if (o instanceof Capacitor) {
				Capacitor c = (Capacitor) o;
				if (atom.xPos() >= c.x && atom.xPos() <= c.x+c.width) {
					yShift += capacitorForce(atom.getCharge(),c.strength,c.up,atom.yPos());
				}
			}
			if (o instanceof ElectricPlate) {
				ElectricPlate ep = (ElectricPlate) o;
				if (atom.xPos() >= ep.x && atom.xPos() <= ep.x+ep.width)
					yShift += electricPlateForce(atom.getCharge(),ep.strength,ep.up,atom.yPos());
			}
		}
//		System.out.printf(Float.toString(yShift)+"\n");
		return yShift;
	}
	
	@Override
	public void dispose () {
		font.dispose();
		batch.dispose();
//		img.dispose();
		atomImage.dispose();
		positivePlate.dispose();
		negativePlate.dispose();
	}

	public void reset() {
		dispose();
		create();
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
		int k = 900000/4;
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
