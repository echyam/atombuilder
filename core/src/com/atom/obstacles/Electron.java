package com.atom.obstacles;

import com.atom.builder.AtomGame;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by echyam on 11/13/2017.
 */

public class Electron extends Obstacle {
    public float x;
    public float y;
    public final float radius = 25;

    public Electron() {
        x = AtomGame.SCENE_WIDTH-200;
        y = AtomGame.SCENE_HEIGHT/2;
    }

    public Electron(float xx, float yy) {
        x = xx;
        y = yy;
    }

    public void draw(SpriteBatch batch, Texture electronImage) {
        batch.draw(electronImage,x-radius,y-radius);
    }

    public void collideAtom(Atom atom) {
        // TODO
        atom.addElectron();
    }
}
