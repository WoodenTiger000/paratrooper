package com.boluomiyu.miyueng.event;

import android.view.MotionEvent;

import com.boluomiyu.miyueng.GameContext;
import com.boluomiyu.miyueng.collection.MaxIndexList;
import com.boluomiyu.miyueng.sprite.Sprite;

/**
 * 类 TouchEventHandler
 * 描述：
 * 菠萝秘密 2013 版权所有.
 * @author 邹彦虎    2013-2-3
 * @version 1.0
 */
public class TouchEventHandler implements EventHandler{

	private MotionEvent motionEvent;
	
	private GameContext context = GameContext.context;
	
	public TouchEventHandler(MotionEvent e) {

		this.motionEvent = e;
		
	}

	@Override
	public void execute() {
		if (this.motionEvent != null) {
			MaxIndexList<Sprite> spriteArray = context.getScenceSpriteArray();
			int size = spriteArray.getMaxCount();
			for (int i = 0; i<size; i++) {
				if (spriteArray.get(i) != null) {
					spriteArray.get(i).onTouchEvent(motionEvent);
				}
			}
		}
	}
	
	public MotionEvent getEvent() {
		return this.motionEvent;
	}
	
	public void setEvent(MotionEvent e) {
		this.motionEvent = e;
	}
	
	
	
}
