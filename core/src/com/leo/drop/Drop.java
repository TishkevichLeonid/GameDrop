package com.leo.drop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by leonidtiskevic on 17.01.17.
 */

public class Drop extends Game { // наследуем от класса Game

    SpriteBatch batch; // объекст класса SpriteBatch, используется для отображения объектов на экране (текстуры)
    BitmapFont font; // используется для отображения текста на экране

    @Override
    public void create() {  // переопределяем метод create

        batch = new SpriteBatch();
        font = new BitmapFont();
        this.setScreen(new MainMenuScreen(this)); //устанавливаем экран игры методом setScreen, которому передаем экземпляр класса MainMenuSc

    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
        font.dispose();
    }
}
