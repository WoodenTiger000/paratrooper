package com.boluomiyu.miyueng.event;

import com.boluomiyu.miyueng.GameContext;
import com.boluomiyu.miyueng.sprite.Sprite;

/**
 * 类 CreateEvent
 * 描述：
 * 菠萝秘密 2013 版权所有.
 * @author 邹彦虎    2013-2-3
 * @version 1.0
 */
public class SpriteCreateEventHandler implements EventHandler {
	
	private GameContext context = GameContext.context;
	
	private Sprite sprite;
	
	public SpriteCreateEventHandler(Sprite sprite) {
		this.sprite = sprite;
	}
	
	@Override
	public void execute() {
		if (sprite != null) {
			context.addToScence(sprite);
		}
	}
	
	
	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}

	public Sprite getSprite() {
		return sprite;
	}
	
	

}
