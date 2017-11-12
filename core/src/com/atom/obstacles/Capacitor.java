package com.atom.obstacles;

import com.atom.builder.AtomGame;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by echyam on 11/12/2017.
 */
public class Capacitor extends Obstacle{
    // up = top is positively charged
    public boolean up;
    public float strength;
    public float height;
    public float width;
    public float x;

    private Rectangle top;
    private Rectangle bottom;

    public Capacitor(){
        height = 100;
        width = 500;
        up = true;
        strength = 50;

        top = new Rectangle(100, AtomGame.SCENE_HEIGHT-height,width,height);
        bottom = new Rectangle(100, 0,width,height);
    }

    public Capacitor(float w) {
        width = w;
        height = 100;
        up = true;
        strength = 15;
        x = 100;

        top = new Rectangle(x, AtomGame.SCENE_HEIGHT-height,width,height);
        bottom = new Rectangle(x, 0,width,height);
    }

    public Capacitor(float x, float w) {
        width = w;
        height = 100;
        up = true;
        strength = 15;
        this.x = x;

        top = new Rectangle(x, AtomGame.SCENE_HEIGHT-height,width,height);
        bottom = new Rectangle(x, 0,width,height);
    }

    public Capacitor(float x, float w, float str) {
        width = w;
        height = 100;
        up = true;
        strength = str;
        this.x = x;

        top = new Rectangle(x, AtomGame.SCENE_HEIGHT-height,width,height);
        bottom = new Rectangle(x, 0,width,height);
    }

    public Capacitor(float x, float w, float str, boolean ontop) {
        width = w;
        height = 100;
        up = ontop;
        strength = str;
        this.x = x;

        top = new Rectangle(x, AtomGame.SCENE_HEIGHT-height,width,height);
        bottom = new Rectangle(x, 0,width,height);
    }

    public void collideAtom(Atom atom) {
        // TODO
    }

    public void draw(SpriteBatch batch, Texture pos, Texture neg) {
        if (up) {
            batch.draw(pos, top.x, top.y);
            batch.draw(neg, bottom.x, bottom.y);
        } else {
            batch.draw(neg, top.x, top.y);
            batch.draw(pos, bottom.x, bottom.y);
        }
    }
}
