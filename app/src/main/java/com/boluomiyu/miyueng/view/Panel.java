package com.boluomiyu.miyueng.view;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.boluomiyu.miyueng.GameContext;
import com.boluomiyu.miyueng.util.Logger;
/**
 * 类 Menu
 * 描述：
 * 菠萝秘密 2013 版权所有.
 * @author 邹彦虎    2013-2-10
 * @version 1.0
 */
public class Panel extends GameView{
	
	private TextView titleText;
	
	/** 无参数构造函数，将按照全屏，传入菜单宽度、高度 */
	public Panel() {
		this.width = GameContext.context.width;
		this.height = GameContext.context.height;
		this.enable = true;
		this.visible = true;
		
		titleText = new TextView("");
		this.addView(titleText, 0, 0);
	}
	
	public Panel(int width, int height) {
		this.width = width;
		this.height = height;
		this.enable = true;
		this.visible = true;
		titleText = new TextView("");
		this.addView(titleText, 0, 0);
	}
	/** 移动菜单到屏幕中央 */
	public void moveToCenter(){
		this.x = GameContext.context.width/2 - this.width/2;
		this.y = GameContext.context.height/2 - this.height/2;
		Logger.tip(width + ":" + GameContext.context.height);
	}
	// 扩展render，对GameView.render进行扩展

	public void setTitle(String title) {
		this.addView(titleText, this.width/2 - title.length()*titleText.getTextSize()/2, 30);
		this.titleText.setText(title);
	}

	@Override
	public void render(Canvas canvas, Paint paint) {
		
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	
}
