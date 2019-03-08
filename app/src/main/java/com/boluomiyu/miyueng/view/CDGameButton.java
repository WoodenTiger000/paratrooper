package com.boluomiyu.miyueng.view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.boluomiyu.miyueng.GameContext;
import com.boluomiyu.miyueng.util.RenderUtil;

/**
 * 类 CDButton
 * 描述：cd按钮
 * 公司 2013 版权所有.
 * @author 邹彦虎    2013-2-12
 * @version 1.0
 */
public class CDGameButton extends TextButton{
	
	private GameContext context;
	
	/** cd 按毫秒设置 */
	private float cd = 0;
	
	/** cd 剩余 */
	private float cdRemain = -1;
	
	/** 触发 */
	private boolean triggered = false;
	
	public CDGameButton() {
		super();
		context = GameContext.context;
	}
	
	@Override
	public void render(Canvas canvas, Paint paint) {
		
		super.render(canvas, paint);
		
		if (triggered) {
			// 竞赛开始时cd消减起效
			if (true == context.getRaceStatus()) {
				cdRemain -= GameContext.context.factFreq;
			}
			if (cdRemain < 0) {
				triggered = false;
			}
			paint.setColor(Color.BLACK);
			paint.setAlpha(90);
			canvas.drawRoundRect(RenderUtil.getRectF(x, y, (int)(x + width*(cdRemain/cd)), y + height), 3f, 3f, paint);
			paint.setAlpha(255);
			paint.setColor(Color.WHITE);
		}
		
	}
	
	public void setCd(float cd) {
		this.cd = cd;
	}
	
	public boolean trigger() {
		if (!this.triggered) {
			this.triggered = true;
			this.cdRemain = cd;
			return true;
		} else {
			return false;
		}
	}
	
	
	
	
	
	
}
