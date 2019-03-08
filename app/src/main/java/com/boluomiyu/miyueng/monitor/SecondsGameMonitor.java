package com.boluomiyu.miyueng.monitor;

import com.boluomiyu.miyueng.GameContext;

/**
 * 名称: SecondsGameMonitor
 * 职责：秒式监控游戏监控器，主要用来做
 * $菠萝蜜语$ 荣誉出品  2013 版权所有.
 * @author 邹彦虎    2013-6-17
 * @version 1.0
 */
public abstract class SecondsGameMonitor implements GameMonitor {

	private GameContext context;
	
	private int currentMills = 0;
	
	/** 执行频率 ms */
	private int during = 0;
	
	/** 运行状态 */
	private boolean runStatus = false;
	
	public SecondsGameMonitor(int duringSeconds) {
		this.context = GameContext.context;
		this.during = duringSeconds * 1000;
		runStatus = true;
	}
	
	@Override
	public void execute() {
		currentMills += 50;
		if (currentMills >= during) {
			this.executeWithSeconds();
			currentMills = 0;
		}
	}
	
	public abstract void executeWithSeconds();
	
	@Override
	public void reset() {
		this.currentMills = 0;
	}
	
	@Override
	public void stop() {
		this.runStatus = false;
	}
	
	@Override
	public void start() {
		this.runStatus = true;
	}

	public boolean getRunStatus() {
		return runStatus;
	}
	
	
	

}
