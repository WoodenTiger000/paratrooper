package com.boluomiyu.ch;

import java.util.HashSet;
import java.util.Set;

import com.boluomiyu.ch.menu.VictoryMenu;
import com.boluomiyu.miyueng.GameContext;
import com.boluomiyu.miyueng.collection.MaxIndexList;
import com.boluomiyu.miyueng.monitor.SecondsGameMonitor;
import com.boluomiyu.miyueng.sprite.Sprite;

/**
 * 类 BattleMonitor
 * 描述：战场监视器，当怪物都被消灭，
 * 		 而且游戏剧本执行完成，则胜利
 * $菠萝蜜语$ 荣誉出品  2013 版权所有.
 * @author 邹彦虎    2013-6-16
 * @version 1.0
 */
public class BattleMonitor extends SecondsGameMonitor {

	
	private Set<Integer> teamSet;
	
	private GameContext gameContext;
	
	public BattleMonitor(int duringSeconds) {
		super(duringSeconds);
		teamSet = new HashSet<Integer>();
		gameContext = GameContext.context;
	}

	@Override
	public void executeWithSeconds() {
		
		// 计算当前场景中的总Team数
		teamSet.clear();
		MaxIndexList<Sprite> spriteArray = gameContext.getScenceSpriteArray();
		int size = spriteArray.getMaxCount();
		for (int i=0; i<size; i++) {
			if (spriteArray.get(i) != null) {
				teamSet.add(spriteArray.get(i).getTeam());
			}
		}
		
		// 剧本结束，游戏正在竞赛，当前场景只剩于一只队伍
		if (gameContext.playbookIsEnd() && gameContext.getRaceStatus()==true && 1 == teamSet.size()) {
			gameContext.showMenu(VictoryMenu.class);
		}
		
	}

}
