package com.boluomiyu.miyueng.clip;


import java.util.Vector;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.boluomiyu.miyueng.resource.ImageManager;
import com.boluomiyu.miyueng.util.Logger;
/**
 * 游戏动画类
 * @author 邹彦虎
 * 俱乐部  ---
 */
@Deprecated
public class SquenceGameMovie implements GameMovie{
	
	private Logger logger = Logger.getLogger(SquenceGameMovie.class);
	private ImageManager im = null;
	
	/** 播放当前帧的ID **/
	public int current = 0;
	/** 用于储存动画资源图片 **/
	private Vector<Bitmap> frames = null;
	/** 是否循环播放 **/
	private boolean loop = false;
	/** 正在播放 **/
	private boolean playing = false;
	/** 停止 */
	private boolean stop = false;
	/** 动画长度 */
	private int length = 0;
	/** delay */
	private int delay = 3;
	/** delay增量 */
	private int deltaDelay = 1; 
	/** 是否已播放到最后一帧/或第一帧 */
	public boolean isEnd;
	/** 播放方向 */
	public int direction;
	/**
	 * 构造动画
	 * @param bitmap：动画位图序列
	 * @param loop：是否循环
	 * @param cellWidth：单元图宽度
	 * @param delay：动画每隔delay * 主频 毫秒播放一帧
	 * @param direction: 动画播放方向
	 */
	public SquenceGameMovie(String fileName, boolean loop, int cellWidth, int delay, int direction){
		this.im = ImageManager.getInstance();
		
		this.direction = direction;
		
		this.delay = delay;
		this.loop = loop;
		frames = new Vector<Bitmap>();
		Bitmap bitmap = im.loadImage(fileName);
		length = (bitmap.getWidth()/cellWidth);
		if(length > 0){
			for(int i=0; i<length; i++){
				Bitmap temp = im.loadImage(fileName + "_" + i);
				if(temp == null){
					temp = Bitmap.createBitmap(bitmap, cellWidth*i, 0, cellWidth, bitmap.getHeight());
					im.putCacheImage(fileName + "_" + i, temp);
				}
				frames.add(temp);
			}
		} else {
			logger.error("位图序列尺寸有问题。");
		}
		
		if(direction == SquenceGameMovie.DIRECTION_FORWARD){
			this.current = 0;
		} else {
			this.current = this.length - 1;
		}
	}
	
	/**
	 * 播放动画中的其中一帧
	 * 
	 * @param Canvas
	 * @param paint
	 * @param x
	 * @param y
	 * @param frameID
	 */
	public void execute(){
		if(stop == true){
			return;
		}
		deltaDelay--;
		if(deltaDelay == 0){
			deltaDelay = delay;
			if(playing == true){
					//循环动画
					if(loop){
						if(this.direction == DIRECTION_FORWARD){
							if(current == length-1){
								current = 0;
							} else {
								current ++;
							}
						} else {
							if(current == 0){
								current = length-1;
							} else {
								current --;
							}
						}
					//非循环
					} else {
						if(this.direction == DIRECTION_FORWARD){
							if(current == length-1){
								playing = false;
								isEnd = true;
							} else {
								current ++;
							}
						} else {
							if(current == 0){
								playing = false;
								isEnd = true;
							} else {
								current --;
							}
						}
					}
			}
		}
	}
	
	public void render(Canvas canvas, Paint paint, float x, float y, int width, int height) {
		if(stop == true){
			return;
		} else {
			if (current < 0) {
				current = 0;
			} else if (current > frames.size() - 1) {
				current = frames.size() -1;
			}
			
			canvas.drawBitmap(frames.get(current), x, y, paint);
		}
	}
	
	/** 开始动画 */
	public void start(){
		if(this.direction == GameMovie.DIRECTION_FORWARD){
			this.current = 0;
		} else {
			this.current = length - 1;
		}
		this.playing = true;
		this.stop = false;
	}
	/** 停止动画 */
	public void stop(){
		if(this.direction == GameMovie.DIRECTION_FORWARD){
			this.current = 0;
		} else {
			this.current = length - 1;
		}
		this.playing = false;
		this.stop = true;
		this.isEnd = false;
	}
	/** 暂停 */
	public void pause(){
		this.playing = false;
	}
	/** 恢复播放 */
	public void resume(){
		this.playing = true;
	}

	@Override
	public void render(Canvas canvas, Paint paint, float x, float y, float rotate) {
		
	}
}