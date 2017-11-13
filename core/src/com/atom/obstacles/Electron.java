package com.atom.obstacles;

import com.atom.builder.AtomGame;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by echyam on 11/13/2017.
 */

public class Electron extends Obstacle {
    public double x;
    public double y;
    public final double radius = 25;
//    public boolean alive = true;

    public Electron() {
        x = AtomGame.SCENE_WIDTH-200;
        y = AtomGame.SCENE_HEIGHT/2;
    }

    public Electron(double xx, double yy) {
        x = xx;
        y = yy;
    }

    public void draw(SpriteBatch batch, Texture electronImage) {
        batch.draw(electronImage,(float)(x-radius),(float)(y-radius));
    }

    public synchronized void collideAtom(Atom atom) {
        // TODO
        if (alive) {
            alive = false;
            atom.addElectron();
        }
    }
}
