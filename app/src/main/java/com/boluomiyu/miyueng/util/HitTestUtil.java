package com.boluomiyu.miyueng.util;

/**
 * 类 HitTestUtil
 * 描述：碰撞检测类
 * $菠萝蜜语$ 荣誉出品  2013 版权所有.
 * @author 邹彦虎    2013-6-16
 * @version 1.0
 */
public class HitTestUtil {


	public static boolean hitPoint(float x1, float y1, float x2, float y2, float delta) {
		
		if (Math.abs(x1 - x2) < delta && Math.abs(y1 - y2) < delta) {
			return true;
		} else {
			return false;
		}
		
	}

	public static boolean hitRect(float x1, float y1, float width1, float height1, float x2, float y2, float width2, float height2) {

		//x1,y1  x1,y1+height1,  x1+width1,y1  x1+width1,y1+height1	
		if (hitRect(x1, y1, x2, y2, width2, height2)
			|| hitRect(x1, y1+height1, x2, y2, width2, height2)
			|| hitRect(x1 + width1, y1, x2, y2, width2, height2)
			|| hitRect(x1 + width1, y1+height1, x2, y2, width2, height2)
		) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean hitRect(float x, float y, float x2, float y2, float width2, float height2) {
		if (x > x2 && x <x2+width2 && y>y2 && y<y2+height2) {
			return true;
		} else {
			return false;
		}
	}



}
