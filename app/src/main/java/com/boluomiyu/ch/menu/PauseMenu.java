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
public class PauseMenu extends Panel {

	private GameContext context;
	
	public PauseMenu() {
		
		context = GameContext.context;
		
		this.width = 400;
		this.height = 350;
		this.setNormalBG("images/ui/menu_bg.png");
		this.moveToCenter();

		TextButton startBtn = new TextButton();
		startBtn.text = "继续游戏";
		startBtn.setNormalBG("images/ui/btn_bg.png");
		startBtn.setPressedBG("images/ui/btn_press_bg.png");
		this.addViewToCenter(startBtn);

		TextButton saveBtn = new TextButton();
		saveBtn.text = "保存游戏";
		saveBtn.setNormalBG("images/ui/btn_bg.png");
		saveBtn.setPressedBG("images/ui/btn_press_bg.png");
		this.addViewToCenter(saveBtn);
		
		TextButton setBtn = new TextButton();
		setBtn.text = "游戏设置";
		setBtn.setNormalBG("images/ui/btn_bg.png");
		setBtn.setPressedBG("images/ui/btn_press_bg.png");
		this.addViewToCenter(setBtn);
		
		TextButton mainMenuBtn = new TextButton();
		mainMenuBtn.text = "回主菜单";
		mainMenuBtn.setNormalBG("images/ui/btn_bg.png");
		mainMenuBtn.setPressedBG("images/ui/btn_press_bg.png");
		this.addViewToCenter(mainMenuBtn);
		
		// 开始游戏
		startBtn.addEventListener(new EventAdapter() {
			@Override
			public void click(MotionEvent e) {
				context.startRace();
				context.hideMenu(PauseMenu.class);
			}
		});
		
		// 游戏存档
		saveBtn.addEventListener(new EventAdapter() {
			@Override
			public void click(MotionEvent e) {
				context.makeText("游戏保存成功");
			}

			@Override
			public void pressed(MotionEvent e) {
				context.playSound(SoundList.BTN_PRESSED);
			}
		});
		
		// 回主菜单
		mainMenuBtn.addEventListener(new EventAdapter() {
			@Override
			public void click(MotionEvent e) {
				context.clearScence();
				PauseMenu.this.hide();
				context.showMenu(MainMenu.class);
				context.stopRace();
			}

			@Override
			public void pressed(MotionEvent e) {
				SoundManager.getInstance().playSound(SoundList.BTN_PRESSED);
			}
		});

	}
}
