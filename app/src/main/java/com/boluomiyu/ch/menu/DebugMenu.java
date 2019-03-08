package com.boluomiyu.ch.menu;

import android.view.MotionEvent;

import com.boluomiyu.miyueng.GameContext;
import com.boluomiyu.miyueng.event.EventAdapter;
import com.boluomiyu.miyueng.resource.SoundList;
import com.boluomiyu.miyueng.resource.SoundManager;
import com.boluomiyu.miyueng.view.Panel;
import com.boluomiyu.miyueng.view.TextButton;

/**
 * 类 PauseMenu
 * 描述：
 * $菠萝蜜语$ 荣誉出品  2013 版权所有.
 * @author 邹彦虎    2013-6-16
 * @version 1.0
 */
public class DebugMenu extends Panel {

	private GameContext context;
	
	public DebugMenu() {
		
		context = GameContext.context;
		
		this.width = 400;
		this.height = 350;
		this.setNormalBG("images/ui/menu_bg.png");
		this.moveToCenter();

		TextButton shadowBtn = new TextButton();
		shadowBtn.text = "关闭阴影";
		shadowBtn.setNormalBG("images/ui/btn_bg.png");
		shadowBtn.setPressedBG("images/ui/btn_press_bg.png");
		this.addViewToCenter(shadowBtn);
		shadowBtn.addEventListener(new EventAdapter(){
			@Override
			public void click(MotionEvent e) {
				
				
			}
		});
		
		

	}
}
