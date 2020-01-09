package com.example.coinman;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.Random;

public class CoinMan extends ApplicationAdapter {
	private SpriteBatch batch;
	private Texture background,coin,bomb;
	private Texture [] man ;
	private int slowMan ,manState;
	private float gravity,velocity;
	private float manY;
	private ArrayList<Integer> coinXs = new ArrayList<>();
    private ArrayList<Integer> coinYs = new ArrayList<>();
	private ArrayList<Integer> bombXs = new ArrayList<>();
	private ArrayList<Integer> bombYs = new ArrayList<>();
	private ArrayList<Rectangle> coinRectangle = new ArrayList<>();
	private ArrayList<Rectangle> bombRectangle = new ArrayList<>();
	private Rectangle manRectangle;
    private Random random ;
    private int coinCount =0,bombCount=0; // determines the spacing
	@Override
	public void create () {
		batch = new SpriteBatch();
		man = new Texture[4];
		background = new Texture("bg.png");
		man[0] = new Texture("frame-1.png");
		man[1] = new Texture("frame-2.png");
		man[2] = new Texture("frame-3.png");
		man[3] = new Texture("frame-4.png");
		coin = new Texture("coin.png");
		bomb = new Texture("bomb.png");
		manState =0;
		slowMan = 0;
		gravity = 0.2f;
		velocity = 0;
		manY = Gdx.graphics.getHeight() / 2;
        random = new Random();


	}

	private void makeCoin(){

	    float height = random.nextFloat()*Gdx.graphics.getHeight();
	    coinYs.add((int)height);
	    coinXs.add(Gdx.graphics.getWidth());
    }
    private void makeBomb(){
		float height = random.nextFloat() * Gdx.graphics.getHeight();
		bombYs.add((int)height);
		bombXs.add(Gdx.graphics.getWidth());
	}

	@Override
	public void render () {
		batch.begin();
		batch.draw(background,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

		//Bombs
		if(bombCount <250){
			bombCount++;
		}else{
			bombCount=0;
			makeBomb();
		}

		// Coins
//		if(coinCount<100){
//		    coinCount++;
//        }else{
//		    coinCount=0;
//		    makeCoin();
//        }
//
//		int i=0;
//		coinRectangle.clear();
//		while(i<coinYs.size()){
//			batch.draw(coin, coinXs.get(i), coinYs.get(i));
//			coinXs.add(i, coinXs.get(i) - 4);
//			coinRectangle.add(new Rectangle(coinXs.get(i),coinYs.get(i),coin.getWidth(),coin.getHeight()));
//			i++;
//		}
		if (coinCount < 100) {
			coinCount++;
		} else {
			coinCount = 0;
			makeCoin();
		}

		coinRectangle.clear();
		for (int i=0;i < coinXs.size();i++) {
			batch.draw(coin, coinXs.get(i), coinYs.get(i));
			coinXs.set(i, coinXs.get(i) - 4);
			coinRectangle.add(new Rectangle(coinXs.get(i), coinYs.get(i), coin.getWidth(), coin.getHeight()));
		}

		bombRectangle.clear();
		for(int i=0;i<bombXs.size();i++){
			batch.draw(bomb,bombXs.get(i),bombYs.get(i));
			bombXs.set(i,bombXs.get(i)-8);
			bombRectangle.add(new Rectangle(bombXs.get(i),bombYs.get(i),bomb.getWidth(),bomb.getHeight()));
		}

		if(Gdx.input.justTouched()){ // jump when touch
			velocity = -10;
		}
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
		velocity += gravity;
		manY -= velocity;
		if(manY<=0){  // so that man doesn't goes too below (below the screen)
		    manY=0;
        }


		batch.draw(man[manState], Gdx.graphics.getWidth() / 2 - man[manState].getWidth() / 2, manY);
		manRectangle = new Rectangle(Gdx.graphics.getWidth()/2-man[manState].getWidth()/2,manY,man[manState].getWidth(),man[manState].getHeight());

		int i = 0;
		while(i<coinYs.size()){
			if(Intersector.overlaps(manRectangle,coinRectangle.get(i))){
				Gdx.app.log("Collision","Coins!!");
			}
			i++;
		}

		for(int j=0;j<bombYs.size();j++){
			if(Intersector.overlaps(manRectangle,bombRectangle.get(j))){
				Gdx.app.log("Collision","Bomb!!");
			}
		}

		batch.end();

	}

	@Override
	public void dispose () {
		batch.dispose();

	}
}
