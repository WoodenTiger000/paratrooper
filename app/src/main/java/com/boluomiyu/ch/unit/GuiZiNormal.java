package com.boluomiyu.ch.unit;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.boluomiyu.miyueng.GameContext;
import com.boluomiyu.miyueng.clip.MovieClip;
import com.boluomiyu.miyueng.sprite.Sprite;
import com.boluomiyu.miyueng.sprite.moveforce.GravityMoveForce;
import com.boluomiyu.miyueng.sprite.moveforce.MoveForce;

public class GuiZiNormal extends Sprite{
	
	private GameContext context;
	
	/**电影 */
	private MovieClip runGM;

	public GuiZiNormal() {
		this.context = GameContext.context;
		
		runGM = new MovieClip("images/sprite/guizi/guizi_walk.png", true, 31, 3, MovieClip.DIRECTION_FORWARD);
		runGM.reset();
		MoveForce gravityMF = context.getMoveForce(GravityMoveForce.class, this);
		this.addMoveForce(gravityMF);
		
	}
	
	
	@Override
	public void render(Canvas canvas, Paint paint) {
		if(runGM != null){
			runGM.setXY(this.getBodyX(), this.getBodyY());
			runGM.render(canvas, paint);
		}
	}
	
	@Override
	public void execute() {
		if(this.isDead()) {
			this.setDestroyed(true);
		} else {
			if(this.getX() < -100){
				this.setX(GameContext.context.width);
			} else {
				if (this.getZ() == 0) {
					this.setX(this.getX() - 2);
				}
			}
		}
		
		runGM.execute();
	}

	@Override
	public float getBodyWidth() {
		return 30;
	}

	@Override
	public float getBodyHeight() {
		return 40;
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
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
