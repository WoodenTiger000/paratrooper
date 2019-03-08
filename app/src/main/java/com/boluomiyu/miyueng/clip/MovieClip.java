package com.boluomiyu.miyueng.clip;

import java.util.Vector;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;

import com.boluomiyu.miyueng.GameContext;
import com.boluomiyu.miyueng.resource.ImageManager;
import com.boluomiyu.miyueng.util.Logger;

/**
 * 类 MovieClip
 * 描述：影片片段，承载角色动画
 * 菠萝秘密 2013 版权所有.
 * @author 邹彦虎    2013-2-5
 * @version 1.0
 */
public class MovieClip {

	public static final int DIRECTION_FORWARD = 1;
	public static final int DIRECTION_BACKWARD = 2;
	
	private Logger logger = Logger.getLogger(MovieClip.class);
	private ImageManager im = null;
	
	/** 播放当前帧的ID **/
	private int current = 0;
	private Bitmap currentFrame = null;
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
	private int delay = 0;
	/** delay增量，用于协助delay */
	private int delayDelta = 0; 
	/** 是否已播放到最后一帧/或第一帧 */
	private boolean isEnd;
	/** 播放方向 */
	private int direction;
	/** 转化器处理器 */
	private Matrix matrix;
	/** 旋转角度 */
	private float rotate = 0;
	
	private float x;
	private float y;
	/** 旋转相对中心 */
	private float rotateX = -1;
	private float rotateY = -1;
	
	/** 缩放比例x, 不为最大Float，则执行缩放*/
	private float scaleX = Float.MAX_VALUE;
	
	/** 缩放比例 y, 不为最大Float, 则执行*/
	private float scaleY = Float.MAX_VALUE;
	
	/**
	 * 构造动画
	 * @param fileName：动画位图序列
	 * @param loop：是否循环
	 * @param cellWidth：单元图宽度，如果为0，则为单帧，不分格
	 * @param delay：动画每隔delay * 主频 毫秒播放一帧
	 * @param direction: 动画播放方向
	 */
	public MovieClip(String fileName, boolean loop, int cellWidth, int delay, int direction){
		
		this.execute();
		
		this.im = ImageManager.getInstance();
		
		this.matrix = new Matrix();
		
		this.direction = direction;
		
		this.delay = delay;

		this.loop = loop;
		frames = new Vector<Bitmap>();
		Bitmap bitmap = im.loadImage(fileName);
		// 单帧
		if (cellWidth == 0) {
			im.putCacheImage(fileName, bitmap);
			frames.add(bitmap);
		// 多帧
		} else {
			length = (bitmap.getWidth()/cellWidth);
			if(length > 0){
				for(int i=0; i<length; i++){
					Bitmap temp = im.loadCacheImage(fileName + "_" + i);
					if(temp == null){
						temp = Bitmap.createBitmap(bitmap, cellWidth*i, 0, cellWidth, bitmap.getHeight());
						im.putCacheImage(fileName + "_" + i, temp);
					}
					frames.add(temp);
				}
			} else {
				logger.error("位图序列尺寸有问题。");
			}
		}
		
		if(direction == MovieClip.DIRECTION_FORWARD){
			this.current = 0;
		} else {
			this.current = this.length - 1;
		}
		
		this.currentFrame = this.frames.get(current);
		
	}
	
	/**
	 * 播放动画中的其中一帧
	 */
	public void execute(){
		if(stop == true){
			return;
		}
		if(playing == true){
			// 间隔
			if (delayDelta > 0) {
				delayDelta--;
				return;
			} else {
				delayDelta = delay;
			}
			//循环动画
			if(loop){
				// 正向
				if(this.direction == DIRECTION_FORWARD){
					if(current == length-1){
						current = 0;
					} else {
						current ++;
					}
				// 反向
				} else {
					if(current == 0){
						current = length-1;
					} else {
						current --;
					}
				}
			//非循环
			} else {
				// 正向
				if(this.direction == DIRECTION_FORWARD){
					if(current == length-1){
						playing = false;
						isEnd = true;
					} else {
						current ++;
					}
				// 反向
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
		// 调整帧到当前
		if (frames != null) {
			this.currentFrame = frames.get(current);
		}
	}
	
	/** 渲染 */
	public void render(Canvas canvas, Paint paint) {
		if(stop == true){
			return;
		} else {
			// 渲染帧矫正，防止溢出
			if (current < 0) {
				current = 0;
			} else if (current > frames.size() - 1) {
				current = frames.size() - 1;
			}
			//渲染
			float rotX = 0;
			float rotY = 0;
			if (rotateX == -1 || rotateX == -1) {
				rotX = currentFrame.getWidth()/2;
				rotY = currentFrame.getHeight()/2;
			} else {
				rotX = rotateX;
				rotY = + rotateY;
			}
			
			matrix.reset();
			matrix.postRotate(-this.rotate, rotX, rotY);

			
			if (scaleX != Float.MAX_VALUE) {
				matrix.postScale(scaleX, 1);
			}
			if (scaleY != Float.MAX_VALUE) {
				matrix.postScale(1, scaleY);
			}
			
			matrix.postTranslate(this.x, this.y);
			
			//Bitmap bitmap = Bitmap.createBitmap(currentFrame, 0, 0, currentFrame.getWidth(), (int)currentFrame.getHeight(), matrix, false);
			
			//currentFrame = Bitmap.createBitmap(currentFrame, 0, 0, currentFrame.getWidth(), currentFrame.getHeight());
			
			canvas.drawBitmap(currentFrame, matrix, paint);
			
			/* Bitmap bitmap = Bitmap.createBitmap(width, height, config)
					
			canvas*/
			if (GameContext.context.debug == true) {
				drawRectBound(canvas, paint);
			}
			// canvas.drawCircle(this.x + rotX, this.y + rotY, 5, paint);
			
			/*
			int[][] xyArray = new int[currentFrame.getWidth()][currentFrame.getHeight()];
			for (int i=0; i<currentFrame.getHeight(); i++){
				for (int j=0; j<currentFrame.getWidth(); j++) {
					if (currentFrame.getPixel(j, i) !=Color.TRANSPARENT) {
						xyArray[j][i] = Color.TRANSPARENT;
					} else {
						xyArray[j][i] = Color.WHITE;
					}
				}
			}
			for(int i=0; i<xyArrray.length; i++) {
				for( int j=0; j<xyArray[i].length; j++) {
					canvas.drawCircle(x + i, y + j, 2, paint);
				}
			}*/
		}
	}
	
	/** 绘制边框 */
	private void drawRectBound(Canvas canvas, Paint paint) {
		canvas.drawLine(this.x, this.y, this.x + currentFrame.getWidth(), this.y, paint);
		canvas.drawLine(this.x + currentFrame.getWidth(), this.y, this.x + currentFrame.getWidth(), this.y + currentFrame.getHeight(), paint);
		canvas.drawLine(this.x, this.y + currentFrame.getHeight(), this.x + currentFrame.getWidth(), this.y + currentFrame.getHeight(), paint);
		canvas.drawLine(this.x, this.y, this.x, this.y + currentFrame.getHeight(), paint);
	}
	
	
	/** 开始动画 */
	public void reset(){
		if(this.direction == MovieClip.DIRECTION_FORWARD){
			this.current = 0;
		} else {
			this.current = length - 1;
		}
		this.playing = true;
		this.stop = false;
		this.isEnd = false;
	}
	
	/** 停止动画 */
	public void stop(){
		if(this.direction == MovieClip.DIRECTION_FORWARD){
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
	public void setXY(float x, float y) {
		this.x = x;
		this.y = y;
	}
	/** 
	 * 设置中心点坐标，如果为-1，则绕中心旋转，如果>0，则按照指定点偏移点旋转，
	 * 具体为 旋转点 Xr = x + centX, Yr = y + centY 
	 * */
	public void setRotateXY(float rotateX, float rotateY) {
		this.rotateX = rotateX;
		this.rotateY = rotateY;
	}
	
	public float getRotateX() {
		return rotateX;
	}

	public float getRotateY() {
		return rotateY;
	}

	// setter and getter
	public int getCurrent() {
		return current;
	}

	public void setCurrent(int current) {
		this.current = current;
	}

	public boolean isLoop() {
		return loop;
	}

	public void setLoop(boolean loop) {
		this.loop = loop;
	}

	public float getRotate() {
		return rotate;
	}

	public void setRotate(float rotate) {
		this.rotate = rotate;
	}

	public boolean isEnd() {
		return isEnd;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public boolean isStop() {
		return stop;
	}
	
	public float getScaleX() {
		return scaleX;
	}

	public void setScaleX(float scaleX) {
		this.scaleX = scaleX;
	}

	public float getScaleY() {
		return scaleY;
	}

	public void setScaleY(float scaleY) {
		this.scaleY = scaleY;
	}

	public float getWidth() {
		if (this.currentFrame != null) {
			return this.currentFrame.getWidth();
		} else {
			return 0;
		}
	}
	
	public float getHeight() {
		if(this.currentFrame != null) {
			return this.currentFrame.getHeight();
		} else {
			return 0;
		}
	}
	
	
	
	
}
