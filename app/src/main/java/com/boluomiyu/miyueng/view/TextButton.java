package com.boluomiyu.miyueng.view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;
/**
 * 游戏按钮组件
 *
 * @author 邹彦虎
 * 俱乐部  --- 
 */
public class TextButton extends GameView{
	/** 文字 */
	public String text;
	public int textColor = Color.WHITE;
	public float textSize = 20;
	
	public TextButton() {
		this.enable = true;//默认可接受事件
		this.visible = true;//默认可见
		this.width = 300;
		this.height = 50;
	}

	@Override
	public void render(Canvas canvas, Paint paint) {
		final int oldColor = paint.getColor();
		final float oldTextSize = paint.getTextSize();
		final Shader olderShader = paint.getShader();
		paint.setTextSize(textSize);
		paint.setColor(textColor);
		paint.setShader(null);
		
		//按钮文字居中显示
		if(text != null){
			canvas.drawText(text, x + width/2 - textSize*text.length()/2, 
								  y + height/2 + textSize/2 - 2, paint);
		}
		
		paint.setColor(oldColor);
		paint.setTextSize(oldTextSize);
		paint.setShader(olderShader);
	}
	
	@Override
	public String toString() {
		return "btn:" + this.text;
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

}
