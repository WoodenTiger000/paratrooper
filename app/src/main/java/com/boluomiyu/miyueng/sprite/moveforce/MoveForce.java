package com.boluomiyu.miyueng.sprite.moveforce;

import com.boluomiyu.miyueng.sprite.Sprite;

/**
 * 名称: SpriteMoveMode
 * 职责：精灵的移动力，一个精灵可以聚合多个移动力
 * 		当驱动力子类仅能拥有一个构造函数
 * $菠萝蜜语$ 荣誉出品  2013 版权所有.
 * @author 邹彦虎    2013-6-25
 * @version 1.0
 */
public abstract class MoveForce {

	public static final int TIME_REMAINING_FOREVER = Integer.MIN_VALUE;
	/** 当timeRemaining小于0时，且不为Integer.MIN_VALUE时会被清理 */
	protected int timeRemaining = 0;
	/** 对象池管理时使用 */
	protected boolean inPool = false;
	/** 作用对象精灵 */
	protected Sprite effectSprite;
	
	/** 充值驱动力参数，返回工厂时会用到 */
	public abstract void reset();
	/** 执行驱动力 */
	public abstract void execute () ;
	
	/** 返回最大负整数则视为永久，返回正整数，则每次按实际运行频率递减 */
	public int getTimeRemaining() {
		return this.timeRemaining;
	}
	
	/** 是否在对象池中 */
	public boolean isInPool() {
		return this.inPool;
	}
	
	/** 回归到池中 */
	public void setInPool(boolean inPool) {
		this.inPool = inPool;
	}

	public void setEffectSprite(final Sprite effectSprite) {
		this.effectSprite = effectSprite;
	}
	
	
	
	
}
