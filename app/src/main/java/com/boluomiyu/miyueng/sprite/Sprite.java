package com.boluomiyu.miyueng.sprite;

import java.util.Vector;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.boluomiyu.ch.unit.WomenGuiZi;
import com.boluomiyu.miyueng.GameContext;
import com.boluomiyu.miyueng.collection.MaxIndexList;
import com.boluomiyu.miyueng.event.EventListener;
import com.boluomiyu.miyueng.resource.ImageManager;
import com.boluomiyu.miyueng.sprite.moveforce.MoveForce;
import com.boluomiyu.miyueng.util.HitTestUtil;

/**
 * 类 Sprite
 * 描述：精灵类
 * $菠萝蜜语$ 荣誉出品  2013 版权所有.
 * @author 邹彦虎    2013-2-14
 * @version 1.0
 */
public abstract class Sprite {
	
	public static final int TEAM_NEUTRAL = 0;
	
	private GameContext context = GameContext.context;
	
	protected ImageManager im;
	/** 已碰撞列表 */
	private Vector<Sprite> hitedList = new Vector<Sprite>();
	/** 事件监听 */
	private EventListener eventListener;
	/** x坐标 */
	private float x;
	/** y坐标 */
	private float y;
	/** z坐标 */
	private float z;
	/** HP最大值 */
	private float maxHP = 0;
	/** MP最大值 */
	private float maxMP = 0;
	/** 经验最大值 */
	private float maxEXP = 0;
	/** 当前HP */
	private float HP = 0;
	/** 当前MP */
	private float MP = 0;
	/** 当前Exp */
	private float EXP = 0;
	
	/** 等级 */
	private float level = 1;
	/** 名称，查找 */
	private String name;
	/** 角色当前的角度 */
	private float rotate;
	/** 移动速度 */
	private float speed;
	/** 是否已死亡，已死亡元素不参与碰撞检测 */
	private boolean dead;
	/** 标记为可销毁 */
	private boolean destroyed = false;
	/** 是否被按下 */
	protected boolean pressed = false;
	/** 驱动力 */
	private MaxIndexList<MoveForce> moveForceList = new MaxIndexList<MoveForce>(10);
	/** 队伍 0：中立 */
	private int team = TEAM_NEUTRAL;
	/** 池中物 */
	private boolean inPool = true;
	/** 一级子分类 */
	private int type = 0;
	/** 是否进行zSort */
	private boolean zSort = true;
	
	/** x轴攻击范围 */
	private float attAreaX;
	
	/** y轴攻击范围 */
	private float attAreaY;
	
	/* ----------------- 接口 --------------------- */
	/** 执行，精灵运行 */
	public abstract void execute();
	/** 碰撞体积的width */
	public abstract float getBodyWidth();
	/** 碰撞体积的height */
	public abstract float getBodyHeight();
	/** 重设，回收时调用 */
	public abstract void reset();
	/** 被碰撞时效果函数 */
	public abstract void effectHit(Sprite sprite);
	/** 子渲染 */
	public abstract void render(Canvas canvas, Paint paint);
	/** 是否支持阴影 */
	public abstract boolean isSupportShadow();
	
	/** 顶层运行 */
	public void executeSprite() {
		for (int i=0; i<moveForceList.getMaxCount(); i++) {
			MoveForce moveForce = moveForceList.get(i);
			if (moveForce != null) {
				moveForce.execute();
				// 非永久力，且作用剩余时间小于等于0，则回收
				if (moveForce.getTimeRemaining() != MoveForce.TIME_REMAINING_FOREVER) {
					if (moveForce.getTimeRemaining() <= 0) {
						this.moveForceList.remove(i);
						context.returnMoveForce(moveForce);
					}
				}
			}
		}
		
		this.execute();
	}
	
	/** 顶层渲染 */
	public void renderSprite(Canvas canvas, Paint paint) {
		
		this.render(canvas, paint);
		
		// debug
		if (GameContext.context.debug) {
			paint.setColor(Color.GREEN);
			this.drawRectBound(canvas, paint);
			paint.setColor(Color.RED);
		}
	}
	
	/** 重置精灵，上台之前，调用，spriteFactory复用时调用 */
	public void prepareSprite() {
		
		this.setLevel(1);
		this.setMaxHP(0);
		this.setMaxMP(0);
		this.setMaxEXP(0);
		this.setTeam(0);
		
		// 非在池中
		this.inPool = false;
		// 设置坐标为界外
		this.setXY(-100, -100);
		// 复活，不会被destoryed
		this.dead = false;
		// 设置为未销毁，不会被回收
		this.destroyed = false;
		// 清理，碰撞检测过的
		this.hitedList.clear();
		// 旋转角度清0
		this.rotate = 0;
		// 调用自重设
		this.reset();
		// 清空所有非永久的驱动力
		for (int i=0; i<this.moveForceList.getMaxCount(); i++) {
			MoveForce mf = this.moveForceList.get(i);
			if (mf != null && mf.getTimeRemaining() != MoveForce.TIME_REMAINING_FOREVER) {
				context.returnMoveForce(mf);
				this.moveForceList.remove(i);
			}
		}
		
	}
	
	/** 是否碰撞到敌人，如果碰到，返回敌人 */
	public Sprite hitEnemy() {
		
		MaxIndexList<Sprite> spriteArray = context.getScenceSpriteArray();
		int size = spriteArray.getMaxCount();
		
		for (int i=0; i<size; i++) {
			Sprite enemy = spriteArray.get(i);
			//如果不在同队，而且为非中立
			if (enemy != null && enemy.team != Sprite.TEAM_NEUTRAL && enemy.dead==false && enemy.team != this.team) {
				//双矩形碰撞检测
				if (HitTestUtil.hitRect(
						this.getBodyX(), this.getBodyY(), this.getBodyWidth(), this.getBodyHeight(), 
						enemy.getBodyX(), enemy.getBodyY(), enemy.getBodyWidth(), enemy.getBodyHeight())
				){
					//如果不在已碰撞列表，加入碰撞列表，并返回
					if(!this.hitedList.contains(enemy)) {
						this.hitedList.add(enemy);
						enemy.effectHit(this);
						return enemy;
					}
				}
			}
		}
		return null;
	}
	
	/** 碰撞到所有敌人 */
	public Sprite hitAllEnemy() {
		// TODO 穿透式碰撞
		
		return null;
	}
	
	/* 碰撞体积 */
	public float getBodyX() {
		return this.getX();
	}

	public float getBodyY() {
		return this.getY() - this.getZ();
	}
	
	public void addEventListener(EventListener eventListener) {
		this.eventListener = eventListener;
	}
	
	public void onTouchEvent(MotionEvent e) {
		
		if (e == null) {
			return;
		}
		if(e.getAction()==MotionEvent.ACTION_DOWN){
			if(inArea(e)){
				if(eventListener != null){
					eventListener.pressed(e);
				}
			}
		} else if(e.getAction() == MotionEvent.ACTION_UP){
			if(inArea(e)){
				if(eventListener != null){
					eventListener.click(e);
				}
			}
		}
		
		if (eventListener != null) {
			this.eventListener.move(e);
		}
	}
	
	/**
     * 判断是否点中图片按钮
     * @param x
     * @param y
     */
    private boolean inArea(MotionEvent e) {
        boolean inArea = false;
        if (e.getX()>this.getBodyX() && e.getX()<this.getBodyX() + this.getBodyWidth() 
        	&& e.getY()>this.getBodyY() && e.getY()<this.getBodyY() + this.getBodyHeight()) {
        	inArea = true;
        }
        return inArea;
    }
	
    /** 绘制碰撞边框_debug */
	private void drawRectBound(Canvas canvas, Paint paint) {
		// 碰撞边界
		canvas.drawLine(this.getBodyX(), this.getBodyY(), this.getBodyX() + this.getBodyWidth(), this.getBodyY(), paint);
		canvas.drawLine(this.getBodyX() + this.getBodyWidth(), this.getBodyY(), this.getBodyX() + this.getBodyWidth(), this.getBodyY() + this.getBodyHeight(), paint);
		canvas.drawLine(this.getBodyX(), this.getBodyY() + this.getBodyHeight(), this.getBodyX() + this.getBodyWidth(), this.getBodyY() + this.getBodyHeight(), paint);
		canvas.drawLine(this.getBodyX(), this.getBodyY(), this.getBodyX(), this.getBodyY() + this.getBodyHeight(), paint);
		
		// 坐标点
		int oldColor = paint.getColor();
		paint.setColor(Color.BLUE);
		canvas.drawCircle(x, y, 2, paint);
		paint.setColor(oldColor);
	}
	
	/** 增加一个驱动影响力 */
	public void addMoveForce(MoveForce moveForce) {
		this.moveForceList.add(moveForce);
	}
	
    public void setXY(float x, float y) {
		this.x = x;
		this.y = y;
	}
    
    public void setXYZ(float x, float y, float z) {
    	this.x = x;
    	this.y = y;
    	this.z = z;
    }
    
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}
	
	public void setZ(float z) {
		this.z = z;
	}
	
	public float getZ() {
		return z;
	}
	
	public int getTeam() {
		return team;
	}

	public void setTeam(int team) {
		this.team = team;
	}
	
	public float getMaxHP() {
		return maxHP;
	}
	public void setMaxHP(float maxHP) {
		this.maxHP = maxHP;
	}
	public float getMaxMP() {
		return maxMP;
	}
	public void setMaxMP(float maxMP) {
		this.maxMP = maxMP;
	}
	public float getMaxEXP() {
		return maxEXP;
	}
	public void setMaxEXP(float maxEXP) {
		this.maxEXP = maxEXP;
	}
	public boolean isInPool() {
		return inPool;
	}
	public void setInPool(boolean inPool) {
		this.inPool = inPool;
	}
	public float getLevel() {
		return level;
	}
	public void setLevel(float level) {
		this.level = level;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public float getRotate() {
		return rotate;
	}
	public void setRotate(float rotate) {
		this.rotate = rotate;
	}
	public float getSpeed() {
		return speed;
	}
	public void setSpeed(float speed) {
		this.speed = speed;
	}
	public boolean isDead() {
		return dead;
	}
	public void setDead(boolean dead) {
		this.dead = dead;
	}
	public boolean isDestroyed() {
		return destroyed;
	}
	public void setDestroyed(boolean destroyed) {
		this.destroyed = destroyed;
	}
	public boolean isPressed() {
		return pressed;
	}
	public void setPressed(boolean pressed) {
		this.pressed = pressed;
	}
	public float getHP() {
		return HP;
	}
	public void setHP(float hP) {
		this.HP = hP;
	}
	public float getMP() {
		return MP;
	}
	public void setMP(float mP) {
		this.MP = mP;
	}
	public float getEXP() {
		return EXP;
	}
	public void setEXP(float eXP) {
		this.EXP = eXP;
	}
	public boolean isZSort() {
		return zSort;
	}
	
	/** 设置是否zSort，子弹、效果等类型的sprite，请设置为false，已免影响性能 */
	public void setZSort(boolean zSort) {
		this.zSort = zSort;
	}
	
	/** 中心对齐自己到另外一个sprite */
	public void alignTo(Sprite sprite) {
		this.setX(sprite.x + sprite.getBodyWidth()/2 - this.getBodyWidth()/2);
		this.setY(sprite.y + sprite.getBodyHeight()/2 - this.getBodyHeight()/2);
	}
	
	public float getCentX() {
		return this.x + this.getBodyWidth()/2;
	}
	
	public float getCentY() {
		return this.y + this.getBodyHeight()/2;
	}
	
	public float getAttAreaX() {
		return this.attAreaX;
	}
	public float getAttAreaY() {
		return attAreaY;
	}
	public void setAttAreaY(float attAreaY) {
		this.attAreaY = attAreaY;
	}
	public void setAttAreaX(float attAreaX) {
		this.attAreaX = attAreaX;
	}
	
	
}
