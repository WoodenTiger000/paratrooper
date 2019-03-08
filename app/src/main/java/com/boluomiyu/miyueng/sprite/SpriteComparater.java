package com.boluomiyu.miyueng.sprite;

import java.util.Comparator;
/**
 * 类 SpriteComparater
 * 描述：
 * $菠萝蜜语$ 荣誉出品  2013 版权所有.
 * @author 邹彦虎    2013-2-17
 * @version 1.0
 */
public class SpriteComparater implements Comparator<Sprite>{

	private static SpriteComparater comparater;
	/**
	 * 获取实例
	 * @return
	 */
	public static SpriteComparater getInstance() {
		if (comparater == null) {
			comparater = new SpriteComparater();
		}
		return comparater;
	}
	
	@Override
	public int compare(Sprite lhs, Sprite rhs) {
		
		if (lhs == null || rhs == null) {
			return 0;
		}
		
		float lGravity = lhs.getY() + lhs.getBodyWidth();
		float rGravity = rhs.getY() + rhs.getBodyWidth();
		
		if (lGravity > rGravity) {
			return 1;
		} else if (rGravity == rGravity) {
			return 0;
		} else {
			return -1;
		}
	}

}
