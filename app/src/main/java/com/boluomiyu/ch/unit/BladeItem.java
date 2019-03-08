package com.boluomiyu.ch.unit;

import java.util.Iterator;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
/**
 * 类 BladeItem
 * 描述：刀身
 * 公司 2013 版权所有.
 * @author 邹彦虎    2013-2-11
 * @version 1.0
 */
public class BladeItem{
	
	public static final int LAZER_WIDTH_MAX = 12;
	
	/** 刀刃宽度 */
	public int lazerWidth = LAZER_WIDTH_MAX;
	
	public float x;
	public float y;
	
	public float lazerLX;
	public float lazerLY;
	public float lazerRX;
	public float lazerRY;
	/** before bladeItem */
	public BladeItem bp;
	
	
	/** 刀身高光 */
	public int color = Color.WHITE;
	/** 刀刃 */
	public int lazerColor = Color.BLUE;
	/** 刀尖 */
	public static double bladeRad = 0;
	
	private Path path = new Path();
	
	private Shader shader = new LinearGradient(0, 0,850, 480, 
							new int[]{Color.BLACK, Color.WHITE, Color.BLACK, Color.WHITE, Color.WHITE}, null, Shader.TileMode.MIRROR);
	
	public BladeItem(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public String toString() {
		return  x + ":" + y;
	}
	
	public void execute(Iterator<BladeItem> it) {
		lazerWidth -= 2;
		if(lazerWidth < 0){
			lazerWidth = 0;
			this.bp = null;
			this.lazerWidth = BladeItem.LAZER_WIDTH_MAX;
			it.remove();
		} else {
			if(bp != null){
				double rad = Math.atan2(-(y - bp.y), (double)(x - bp.x));
				
				lazerLX = (int)(x - lazerWidth*Math.sin(bladeRad + rad));
				lazerLY = (int)(y - lazerWidth*Math.cos(bladeRad + rad));
				
				lazerRX = (int)(x + lazerWidth*Math.sin(rad - bladeRad));
				lazerRY = (int)(y + lazerWidth*Math.cos(rad - bladeRad));
				//音效
				/*
				Sound sound = SoundManager.getInstance().getSound(SoundList.BLADE_MOVE);
				if(this.distanceTo(bp) > 50){
					if(!sound.isPlaying){
						sound.play();
						sound.isPlaying = true;
						Logger.tip("playing...");
					} else {
						sound.cursor += Hamster.curFreq;
						if(sound.cursor > sound.length){
							sound.isPlaying = false;
							sound.cursor = 0;
						}
					}
				}*/
				
			}
		}
	}
	
	public void render(Canvas canvas, Paint paint) {
		if(bp != null){
			path.reset();
			path.moveTo(x, y);
			path.lineTo(lazerLX, lazerLY);
			path.lineTo(bp.lazerLX, bp.lazerLY);
			path.lineTo(bp.x, bp.y);
			path.lineTo(bp.lazerRX, bp.lazerRY);
			path.lineTo(lazerRX, lazerRY);
			path.lineTo(x, y);
			path.close();
			paint.setShader(shader);
			canvas.drawPath(path, paint);
			/*
			canvas.drawLine(x, y, bp.x, bp.y, paint);
			canvas.drawLine(lazerLX, lazerLY, bp.lazerLX, bp.lazerLY, paint);
			canvas.drawLine(lazerRX, lazerRY, bp.lazerRX, bp.lazerRY, paint);
			canvas.drawLine(x, y, lazerLX, lazerLY, paint);
			canvas.drawLine(x, y, lazerRX, lazerRY, paint);
			*/
			paint.setShader(null);
		}
	}
	/** 距离 */
	public double distanceTo(BladeItem bladeItem){
		return Math.sqrt((bladeItem.x - this.x)*(bladeItem.x - this.x) + (bladeItem.y - this.y)*(bladeItem.y - this.y));
	}
	
}
