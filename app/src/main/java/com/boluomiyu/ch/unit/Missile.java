package com.boluomiyu.ch.unit;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.boluomiyu.ch.GameClient;
import com.boluomiyu.ch.effect.Explore;
import com.boluomiyu.miyueng.GameContext;
import com.boluomiyu.miyueng.clip.MovieClip;
import com.boluomiyu.miyueng.resource.ImageManager;
import com.boluomiyu.miyueng.sprite.Sprite;
import com.boluomiyu.miyueng.sprite.moveforce.MoveForce;
import com.boluomiyu.miyueng.sprite.moveforce.XZForce;
import com.boluomiyu.miyueng.util.HWMath;

/**
 * 类 Missile
 * 描述：炮弹
 * 菠萝秘密 2013 版权所有.
 * @author 邹彦虎    2013-2-3
 * @version 1.0
 */
public class Missile extends Sprite{

	private GameContext context;
	
	private MovieClip normalMovie; 
	
	private MovieClip shootMovie;

	public Missile() {
		
		this.context = GameContext.context;
		
		this.setZSort(false);
		
		im = ImageManager.getInstance();
		normalMovie = new MovieClip("images/sprite/maingun/missile.png", false, 0, 0, MovieClip.DIRECTION_FORWARD);
		normalMovie.reset();
		
		this.shootMovie = new MovieClip("images/sprite/maingun/missile_shoot.png", false, 0, 0, MovieClip.DIRECTION_FORWARD);
		this.shootMovie.reset();
		
	}
	
	@Override
	public void execute() {
		
		this.setX((float)(this.getX() + this.getSpeed() * Math.cos(HWMath.degree2rad(this.getRotate()))));
		this.setY((float)(this.getY() - this.getSpeed() * Math.sin(HWMath.degree2rad(this.getRotate()))));
		
		//碰撞检测
		Sprite enemy = this.hitEnemy();
		if (enemy != null) {
			this.setDestroyed(true);
			
			Explore explore = GameClient.context.getSpriteFromFactory(Explore.class);
			explore.alignTo(this);
			
			enemy.setX(enemy.getX() + 5);
			
			GameClient.context.addToScence(explore);
		}
		
		//出界回收
		if (this.getX() < 0 || this.getX() > GameContext.context.width || this.getY() < 0 || this.getY() > GameContext.context.height) {
			this.setDestroyed(true);
		}
		
		//落地回收
		
	}
	@Override
	public void render(Canvas canvas, Paint paint) {
		if (!shootMovie.isStop()) {
			//shootMovie.setXY(this.getX()-22, this.getY()-15);
			shootMovie.setXY(this.getX(), this.getY()-2);
			shootMovie.setRotate(this.getRotate());
			shootMovie.render(canvas, paint);
			shootMovie.stop();
		} else {
			//normalMovie.setXY(this.getX()-20, this.getY()-5);
			normalMovie.setXY(this.getX(), this.getY());
			normalMovie.setRotate(this.getRotate());
			normalMovie.render(canvas, paint);
		}
	}

	@Override
	public void reset() {
		this.normalMovie.reset();
		this.shootMovie.reset();
	}
	
	@Override
	public float getBodyX() {
		return this.getX();
	}
	@Override
	public float getBodyY() {
		return this.getY();
	}
	
	@Override
	public float getBodyWidth() {
		return 12;
	}
	@Override
	public float getBodyHeight() {
		return 3;
	}
	
	@Override
	public void effectHit(Sprite sprite) {
		// 逻辑：子弹只用来撞击，不会被撞击
	}
	
	@Override
	public boolean isSupportShadow() {
		return false;
	}

}
