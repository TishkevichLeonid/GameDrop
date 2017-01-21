package com.leo.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by leonidtiskevic on 20.01.17.
 */

public class GameOverScreen implements Screen {

    final Drop game;
    private final Texture gameOver;
    OrthographicCamera camera;

    public GameOverScreen(Drop gam) {
        this.game = gam;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        gameOver = new Texture("GameOver.png");
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(gameOver, 0, 0);
        game.font.draw(game.batch, "Toutch to start", 400, 100);
        game.batch.end();

        if (Gdx.input.isTouched()){ // проверяем было ли прикосновение к экрану
            game.setScreen(new GameScreen(game)); // запускаем экран игры
            dispose(); // освобождаем ресурс меню
        }

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
