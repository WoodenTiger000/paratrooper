package com.boluomiyu.miyueng.view;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.boluomiyu.miyueng.resource.ImageManager;
import com.boluomiyu.miyueng.util.RenderUtil;
/**
 * 类 PercentImageView
 * 描述：
 * 公司 2013 版权所有.
 * @author 邹彦虎    2013-2-13
 * @version 1.0
 */
public class PercentImageView extends GameView{

	/** 当前百分比 */
	private float percent;
	
	private Bitmap percentImage;
	
	@Override
	public void renderView(Canvas canvas, Paint paint) {
		float rate = percent/100f;
		if (percentImage != null) {
			canvas.drawBitmap(percentImage, RenderUtil.getRect(0, 0, percentImage.getWidth(), percentImage.getHeight()), 
							  RenderUtil.getRect2(
									  	(int)(x + width/20f), 
										y + height/3, 
										(int)((x + (width - width/20f) * rate)), 
										y + height -  height/3
										), 
							  paint);
		}
		super.renderView(canvas, paint);
	}
	
	@Override
	public void render(Canvas canvas, Paint paint) {
		
	}

	public void setPercent(int percent) {
		if (percent > 100) {
			percent = 100;
		}
		if (percent < 0) {
			percent = 0;
		}
		this.percent = percent;
	}

	public void setPercentImage(String fileName) {
		this.percentImage = ImageManager.getInstance().loadImage(fileName);
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

}
