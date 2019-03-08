package com.boluomiyu.ch.menu;

import android.view.MotionEvent;

import com.boluomiyu.ch.GameClient;
import com.boluomiyu.miyueng.GameContext;
import com.boluomiyu.miyueng.Scence;
import com.boluomiyu.miyueng.event.EventAdapter;
import com.boluomiyu.miyueng.resource.SoundList;
import com.boluomiyu.miyueng.resource.SoundManager;
import com.boluomiyu.miyueng.view.Panel;
import com.boluomiyu.miyueng.view.TextButton;
import com.boluomiyu.miyueng.view.TextView;

/**
 * 类 BattleSuccessMenu
 * 描述：战斗胜利
 * $菠萝蜜语$ 荣誉出品  2013 版权所有.
 * @author 邹彦虎    2013-6-16
 * @version 1.0
 */
public class BattleSuccessMenu extends Panel {

	public BattleSuccessMenu() {
		this.width = 800;
		this.height = 480;
		this.setNormalBG("images/ui/menu_bg.png");

		TextView textView = new TextView("恭喜！消灭了所有的Moster");
		this.addViewToCenter(textView);
		
		TextButton mainMenuBtn = new TextButton();
		mainMenuBtn.text = "回主菜单";
		mainMenuBtn.setNormalBG("images/ui/btn_bg.png");
		mainMenuBtn.setPressedBG("images/ui/btn_press_bg.png");
		this.addViewToCenter(mainMenuBtn);

		// 回主菜单
		mainMenuBtn.addEventListener(new EventAdapter() {
			@Override
			public void click(MotionEvent e) {
				Scence.getInstance().clear();
				BattleSuccessMenu.this.hide();
				GameClient.context.showMenu(MainMenu.class);
				//GameContext.gameRun = false;
			}

			@Override
			public void pressed(MotionEvent e) {
				SoundManager.getInstance().playSound(SoundList.BTN_PRESSED);
			}
		});

	}
}
