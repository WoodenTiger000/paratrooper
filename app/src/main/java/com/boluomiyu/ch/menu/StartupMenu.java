package com.boluomiyu.ch.menu;

import com.boluomiyu.ch.GameClient;
import com.boluomiyu.miyueng.GameContext;
import com.boluomiyu.miyueng.view.Panel;
/**
 * 类 StartupMenu
 * 描述：启动界面
 * 公司 2013 版权所有.
 * @author 邹彦虎    2013-2-11
 * @version 1.0
 */
public class StartupMenu extends Panel{
	
	public StartupMenu() {
		
		final GameContext context = GameClient.context;
		
		this.setNormalBG("images/ui/startup.jpg");
		context.postDelay(new Runnable(){
			@Override
			public void run() {
				context.hideMenu(StartupMenu.class);
				context.showMenu(MainMenu.class);
			}
		}, 2000);
	}
}
