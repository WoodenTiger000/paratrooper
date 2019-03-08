package com.boluomiyu.miyueng.view;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.boluomiyu.miyueng.resource.ImageManager;
import com.boluomiyu.miyueng.util.RenderUtil;
/**
 * 类 ImageView
 * 描述：
 * 公司 2013 版权所有.
 * @author 邹彦虎    2013-2-12
 * @version 1.0
 */
public class IconButton extends GameView{
	
	private Bitmap icon;
	
	
	@Override
	public void renderView(Canvas canvas, Paint paint) {
		if (icon != null) {
			int widthDelta = (width-icon.getWidth())/2;
			int heightDelta = (height-icon.getHeight())/2;
			canvas.drawBitmap(icon, RenderUtil.getRect(0, 0, icon.getWidth(), icon.getHeight()), 
									RenderUtil.getRect2(x + widthDelta, 
														y + heightDelta, 
														x + icon.getWidth() + widthDelta, 
														y + icon.getWidth() + heightDelta
														), 
							  paint);
		}
		super.renderView(canvas, paint);
	}
	@Override
	public void render(Canvas canvas, Paint paint) {
		
	}

	public void setIcon(String fileName) {
		icon = ImageManager.getInstance().loadImage(fileName);
	}
	@Override
	public void reset() {
		
	}
	
	
	

}
