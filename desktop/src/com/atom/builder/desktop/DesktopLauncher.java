package com.atom.builder.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.atom.builder.AtomGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "atomgame";
		config.width = 1600;
		config.height = 900;
		new LwjglApplication(new AtomGame(), config);
	}
}
