package com.atom.obstacles;

import com.atom.builder.AtomGame;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Lizzy on 11/12/2017.
 */
public class ElectricPlate extends Obstacle {
    // up means top
    public boolean up;
    public boolean positive;
    public float strength;
    public float height;
    public float width;
    public float x;
    public float y;

    private Rectangle plate;

    public ElectricPlate() {
        height = 100;
        width = 100;
        up = true;
        positive = true;
        strength = 50;
        x = 100;
        y = AtomGame.SCENE_HEIGHT - height;

        plate = new Rectangle(x, y, width, height);
    }

    public ElectricPlate(float w) {
        width = w;
        height = 100;
        up = true;
        positive = true;
        strength = 50;
        x = 100;
        y = AtomGame.SCENE_HEIGHT - height;

        plate = new Rectangle(x, y, width, height);
    }

    public ElectricPlate(float w, boolean top) {
        width = w;
        height = 100;
        up = top;
        positive = true;
        strength = 50;
        x = 100;
        y = (up) ? AtomGame.SCENE_HEIGHT - height : 0;

        plate = new Rectangle(x, y, width, height);
    }

    public ElectricPlate(float x, float w) {
        height = 100;
        width = w;
        up = true;
        strength = 50;
        this.x = x;
        y = AtomGame.SCENE_HEIGHT - height;

        plate = new Rectangle(x, y, width, height);
    }

    public ElectricPlate(float x, float w, float str) {
        height = 100;
        width = w;
        up = true;
        positive = true;
        strength = str;
        this.x = x;
        y = AtomGame.SCENE_HEIGHT - height;

        plate = new Rectangle(x, y, width, height);
    }

    public ElectricPlate(float x, float w, float str, boolean top){
        height = 100;
        width = w;
        up = top;
        positive = true;
        strength = str;
        this.x = x;
        y = (up) ? AtomGame.SCENE_HEIGHT - height : 0;

        plate = new Rectangle(x, y, width, height);
    }

    public void collideAtom(Atom atom){
        // TODO
    }

    public void draw(SpriteBatch batch, Texture pos, Texture neg) {
        if (positive)
            batch.draw(pos,plate.x,plate.y);
        else
            batch.draw(neg,plate.x,plate.y);
    }
}
