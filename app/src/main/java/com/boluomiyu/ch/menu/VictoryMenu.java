package com.boluomiyu.ch.menu;
import com.boluomiyu.miyueng.GameContext;
import com.boluomiyu.miyueng.view.Panel;
/**
 * 名称: VictoryMenu
 * 职责：胜利界面
 * $菠萝蜜语$ 荣誉出品  2013 版权所有.
 * @author 邹彦虎    2013-6-17
 * @version 1.0
 */
public class VictoryMenu extends Panel {
	
	private GameContext context = GameContext.context;
	
	public VictoryMenu() {
		
		this.context = GameContext.context;
		
		this.setNormalBG("images/ui/victory.png");
		this.x = 256;
		this.y = 65;
		this.width = 357;
		this.height = 307;
		
	}
	
	@Override
	public void show() {
		super.show();
		// 竞赛结束
		context.stopRace();
		context.resetAndStartAllGameMonitor();
		
		context.postDelay(new Runnable() {
			@Override
			public void run() {
				
				// 游戏清场
				context.clear();
				
				// 显示调整菜单
				context.hideAllMenu();
				context.showMenu(BattleMiddleMenu.class);
			}
		}, 3000);
	}
	
	
}
