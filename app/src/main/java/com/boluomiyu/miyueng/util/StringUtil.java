package com.boluomiyu.miyueng.util;

import java.util.Locale;

/**
 * 类 StringUtil
 * 描述：
 * $菠萝蜜语$ 荣誉出品  2013 版权所有.
 * @author 邹彦虎    2013-2-15
 * @version 1.0
 */
public class StringUtil {

	/** 首字母大写 */
	public static String firstToUpper(String s) {
		
		return s.substring(0, 1).toUpperCase(Locale.getDefault()) + s.substring(1, s.length());
		
	}
	
	/** 计算字符串宽度	默认： 半角10 px，全角20 px */
	public static int caculateStringWidth(String s) {
		int length = 0;
		int n=0;
		for(int i=0; i<s.length(); i++) {
			n = (int)s.charAt(i);
			if((19968 <= n && n <40623)) {
				length += 20;
			} else if (Character.isUpperCase(s.charAt(i))) {
				length += 20;
			} else {
				length += 10;
			}
		}
		return length;
	}
	
}
