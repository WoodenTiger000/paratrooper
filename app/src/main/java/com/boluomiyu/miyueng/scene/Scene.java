package com.boluomiyu.miyueng.scene;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.boluomiyu.miyueng.collection.MaxIndexList;
import com.boluomiyu.miyueng.exception.InitSpritePoolFail;
import com.boluomiyu.miyueng.sprite.Sprite;
/**
 * 名称: Scene
 * 职责：舞台规约
 * $菠萝蜜语$ 荣誉出品  2013 版权所有.
 * @author 邹彦虎    2013-6-19
 * @version 1.0
 */
public interface Scene {

	/**
	 * 渲染舞台
	 * @param canvas
	 * @param paint
	 */
	public void render(Canvas canvas, Paint paint);

	/** 驱动，由主线程发起心跳 */
	public void execute();

	/** 加载场景配置 */
	public void loadConfig(String sceneConfig);

	/** 初始化精灵池 */
	public void initSpritePool(String spritePoolConfig) throws InitSpritePoolFail;

	/** 通过数据加载一个Sprite到场景 */
	public void loadSprite(String spriteData);
	
	/** 获取所有的场景中的精灵 */
	public MaxIndexList<Sprite> getAllSpriteFromScene();
	
	/** 获取最大精灵索引，精灵池是个大数组，创建时会填充 */
	public int getSceneSpriteCount();
	
	/** */
	public <T> T getSpriteFromScence(String name);
	
	public <T extends Sprite> T getSpriteFromFactory(Class<? extends Sprite> clazz);
	
	public void addSprite(Sprite sprite);
	
	public void clear();
	
	public void loadBackground(String filePath);

	public Sprite findNearestEnemy(Sprite selfSprite);
	
	
	
	

}
