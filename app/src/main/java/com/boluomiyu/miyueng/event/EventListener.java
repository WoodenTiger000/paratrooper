package com.boluomiyu.miyueng.event;

import android.view.MotionEvent;

/**
 * 类 EventListener
 * 描述：事件监听器
 * $菠萝蜜语$ 荣誉出品  2013 版权所有.
 * @author 邹彦虎    2013-6-16
 * @version 1.0
 */
public interface EventListener {

	public void click(MotionEvent e);
	
	public void pressed(MotionEvent e);
	
	public void move(MotionEvent e);
	
}
