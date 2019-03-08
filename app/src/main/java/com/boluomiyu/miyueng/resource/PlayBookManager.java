package com.boluomiyu.miyueng.resource;

import java.io.BufferedInputStream;

import com.boluomiyu.miyueng.GameContext;
import com.boluomiyu.miyueng.util.Logger;

/**
 * 类 PlayBookManager
 * 描述：剧本解释器
 * $菠萝蜜语$ 荣誉出品  2013 版权所有.
 * @author 邹彦虎    2013-2-17
 * @version 1.0
 */
public class PlayBookManager {
	
	private GameContext context;
	
	private static final Logger logger = Logger.getLogger(PlayBookManager.class);
	
	private static PlayBookManager playBookManager;
	
	private boolean isEnd = false;
	
	private PlayBook currentPlayBook;
	
	private PlayBookManager() {
		this.context = GameContext.context;
	}
	
	public static PlayBookManager getInstance() {
		if(playBookManager == null){
			playBookManager = new PlayBookManager();
		}
		return playBookManager;
	}
	/** 加载一个剧本 */
	public void load(String fileName){
		
		logger.info("加载剧本：" + fileName);
		// 清空舞台
		context.clearScence();
		// 新剧本开演
		String playbook = null;
 		try{
 			BufferedInputStream bis = new BufferedInputStream(GameContext.context.getAssets().open(fileName), 1024);
 			StringBuilder sb = new StringBuilder();
 			byte[] buffer = new byte[1024];
 			int length = -1;
 			while((length = bis.read(buffer)) != -1){
 				sb.append(new String(buffer, 0, length));
 			}
 			playbook = sb.toString();
 		} catch(Exception e){
 			e.printStackTrace();
 		}
 		this.isEnd = false;
 		currentPlayBook = new PlayBook(playbook);
	}
	
	/** 执行当前的剧本 */
	public void execute(){
		try {
			if(currentPlayBook != null){
				//如果剧本当前句延迟为0，则执行下一句，否则叠加delay
				if(currentPlayBook.delay == 0){
					//总剧本长度大于1，而且当前剧本不为最后一条剧本
					String nextStatement = currentPlayBook.getNext();
					if(nextStatement != null) {
						if(!nextStatement.trim().equals("")){
							
							String[] statement = nextStatement.split("--");
							for (int i=0; i<statement.length; i++) {
								statement[i] = statement[i].trim();
							}
							
							String order = statement[0];
							//报幕
							if(order.charAt(0)=='#'){
								logger.info("注释：" + nextStatement);
							} else {
	
								logger.info("脚本语句：" + nextStatement);
								// 剧本时间
								currentPlayBook.delay = (long) (Double.parseDouble(statement[1]) * 1000);
								// 报幕
								if (order.equals("curtain")) {
									context.putData("curtain", statement[2].trim());
								// 加载舞台配置
								} else if (order.equals("config")) {
									context.loadSceneConfig(statement[2].trim());
								// 初始化精灵池，后台
								} else if (order.equals("spritepool")) {
									context.initSceneSpritePool(statement[2].trim());
								// 演员登场
								} else if (order.equals("create")) {
									context.loadSceneSprite(statement[2].trim());
								// 开始
								}else if (order.equals("start")) {
									context.startRace();
								// 停止	
								} else if (order.equals("stop")) {
									context.stopRace();
								// 场景背景
								} else if (order.equals("bg")) {
									String bg = statement[2].trim();
									context.loadSceneBG(bg);
								// 消息
								} else if (order.equals("message")) {
									String msg = statement[2];
									GameContext.context.info = msg;
								}
								
							}
						} else {
							currentPlayBook.delay = 0;
						}
					} else {
						//logger.info("剧本执行结束~！");
						//Hamster.info = Hamster.screenWidthRate + ":" + Hamster.screenHeightRate;
						this.isEnd = true;
					}
				} else {
					currentPlayBook.delay  -= context.minFreq;
				}
			}//playbook isnot null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void reset() {
		this.currentPlayBook = null;
	}

	public boolean isEnd() {
		return isEnd;
	}
	
}
