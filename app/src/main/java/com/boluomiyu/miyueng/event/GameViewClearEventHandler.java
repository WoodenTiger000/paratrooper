package com.boluomiyu.miyueng.event;

import com.boluomiyu.miyueng.view.ViewManager;

/**
 * 名称: GameViewClearEventHandler
 * 职责：菜单显示事件[引擎系统事件]
 * $菠萝蜜语$ 荣誉出品  2013 版权所有.
 * @author 邹彦虎    2013-2-11
 * @version 1.0
 */
public class GameViewClearEventHandler implements EventHandler{

	private Object object;
	
	@Override
	public void execute() {
		if (object != null) {
			ViewManager.getInstance().getGameViewOrderVector().clear();
			object = null;
		}
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}
	
}
