package com.atom.obstacles;

import com.atom.builder.AtomGame;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by echyam on 11/12/2017.
 */
public class Capacitor{
    // up = top is positively charged
    public String direction;
    public float strength;
    public float height;
    public float width;
    public float x;
    public float y;

    private Rectangle top;
    private Rectangle bottom;

    public Capacitor(){
        height = 100;
        width = 500;
        direction = "up";
        strength = 50;

        top = new Rectangle(100, AtomGame.SCENE_HEIGHT-height,width,height);
        bottom = new Rectangle(100, 0,width,height);
    }

    public Capacitor(float w) {
        width = w;
        height = 100;
        direction = "up";
        strength = 15;

        top = new Rectangle(100, AtomGame.SCENE_HEIGHT-height,width,height);
        bottom = new Rectangle(100, 0,width,height);
    }

    public Capacitor(float x, float w) {
        width = w;
        height = 100;
        direction = "up";
        strength = 15;

        top = new Rectangle(x, AtomGame.SCENE_HEIGHT-height,width,height);
        bottom = new Rectangle(x, 0,width,height);
    }

    public Capacitor(float x, float w, float str) {
        width = w;
        height = 100;
        direction = "up";
        strength = str;

        top = new Rectangle(x, AtomGame.SCENE_HEIGHT-height,width,height);
        bottom = new Rectangle(x, 0,width,height);
    }

    public Capacitor(float x, float w, float str, String dir) {
        width = w;
        height = 100;
        direction = dir;
        strength = str;

        top = new Rectangle(x, AtomGame.SCENE_HEIGHT-height,width,height);
        bottom = new Rectangle(x, 0,width,height);
    }

    public void draw(SpriteBatch batch, Texture capacitorImage) {
        batch.draw(capacitorImage,top.x,top.y);
        batch.draw(capacitorImage,bottom.x,bottom.y);
    }
}
