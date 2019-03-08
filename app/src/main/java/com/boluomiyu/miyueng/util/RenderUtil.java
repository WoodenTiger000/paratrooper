package com.boluomiyu.miyueng.util;

import android.graphics.Rect;
import android.graphics.RectF;

public class RenderUtil {
	
	/** 辅助rect，防止重新new，节约内存 */
	private static Rect rect = new Rect();
	private static Rect rect2 = new Rect();
	
	private static RectF rectF = new RectF();
	
	public static Rect getRect(int left, int top, int right, int bottom){
		rect.set(left, top, right, bottom);
		return rect;
	}
	public static Rect getRect2(int left, int top, int right, int bottom){
		rect2.set(left, top, right, bottom);
		return rect2;
	}
	public static RectF getRectF(int left, int top, int right, int bottom){
		rectF.set(left, top, right, bottom);
		return rectF;
	}
	
}
