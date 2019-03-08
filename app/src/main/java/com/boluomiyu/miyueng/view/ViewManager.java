package com.boluomiyu.miyueng.view;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.boluomiyu.miyueng.GameContext;
import com.boluomiyu.miyueng.event.EventHandlerCenter;
import com.boluomiyu.miyueng.exception.EventException;
import com.boluomiyu.miyueng.sprite.Sprite;
import com.boluomiyu.miyueng.sprite.SpriteFactory;
/**
 * 菜单管理器
 * @author 邹彦虎
 * 俱乐部  --- 
 */
public class ViewManager {
	
	private GameContext context;
	
	private static ViewManager viewManager;
	
	private EventHandlerCenter eventCenter = EventHandlerCenter.getInstance();
	
	/** 可渲染和执行角色列表, 存储 */
	private Map<String, GameView> gameViewMap;
	
	/** 渲染顺序 */
	private Vector<GameView> gameViewOrderVector;
	
	public static ViewManager getInstance() {
		if(viewManager == null){
			viewManager = new ViewManager();
			viewManager.context = GameContext.context;
		}
		return viewManager;
	}
	
	private ViewManager() {
		this.gameViewMap = new HashMap<String, GameView>();
		this.gameViewOrderVector = new Vector<GameView>();
	}
	
	/**
	 * 显示菜单
	 * @param menuClass
	 */
	public void showMenu(Class<? extends GameView> menuClass) {
		try {
			eventCenter.pushShowGameViewEvent(menuClass);
		} catch (EventException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 显示菜单
	 * @param menuClass
	 */
	public void hideMenu(Class<? extends GameView> menuClass) {
		try {
			eventCenter.pushHideGameViewEvent(menuClass);
		} catch (EventException e) {
			e.printStackTrace();
		}
	}
	
	/** 隐藏所有的菜单 */
	public void hideAllMenu() {
		try {
			eventCenter.pushClearGameViewEvent();
		} catch (EventException e) {
			e.printStackTrace();
		}

	}
	
	// 源事件
	public boolean onTouchEvent(final MotionEvent e) {
		int size = this.gameViewOrderVector.size();
		// 事件要倒堆栈传递
		for (int i=size-1; i>-1; i--) {
			GameView gameView = this.getGameViewOrderVector().get(i);
			if(gameView.onTouchEvent(e)) {
				return true;
			}
		}
		
		// 精灵操作，事件推入
		eventCenter.pushTouchEvent(e);
		return false;
	}
	
	//渲染
	public void render(Canvas canvas, Paint paint) {
		// 游戏UI组件
		for(GameView v : gameViewOrderVector){
			v.renderView(canvas, paint);
		}
		
		// 调试模式信息
		paint.setColor(Color.WHITE);
		if (context.debug) {
			canvas.drawText("信息：" + context.info, 30, 20, paint);
			canvas.drawText("Debug检视模式", 260, 20, paint);
		

			// 精灵监控
			canvas.drawText("scence: " + context.getSceneSpriteCount(), context.width-220, 20, paint);
			canvas.drawText("factory: " + SpriteFactory.getInstance().getSpriteCount(), context.width-220, 30, paint);
		}
		// 帧频
		canvas.drawText("fps: " + context.factFreq, context.width-60, 20, paint);
		Sprite sprite = context.getSpriteFromScence("women");
		if (sprite != null) {
			canvas.drawText("hp: " + sprite.getHP(), context.width-60, 40, paint);
		}
	}

	public Map<String, GameView> getGameViewMap() {
		return gameViewMap;
	}

	public void setGameViewMap(Map<String, GameView> gameViewMap) {
		this.gameViewMap = gameViewMap;
	}

	public Vector<GameView> getGameViewOrderVector() {
		return gameViewOrderVector;
	}

}


