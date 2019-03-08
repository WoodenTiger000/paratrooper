package com.boluomiyu.miyueng.event;

import com.boluomiyu.miyueng.view.GameView;
import com.boluomiyu.miyueng.view.Panel;
import com.boluomiyu.miyueng.view.ViewManager;

/**
 * 类 ShowMenuEventHandler
 * 描述：菜单显示事件
 * 公司 2013 版权所有.
 * @author 邹彦虎    2013-2-11
 * @version 1.0
 */
public class GameViewHideEventHandler implements EventHandler{

	private Class<? extends GameView> menuClass;

	public GameViewHideEventHandler(Class<GameView> menuClass) {
		this.menuClass = menuClass;
	}
	
	@Override
	public void execute() {
		if (this.menuClass != null) {
			ViewManager viewManager = ViewManager.getInstance();
			GameView menu = (Panel) viewManager.getGameViewMap().get(menuClass.toString());
			if (menu != null) {
				menu.hide();
				viewManager.getGameViewOrderVector().remove(menu);
			}
			// 释放事件
			this.menuClass = null;
		}
	}
	
	public Class<? extends GameView> getMenuClass() {
		return menuClass;
	}

	public void setMenuClass(Class<? extends GameView> menuClass) {
		this.menuClass = menuClass;
	}

}
