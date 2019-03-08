package com.boluomiyu.ch.unit;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.boluomiyu.miyueng.GameContext;
import com.boluomiyu.miyueng.clip.MovieClip;
import com.boluomiyu.miyueng.sprite.Sprite;
import com.boluomiyu.miyueng.sprite.moveforce.GravityMoveForce;
import com.boluomiyu.miyueng.sprite.moveforce.MoveForce;

/**
 * 名称: WomenGuiZi
 * 职责：
 * $菠萝蜜语$ 荣誉出品  2013 版权所有.
 * @author 邹彦虎    2013-6-22
 * @version 1.0
 */
public class WomenGuiZi extends Sprite{
	
	private GameContext context;
	
	/**电影 */
	private MovieClip runGM; 
	
	public WomenGuiZi() {

		this.context = GameContext.context;
		runGM = new MovieClip("images/sprite/guizi/guizi_women_2.png", true, 140, 2, MovieClip.DIRECTION_BACKWARD);
		runGM.reset();
		MoveForce gravityMF = context.getMoveForce(GravityMoveForce.class, this);
		this.addMoveForce(gravityMF);
	}
	
	@Override
	public void render(Canvas canvas, Paint paint) {
		if(runGM != null){
			runGM.setXY(this.getBodyX() - 40, this.getBodyY());
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
		
		// 动画执行
		this.runGM.execute();
		
	}
	
	public void reset() {
		this.runGM.reset();
	}
	
	@Override
	public float getBodyX() {
		return this.getX();
	}

	@Override
	public float getBodyY() {
		return this.getY() - this.getZ();
	}
	@Override
	public float getBodyWidth() {
		return 40;
	}

	@Override
	public float getBodyHeight() {
		return 100;
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
