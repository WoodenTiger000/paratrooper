package com.boluomiyu.ch.unit;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.boluomiyu.miyueng.GameContext;
import com.boluomiyu.miyueng.clip.MovieClip;
import com.boluomiyu.miyueng.sprite.Sprite;
import com.boluomiyu.miyueng.sprite.moveforce.GravityMoveForce;
import com.boluomiyu.miyueng.sprite.moveforce.MoveForce;

public class FlyFish extends Sprite {
	
	private GameContext context;
	
	private MovieClip flyGM;
	
	private MovieClip deadGM;

	public FlyFish() {
		
		this.context = GameContext.context;
		
		flyGM = new MovieClip("images/sprite/flyfish/flyfish_fly.png", true, 40, 3, MovieClip.DIRECTION_FORWARD);
		flyGM.reset();
		
		deadGM = new MovieClip("images/sprite/flyfish/flyfish_dead.png", false, 54, 3, MovieClip.DIRECTION_FORWARD);
		deadGM.reset();
		
	}
	
	@Override
	public void render(Canvas canvas, Paint paint) {
		
		if (!this.isDead()) {
			flyGM.setXY(this.getBodyX(), this.getBodyY());
			flyGM.render(canvas, paint);
		} else {
			deadGM.setXY(this.getBodyX(), this.getBodyY());
			deadGM.render(canvas, paint);
		}
		
	}
	
	@Override
	public void execute() {
		if(this.isDead()) {
			if (deadGM.isEnd()) {
				this.setDestroyed(true);
				
			}
			deadGM.execute();
		} else {
			if(this.getX() < -100){
				this.setX(GameContext.context.width);
			} else {
				this.setX(this.getX() - 2);
			}
			flyGM.execute();
		}
		
		
	}

	@Override
	public float getBodyWidth() {
		return 40;
	}

	@Override
	public float getBodyHeight() {
		return 38;
	}

	@Override
	public void reset() {
		this.flyGM.reset();
		this.deadGM.reset();
	}

	@Override
	public void effectHit(Sprite sprite) {
		this.setHP(this.getHP() - 100);
		if (this.getHP() < 0) {
			this.setDead(true);
		}
	}



	@Override
	public boolean isSupportShadow() {
		return true;
	} 

}
