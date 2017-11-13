package com.atom.obstacles;

import com.atom.builder.AtomGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Lizzy on 11/12/2017.
 */
public class Atom {
    private int atomicNum;
    private int numElectrons;
    private int numNeutrons;

    private float charge;
    private float strength;
    private float speed;
    private boolean insulator;

    private float boost = 0;
    private final float boostCooldown = 2;

    private float radius = 100;
    private Circle atomShape;
    private Circle faradaysCage;

    public boolean alive;

    public final float keyboardSpeed = 800;

    private String[] elements = {"","H","He","Li","Be","B","C","N","O","F","Ne","Na","Mg","Al","Si","P","S","Cl","Ar","K","Ca","Sc","Ti","V","Cr","Mn","Fe","Co","Ni","Cu","Zn","Ga","Ge","As","Se","Br","Kr","Rb","Sr","Y","Zr","Nb","Mo","Tc","Ru","Rh","Pd","Ag","Cd","In","Sn","Sb","Te","I","Xe","Cs","Ba","La","Ce","Pr","Nd","Pm","Sm","Eu","Gd","Tb","Dy","Ho","Er","Tm","Yb","Lu","Hf","Ta","W","Re","Os","Ir","Pt","Au","Hg","Tl","Pb","Bi","Po","At","Rn","Fr","Ra","Ac","Th","Pa","U","Np","Pu","Am","Cm","Bk","Cf","Es","Fm","Md","No","Lr","Rf","Db","Sg","Bh","Hs","Mt","Ds","Rg","Cn","Nh","Fl","Mc","Lv","Ts","Og"};

    public Atom() {
        alive = true;
        atomicNum = 1;
        numElectrons = 1;
        numNeutrons = 0;
        insulator = false;

        strength = atomicNum - numElectrons;
        getSpeed();

        atomShape = new Circle();
        atomShape.setRadius(radius);
        atomShape.x = AtomGame.SCENE_WIDTH/4;
        atomShape.y = 0;

        faradaysCage = new Circle();
        faradaysCage.setRadius(radius*1.5f);
        faradaysCage.x = atomShape.x;
        faradaysCage.y = atomShape.y;
    }

    public void reset() {
        alive = true;
        atomicNum = 1;
        numElectrons = 0;
        numNeutrons = 0;
        insulator = false;

        strength = atomicNum - numElectrons;
        speed = -1*(atomicNum+numNeutrons)*(atomicNum+numNeutrons)/900 + 150;

        atomShape.x = AtomGame.SCENE_WIDTH/2;
        atomShape.y = 0;
        faradaysCage.x = atomShape.x;
        faradaysCage.y = atomShape.y;
    }

    public float getSpeed() {
        speed = -1*(atomicNum+numNeutrons)*(atomicNum+numNeutrons)/1400 + 10;
//        speed = 10;
        return speed;
    }

    public float getCharge() {
        charge = atomicNum - numElectrons;
        return charge;
    }

    public int getAtomicNum(){
        return atomicNum;
    }

    public int getNumElectrons() { return numElectrons; }

    public int getNumNeutrons() { return numNeutrons; }

    public float getRadius() {
        return radius;
    }

    public void addProton() {
//        System.out.printf("proton!\n"+Float.toString(atomShape.x)+","+Float.toString(AtomGame.SCENE_HEIGHT/2+atomShape.y));
        if (atomicNum < elements.length-1)
            atomicNum += 1;
        else
            atomicNum = 0;
    }

    public void addElectron() {
//        System.out.printf("electron!\n");
        if (numElectrons < elements.length-1)
            numElectrons+= 1;
        else
            numElectrons = 0;
    }

    public void addNeutron() {
        numNeutrons += 1;
    }

    public float xPos() {
        return atomShape.x;
    }

    public float yPos() {
        return atomShape.y;
    }

    public void ejectElectron() {
        // TODO
    }

    public void ejectProton() {
        // TODO
    }

    public void ejectNeutron() {
        // TODO
    }

    public void alphaDecay() {
        // TODO
    }

    public void physicsUpdate(){
        // TODO
        if (Gdx.input.isKeyPressed(Input.Keys.NUM_1))
            ejectElectron();
        if (Gdx.input.isKeyPressed(Input.Keys.NUM_2))
            ejectProton();
        if (Gdx.input.isKeyPressed(Input.Keys.NUM_3))
            ejectNeutron();
        if (Gdx.input.isKeyPressed(Input.Keys.NUM_4))
            alphaDecay();

    }

    public void graphicsUpdate(OrthographicCamera camera, float yShift) {
        if (Math.abs(yShift) > 800)
            yShift = 0;
        atomShape.y += yShift;
        atomShape.x += getSpeed();

        if (alive) {
            // updates
            // android input
            if (Gdx.input.isTouched()) {
                Vector3 touchPos = new Vector3();
                touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
                camera.unproject(touchPos);
                if (touchPos.x < (atomShape.x)) atomShape.x -= keyboardSpeed * Gdx.graphics.getDeltaTime();
                if (touchPos.x > 2 * radius + atomShape.x)
                    atomShape.x += keyboardSpeed * Gdx.graphics.getDeltaTime();
                if (touchPos.y > 2 * radius + AtomGame.SCENE_HEIGHT / 2 + atomShape.y)
                    atomShape.y += keyboardSpeed * Gdx.graphics.getDeltaTime();
                if (touchPos.y < AtomGame.SCENE_HEIGHT / 2 + atomShape.y)
                    atomShape.y -= keyboardSpeed * Gdx.graphics.getDeltaTime();
            }

            // keyboard input
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A))
                atomShape.x -= keyboardSpeed * Gdx.graphics.getDeltaTime();
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D))
                atomShape.x += keyboardSpeed * Gdx.graphics.getDeltaTime();
            if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W))
                atomShape.y += keyboardSpeed * Gdx.graphics.getDeltaTime();
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S))
                atomShape.y -= keyboardSpeed * Gdx.graphics.getDeltaTime();

            // guarantee atomShape in bounds
            if (atomShape.x < (-radius)) atomShape.x = (-radius);
            if (atomShape.y < (-AtomGame.SCENE_HEIGHT / 2 + radius))
                atomShape.y = (-AtomGame.SCENE_HEIGHT / 2 + radius);
            if (atomShape.x > AtomGame.SCENE_WIDTH - radius)
                atomShape.x = AtomGame.SCENE_WIDTH - radius;
            if (atomShape.y > AtomGame.SCENE_HEIGHT / 2 - radius)
                atomShape.y = AtomGame.SCENE_HEIGHT / 2 - radius;
        }
    }

    public void draw(SpriteBatch batch, OrthographicCamera camera, Texture atomTexture, Texture insulatorTexture, float yShift, BitmapFont font) {
        graphicsUpdate(camera,yShift);

        // draw insulator
        if (insulator) {
            faradaysCage.x = atomShape.x;
            faradaysCage.y = atomShape.y;
            // TODO test coords
            batch.draw(insulatorTexture,atomShape.x,AtomGame.SCENE_HEIGHT/2 - radius + atomShape.y+yShift);
        }

        // draw atom
        batch.draw(atomTexture,atomShape.x-radius,AtomGame.SCENE_HEIGHT/2+atomShape.y-radius+yShift);

        // draw element number
        font.draw(batch,elements[atomicNum]+"",atomShape.x-35,atomShape.y+AtomGame.SCENE_HEIGHT/2+15);

//        System.out.printf(atomShape.y+" "+yShift);
    }

    public void die() {
        alive = false;
    }
}
