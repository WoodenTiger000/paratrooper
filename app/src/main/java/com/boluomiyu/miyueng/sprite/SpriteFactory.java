package com.boluomiyu.miyueng.sprite;

import java.util.ArrayList;
import java.util.List;

import com.boluomiyu.miyueng.util.Logger;

/**
 * 类 SpriteFactory2
 * 描述：精灵梦工厂2.0（运行时对象池）
 * $菠萝蜜语$ 荣誉出品  2013 版权所有.
 * @author 邹彦虎    2013-2-14
 * @version 1.0
 */
public class SpriteFactory {
	
	private Logger logger = Logger.getLogger(SpriteFactory.class);

	private static SpriteFactory spriteFactory;
	
	private List<Sprite> spritePool;
	
	public static SpriteFactory getInstance() {
		if(spriteFactory == null){
			spriteFactory = new SpriteFactory();
		}
		return spriteFactory;
	}
	
	public SpriteFactory() {
		this.spritePool = new ArrayList<Sprite>();
	}
	
	/** 批量生产Sprite */
	public void createSprite(Class<? extends Sprite> clazz, int initCount) {
		try {
			for (int i=0; i<initCount; i++) {
				Sprite sprite = clazz.newInstance();
				sprite.setInPool(true);
				this.spritePool.add(sprite);
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 从池中拿出一个精灵，如果没有创建一个新的精灵
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T extends Sprite> T getSprite(Class<? extends Sprite> clazz) {
	
		int size = spritePool.size();
		Sprite sprite = null;
		boolean poolEnough = false;
		for(int i=0; i<size; i++) {
			sprite = spritePool.get(i);
			if(sprite.getClass().equals(clazz)) {
				if (sprite.isInPool()) {
					sprite.prepareSprite();
					poolEnough = true;
					break;
				}
			}
		}
		
		// 池中不足，放一个鱼苗进去
		if (!poolEnough) {
			try {
				logger.warn("池中实例不足，扩展一个：" + clazz.toString());
				sprite = clazz.newInstance();
				sprite.prepareSprite();
				spritePool.add(sprite);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			}
		}
		
		return (T) sprite;
	}
	
	/** 有借有还，再借不难 */
	public void returnSprite(Sprite sprite) {
		sprite.reset();
		sprite.setInPool(true);
	}
	
	/** 重设 */
	public void reset() {
		this.spritePool.clear();
	}
	
	public int getSpriteCount() {
		return this.spritePool.size();
	}
	
}
