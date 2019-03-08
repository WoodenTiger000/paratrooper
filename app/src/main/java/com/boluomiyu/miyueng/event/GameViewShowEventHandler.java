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
public class GameViewShowEventHandler implements EventHandler{

	private Class<? extends GameView> menuClass;

	public GameViewShowEventHandler(Class<GameView> menuClass) {
		this.menuClass = menuClass;
	}
	
	@Override
	public void execute() {
		if (this.menuClass != null) {
			ViewManager viewManager = ViewManager.getInstance();
			try {
				GameView menu = (Panel) viewManager.getGameViewMap().get(menuClass.toString());
				if (menu == null) {
					menu = menuClass.newInstance();
					viewManager.getGameViewMap().put(menuClass.toString(), menu);
				} else {
					viewManager.getGameViewOrderVector().remove(menu);
				}
				
				int size = viewManager.getGameViewOrderVector().size();
				if (size <= 0) {
					size = 1;
				}
				viewManager.getGameViewOrderVector().add(menu);
				menu.reset();
				menu.show();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
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
