package com.atom.obstacles;

import com.atom.builder.AtomGame;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Lizzy on 11/13/2017.
 */

public class Neutron extends Obstacle {
    public float x;
    public float y;
    public final float radius = 50;

    public Neutron() {
        x = AtomGame.SCENE_WIDTH-200;
        y = AtomGame.SCENE_HEIGHT/2;
    }

    public Neutron(float xx, float yy) {
        x = xx;
        y = yy;
    }

    public void draw(SpriteBatch batch, Texture neutronImage) {
        batch.draw(neutronImage,x-radius,y-radius);
    }

    public void collideAtom(Atom atom) {
        // TODO
        atom.addNeutron();
    }
}
