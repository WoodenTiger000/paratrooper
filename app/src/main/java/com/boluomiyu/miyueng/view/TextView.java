package com.boluomiyu.miyueng.view;

import com.boluomiyu.miyueng.util.StringUtil;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
/**
 * 类 TextView
 * 描述：
 * 公司 2013 版权所有.
 * @author 邹彦虎    2013-2-11
 * @version 1.0
 */
public class TextView extends GameView{

	private String text;

	private int color = Color.WHITE;
	
	private int textSize = 20;
	
	public TextView(String text) {
		this.text = text;
		this.width = StringUtil.caculateStringWidth(text);
	}
	
	@Override
	public void render(Canvas canvas, Paint paint) {
		float oldTextSize = paint.getTextSize();
		int oldColor = paint.getColor();
		paint.setColor(color);
		paint.setTextSize(textSize);
		canvas.drawText(text, x, y, paint);
		paint.setTextSize(oldTextSize);
		paint.setColor(oldColor);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public int getTextSize() {
		return textSize;
	}

	public void setTextSize(int textSize) {
		this.textSize = textSize;
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}
	

	

}
