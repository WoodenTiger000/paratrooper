package com.boluomiyu.miyueng.sprite.moveforce;


/**
 * 名称: GravityForce
 * 职责：重力
 * $菠萝蜜语$ 荣誉出品  2013 版权所有.
 * @author 邹彦虎    2013-6-29
 * @version 1.0
 */
public class GravityMoveForce extends MoveForce {

	/** 重力加速度 */
	private float fallingSpeed = 0;
	
	/** 最小下落速度 */
	private float minFallingSpeed = 2;
	
	/** 最大下落速度 */
	private float maxFailingSpeed = 8;
	
	public GravityMoveForce() {
		this.timeRemaining =  MoveForce.TIME_REMAINING_FOREVER;
	}
	
	@Override
	public void execute() {
		if (this.effectSprite != null) {
			if (this.effectSprite.getZ() > 0) {
				fallingSpeed += 1;
				if (fallingSpeed > this.maxFailingSpeed) {
					this.fallingSpeed = this.maxFailingSpeed;
				}
				float resultZ = this.effectSprite.getZ() - fallingSpeed;
				if (resultZ < 0) {
					resultZ = 0;
					fallingSpeed = minFallingSpeed;
				}
				this.effectSprite.setZ(resultZ);
			}
		}
		
	}

	public void setFallingSpeed(float fallingSpeed) {
		this.fallingSpeed = fallingSpeed;
	}

	@Override
	public void reset() {}


}
