package com.boluomiyu.ch.unit;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.boluomiyu.ch.effect.BigExplore;
import com.boluomiyu.miyueng.GameContext;
import com.boluomiyu.miyueng.clip.MovieClip;
import com.boluomiyu.miyueng.sprite.Sprite;
import com.boluomiyu.miyueng.sprite.moveforce.GravityMoveForce;
import com.boluomiyu.miyueng.sprite.moveforce.MoveForce;
import com.boluomiyu.miyueng.sprite.moveforce.ZKeepForce;

/**
 * 类 Battleplane
 * 描述：战斗机
 * 公司 2013 版权所有.
 * @author 邹彦虎    2013-2-11
 * @version 1.0
 */
public class Battleplane extends Sprite{
	
	private GameContext context;
	
	/**电影 */
	private MovieClip standupGM; 
	
	public Battleplane() {
		this.context = GameContext.context;
		standupGM = new MovieClip("images/sprite/battleplane_standup.png", false, 95, 1, MovieClip.DIRECTION_FORWARD);
		standupGM.reset();
		/**
		MoveForce moveForce = context.getMoveForce(GravityMoveForce.class, this);
		super.addMoveForce(moveForce);
		*/
		ZKeepForce keepForce = (ZKeepForce) context.getMoveForce(ZKeepForce.class, this);
		keepForce.setKeepZ(200);
		keepForce.setSpeed(2);
		super.addMoveForce(keepForce);
	}
	
	@Override
	public void render(Canvas canvas, Paint paint) {
		if(standupGM != null){
			standupGM.setXY(this.getBodyX(), this.getBodyY());
		}
		standupGM.render(canvas, paint);
	}
	
	@Override
	public void execute() {
		if(this.isDead()) {
			this.setXYZ(this.getX() - 10, this.getY(), this.getZ() - 5);
			if (this.getZ() < 0) {
				this.setDestroyed(true);
				Sprite bigExplore = context.getSpriteFromFactory(BigExplore.class);
				bigExplore.alignTo(this);
				context.addToScence(bigExplore);
			}
		} else {
			if(this.getX() < -100){
				this.setX(GameContext.context.width);
			} else {
				this.setX(this.getX() - 5);
			}
		}
	}
	
	public void reset() {
		this.standupGM.reset();
	}
	
	@Override
	public float getBodyWidth() {
		return this.standupGM.getWidth();
	}

	@Override
	public float getBodyHeight() {
		return this.standupGM.getHeight();
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
