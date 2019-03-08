package com.boluomiyu.ch.unit;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.boluomiyu.ch.GameClient;
import com.boluomiyu.miyueng.GameContext;
import com.boluomiyu.miyueng.MainCanvas;
import com.boluomiyu.miyueng.clip.MovieClip;
import com.boluomiyu.miyueng.resource.SoundList;
import com.boluomiyu.miyueng.sprite.Sprite;
import com.boluomiyu.miyueng.util.HWMath;
import com.boluomiyu.miyueng.util.Logger;
/**
 * 类 MainGunBody
 * 描述：主炮炮身
 * 菠萝秘密 2013 版权所有.
 * @author 邹彦虎    2013-1-20
 * @version 1.0
 */
public class MainGun extends Sprite{

	
	private GameContext context;
	/** 底座  */
	private MovieClip landMC;
	
	/** 炮身 */
	private MovieClip gunMC;
	
	/** CD */
	private int missileCD = 0;
	
	private Sprite enemy;
	
	public MainGun() {
		// 0 - 100
		context = GameContext.context;
		landMC = new MovieClip("images/sprite/maingun/maingun_land.png", false, 0, 0, MovieClip.DIRECTION_FORWARD);
		gunMC = new MovieClip("images/sprite/maingun/maingun_gun.png", false, 0, 0, MovieClip.DIRECTION_FORWARD);
		landMC.reset();
		gunMC.reset();
		this.setAttAreaX(500);
		
	}
	
	@Override
	public void render(Canvas canvas, Paint paint) {
		
		gunMC.setRotate(this.getRotate());
		gunMC.setRotateXY(19, 12);
		gunMC.setXY(this.getX()-12, this.getY() - 6);//偏移套炮嘴
		
		//同步底座
		landMC.setXY(this.getX(), this.getY());
		
		gunMC.render(canvas, paint);
		landMC.render(canvas, paint);
		
		if (context.debug && MainCanvas.fingerX > 0) {
			//canvas.drawLine(this.getX(), this.getY(), MainCanvas.fingerX, MainCanvas.fingerY, paint);
			canvas.drawCircle(gunMC.getX() + gunMC.getRotateX(), gunMC.getY() + gunMC.getRotateY(), 5, paint);
		}
	}
	
	@Override
	public void execute() {
		
		this.missileCD --;
		if (this.missileCD < 0) {
			this.missileCD = 0;
		}
		// 查找敌人
		enemy = context.findNearestEnemy(this);
		
		// 死亡或超出攻击范围，丢弃目标
		if (enemy != null && (enemy.isDead() || Math.abs(enemy.getBodyX() - this.getBodyX()) > this.getAttAreaX()) ) {
			enemy = null;
		}
		
		
		

		
		double rad = 0;
//		
//		if (enemy == null) {
//			return;
//		}
		
//		rad = Math.atan2(-(enemy.getBodyY() - this.getBodyY()), enemy.getBodyX() - this.getBodyX());
//		//角度矫正
//		if (rad < -0.2616) {//-15度对应
//			rad = -0.2616;
//		} else if (rad > 1.57) {//90度对应
//			rad = 1.57;
//		}
//		
//		this.setRotate((int)HWMath.rad2degree(rad));
		
		
		// 手动控制
		
		if (MainCanvas.fingerDown) {
			rad = Math.atan2(-(MainCanvas.fingerY - MainGun.this.getY()), MainCanvas.fingerX - MainGun.this.getX());
			//角度矫正
			if (rad < -0.2616) {//-15度对应
				rad = -0.2616;
			} else if (rad > 1.57) {//90度对应
				rad = 1.57;
			}
			//右侧控制栏 
			if (MainCanvas.fingerX < 100) {
				rad = (MainCanvas.fingerY + 50)/(480-50) * HWMath.degree2rad(105);
				rad = Math.PI/2 - rad;
			}
			this.setRotate((int)HWMath.rad2degree(rad));
		}
		if (missileCD == 0 && MainCanvas.fingerDown==true) {
		
			// 射击
			if (missileCD == 0) {
				missileCD = 10;
				
				float deltaX = (float)(52 * Math.cos(rad));
				float deltaY = -(float)(52 * Math.sin(rad));
				
				Missile missile = context.getSpriteFromFactory(Missile.class);
				missile.setX(gunMC.getX() + gunMC.getRotateX() - 6 + deltaX);
				missile.setY(gunMC.getY() + gunMC.getRotateY() - 1.5f + deltaY);
				missile.setRotate(this.getRotate());
				missile.setTeam(1);
				missile.setSpeed(20);
				context.addToScence(missile);
				//context.playSound(SoundList.MAINGUN_SHOOT);
			}
			if (this.getMP() < this.getMaxMP()) {
				this.setMP(this.getMP() + 0.1f);
			}
		}
		
	}
	
	public void thump1() {
		
		Logger.tip("MainGun::技能1");
		
		context.playSound(SoundList.MAINGUN_SHOOT);
		context.playSound(SoundList.MAINGUN_SHOOT);
		context.playSound(SoundList.MAINGUN_SHOOT);
		
		this.setMP(this.getMP() - 10);
		
		float deltaX = (float)(120 * Math.cos(HWMath.degree2rad(this.getRotate() - 10)));
		float deltaY = -(float)(120 * Math.sin(HWMath.degree2rad(this.getRotate() - 10)));
		Missile missile = context.getSpriteFromFactory(Missile.class);
		missile.setX(gunMC.getX() + gunMC.getRotateX() -20 + deltaX);
		missile.setY(gunMC.getY() + gunMC.getRotateY() - 7 + deltaY);
		missile.setRotate(this.getRotate());
		missile.setTeam(1);
		missile.setSpeed(20);
		context.addToScence(missile);
		
		deltaX = (float)(120 * Math.cos(HWMath.degree2rad(this.getRotate() - 5)));
		deltaY = -(float)(120 * Math.sin(HWMath.degree2rad(this.getRotate()- 5)));
		missile = context.getSpriteFromFactory(Missile.class);
		missile.setX(gunMC.getX() + gunMC.getRotateX() -20 + deltaX);
		missile.setY(gunMC.getY() + gunMC.getRotateY() - 7 + deltaY);
		missile.setRotate(this.getRotate());
		missile.setTeam(1);
		missile.setSpeed(20);
		context.addToScence(missile);
		
		deltaX = (float)(120 * Math.cos(HWMath.degree2rad(this.getRotate())));
		deltaY = -(float)(120 * Math.sin(HWMath.degree2rad(this.getRotate())));
		missile = context.getSpriteFromFactory(Missile.class);
		missile.setX(gunMC.getX() + gunMC.getRotateX() -20 + deltaX);
		missile.setY(gunMC.getY() + gunMC.getRotateY() - 7 + deltaY);
		missile.setRotate(this.getRotate());
		missile.setTeam(1);
		missile.setSpeed(20);
		context.addToScence(missile);
		
		deltaX = (float)(120 * Math.cos(HWMath.degree2rad(this.getRotate() + 5)));
		deltaY = -(float)(120 * Math.sin(HWMath.degree2rad(this.getRotate()+ 5)));
		missile = context.getSpriteFromFactory(Missile.class);
		missile.setX(gunMC.getX() + gunMC.getRotateX() -20 + deltaX);
		missile.setY(gunMC.getY() + gunMC.getRotateY() - 7 + deltaY);
		missile.setRotate(this.getRotate());
		missile.setTeam(1);
		missile.setSpeed(20);
		context.addToScence(missile);
		
		deltaX = (float)(120 * Math.cos(HWMath.degree2rad(this.getRotate() + 10)));
		deltaY = -(float)(120 * Math.sin(HWMath.degree2rad(this.getRotate() + 10)));
		missile = context.getSpriteFromFactory(Missile.class);
		missile.setX(gunMC.getX() + gunMC.getRotateX() -20 + deltaX);
		missile.setY(gunMC.getY() + gunMC.getRotateY() - 7 + deltaY);
		missile.setRotate(this.getRotate());
		missile.setTeam(1);
		missile.setSpeed(20);
		context.addToScence(missile);
	
	}
	
	public void thump2() {
		Logger.tip("MainGun::技能2");
		
		this.setMP(this.getMP() - 10);
		
		context.playSound(SoundList.MAINGUN_SHOOT);
		context.playSound(SoundList.MAINGUN_SHOOT);
		context.playSound(SoundList.MAINGUN_SHOOT);
		context.playSound(SoundList.MAINGUN_SHOOT);
		context.playSound(SoundList.MAINGUN_SHOOT);
		context.playSound(SoundList.MAINGUN_SHOOT);
		
		
		
		float deltaX = (float)(120 * Math.cos(HWMath.degree2rad(this.getRotate())));
		float deltaY = -(float)(120 * Math.sin(HWMath.degree2rad(this.getRotate())));
		Missile missile = context.getSpriteFromFactory(Missile.class);
		missile.setX(gunMC.getX() + gunMC.getRotateX() -20 + deltaX);
		missile.setY(gunMC.getY() + gunMC.getRotateY() - 7 + deltaY);
		missile.setRotate(this.getRotate()-10);
		missile.setTeam(1);
		missile.setSpeed(20);
		context.addToScence(missile);
		
		deltaX = (float)(120 * Math.cos(HWMath.degree2rad(this.getRotate())));
		deltaY = -(float)(120 * Math.sin(HWMath.degree2rad(this.getRotate())));
		missile = context.getSpriteFromFactory(Missile.class);
		missile.setX(gunMC.getX() + gunMC.getRotateX() -20 + deltaX);
		missile.setY(gunMC.getY() + gunMC.getRotateY() - 7 + deltaY);
		missile.setRotate(this.getRotate()-15);
		missile.setTeam(1);
		missile.setSpeed(20);
		context.addToScence(missile);
		
		deltaX = (float)(120 * Math.cos(HWMath.degree2rad(this.getRotate())));
		deltaY = -(float)(120 * Math.sin(HWMath.degree2rad(this.getRotate())));
		missile = context.getSpriteFromFactory(Missile.class);
		missile.setX(gunMC.getX() + gunMC.getRotateX() -20 + deltaX);
		missile.setY(gunMC.getY() + gunMC.getRotateY() - 7 + deltaY);
		missile.setRotate(this.getRotate()-5);
		missile.setTeam(1);
		missile.setSpeed(20);
		context.addToScence(missile);
		
		deltaX = (float)(120 * Math.cos(HWMath.degree2rad(this.getRotate())));
		deltaY = -(float)(120 * Math.sin(HWMath.degree2rad(this.getRotate())));
		missile = context.getSpriteFromFactory(Missile.class);
		missile.setX(gunMC.getX() + gunMC.getRotateX() -20 + deltaX);
		missile.setY(gunMC.getY() + gunMC.getRotateY() - 7 + deltaY);
		missile.setRotate(this.getRotate());
		missile.setTeam(1);
		missile.setSpeed(20);
		context.addToScence(missile);
		
		deltaX = (float)(120 * Math.cos(HWMath.degree2rad(this.getRotate())));
		deltaY = -(float)(120 * Math.sin(HWMath.degree2rad(this.getRotate())));
		missile = context.getSpriteFromFactory(Missile.class);
		missile.setX(gunMC.getX() + gunMC.getRotateX() -20 + deltaX);
		missile.setY(gunMC.getY() + gunMC.getRotateY() - 7 + deltaY);
		missile.setRotate(this.getRotate() + 5);
		missile.setTeam(1);
		missile.setSpeed(20);
		context.addToScence(missile);
		
		deltaX = (float)(120 * Math.cos(HWMath.degree2rad(this.getRotate())));
		deltaY = -(float)(120 * Math.sin(HWMath.degree2rad(this.getRotate())));
		missile = context.getSpriteFromFactory(Missile.class);
		missile.setX(gunMC.getX() + gunMC.getRotateX() -20 + deltaX);
		missile.setY(gunMC.getY() + gunMC.getRotateY() - 7 + deltaY);
		missile.setRotate(this.getRotate() + 10);
		missile.setTeam(1);
		missile.setSpeed(20);
		
		deltaX = (float)(120 * Math.cos(HWMath.degree2rad(this.getRotate())));
		deltaY = -(float)(120 * Math.sin(HWMath.degree2rad(this.getRotate())));
		missile = context.getSpriteFromFactory(Missile.class);
		missile.setX(gunMC.getX() + gunMC.getRotateX() -20 + deltaX);
		missile.setY(gunMC.getY() + gunMC.getRotateY() - 7 + deltaY);
		missile.setRotate(this.getRotate() + 15);
		missile.setTeam(1);
		missile.setSpeed(20);
		context.addToScence(missile);
	
	}
	
	/** 技能3 */
	public void thump3() {
		float deltaX = 0; 
		float deltaY = 0;
		deltaX = (float)(120 * Math.cos(HWMath.degree2rad(this.getRotate())));
		deltaY = -(float)(120 * Math.sin(HWMath.degree2rad(this.getRotate())));
		Sk3Missile missile = context.getSpriteFromFactory(Sk3Missile.class);
		missile.setX(gunMC.getX() + gunMC.getRotateX() -20 + deltaX);
		missile.setY(gunMC.getY() + gunMC.getRotateY() - 7 + deltaY);
		missile.setRotate(this.getRotate() + 5);
		missile.setTeam(1);
		missile.setSpeed(20);
		context.addToScence(missile);
		
	}
	
	public void reset() {
		this.gunMC.reset();
		this.landMC.reset();
	}
	
	/* 碰撞体积 */
	@Override
	public float getBodyX() {
		return this.landMC.getX();
	}
	@Override
	public float getBodyY() {
		return this.landMC.getY() - this.getZ();
	}
	@Override
	public float getBodyWidth() {
		return this.landMC.getWidth();
	}
	@Override
	public float getBodyHeight() {
		return this.landMC.getHeight();
	}
	
	@Override
	public void effectHit(Sprite sprite) {
		Logger.tip("MainGun::aa被打");
	}
	@Override
	public boolean isSupportShadow() {
		return true;
	}




}
