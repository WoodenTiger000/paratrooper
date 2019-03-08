package com.boluomiyu.miyueng.sprite.moveforce;
/**
 * 名称: ZKeepForce
 * 职责：高度保持驱动力
 * $菠萝蜜语$ 荣誉出品  2013 版权所有.
 * @author 邹彦虎    2013-6-30
 * @version 1.0
 */
public class ZKeepForce extends MoveForce {

	/** 保持的z高度 */
	private float keepZ = 0;
	
	/** 回落或升起时的速度 */
	private float speed = 1;
	
	@Override
	public int getTimeRemaining() {
		return MoveForce.TIME_REMAINING_FOREVER;
	}
	
	@Override
	public void reset() {}

	@Override
	public void execute() {
		
		if (Math.abs(this.effectSprite.getZ() - keepZ) < speed) {
			return;
		}
		
		if (this.effectSprite.getZ() > keepZ) {
			float deltaZ = this.effectSprite.getZ() - speed;
			if (deltaZ < speed) {
				this.effectSprite.setZ(keepZ); 
			} else {
				this.effectSprite.setZ(deltaZ);
			}
		} else if(this.effectSprite.getZ() < keepZ) {
			float deltaZ = this.effectSprite.getZ() + speed;
			if (deltaZ < speed) {
				this.effectSprite.setZ(keepZ); 
			} else {
				this.effectSprite.setZ(deltaZ);
			}
		}
		
	}

	public void setKeepZ(float keepZ) {
		this.keepZ = keepZ;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	
	
	

}
