package com.atom.obstacles;

import com.atom.builder.AtomGame;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by echyam on 11/13/2017.
 */

public class Neutron extends Obstacle {
    public double x;
    public double y;
    public final double radius = 50;
//    public boolean alive = true;

    public Neutron() {
        x = AtomGame.SCENE_WIDTH-200;
        y = AtomGame.SCENE_HEIGHT/2;
    }

    public Neutron(double xx, double yy) {
        x = xx;
        y = yy;
    }

    public void draw(SpriteBatch batch, Texture neutronImage) {
        batch.draw(neutronImage,(float)(x-radius),(float)(y-radius));
    }

    public synchronized void collideAtom(Atom atom) {
        // TODO
        if (alive) {
            alive = false;
            atom.addNeutron();
        }
    }
}
