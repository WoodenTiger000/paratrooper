package com.boluomiyu.ch.unit;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.boluomiyu.miyueng.MainCanvas;
import com.boluomiyu.miyueng.sprite.Sprite;
/**
 * 刀刃
 * @author 邹彦虎
 * 俱乐部  --- 
 */
public class Blade extends Sprite{

	public Vector<BladeItem> pointList;
	public Vector<BladeItem> pointPool;
	public boolean allowPush;
	
	private Blade instance;
	
	private long startTime = 0;
	
	private Blade() {
		pointList = new Vector<BladeItem>();
		pointPool = new Vector<BladeItem>();
		for(int i=0; i<30; i++){
			pointPool.add(new BladeItem(0, 0));
		}
	}
	
	public void clearAll(){
		pointList.clear();
	}
	public void push(float x, float y){
		if(allowPush == true){
			BladeItem bi = this.getBladeItem(x, y);
			bi.lazerLX = x;
			bi.lazerRX = x;
			bi.lazerLY = y;
			bi.lazerRY = y;
			this.pointList.add(bi);
		}
	}
	/** 从池中抽取一个 */
	int i=0;
	private BladeItem getBladeItem(float x, float y) {
		if(i==7){
			this.getClass();
		}
		for(BladeItem bi : this.pointPool){
			if(bi.lazerWidth == BladeItem.LAZER_WIDTH_MAX){
				bi.x = x;
				bi.y = y;
				i++;
				return bi;
			}
		}
		return null;
	}

	public void setAllowPush(boolean allowPush) {
		if(allowPush == true){
			this.startTime = System.currentTimeMillis();
		}
		this.allowPush = allowPush;
	}
	public List<BladeItem> getPointList(){
		return pointList;
	}

	public long getStartTime() {
		return startTime;
	}

	public boolean isAllowPush() {
		return allowPush;
	}

	public void execute(){
		this.push(MainCanvas.fingerX, MainCanvas.fingerY);
		Iterator<BladeItem> it = pointList.iterator();
		BladeItem bp = null;
		while(it.hasNext()){
			BladeItem p = it.next();
			p.execute(it);
			p.bp = bp;
			bp = p;
		}
	}

	@Override
	public void render(Canvas canvas, Paint paint) {
		for(BladeItem b : pointList){
			b.render(canvas, paint);
		}
	}

	@Override
	public void reset() {
		
	}

	@Override
	public float getBodyWidth() {
		//  废弃
		return 0;
	}

	@Override
	public float getBodyHeight() {
		//  废弃
		return 0;
	}

	@Override
	public void effectHit(Sprite sprite) {
		//  废弃
	}
	@Override
	public boolean isSupportShadow() {
		return true;
	}

}
