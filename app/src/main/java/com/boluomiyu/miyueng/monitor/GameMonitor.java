package com.boluomiyu.miyueng.monitor;
/**
 * 类 GameMonitor
 * 描述：
 * $菠萝蜜语$ 荣誉出品  2013 版权所有.
 * @author 邹彦虎    2013-6-16
 * @version 1.0
 */
public interface GameMonitor {

	public void execute();
	
	public void reset();

	public void stop();

	public void start();
	
	/**
	 * 运行状态true
	 * 停止状态false
	 * @return
	 */
	public boolean getRunStatus();
	
}
