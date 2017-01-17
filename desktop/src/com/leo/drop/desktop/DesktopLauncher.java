package com.leo.drop.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.leo.drop.Drop;
import com.leo.drop.GameScreen;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title="Drpop";
		config.width=800;
		config.height=400;
		new LwjglApplication(new Drop(), config);
	}
}
