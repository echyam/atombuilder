package com.atom.obstacles;

import com.atom.builder.AtomGame;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by echyam on 11/13/2017.
 */

public class Proton extends Obstacle {
    public double x;
    public double y;
    public final double radius = 50;
//    public boolean alive = true;

    public Proton() {
        x = AtomGame.SCENE_WIDTH-200;
        y = AtomGame.SCENE_HEIGHT/2;
    }

    public Proton(double xx, double yy) {
        x = xx;
        y = yy;
    }

    public void draw(SpriteBatch batch, Texture protonImage) {
        batch.draw(protonImage,x-radius,y-radius);
    }

    public synchronized void collideAtom(Atom atom) {
        // TODO
        if (alive) {
            alive = false;
            atom.addProton();
        }
    }
}
