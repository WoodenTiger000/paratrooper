package com.boluomiyu.ch.menu;

import android.view.MotionEvent;

import com.boluomiyu.ch.GameClient;
import com.boluomiyu.miyueng.GameContext;
import com.boluomiyu.miyueng.event.EventAdapter;
import com.boluomiyu.miyueng.view.Panel;
import com.boluomiyu.miyueng.view.TextButton;
/**
 * 名称: MainMenu
 * 职责：主菜单
 * $菠萝蜜语$ 荣誉出品  2013 版权所有.
 * @author 邹彦虎    2013-2-11
 * @version 1.0
 */
public class MainMenu extends Panel {

	private GameContext context;
	
	public MainMenu() {
		
		this.context = GameContext.context;
		this.setNormalBG("images/ui/mainmenu_bg.jpg");
		
		TextButton continueBtn = new TextButton();
		continueBtn.text = "继续游戏";
		continueBtn.height = 42;
		continueBtn.width = 291;
		continueBtn.setNormalBG("images/ui/mainmenu_btn.png");
		continueBtn.setPressedBG("images/ui/mainmenu_press_btn.png");
		this.addView(continueBtn, 34, 71);
		
		TextButton newGameBtn = new TextButton();
		newGameBtn.text = "新游戏";
		newGameBtn.height = 42;
		newGameBtn.width = 291;
		newGameBtn.setNormalBG("images/ui/mainmenu_btn.png");
		newGameBtn.setPressedBG("images/ui/mainmenu_press_btn.png");
		this.addView(newGameBtn, 34, 129);
		
		TextButton setBtn = new TextButton();
		setBtn.text = "游戏设置";
		setBtn.height = 42;
		setBtn.width = 291;
		setBtn.setNormalBG("images/ui/mainmenu_btn.png");
		setBtn.setPressedBG("images/ui/mainmenu_press_btn.png");
		this.addView(setBtn, 34, 187);
		
		TextButton aboutBtn = new TextButton();
		aboutBtn.text = "关于游戏";
		aboutBtn.height = 42;
		aboutBtn.width = 291;
		aboutBtn.setNormalBG("images/ui/mainmenu_btn.png");
		aboutBtn.setPressedBG("images/ui/mainmenu_press_btn.png");
		this.addView(aboutBtn, 34, 245);
		
		TextButton personnalBtn = new TextButton();
		personnalBtn.text = "玩家信息";
		personnalBtn.height = 42;
		personnalBtn.width = 291;
		personnalBtn.setNormalBG("images/ui/mainmenu_btn.png");
		personnalBtn.setPressedBG("images/ui/mainmenu_press_btn.png");
		this.addView(personnalBtn, 34, 303);		
		
		TextButton exitBtn = new TextButton();
		exitBtn.text = "退出游戏";
		exitBtn.height = 42;
		exitBtn.width = 291;
		exitBtn.setNormalBG("images/ui/mainmenu_btn.png");
		exitBtn.setPressedBG("images/ui/mainmenu_press_btn.png");
		this.addView(exitBtn, 34, 361);		
		
		continueBtn.addEventListener(new EventAdapter() {
			@Override
			public void click(MotionEvent e) {
				//GameClient.hideAllMenu();
				//PlayBookManager.getInstance().load("stage1_1.stg");
				
				//GameClient.context.showMenu(ConfirmMenu.class);
			}
		});
		
		newGameBtn.addEventListener(new EventAdapter() {
			@Override
			public void click(MotionEvent e) {
				
				context.startRace();
				context.hideAllMenu();
				context.showMenu(BattleMenu.class);
				context.loadPlayBook("playbooks/stage_demo1.stg");
			}
		});
		
		setBtn.addEventListener(new EventAdapter() {
			@Override
			public void click(MotionEvent e) {
				context.hideMenu(PersonnalMenu.class);
				context.hideMenu(AboutMenu.class);
				context.showMenu(SettingMenu.class);
			}
		});
		aboutBtn.addEventListener(new EventAdapter() {
			@Override
			public void click(MotionEvent e) {
				GameClient.context.hideMenu(PersonnalMenu.class);
				GameClient.context.hideMenu(SettingMenu.class);
				GameClient.context.showMenu(AboutMenu.class);
			}
		});
		personnalBtn.addEventListener(new EventAdapter() {
			@Override
			public void click(MotionEvent e) {
				GameClient.context.showMenu(PersonnalMenu.class);
				GameClient.context.hideMenu(SettingMenu.class);
				GameClient.context.hideMenu(AboutMenu.class);
			}
		});
		
		exitBtn.addEventListener(new EventAdapter() {
			@Override
			public void click(MotionEvent e) {
				context.exit();
			}
		});
		
	}
	
	@Override
	public void show() {
		super.show();
		GameClient.context.showMenu(PersonnalMenu.class);
	}
	
	
}
