package com.boluomiyu.ch;

import android.os.Bundle;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.boluomiyu.ch.menu.BattleMenu;
import com.boluomiyu.ch.menu.PauseMenu;
import com.boluomiyu.ch.menu.StartupMenu;
import com.boluomiyu.miyueng.GameContext;
import com.boluomiyu.miyueng.ch.R;

/**
 * 类 GameClient
 * 描述：游戏客户端
 * $菠萝蜜语$ 荣誉出品  2013 版权所有.
 * @author 邹彦虎    2013-2-16
 * @version 1.0
 */
public class GameClient extends GameContext {
	
	/** 游戏是否已经初始化 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);  
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
   
		setContentView(R.layout.main);
        ViewGroup layout = (ViewGroup) this.findViewById(R.id.rootLayout);
        layout.addView(mainCanvas);  //重要步骤1：将主画布加入布局
        
        this.init();
        
	}
	
	/** 初始化 */
	public void init(){
		// 启动菜单
		//this.showMenu(StartupMenu.class);
		
		// 添加胜负决策监视器
		BattleMonitor battleMonitor = new BattleMonitor(1);
		this.addGameMonitor(battleMonitor);
		
		// 测试战场
		this.hideAllMenu();
		this.showMenu(BattleMenu.class);
		this.loadPlayBook("playbooks/stage_dev.stg");
		
	}
	
	@Override
	public void finish() {
		if (context.getRaceStatus() == true) {
			context.showMenu(PauseMenu.class);
			context.stopRace();
		}
	}




}