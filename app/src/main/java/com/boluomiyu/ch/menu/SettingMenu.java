package com.boluomiyu.ch.menu;

import com.boluomiyu.miyueng.view.Panel;
import com.boluomiyu.miyueng.view.TextView;
/**
 * 类 SettingMenu
 * 描述：设置菜单
 * 公司 2013 版权所有.
 * @author 邹彦虎    2013-2-11
 * @version 1.0
 */
public class SettingMenu extends Panel {

	public SettingMenu() {
		
		this.setNormalBG("images/ui/menu_bg_tran.png");
		this.x = 390;
		this.y = 72;
		this.width = 427;
		this.height = 296;
		
		TextView text = new TextView("游戏设置");
		text.width = 100;
		text.height = 50;

		this.addView(text, 20, 30);
		
		
	}
	
}
