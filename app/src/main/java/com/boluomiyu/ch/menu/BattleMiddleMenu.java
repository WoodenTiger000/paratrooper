package com.boluomiyu.ch.menu;

import android.view.MotionEvent;

import com.boluomiyu.ch.GameClient;
import com.boluomiyu.miyueng.GameContext;
import com.boluomiyu.miyueng.event.EventAdapter;
import com.boluomiyu.miyueng.resource.SoundList;
import com.boluomiyu.miyueng.resource.SoundManager;
import com.boluomiyu.miyueng.view.ImageView;
import com.boluomiyu.miyueng.view.Panel;
import com.boluomiyu.miyueng.view.TextButton;

/**
 * 类 BattleSuccessMenu
 * 描述：战斗胜利
 * $菠萝蜜语$ 荣誉出品  2013 版权所有.
 * @author 邹彦虎    2013-6-16
 * @version 1.0
 */
public class BattleMiddleMenu extends Panel {

	private GameContext context;
	
	private int temp = 0;
	
	public BattleMiddleMenu() {
		
		this.context = GameContext.context;
		
		this.width = GameClient.context.width;
		this.height = GameClient.context.height;
		this.setNormalBG("images/ui/menu_bg.png");

		ImageView storeImg = new ImageView();
		storeImg.width = 263;
		storeImg.height = 188;
		storeImg.setNormalBG("images/ui/store/store.png");
		this.addView(storeImg, 300, 125);
		
		
		TextButton mainMenuBtn = new TextButton();
		mainMenuBtn.text = "主菜单";
		mainMenuBtn.setNormalBG("images/ui/btn_bg.png");
		mainMenuBtn.setPressedBG("images/ui/btn_press_bg.png");
		mainMenuBtn.width = 200;
		mainMenuBtn.height = 40;
		this.addView(mainMenuBtn, 50, 390);
		
		TextButton storeBtn = new TextButton();
		storeBtn.text = "武器库";
		storeBtn.setNormalBG("images/ui/btn_bg.png");
		storeBtn.setPressedBG("images/ui/btn_press_bg.png");
		storeBtn.width = 200;
		storeBtn.height = 40;
		this.addView(storeBtn, 322, 390);
		
		TextButton nextStageBtn = new TextButton();
		nextStageBtn.text = "继续下一关";
		nextStageBtn.setNormalBG("images/ui/btn_bg.png");
		nextStageBtn.setPressedBG("images/ui/btn_press_bg.png");
		nextStageBtn.width = 200;
		nextStageBtn.height = 40;
		this.addView(nextStageBtn, 604, 390);
		
		// 回主菜单
		mainMenuBtn.addEventListener(new EventAdapter() {
			@Override
			public void click(MotionEvent e) {
				context.clearScence();
				BattleMiddleMenu.this.hide();
				GameClient.context.showMenu(MainMenu.class);
				GameClient.context.stopRace();
			}

			@Override
			public void pressed(MotionEvent e) {
				SoundManager.getInstance().playSound(SoundList.BTN_PRESSED);
			}
		});
		
		// 武器库
		storeBtn.addEventListener(new EventAdapter() {
			@Override
			public void click(MotionEvent e) {
				context.makeText("尚未开放");
			}
		});
		
		// 继续下一关
		nextStageBtn.addEventListener(new EventAdapter() {
			@Override
			public void click(MotionEvent e) {
				
				context.hideAllMenu();
				context.clear();
				
				if (temp == 0) {
					context.loadPlayBook("playbooks/stage_demo2.stg");
					temp ++;
				} else {
					context.loadPlayBook("playbooks/stage_demo3.stg");
				}
				
				context.showMenu(BattleMenu.class);
				
			}
		});
		

	}
}
