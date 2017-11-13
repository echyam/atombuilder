package com.atom.obstacles;

import com.atom.builder.AtomGame;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Lizzy on 11/13/2017.
 */

public class Proton extends Obstacle {
    public float x;
    public float y;

    public Proton() {
        x = AtomGame.SCENE_WIDTH-200;
        y = AtomGame.SCENE_HEIGHT/2;
    }

    public Proton(float xx, float yy) {
        x = xx;
        y = yy;
    }

    public void collideAtom(Atom atom) {
        // TODO
        atom.addProton();
    }

    public void draw(SpriteBatch batch, Texture protonImage) {
        batch.draw(protonImage,x-25,y-25);
    }
}
