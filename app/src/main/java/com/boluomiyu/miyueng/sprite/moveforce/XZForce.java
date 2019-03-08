package com.boluomiyu.miyueng.sprite.moveforce;

import com.boluomiyu.miyueng.GameContext;
import com.boluomiyu.miyueng.util.HWMath;

/**
 * 名称: XZShortForce
 * 职责：XZ方向力
 * $菠萝蜜语$ 荣誉出品  2013 版权所有.
 * @author 邹彦虎    2013-6-29
 * @version 1.0
 */
public class XZForce extends MoveForce{

	private GameContext context;
	/** 驱动力角度 */
	private float angle = 90;
	
	/** 驱动力速度--简单化为速度 */
	private float speed = 10;
	
	/** 默认向上，0.5秒，10速度，90角度 */
	@Override
	public void reset() {
		this.context = GameContext.context;
		this.timeRemaining = 100;
		this.speed = 10;
		this.angle = 70;
	}
	
	public void execute() {
		
		if (effectSprite != null) {
			
			this.timeRemaining -= context.minFreq;
			
			float spriteX = (float)(effectSprite.getX() + speed * Math.cos(HWMath.degree2rad(angle)));
			float spriteZ = (float)(effectSprite.getZ() + speed * Math.sin(HWMath.degree2rad(angle)));
			if (spriteZ < 0) {
				spriteZ = 0;
			}
			
			effectSprite.setX(spriteX);
			effectSprite.setZ(spriteZ);
		}
	}

	public void setAngle(float angle) {
		this.angle = angle;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}
	

	
	 



}
