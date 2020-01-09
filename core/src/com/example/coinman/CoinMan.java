package com.example.coinman;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class CoinMan extends ApplicationAdapter {
	private SpriteBatch batch;
	private Texture background;
	private Texture [] man ;
	private int slowMan ,manState;
	@Override
	public void create () {
		batch = new SpriteBatch();
		man = new Texture[4];
		background = new Texture("bg.png");
		man[0] = new Texture("frame-1.png");
		man[1] = new Texture("frame-2.png");
		man[2] = new Texture("frame-3.png");
		man[3] = new Texture("frame-4.png");
		manState =0;
		slowMan = 0;
	}

	@Override
	public void render () {
		batch.begin();

		if(slowMan<8) {  // arbitrarily chosen number so that man's movement slows down
            slowMan++;
		}else {
            if (manState < 3) {
                manState++;
            } else {
                manState = 0;
            }
            slowMan = 0;
        }
		batch.draw(background,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

		batch.draw(man[manState], Gdx.graphics.getWidth() / 2 - man[manState].getWidth() / 2, Gdx.graphics.getHeight() / 2);


		batch.end();

	}
	
	@Override
	public void dispose () {
		batch.dispose();

	}
}
