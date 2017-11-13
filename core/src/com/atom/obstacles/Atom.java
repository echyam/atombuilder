package com.atom.obstacles;

import com.atom.builder.AtomGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
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

    private float radius = 100;
    private Circle atomShape;
    private Circle faradaysCage;

    public Atom() {
        atomicNum = 1;
        numElectrons = 0;
        numNeutrons = 0;
        insulator = false;

        strength = atomicNum - numElectrons;
        speed = -1*(atomicNum+numNeutrons)*(atomicNum+numNeutrons)/900 + 150;

        atomShape = new Circle();
        atomShape.setRadius(radius);
        atomShape.x = AtomGame.SCENE_WIDTH/2;
        atomShape.y = 0;

        faradaysCage = new Circle();
        faradaysCage.setRadius(radius*1.5f);
        faradaysCage.x = atomShape.x;
        faradaysCage.y = atomShape.y;
    }

    public float getSpeed() {
        speed = -1*(atomicNum+numNeutrons)*(atomicNum+numNeutrons)/900 + 150;
        return speed;
    }

    public float getCharge() {
        charge = atomicNum - numElectrons;
        return charge;
    }

    public void addProton() {
        atomicNum += 1;
    }

    public void addElectron() {
        numElectrons += 1;
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

    public void draw(SpriteBatch batch, OrthographicCamera camera, Texture atomTexture, Texture insulatorTexture, float yShift) {
        if (Math.abs(yShift) > 800)
            yShift = 0;
        atomShape.y += yShift;

        // updates
        // android input
        if(Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            if (touchPos.x < (atomShape.x)) atomShape.x -= 400 * Gdx.graphics.getDeltaTime();
            if (touchPos.x > 2*radius + atomShape.x) atomShape.x += 400 * Gdx.graphics.getDeltaTime();
            if (touchPos.y > 2*radius + AtomGame.SCENE_HEIGHT/2 + atomShape.y) atomShape.y += 400 * Gdx.graphics.getDeltaTime();
            if (touchPos.y < AtomGame.SCENE_HEIGHT/2 + atomShape.y) atomShape.y -= 400 * Gdx.graphics.getDeltaTime();
        }

        // keyboard input
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) atomShape.x -= 400 * Gdx.graphics.getDeltaTime();
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) atomShape.x += 400 * Gdx.graphics.getDeltaTime();
        if(Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) atomShape.y += 400 * Gdx.graphics.getDeltaTime();
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) atomShape.y -= 400 * Gdx.graphics.getDeltaTime();

        // guarantee atomShape in bounds
        if (atomShape.x < (-radius)) atomShape.x = (-radius);
        if (atomShape.y < (-AtomGame.SCENE_HEIGHT/2 + radius)) atomShape.y = (-AtomGame.SCENE_HEIGHT/2 + radius);
        if (atomShape.x > AtomGame.SCENE_WIDTH-radius) atomShape.x = AtomGame.SCENE_WIDTH-radius;
        if (atomShape.y > AtomGame.SCENE_HEIGHT/2-radius) atomShape.y = AtomGame.SCENE_HEIGHT/2-radius;

        batch.draw(atomTexture,atomShape.x,AtomGame.SCENE_HEIGHT/2 - radius + atomShape.y+yShift);

        if (insulator) {
            faradaysCage.x = atomShape.x;
            faradaysCage.y = atomShape.y;
            batch.draw(insulatorTexture,atomShape.x,AtomGame.SCENE_HEIGHT/2 - radius + atomShape.y+yShift);
        }

//        System.out.printf(atomShape.y+" "+yShift);
    }
}
