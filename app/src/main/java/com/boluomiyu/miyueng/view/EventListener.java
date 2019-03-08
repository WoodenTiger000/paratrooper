package com.boluomiyu.miyueng.view;

import android.view.MotionEvent;

/**
 * 事件监听器
 *
 * @author 邹彦虎
 * 俱乐部  --- 
 */
public interface EventListener {

	public void click(MotionEvent e);
	
	public void pressed(MotionEvent e);
	
	public void move(MotionEvent e);
	
}
