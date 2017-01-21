package com.leo.drop;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * Created by leonidtiskevic on 20.01.17.
 */

public class GameOverScreen implements Screen {

    final Drop game;
    OrthographicCamera camera;

    public GameOverScreen(Drop gam) {
        this.game = gam;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
