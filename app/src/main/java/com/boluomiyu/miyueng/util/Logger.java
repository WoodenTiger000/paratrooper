package com.boluomiyu.miyueng.util;

import android.util.Log;
/**
 * 名称: Logger
 * 职责：日志
 * $菠萝蜜语$ 荣誉出品  2013 版权所有.
 * @author 邹彦虎    2013-6-30
 * @version 1.0
 */
public class Logger {

	private String clazz;
	
	private static boolean loggerOpen = false;
	
	public static Logger getLogger(Class<?> clazz){
		return new Logger(clazz);
	}
	
	private Logger(Class<?> clazz) {
		this.clazz = clazz.getSimpleName();
	}
	
	public void info(String msg){
		if (loggerOpen) {
			Log.i(clazz, msg);
		}
	}
	public void error(String msg){
		if (loggerOpen) {
			Log.e(clazz, msg);
		}
	}
	
	public static void tip(Object msg){
		if (loggerOpen) {
			Log.i("调试提示：", msg.toString());
		}
	}

	public void warn(String msg) {
		if (loggerOpen) {
			Log.w(clazz, msg);
		}
	}
	
}
