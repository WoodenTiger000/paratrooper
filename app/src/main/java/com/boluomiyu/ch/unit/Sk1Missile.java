package com.boluomiyu.ch.unit;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.boluomiyu.miyueng.GameContext;
import com.boluomiyu.miyueng.clip.MovieClip;
import com.boluomiyu.miyueng.resource.ImageManager;
import com.boluomiyu.miyueng.sprite.Sprite;
import com.boluomiyu.miyueng.util.HWMath;

/**
 * 类 Missile
 * 描述：炮弹
 * 菠萝秘密 2013 版权所有.
 * @author 邹彦虎    2013-2-3
 * @version 1.0
 */
public class Sk1Missile extends Sprite{

	private MovieClip normalMovie; 
	
	public Sk1Missile() {
		im = ImageManager.getInstance();
		normalMovie = new MovieClip("images/sprite/sk_1.png", false, 0, 0, MovieClip.DIRECTION_FORWARD);
		normalMovie.execute();
		
	}
	@Override
	public void render(Canvas canvas, Paint paint) {
		//normalMovie.setXY(this.getX()-20, this.getY()-5);
		normalMovie.setXY(this.getX(), this.getY());
		normalMovie.setRotate(this.getRotate());
		normalMovie.render(canvas, paint);
	}

	@Override
	public void execute() {
		
		this.setX((float)(this.getX() + this.getSpeed() * Math.cos(HWMath.degree2rad(this.getRotate()))));
		this.setY((float)(this.getY() - this.getSpeed() * Math.sin(HWMath.degree2rad(this.getRotate()))));
		
		//碰撞检测
		Sprite enemy = this.hitEnemy();
		if (enemy != null) {
			enemy.effectHit(this);
			this.setDestroyed(true);
		}
		
		//出界回收
		if (this.getX() < 0 || this.getX() > GameContext.context.width || this.getY() < 0 || this.getY() > GameContext.context.height) {
			this.setDestroyed(true);
		}
		
		//落地回收
		
	}

	@Override
	public void reset() {
		this.normalMovie.execute();;
	}
	
	@Override
	public void effectHit(Sprite sprite) {
		// 逻辑：子弹只用来撞击，不会被撞击
	}
	@Override
	public float getBodyWidth() {
		return normalMovie.getWidth();
	}
	@Override
	public float getBodyHeight() {
		return normalMovie.getHeight();
	}
	@Override
	public boolean isSupportShadow() {
		return false;
	}

}
