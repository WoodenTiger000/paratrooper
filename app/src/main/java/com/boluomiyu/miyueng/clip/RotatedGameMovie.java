package com.boluomiyu.miyueng.clip;


import java.util.Vector;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

import com.boluomiyu.miyueng.resource.ImageManager;
/**
 * 游戏动画类
 * @author 邹彦虎
 * 俱乐部  ---
 */
@Deprecated
@SuppressWarnings("unused")
public class RotatedGameMovie implements GameMovie{
	
	public static final int DIRECTION_FORWARD = 1;
	public static final int DIRECTION_BACKWARD = 2;
	
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
	/** 是否已播放到最后一帧/或第一帧 */
	public boolean isEnd;
	/** 播放方向 */
	public int direction;
	
	private Matrix matrix;
	/**
	 * 构造动画
	 * @param bitmap：动画位图序列
	 * @param loop：是否循环
	 * @param cellWidth：单元图宽度
	 * @param delay：动画每隔delay * 主频 毫秒播放一帧
	 * @param direction: 动画播放方向
	 */
	public RotatedGameMovie(String fileName, boolean loop, int width, int height, int delay, int direction){
		this.im = ImageManager.getInstance();
		
		matrix = new Matrix();
		
		this.direction = direction;
		
		this.loop = loop;
		frames = new Vector<Bitmap>();
		frames.add(im.loadImage(fileName));
		
		this.current = 0;
	}
	
	public void execute(){
		
	}
	
	public void render(Canvas canvas, Paint paint, float x, float y, float rotate) {
		matrix.reset();
		matrix.postRotate(-rotate, frames.get(current).getWidth()/2, frames.get(current).getHeight()/2);
		matrix.postTranslate(x, y);
		canvas.drawBitmap(frames.get(current), matrix, paint);
	}
	
	public void render(Canvas canvas, Paint paint, float x, float y, int width, int height) {
		canvas.drawBitmap(frames.get(current), matrix, paint);
	}
	
	/** 开始动画 */
	public void start(){
		
		this.playing = true;
		this.stop = false;
	}
	/** 停止动画 */
	public void stop(){
		
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
}