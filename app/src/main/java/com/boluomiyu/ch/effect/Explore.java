package com.boluomiyu.ch.effect;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.boluomiyu.miyueng.clip.MovieClip;
import com.boluomiyu.miyueng.sprite.Sprite;
import com.boluomiyu.miyueng.util.Logger;

/**
 * 类 Explore
 * 描述：爆炸效果
 * $菠萝蜜语$ 荣誉出品  2013 版权所有.
 * @author 邹彦虎    2013-2-17
 * @version 1.0
 */
public class Explore extends Sprite{

	private MovieClip exploreMC;
	
	public Explore() {
		this.setZSort(false);
		this.exploreMC = new MovieClip("images/effect/explore_1.png", false, 64, 1, MovieClip.DIRECTION_FORWARD);
		this.exploreMC.reset();
	}
	
	@Override
	public void execute() {
		this.exploreMC.execute();
		// 爆炸播放结束，回收
		if (this.exploreMC.isEnd()) {
			this.setDestroyed(true);
		}
	}
	@Override
	public void render(Canvas canvas, Paint paint) {
		Logger.tip("current:" + exploreMC.getCurrent());
		this.exploreMC.setXY(this.getX() - 32, this.getY() - 24);
		this.exploreMC.render(canvas, paint);
	}

	@Override
	public float getBodyX() {
		return 0;
	}

	@Override
	public float getBodyY() {
		return 0;
	}
	@Override
	public float getBodyWidth() {
		return 0;
	}

	@Override
	public float getBodyHeight() {
		return 0;
	}

	@Override
	public void reset() {
		this.exploreMC.reset();
	}

	@Override
	public void effectHit(Sprite sprite) {
		
	}
	@Override
	public boolean isSupportShadow() {
		return false;
	}


	

}
