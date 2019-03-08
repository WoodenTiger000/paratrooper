package com.boluomiyu.miyueng.scene;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;

import com.boluomiyu.miyueng.GameContext;
import com.boluomiyu.miyueng.collection.MaxIndexList;
import com.boluomiyu.miyueng.exception.InitSpritePoolFail;
import com.boluomiyu.miyueng.resource.ImageManager;
import com.boluomiyu.miyueng.sprite.Sprite;
import com.boluomiyu.miyueng.sprite.SpriteFactory;
import com.boluomiyu.miyueng.util.HWMath;
import com.boluomiyu.miyueng.util.Logger;
import com.boluomiyu.miyueng.util.RenderUtil;
/**
 * 类 Scence
 * 描述：场景，即是战场
 * $菠萝蜜语$ 荣誉出品  2013 版权所有.
 * @author 邹彦虎    2013-2-14
 * @version 1.0
 */
public class SimpleScene implements Scene {
	
	private GameContext context;
	
	private static final Logger logger = Logger.getLogger(SimpleScene.class);
	
	private static SimpleScene scene;
	
	private SpriteFactory spriteFactory;
	
	private ImageManager imageManager;
	
	/** 背景 */
	private Bitmap bg;
	
	private int sceneSpriteCount = 0;
	
	private int spriteBufferSize = 500;
	
	/** 角色集合 - 大数组模糊并发解决方案 */
	private MaxIndexList<Sprite> spriteArray;
	
	/** 场景参数 */
	public Map<String, String> config;
	
	/** 变换器-阴影 */
	private Matrix matrix;
	
	public static SimpleScene getInstance() {
		if(scene == null){
			scene = new SimpleScene();
			scene.imageManager = ImageManager.getInstance();
			scene.matrix = new Matrix();
		}
		return scene;
	}
	
	public SimpleScene() {
		context = GameContext.context;
		this.spriteFactory = SpriteFactory.getInstance();
		this.spriteArray = new MaxIndexList<Sprite>(spriteBufferSize);
		this.config = new HashMap<String, String>();
	}
	
	/** 加载游戏背景 */
	public void loadBackground(String filePath) {
		ImageManager imageManager = ImageManager.getInstance();
		bg = imageManager.loadImage(filePath);
	}
	
	/** 添加一个游戏角色，到舞台, zSort */
	public void addSprite(Sprite sprite){
		
		spriteArray.add(sprite);
		
		sceneSpriteCount++;
		
		// sprite Z排序
		this.zSortSprite(spriteArray);
	}
	
	/**
	 * 使用 y 坐标 + bodyHeight，的顺序进行排序
	 * @param spriteArr
	 */
	private void zSortSprite(MaxIndexList<Sprite> spriteArray) {
		int size = spriteArray.getMaxCount();
		for (int i = 1; i < size; i++) {
			for (int j = i; j > 0; j--) {
				if (spriteArray.get(j) == null || spriteArray.get(j - 1) == null) {
					continue;
				}
				if (spriteArray.get(j).getY() + spriteArray.get(j).getBodyHeight() < spriteArray.get(j - 1).getY() + spriteArray.get(j - 1).getBodyHeight()) {
					Sprite temp = spriteArray.get(j - 1);
					spriteArray.set(j-1, spriteArray.get(j));
					spriteArray.set(j, temp);
				} else {
					break;
				}
			}
		}
	}
	
	/** 清场 */
	public void clear() {
		
		// 清理Sprite集合
		for (int i = 0; i < spriteArray.getMaxCount(); i++) {
			spriteArray.set(i, null);
		}
		
		this.sceneSpriteCount = 0;
		
		this.config.clear();
		this.spriteFactory.reset();
		this.bg = null;
		
	}
	
	/** 渲染阴影-公共 */
	private void drawShadow(Sprite sprite, Canvas canvas, Paint paint) {
		Bitmap shadow = imageManager.loadImage("images/sprite/common/shadow.png");
		matrix.reset();
		matrix.postScale(sprite.getBodyWidth()/shadow.getWidth(), 0.5f);
		matrix.postTranslate(sprite.getX() , sprite.getY() + sprite.getBodyHeight() - 10);
		canvas.drawBitmap(shadow, matrix, paint);
	}
	
	/** 渲染场景 */
	public void render(Canvas canvas, Paint paint) {
		paint.setShader(null);
		paint.setColor(Color.RED);
		
		//渲染背景
		if(bg != null){
			canvas.drawBitmap(bg, RenderUtil.getRect(0, 0, bg.getWidth(), bg.getHeight()), 
								  RenderUtil.getRect2(0, 0, GameContext.context.width, GameContext.context.height), paint);
		} else {
			canvas.drawColor(Color.BLACK);
		}
		
		// 渲染阴影
		for (int i=0; i < spriteArray.getMaxCount(); i++) {
			Sprite sprite = spriteArray.get(i);
			if (sprite != null && sprite.isSupportShadow()) {
				this.drawShadow(sprite, canvas, paint);
			}
		}
		
 		// 渲染角色列表
 		for (int i = 0; i < spriteArray.getMaxCount(); i++) {
 			if (spriteArray.get(i) != null) {
 				spriteArray.get(i).renderSprite(canvas, paint);
 			}
		}

 		//渲染刀刃，模拟水果忍者效果
		/*
 		final Blade blade = Blade.getInstance();
		blade.render(canvas, paint);*/
	}
	
	/** 执行程序，竞赛开始，场景角色开始活动 */
	public void execute(){
		if(context.getRaceStatus() == true){
			for (int i = 0; i < spriteArray.getMaxCount(); i++) {
				Sprite sprite = spriteArray.get(i);
				if (sprite != null) {
					if(sprite.isDestroyed()){
						spriteFactory.returnSprite(sprite);
						spriteArray.set(i, null);
						sceneSpriteCount --;
						//Logger.tip("scence,execute: 删除:" + sprite);
					} else {
						sprite.executeSprite();
					}
				}
			}
			
		}
		// 产生demo飞机
		/*
		if (GameContext.gameRun && createDemoInterval < 0 && this.spriteList.size() < 40) {
			createDemoInterval = 50;
			Battleplane bp = SpriteFactory.getInstance().getBattleplane();
			bp.setTeam(2);
			bp.setXY(HWMath.rand(900, 1000), HWMath.rand(50, 200));
			bp.setHp(3);
			this.addSprite(bp);
		} else {
			createDemoInterval --;
		}*/
		//System.out.println("execute");
	}

	/**
	 * 加载配置，精灵根路径，场景宽高
	 * @param trim
	 */
	public void loadConfig(String data) {
		//{unitbase:com.boluomiyu.ch.unit, width:854, height:480}
		data = data.substring(1, data.length()-1);
		String[] dataArr = data.split(",");
		String[] keyValuePair;
		for (int i=0; i<dataArr.length; i++) {
			keyValuePair = dataArr[i].split(":");
			this.config.put(keyValuePair[0].trim(), keyValuePair[1].trim());
		}
	}
	
	/**
	 * 幕后[factory] 初始化对象池，创建对象
	 * @param trim
	 * @throws InitSpritePoolFail 
	 */
	@SuppressWarnings("unchecked")
	public void initSpritePool(String data) throws InitSpritePoolFail {
		
		//{preload:MainGun_1^BattlePlane_30^Missile_100}
		data = data.substring(1, data.length()-1);
		
		String unitbase = this.config.get("unitbase") + ".";
		String[] dataArr = data.split(",");
		String[] keyValuePair;
		for (int i=0; i<dataArr.length; i++) {
			keyValuePair = dataArr[i].split(":");
			// 预建池
			if ("preloadSprite".equals(keyValuePair[0])) {
				String[] spriteClasses = keyValuePair[1].trim().split("\\^");
				for (int j=0; j<spriteClasses.length; j++) {
					String[] spriteArr = spriteClasses[j].split("_");
					try {
						int spriteCount = Integer.parseInt(spriteArr[1]);
						this.spriteFactory.createSprite((Class<? extends Sprite>)Class.forName(unitbase + spriteArr[0]), spriteCount);
					} catch (ClassNotFoundException e) {
						logger.error("初始化精灵池失败：class:" + unitbase + spriteArr[0]);
						throw new InitSpritePoolFail();
					}
				}
			} else if("xx".equals(keyValuePair[0])) {
				
			}
		}
	}
	
	/** 
	 * 从场景加载一个Sprite到舞台,由剧本传入参数
	 * 
	 * @param data  eg. 
	 * class:BattlePlane, x:120, y:100, rotate:0, team:1, hp:100, mp:60, exp:100
	 */
	@SuppressWarnings("unchecked")
	public void loadSprite(String data) {
		//{class:MainGun, name:maingun, x:100, y:300, level=1}
		data = data.substring(1, data.length()-1);
		
		String unitbase = this.config.get("unitbase") + ".";
		
		String[] dataArr = data.split(",");
		String[] keyValuePair; 
		
		Sprite sprite = null;
		try {
			for (int i=0; i<dataArr.length; i++) {
				keyValuePair = dataArr[i].split(":");
				String key = keyValuePair[0].trim();
				String value = keyValuePair[1].trim();
				
				//{class:MainGun, name:maingun, x:100, y:300, level=1}
				if ("class".equals(key)) {
					sprite = this.spriteFactory.getSprite((Class<? extends Sprite>)Class.forName(unitbase + value));
				} else {
					for (Method m : sprite.getClass().getMethods()) {
						if (m.getName().startsWith("set")) {
							// method name
							String feildName = m.getName().substring(3, m.getName().length()).toLowerCase(Locale.getDefault());
							if (feildName.equals(key.toLowerCase(Locale.getDefault()))) {
								String feildType = m.getParameterTypes()[0].getName();
								if (key.equals("x") || key.equals("y")) {
									if ("R".equals(value)) {
										m.invoke(sprite, HWMath.rand(200, 400));
									} else {
										m.invoke(sprite, Integer.parseInt(value));
									}
								} else if (feildType.equals("java.lang.String")) {
									m.invoke(sprite, value);
								} else if (feildType.equals("int") || feildType.equals("java.lang.Integer")) {
									m.invoke(sprite, Integer.parseInt(value));
								} else if (feildType.equals("float") || feildType.equals("java.lang.Float")) {
									m.invoke(sprite, Float.parseFloat(value));
								}
							}
						}
					}
				}
			}//end for 
			this.addSprite(sprite);
			
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	/** 从舞台中获取一个精灵，通过别名 */
	@SuppressWarnings("unchecked")
	public <T> T getSpriteFromScence(String name) {
		
		for (int i = 0; i < spriteArray.getMaxCount(); i++) {
			Sprite sprite = spriteArray.get(i);
			if (sprite != null && name.equals(sprite.getName())) {
				return (T) sprite;
			}
		}
		
		return null;
	}
	
	/** 从工厂中获取一个精灵 */
	public <T extends Sprite> T getSpriteFromFactory(Class<? extends Sprite> clazz) {
		return this.spriteFactory.getSprite(clazz);
	}

	public MaxIndexList<Sprite> getAllSpriteFromScene() {
		return spriteArray;
	}

	@Override
	public int getSceneSpriteCount() {
		return this.sceneSpriteCount;
	}

	public Sprite findNearestEnemy(Sprite selfSprite) {
		Sprite nearestSprite = null;
		float minDistance = Float.MAX_VALUE;
		for (int i = 0; i < spriteArray.getMaxCount(); i++) {
			Sprite sprite = spriteArray.get(i);
			if (sprite == null) {
				continue;
			}
			// 不同组，且不为自然单位
			if (sprite.getTeam()==Sprite.TEAM_NEUTRAL) {
				continue;
			}
			if (sprite.getTeam() == selfSprite.getTeam()) {
				continue;
			}
			// 没有死，而且在攻击范围中
			float distance = Math.abs(selfSprite.getBodyX() - sprite.getBodyX());
			if (distance < 10) {
				continue;
			}
			float absDistance = Math.abs(distance);
			if (!sprite.isDead() && distance < selfSprite.getAttAreaX()) {
				if (absDistance < minDistance) {
					minDistance = absDistance;
					nearestSprite = sprite;
				}
			}
		}
		
		return nearestSprite;
	}

	





	
	
	
}
