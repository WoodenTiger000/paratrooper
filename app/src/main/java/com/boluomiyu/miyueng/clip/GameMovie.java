package com.boluomiyu.miyueng.clip;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * 类 GameMovie
 * 描述：
 * 菠萝秘密 2013 版权所有.
 * @author 邹彦虎    2013-1-21
 * @version 1.0
 */
@Deprecated
public interface GameMovie {
	
	public static final int DIRECTION_FORWARD = 1;
	public static final int DIRECTION_BACKWARD = 2;
	
	public void execute();
	
	public void render(Canvas canvas, Paint paint, float x, float y, int width, int height);
	
	public void render(Canvas canvas, Paint paint, float x, float y, float rotate);
	
	public void start();
	
	public void stop();
	
	
	
	
}
