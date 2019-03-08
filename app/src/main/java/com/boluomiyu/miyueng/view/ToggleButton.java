package com.boluomiyu.miyueng.view;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.NinePatch;
import android.graphics.Paint;

import com.boluomiyu.miyueng.resource.ImageManager;
import com.boluomiyu.miyueng.util.Logger;
import com.boluomiyu.miyueng.util.RenderUtil;

/**
 * 类 ToggleGameButton
 * 描述：切换模式按钮
 * 公司 2013 版权所有.
 * @author 邹彦虎    2013-2-12
 * @version 1.0
 */
public class ToggleButton extends TextButton{

	private static final Logger logger = Logger.getLogger(ToggleButton.class);
	
	private boolean choosed = false;
	/** 选中 */
	private Bitmap choosedBG;
	/** 未选中 */
	private Bitmap unchoosedBG;
	/** 普通九宫格 */
	private NinePatch choosedNP;
	/** 按下九宫格 */
	private NinePatch unchoosedNP;
	
	private boolean choosedIsNP = false;
	
	private boolean unchoosedIsNP = false;
	
	
	@Override
	public void render(Canvas canvas, Paint paint) {
		//toggle 背景
		if(this.visible == true){
    		// 选中时
			if (choosed) {
				if (choosedBG != null) {
					if(choosedIsNP){
						choosedNP.draw(canvas, RenderUtil.getRect(x, y, x + width, y + height));
	    			} else {
	    				canvas.drawBitmap(choosedBG, RenderUtil.getRect(0, 0, choosedBG.getWidth(), choosedBG.getHeight()), 
	    											RenderUtil.getRect2(x, y, x + width, y + height), paint);
	    			}
				}
    		// 未选中时
			} else {
				if(unchoosedBG != null){
					if(unchoosedIsNP){
						unchoosedNP.draw(canvas, RenderUtil.getRect(x, y, x + width, y + height));
	    			} else {
	    				canvas.drawBitmap(unchoosedBG, RenderUtil.getRect(0, 0, unchoosedBG.getWidth(), unchoosedBG.getHeight()), 
	    											RenderUtil.getRect2(x, y, x + width, y + height), paint);
	    			}
				}
			}
    	}
		
		super.render(canvas, paint);
	}
	
	@Override
	public void setNormalBG(String fileName) {
		logger.error("toggle按钮不支持normalBG !");
	}
	
	@Override
	public void setPressedBG(String fileName) {
		logger.error("toggle按钮不支持pressBG !");
	}
	
	/** 设置按下的背景 .png, 支持九宫格 */
    public void setChoosedBG(String fileName){
    	choosedBG = ImageManager.getInstance().loadImage(fileName);
    	if(choosedBG != null){
    		if(NinePatch.isNinePatchChunk(choosedBG.getNinePatchChunk())){
    			choosedNP = new NinePatch(choosedBG, choosedBG.getNinePatchChunk(), null);
    			choosedIsNP = true;
    		}
    	}
    }
    
    public void setUnchoosedBG(String fileName){
    	unchoosedBG = ImageManager.getInstance().loadImage(fileName);
    	if(unchoosedBG != null){
    		if(NinePatch.isNinePatchChunk(unchoosedBG.getNinePatchChunk())){
    			unchoosedNP = new NinePatch(unchoosedBG, unchoosedBG.getNinePatchChunk(), null);
    			unchoosedIsNP = true;
    		}
    	}
    }
	
    public void setChoosed(boolean choosed) {
		this.choosed = choosed;
	}
    
	
}
