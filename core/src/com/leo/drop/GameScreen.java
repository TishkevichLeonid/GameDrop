package com.leo.drop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

public class GameScreen implements Screen {  //релизуем интрефейс Screen
	private final Drop game;
	private OrthographicCamera camera;  // создаем поля для каждого ресурса, объявляя их типы
	private SpriteBatch batch;
	private Texture dropImage;
	private Texture bucketImage;
	private final Texture backgorund;
	private Sound dropSound;
	private Music rainMusic;
	private Rectangle bucket; // класс для сохранения позиции и размера ведра
	Vector3 touchPos; // создаем вектор для считывания прикосновения
	Array<Rectangle> raindrops; // список экземпляров Rectangle каждый из которых хранит позицию и размер капли
	long lastDropTime;
	private int dropsCollect;
	

	public GameScreen (final Drop gam) {   //используем конструктор и передаем в него объект Drop

		this.game = gam;

		camera = new OrthographicCamera(); // создали камеру
		camera.setToOrtho(false, 800, 480); // метод, который позволяет убедиться что камера всегда показывает область мира игры размером 800x400
		batch = new SpriteBatch();
		dropImage = new Texture("droplet.png"); // загрузка изображения капли
		bucketImage = new Texture("bucket.png"); // загрузка изображения ведра
		backgorund = new Texture("background.jpg"); // загрузка background
		touchPos = new Vector3();

		dropSound = Gdx.audio.newSound(Gdx.files.internal("waterdrop.wav")); // загружаем звуковой эффект (продолжительность звука < 10c)
		rainMusic = Gdx.audio.newMusic(Gdx.files.internal("undertreeinrain.mp3")); //

		rainMusic.setLooping(true); // организует повторение музыки
		rainMusic.play(); // запускает при старте приложения

		bucket = new Rectangle(); // создание объекта Rectangle
		bucket.x = 800 / 2 - 64 / 2; // размещаем ведро в центре экрана
		bucket.y = 20; // и выше на 20 пикселей низа экрана
		bucket.width = 64; // размеры ведра
		bucket.height = 64;

		raindrops = new Array<Rectangle>(); // создаем экземпляр массива капель
		spawnRaindrop(); //  порождаем первую каплю

	}

	private void spawnRaindrop(){ // метод создания капли. Создает новый Rectangle, устанавливает в слуйчаной позиции в верхней часте экрана и добавляет его в массив raindrops
		Rectangle raindrop = new Rectangle();
		raindrop.x = MathUtils.random(0, 800 - 64); // возвращается случайное число между 0 и 800-64
		raindrop.y = 480; // верхняя граница экрана
		raindrop.width = 64;
		raindrop.height = 64;
		raindrops.add(raindrop);
		lastDropTime = TimeUtils.nanoTime(); // записываем текущее время в наносекундах
	}

	@Override
	public void render (float delta) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1); // очищаем экран в синий цвет
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update(); // обновление камеры (1 в кадр)

		game.batch.setProjectionMatrix(camera.combined); /** сообщаем Spritebatch что нужно использовать систему координат камеры
		* это делается с помощью матрицы, то есть матрицы проекции, поле camera.combined является такой матрицей
		*/
		game.batch.begin(); // начинает новую batch серию
		game.batch.draw(backgorund, 0, 0);
		game.font.draw(game.batch, "Drops collect: " + dropsCollect, 0, 480); // сообщает о кол-ве пойманных капель
		game.batch.draw(bucketImage, bucket.x, bucket.y); // отрисовываем наше ведро
		for(Rectangle raindrop: raindrops){ // отображение капель на экране
			game.batch.draw(dropImage, raindrop.x, raindrop.y);

		}
		game.batch.end();

		if (Gdx.input.isTouched()){ //(есть ли приконсновение к экрану
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0); // устанавливаем текущие координаты прикосновения
			camera.unproject(touchPos); // преобразовние координат прикосновением в систему координат камеры unproject(вх: трехмерный вектор)
			bucket.x = touchPos.x - 64 / 2; // изменяется позиция ведра, только по х и в центре прикосновения к экрану
		}
		/** метод isKeyPressed сообщает о нажатии определенной клавиши Keys содержит все коды клавиш, метод GetDeltaTime() возвращает время
		 *  прошедшие между последним и текущим кадром в секундах
		 */

		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) bucket.x -= 200 * Gdx.graphics.getDeltaTime();
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) bucket.x += 200 * Gdx.graphics.getDeltaTime();

		if (bucket.x < 0) bucket.x = 0; // убеждаемся что ведро остается в переделах экрана
		if (bucket.x > 800-64) bucket.x = 800 - 64; // убеждаемся что ведро остается в переделах экрана [2]

		if (TimeUtils.nanoTime() - lastDropTime > 1000000000) spawnRaindrop(); // проверяет сколько времени прошло с прошлой капли и создает новую если нужно

		/**
		 * сделаем так что бы капли двигались с постоянной скоростью 200 пикселей в секунду, если капля находится ниже нижнего края экрана
		 * то мы ее удаляем из массива
		 */
		Iterator<Rectangle> iter = raindrops.iterator();

		while (iter.hasNext()){
			Rectangle raindrop = iter.next();
			raindrop.y -= 200 * Gdx.graphics.getDeltaTime();
			if (raindrop.y + 64 < 0) iter.remove();
			if (raindrop.overlaps(bucket)) { // если капля столкнулась с ведром overlaps проверяет пересечение треугольников
				dropsCollect++; // пополняется счет собраных капель
				dropSound.play(); // воспроизводится звук капли
				iter.remove(); // капля удаляется из массива
			}
		}

	}
	
	@Override
	public void dispose () {
		dropImage.dispose();
		bucketImage.dispose();
		dropSound.dispose();
		rainMusic.dispose();

	}

	@Override
	public void show() {
		rainMusic.play();
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
}
