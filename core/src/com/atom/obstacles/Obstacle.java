package com.atom.obstacles;

/**
 * Created by echyam on 11/12/2017.
 */
public abstract class Obstacle {
    public volatile boolean alive = true;
    public abstract void collideAtom(Atom atom);
}
