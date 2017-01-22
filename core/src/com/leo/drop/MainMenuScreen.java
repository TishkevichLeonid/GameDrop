package com.leo.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import sun.font.TrueTypeFont;

/**
 * Created by leonidtiskevic on 17.01.17.
 */

public class MainMenuScreen implements Screen { // реализуем интерфейс Screen
    /**
     * Интерфейс Screen не предоставляет метода create, поэтому  вместо этого используется конструктор
     * единственным необходимым параметром конструктора является экземпляр класса Drop
     */

    final Drop game; // экземпляр класса Drop
    OrthographicCamera camera; // экземпляр класса камера
    private Texture mainMenu;


    public MainMenuScreen(Drop gam) {       // конструктор
        this.game = gam;                    //
        camera = new OrthographicCamera();  // создаем объект класса камера
        camera.setToOrtho(false, 800, 480); // устанавливаем параметры для камеры
        mainMenu = new Texture("MainMenu.jpg");
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
        game.batch.draw(mainMenu, 0, 0);
        game.font.draw(game.batch, "Collect all drops!", 340, 300);
        game.font.draw(game.batch, "Toutch to start", 345, 200);
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
