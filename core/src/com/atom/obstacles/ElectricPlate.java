package com.atom.obstacles;

import com.atom.builder.AtomGame;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Lizzy on 11/12/2017.
 */
public class ElectricPlate {
    // up means top
    public String direction;
    public float strength;
    public float height;
    public float width;

    private Rectangle plate;

    public ElectricPlate() {
        height = 100;
        width = 100;
        direction = "up";
        strength = 50;

        plate = new Rectangle(100, AtomGame.SCENE_HEIGHT - height, width, height);
    }

    public ElectricPlate(float w) {
        width = w;
        height = 100;
        direction = "up";
        strength = 50;

        plate = new Rectangle(100, AtomGame.SCENE_HEIGHT - height, width, height);
    }

    public ElectricPlate(float w, String dir) {
        width = w;
        height = 100;
        direction = dir;
        strength = 50;

        if (dir.equals("up"))
            plate = new Rectangle(100, AtomGame.SCENE_HEIGHT - height, width, height);
        else
            plate = new Rectangle(100, 0, width, height);
    }

    public ElectricPlate(float x, float w) {
        height = 100;
        width = w;
        direction = "up";
        strength = 50;

        plate = new Rectangle(x, AtomGame.SCENE_HEIGHT - height, width, height);
    }

    public ElectricPlate(float x, float w, float str) {
        height = 100;
        width = w;
        direction = "up";
        strength = str;

        plate = new Rectangle(x, AtomGame.SCENE_HEIGHT - height, width, height);
    }

    public ElectricPlate(float x, float w, float str, String dir){
        height = 100;
        width = w;
        direction = dir;
        strength = str;

        if (dir.equals("up"))
            plate = new Rectangle(x, AtomGame.SCENE_HEIGHT - height, width, height);
        else
            plate = new Rectangle(x, 0, width, height);
    }

    public void draw(SpriteBatch batch, Texture capacitorImage) {
        batch.draw(capacitorImage,plate.x,plate.y);
    }
}
