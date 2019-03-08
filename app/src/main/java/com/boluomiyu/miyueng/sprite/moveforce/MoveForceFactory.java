package com.boluomiyu.miyueng.sprite.moveforce;

import com.boluomiyu.miyueng.collection.MaxIndexList;
import com.boluomiyu.miyueng.sprite.Sprite;
import com.boluomiyu.miyueng.util.Logger;

/**
 * 名称: MoveForceFactory
 * 职责：驱动力对象工厂
 * $菠萝蜜语$ 荣誉出品  2013 版权所有.
 * @author 邹彦虎    2013-6-29
 * @version 1.0
 */
public class MoveForceFactory {

	private Logger logger = Logger.getLogger(MoveForceFactory.class);
	
	private MaxIndexList<MoveForce> mfList;
	
	public MoveForceFactory() {
		mfList = new MaxIndexList<MoveForce>(50);
	}
	
	/** 获取一个驱动力*/
	public MoveForce getMoveForce(Class<? extends MoveForce> moveForceClass, Sprite targetSprite) {
		
		MoveForce moveForce = null;
		
		// 1. 从池中获取
		for (int i=0; i<mfList.getMaxCount(); i++) {
			MoveForce mf = mfList.get(i);
			if (mf != null && mf.isInPool() && mf.getClass().equals(moveForceClass)) {
				moveForce = mf;
				moveForce.setEffectSprite(targetSprite);
				moveForce.setInPool(false);
			}
		}
		
		// 2. 池中没有创建一个新的，加入池中
		if (moveForce == null) {
			try {
				moveForce = moveForceClass.newInstance();
				moveForce.setEffectSprite(targetSprite);
				moveForce.setInPool(false);
				this.mfList.add(moveForce);
			} catch (IllegalAccessException e) {
				logger.error("驱动力的子类，仅允许有一个无参数构造函数，如果需要初始化参数，则指定init函数");
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			}
		}
		
		// 3. 重置驱动力
		moveForce.reset();
		
		
		return moveForce;
		
	}

	/** 返还一个驱动力到池中 */
	public void returnMoveForce(MoveForce moveForce) {
		moveForce.setEffectSprite(null);
		moveForce.setInPool(true);
	}

	
	
	

}
