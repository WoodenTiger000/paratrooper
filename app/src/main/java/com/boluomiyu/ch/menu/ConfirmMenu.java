package com.boluomiyu.ch.menu;

import android.view.MotionEvent;

import com.boluomiyu.ch.GameClient;
import com.boluomiyu.miyueng.event.EventAdapter;
import com.boluomiyu.miyueng.view.Panel;
import com.boluomiyu.miyueng.view.TextButton;
/**
 * 类 SettingMenu
 * 描述：设置菜单
 * 公司 2013 版权所有.
 * @author 邹彦虎    2013-2-11
 * @version 1.0
 */
public class ConfirmMenu extends Panel {

	public ConfirmMenu() {
		
		this.isMode = true;
		this.setNormalBG("images/ui/menu_bg_tran.png");
		this.width = 500;
		this.height = 296;
		this.moveToCenter();
		
		this.setTitle("选择存档位置");
		
		TextButton save1Btn = new TextButton();
		save1Btn.setNormalBG("images/ui/btn_bg.png");
		save1Btn.setPressedBG("images/ui/btn_press_bg.png");
		save1Btn.text = "存档一";
		this.addView(save1Btn, HORIZONTAL_CENTER, 50);
		
		TextButton save2Btn = new TextButton();
		save2Btn.setNormalBG("images/ui/btn_bg.png");
		save2Btn.setPressedBG("images/ui/btn_press_bg.png");
		save2Btn.text = "新存档";
		this.addView(save2Btn, HORIZONTAL_CENTER, 105);
		
		TextButton cancelBtn = new TextButton();
		cancelBtn.setNormalBG("images/ui/btn_bg.png");
		cancelBtn.setPressedBG("images/ui/btn_press_bg.png");
		cancelBtn.text = "取消";
		this.addView(cancelBtn, HORIZONTAL_CENTER, 160);
		
		cancelBtn.addEventListener(new EventAdapter(){
			@Override
			public void click(MotionEvent e) {
				GameClient.context.hideMenu(ConfirmMenu.class);
			}
		});
		
		
		
		
		
	}
	
	
	
}
