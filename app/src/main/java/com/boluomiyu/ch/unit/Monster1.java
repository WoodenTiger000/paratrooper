package com.boluomiyu.ch.unit;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.boluomiyu.miyueng.GameContext;
import com.boluomiyu.miyueng.clip.MovieClip;
import com.boluomiyu.miyueng.sprite.Sprite;
import com.boluomiyu.miyueng.sprite.moveforce.GravityMoveForce;
import com.boluomiyu.miyueng.sprite.moveforce.MoveForce;
/**
 * 类 Monster1
 * 描述：怪兽1
 * $菠萝蜜语$ 荣誉出品  2013 版权所有.
 * @author 邹彦虎    2013-2-17
 * @version 1.0
 */
public class Monster1 extends Sprite{

	private GameContext context;
	
	/**电影 */
	private MovieClip normalGM; 
	
	public Monster1() {
		this.context = GameContext.context;
		
		normalGM = new MovieClip("images/sprite/monster/monster_1.png", false, 250, 1, MovieClip.DIRECTION_FORWARD);
		normalGM.reset();
		MoveForce gravityMF = context.getMoveForce(GravityMoveForce.class, this);
		this.addMoveForce(gravityMF);
	}
	
	@Override
	public void execute() {
		if(this.isDead()) {
			this.setY(this.getY() + 1);
			if (this.getY() > 302) {
				this.setDestroyed(true);
			}
		} else {
			if(this.getX() < -100){
				this.setX(GameContext.context.width);
			} else {
				if (this.getZ() == 0) {
					this.setX(this.getX() - 1);
				}
			}
		}
	}
	
	@Override
	public void render(Canvas canvas, Paint paint) {
		if(normalGM != null){
			normalGM.setXY(this.getBodyX()-20, this.getBodyY() - 60);
		}
		normalGM.render(canvas, paint);
	}
	
	@Override
	public float getBodyWidth() {
		return this.normalGM.getWidth() - 160;
	}

	@Override
	public float getBodyHeight() {
		return this.normalGM.getHeight() - 80;
	}

	@Override
	public void effectHit(Sprite sprite) {
		this.setHP(this.getHP() - 100);
		this.setX(this.getX() + 5);
		if (this.getHP() < 0) {
			this.setDead(true);
		}
	}
	@Override
	public void reset() {
		
	}
	@Override
	public boolean isSupportShadow() {
		return true;
	}



}
