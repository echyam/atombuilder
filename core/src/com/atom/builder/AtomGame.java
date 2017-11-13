package com.atom.builder;

import com.atom.obstacles.Atom;
import com.atom.obstacles.Capacitor;
import com.atom.obstacles.ElectricPlate;
import com.atom.obstacles.Electron;
import com.atom.obstacles.Neutron;
import com.atom.obstacles.Obstacle;
import com.atom.obstacles.Proton;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector3;

import java.awt.Color;
import java.awt.geom.Arc2D;
import java.util.ArrayList;

public class AtomGame extends ApplicationAdapter {
    public static final float SCENE_WIDTH = 1600;
    public static final float SCENE_HEIGHT = 900;
    public final double ALLOWANCE = 15;

    BitmapFont font;
    BitmapFont elementFont;

    private OrthographicCamera camera;
    private SpriteBatch batch;

    private Texture atomImage;
    private Texture electronImage;
    private Texture protonImage;
    private Texture neutronImage;
    private Texture insulatorImage;
    private Texture positivePlate;
    private Texture negativePlate;
//	private Music backgroundMusic;

    private Capacitor cap;
    private ElectricPlate plate;
    private Atom atom;
    private Electron elec;
    private Proton prot;
    private Neutron neutr;

    private ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>();

    public final int K = 900000/6;
    double yShift;
    private boolean eLock = true;
    private boolean pLock = true;
    private boolean nLock = true;

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
        neutronImage = new Texture("neutron.png");
        insulatorImage = new Texture("insulator.png");
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
        plate = new ElectricPlate(800, 300, true, true);
        obstacles.add(plate);
        plate = new ElectricPlate(800, 300, false, true);
        obstacles.add(plate);

        elec = new Electron(200,300);
        obstacles.add(elec);
        prot = new Proton(1400,700);
        obstacles.add(prot);
        neutr = new Neutron(1300, 300);
        obstacles.add(neutr);
    }

    @Override
    public void render () {
        Gdx.gl.glClearColor(0.710f, 0.710f, 0.835f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update();

//		System.out.printf(Boolean.toString(atom.alive)+" ");

        yShift = calcShift();

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
            if (o instanceof Neutron) {
                Neutron n = (Neutron) o;
                n.draw(batch,neutronImage);
            }
        }
        atom.draw(batch, camera, atomImage, insulatorImage, yShift, elementFont);
        font.draw(batch,"Atomic Number: "+atom.getAtomicNum(),50,90);
        font.draw(batch,"electrons: "+atom.getNumElectrons(),50,60);
        font.draw(batch,"neutrons: "+atom.getNumNeutrons(),50,30);
        batch.end();

        if(Gdx.input.isKeyPressed(Input.Keys.R)) reset();

//		System.out.println(capacitorForce(atom.getCharge(),plate.strength,plate.up,atom.yPos()));
//		System.out.println(electricPlateForce(atom.getCharge(),plate.strength,plate.up,atom.yPos()));
    }

    public synchronized void update() {
        atom.physicsUpdate();

//		System.out.printf(Integer.toString(atom.getAtomicNum())+"\n");

        for(int i = 0; i < obstacles.size(); i++) {
            Obstacle o = obstacles.get(i);
            if (o instanceof  Capacitor) {
                Capacitor c = (Capacitor) o;
            }
            if (o instanceof ElectricPlate) {
                ElectricPlate ep = (ElectricPlate) o;
                if ((ep.up && (atom.yPos()+atom.getRadius()+SCENE_HEIGHT/2 >= ep.y + ALLOWANCE))
//					System.out.printf(" ep1\n");
//					System.out.printf("atom:"+ Float.toString(atom.yPos())+" plate:"+Float.toString(ep.y));
                        || (!ep.up && (atom.yPos()-atom.getRadius()+SCENE_HEIGHT/2 <= ep.y+ep.height)) ) {
//					System.out.printf(" ep2\n");
                    ep.collideAtom(atom);
                }
            }
            if (o instanceof Electron) {
                if (eLock) {
                    obstacles.remove(i);
                    eLock = false;
                    Electron e = (Electron) o;
                    double xs = (atom.xPos()) - (e.x);
                    double ys = (SCENE_HEIGHT / 2 + atom.yPos()) - (e.y);
                    double dist = Math.sqrt((double) (xs * xs) + (double) (ys * ys));
//				System.out.printf("\n"+dist);
                    if (dist <= (atom.getRadius() + e.radius + ALLOWANCE)) {
                        e.collideAtom(atom);
                    } else {
                        obstacles.add(o);
                    }
                }
                eLock = true;
            }
            if (o instanceof Proton) {
                if (pLock) {
                    pLock = false;
                    obstacles.remove(i);
                    Proton p = (Proton) o;
                    double xs = (atom.xPos()) - (p.x);
                    double ys = (SCENE_HEIGHT / 2 + atom.yPos()) - (p.y);
                    double dist = Math.sqrt((double) (xs * xs) + (double) (ys * ys));
//				System.out.printf("\n"+dist);
                    if (dist <= (atom.getRadius() + p.radius + ALLOWANCE)) {
                        p.collideAtom(atom);
//					System.out.printf(" "+Float.toString(p.x)+","+Float.toString(p.y));
                    } else {
                        obstacles.add(o);
                    }
                }
                pLock = true;
            }
            if (o instanceof Neutron) {
                if (nLock) {
                    nLock = false;
                    obstacles.remove(i);
                    Neutron n = (Neutron) o;
                    double xs = (atom.xPos()) - (n.x);
                    double ys = (SCENE_HEIGHT / 2 + atom.yPos()) - (n.y);
                    double dist = Math.sqrt((double) (xs * xs) + (double) (ys * ys));
//				System.out.printf("\n"+dist);
                    if (dist <= (atom.getRadius() + n.radius + ALLOWANCE)) {
                        n.collideAtom(atom);
//					System.out.printf(" "+Float.toString(p.x)+","+Float.toString(p.y));
                    } else {
                        obstacles.add(o);
                    }
                }
                nLock = true;
            }
        }

        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            if (touchPos.x < 300 && touchPos.y > 800) {
                reset();
            }
        }

//		System.out.printf("done updating\n");
    }

    public double calcShift() {
        double yShift = 0;
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
        electronImage.dispose();
        protonImage.dispose();
        insulatorImage.dispose();
        positivePlate.dispose();
        negativePlate.dispose();
    }

    public void reset() {
        dispose();
        create();
        atom.reset();
        yShift = 0;
        eLock = true;
        pLock = true;
        nLock = true;
    }

    public double electricPlateForce(double atom, double plate, boolean up, double ypos) {
        int sign = (Math.signum(atom) == Math.signum(plate))? 1 : -1;
        double dist;
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
        return (double) sign * K*(atom/dist)*(plate/dist);
    }

    // plus on top is up
    public double capacitorForce(double atom, double capacitor, boolean up, double ypos) {
        double pos,neg;
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
