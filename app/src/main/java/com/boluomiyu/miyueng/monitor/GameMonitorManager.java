package com.boluomiyu.miyueng.monitor;

import java.util.ArrayList;
import java.util.List;

import com.boluomiyu.miyueng.GameContext;

/**
 * 类 MonitorManager
 * 描述：
 * $菠萝蜜语$ 荣誉出品  2013 版权所有.
 * @author 邹彦虎    2013-6-16
 * @version 1.0
 */
public class GameMonitorManager {

	private GameContext context;
	
	public List<GameMonitor> gameMonitorList;
	
	public GameMonitorManager() {
		context = GameContext.context;
		this.gameMonitorList = new ArrayList<GameMonitor>();
	}
	
	public void execute() {
		for (GameMonitor gameMonitor : gameMonitorList) {
			// 竞赛正在进行，且监控器为运行状态，则执行监视器
			if (context.getRaceStatus() == true && gameMonitor.getRunStatus() == true) {
				gameMonitor.execute();
			}
		}
	}

	public synchronized void addGameMonitor(GameMonitor gameMonitor) {
		this.gameMonitorList.add(gameMonitor);
	}

	/** 清理所有的监视器 */
	public synchronized void clearAll() {
		this.gameMonitorList.clear();
	}
	
	/** 重设所有的监视器 */
	public synchronized void resetAll() {
		for (GameMonitor monitor : this.gameMonitorList) {
			monitor.reset();
		}
	}
	
	/** 停止所有的监视器 */
	public synchronized void stopAll() {
		for (GameMonitor monitor : this.gameMonitorList) {
			monitor.stop();
		}
	}

	public synchronized void startAll() {
		for (GameMonitor monitor : this.gameMonitorList) {
			monitor.start();
		}
	}
	
	

}
