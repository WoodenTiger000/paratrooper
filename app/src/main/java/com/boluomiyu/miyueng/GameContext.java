package com.boluomiyu.miyueng;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.KeyEvent;
import android.widget.Toast;

import com.boluomiyu.miyueng.collection.MaxIndexList;
import com.boluomiyu.miyueng.event.EventHandlerCenter;
import com.boluomiyu.miyueng.exception.InitSpritePoolFail;
import com.boluomiyu.miyueng.monitor.GameMonitor;
import com.boluomiyu.miyueng.monitor.GameMonitorManager;
import com.boluomiyu.miyueng.resource.PlayBookManager;
import com.boluomiyu.miyueng.resource.SoundManager;
import com.boluomiyu.miyueng.scene.SimpleScene;
import com.boluomiyu.miyueng.sprite.Sprite;
import com.boluomiyu.miyueng.sprite.moveforce.MoveForce;
import com.boluomiyu.miyueng.sprite.moveforce.MoveForceFactory;
import com.boluomiyu.miyueng.util.Logger;
import com.boluomiyu.miyueng.view.GameView;
import com.boluomiyu.miyueng.view.ViewManager;
/**
 * 类 GameContext
 * 描述：引擎门面
 * $菠萝蜜语$ 荣誉出品  2013 版权所有.
 * @author 邹彦虎    2013-2-14 
 * @version 1.0
 */
public class GameContext extends Activity {
    
	private static Handler handler = new Handler();
	
	/** 自身引用 */
	public static GameContext context;
	
	/** 场景 */
	public SimpleScene scence;
	
	/** 主线程 */
	public Thread mainThread;
	
	/** 主绘制板 */
	protected MainCanvas mainCanvas;
	
	/** 剧本管理器 */
	private PlayBookManager playBookManager;
	
	/** 视图管理UI */
	private ViewManager viewManager;
	
	/** 事件处理器 */
	private EventHandlerCenter eventCenter;
	
	/** 监视器  */
	private GameMonitorManager gameMonitorManager;
	
	/** 驱动力工厂 */
	private MoveForceFactory moveForceFactory;
	
	/** lock */
	private Object lock = new Object();
	
	/** 游戏主线程符号 */
	private boolean start = true;
	
	/** 开发机标配-宽度，其它机按此适配 */
	public int width = 854;
	
	/** 开发机标配-高度，其它机按此适配 */
	public int height = 480;
	
	/** 当前频率current frequece */
	public long curFreq = 20;
	
	/** 最小频率ms */
	public long minFreq = 20;
	
	/** 显示频率 ms */
	public long factFreq = 0;
	
	/** 信息提示 */
	public String info;
	
	private long startTime = System.currentTimeMillis();

	/** 竞赛状态 true:正在竞赛	false:竞赛结束 */
	private boolean raceStatus = false;
	
	public boolean debug = false;
	
	private float screenWidthRate;
	
	private float screenHeightRate;
	
	public long toastStart;
	
	public Map<String, String> dataMap;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		Logger.tip("game client create!");
		super.onCreate(savedInstanceState);
		context = this;
  
        Display display = getWindowManager().getDefaultDisplay();
        int widthNow = display.getWidth();
        int heightNow = display.getHeight();
        // 屏幕缩放比
        screenWidthRate = widthNow/(float)width;
        screenHeightRate = heightNow/(float)height;
        // 主画布
        this.mainCanvas = new MainCanvas(this);
        // 主场景
 		this.scence = SimpleScene.getInstance();
        // 剧本管理器
        this.playBookManager = PlayBookManager.getInstance();
        // 事件中心
        this.eventCenter = EventHandlerCenter.getInstance();
        // 视图中心UI
        this.viewManager = ViewManager.getInstance();
        //驱动力工厂
        this.moveForceFactory = new MoveForceFactory();
        // 心跳
        this.gameMonitorManager = new GameMonitorManager();
        // 数据
        this.dataMap = new HashMap<String, String>();
    }
	
	/** main canvas初始化完成后，执行启动 */
	public void start(){
		//执行线程  -- 10ms
		mainThread = new Thread(){
			public void run() {
				try {
					while(start){
						synchronized (lock) {
							startTime = System.currentTimeMillis();
							// 渲染，主线程存在，游戏将渲染，此为游戏唯一的渲染入口
							mainCanvas.render(scence);
							// 如果游戏运行，则渲染和计算
							scence.execute();
							// 执行剧本管理器
							playBookManager.execute();
							// 事件执行
							eventCenter.execute();
							// 监控器
							gameMonitorManager.execute();
							
							curFreq = System.currentTimeMillis() - startTime;
							startTime = System.currentTimeMillis();
							//等待n毫秒，如果当前频率低于最低频率，则使用最低频率，否则使用当前频率
							if(curFreq < minFreq){
								lock.wait(minFreq-curFreq);
								factFreq = minFreq;
							} else {
								factFreq = curFreq;
							}
							
						}
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			};
		};
		//渲染线程 -- 30ms
		mainThread.start();
	}
    
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_MENU) {
			if (context.debug) {
				context.debug = false;
			} else {
				context.debug = true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	
	
	// -------------- UI ---------------------
	/** 显示菜单 */
	public void showMenu(Class<? extends GameView> gameViewClass) {
		viewManager.showMenu(gameViewClass);
	}
	
	/** 隐藏菜单 */
	public void hideMenu(Class<? extends GameView> gameViewClass) {
		viewManager.hideMenu(gameViewClass);
	}
	
	/** 隐藏所有菜单 */
	public void hideAllMenu() {
		viewManager.hideAllMenu();
	}
	
	/** 
	 * 延迟执行,Runnable
	 * @param runnable
	 * @param mills：延迟毫秒
	 */
	public void postDelay(Runnable runnable, int mills) {
		handler.postDelayed(runnable, mills);
	}
	
	// --------------- 场景与精灵 ---------------
	
	/** 获取场景中精灵的数量 */
	public int getSceneSpriteCount() {
		
		return this.scence.getSceneSpriteCount();
	}

	/** 获取场景中的所有精灵，包含空的 */
	public MaxIndexList<Sprite> getScenceSpriteArray() {
		return this.scence.getAllSpriteFromScene();
	}
	
	/** 从场景持有一个精灵 */
	public <T> T getSpriteFromScence(String name) {
		return this.scence.getSpriteFromScence(name);
	}
	
	/** 从池中获取一个对象 */
	public <T extends Sprite> T getSpriteFromFactory(Class<? extends Sprite> clazz) {
		return this.scence.getSpriteFromFactory(clazz);
	}
	
	/** 添加精灵到舞台事件，供主线程外线程调度 */
	public void addScenceCreateEvent(Sprite sprite) {
		this.eventCenter.pushCreate(sprite);
	}	
	
	/** 添加精灵到舞台 */
	public void addToScence(Sprite sprite) {
		this.scence.addSprite(sprite);
	}
	
	/** 查找最近的一个敌人 */
	public Sprite findNearestEnemy(Sprite selfSprite) {
		return this.scence.findNearestEnemy(selfSprite);
	}
	
	/** 清理场景 */
	public void clearScence() {
		this.scence.clear();
	}
	
	public void loadSceneConfig(String sceneConfig) {
		this.scence.loadConfig(sceneConfig);
		
	}

	public void initSceneSpritePool(String spritePoolConfig) {
		try {
			this.scence.initSpritePool(spritePoolConfig);
		} catch (InitSpritePoolFail e) {
			e.printStackTrace();
		}
	}

	public void loadSceneSprite(String spriteData) {
		this.scence.loadSprite(spriteData);
		
	}

	public void loadSceneBG(String filePath) {
		this.scence.loadBackground(filePath);
	}

	
	// ---------------- 剧本 ---------------
	/** 加载一个剧本 */
	public void loadPlayBook(String playbook) {
		playBookManager.load(playbook);
	}
	
	/** 显示Toast */
	public void makeText(String msg) {
		
		if (context.toastStart == 0 || System.currentTimeMillis()-context.toastStart > 5000) {
			context.toastStart = System.currentTimeMillis();
			Toast.makeText(GameContext.context, msg, Toast.LENGTH_SHORT).show();
		}
		
	}

	public void clear() {
		this.playBookManager.reset();
		this.scence.clear();
	}
	
	// --------------- 监视器  ---------------
	/** 添加战场监视器 */
	public void addGameMonitor(GameMonitor gameMonitor) {
		this.gameMonitorManager.addGameMonitor(gameMonitor);
	}
	
	/** 停止所有的监视器 */
	public void stopAllGameMonitor() {
		this.gameMonitorManager.stopAll();
	}
	
	public void resetAndStartAllGameMonitor() {
		this.gameMonitorManager.resetAll();
		this.gameMonitorManager.startAll();
	}
	
	public void clearAllGameMonitor() {
		this.gameMonitorManager.clearAll();
	}
	
	// ---------------- 驱动力 -------------
	
	/** 从工厂中获取一个驱动力 */
	public MoveForce getMoveForce(Class<? extends MoveForce> moveForceClass, Sprite targetSprite) {
		return this.moveForceFactory.getMoveForce(moveForceClass, targetSprite);
	}
	
	/** 返回一个驱动力 */
	public void returnMoveForce(MoveForce moveForce) {
		this.moveForceFactory.returnMoveForce(moveForce);
	}
	
	
	// ---------------- 系统 ---------------
	public float getScreenWidthRate() {
		return context.screenWidthRate;
	}

	public float getScreenHeightRate() {
		return context.screenHeightRate;
	}

	public boolean playbookIsEnd() {
		return playBookManager.isEnd();
	}
	
	/** 获取当前竞赛状态 */
	public boolean getRaceStatus() {
		return this.raceStatus;
	}
	
	/** 启动竞赛 */
	public void startRace() {
		this.raceStatus = true;
	}
	
	/** 停止竞赛 */
	public void stopRace() {
		this.raceStatus = false;
	}
	
	/** 退出游戏 */
	public void exit() {
		System.exit(0);
	}
	
	/** 获取数据 */
	public String getData(String key) {
		String value = this.dataMap.get(key);
		if (value == null) {
			value = "";
		}
		return value;
	}
	
	public void putData(String key, String value) {
		this.dataMap.put(key, value);
	}
	
	// ---------------- 音效 ---------------
	// 播放声音
	public void playSound(int soundId) {
		SoundManager.getInstance().playSound(soundId);
	}








	
}