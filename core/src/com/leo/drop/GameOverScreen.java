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
    private Texture backgorund;

    public GameOverScreen(Drop gam) {
        this.game = gam;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        backgorund = new Texture("background.jpg");
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
        game.batch.draw(backgorund, 0, 0);
        game.batch.draw(gameOver, 120, 180);
        game.font.draw(game.batch, "Toutch to start", 350, 100);
        game.font.draw(game.batch, "Your score is: " + GameScreen.dropsCollect, 345, 150);
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
