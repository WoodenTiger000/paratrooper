package com.boluomiyu.miyueng;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.boluomiyu.miyueng.scene.Scene;
import com.boluomiyu.miyueng.util.Logger;
import com.boluomiyu.miyueng.view.ViewManager;

/**
 * 类 MainCanvas
 * 描述：
 * 菠萝秘密 2013 版权所有.
 * @author 邹彦虎    2013-2-3
 * @version 1.0
 */
public class MainCanvas extends GLSurfaceView implements SurfaceHolder.Callback {

	private static final Logger logger = Logger.getLogger(MainCanvas.class);
	
	private SurfaceHolder holder;
	
	private GameContext context = GameContext.context;
	
	private ViewManager viewManager;
	
	private Paint paint;
	
	public static float fingerX = 0;
	public static float fingerY = 0;

	public static boolean fingerDown = false;
	// 锁
	private byte[] lock;
	/** 屏幕匹配矩阵 */
	private Matrix matchMatrix = new Matrix();
	
	public MainCanvas(Context context) {
		super(context);
		this.setKeepScreenOn(true);// 设置背景常亮
		
		lock = new byte[1];
		viewManager = ViewManager.getInstance();
		holder = this.getHolder();
		holder.addCallback(this);
		this.paint = new Paint();
		paint.setAntiAlias(true);// 设置画笔无锯齿(如果不设置可以看到效果很差)
		paint.setColor(Color.RED);
		
		logger.info("MainCanvas 已被创建");
		
		matchMatrix.reset();
		matchMatrix.postScale(GameContext.context.getScreenWidthRate(), GameContext.context.getScreenHeightRate());

		
	}
	/** 主渲染 */
	public void render(Scene scence) {
		Canvas canvas = this.holder.lockCanvas();
		if (canvas != null) {
			canvas.setMatrix(matchMatrix);
			try{
				canvas.drawColor(Color.BLACK);
				// 场景
				scence.render(canvas, paint);
				// 界面
				viewManager.render(canvas, paint);
			
			} finally{
				if(canvas != null){
					this.holder.unlockCanvasAndPost(canvas);
				}
			}
		}
	}
	
	@Override  
    public boolean onTouchEvent(MotionEvent e) {  
		
		e.setLocation(e.getX()/context.getScreenWidthRate(), e.getY()/context.getScreenHeightRate());
		// 菜单被点击，中断事件
		if (viewManager.onTouchEvent(e)) {
			return true;
		}
		
		if (e.getAction() == MotionEvent.ACTION_DOWN) {  
        	fingerX = e.getX();
        	fingerY = e.getY();
        	fingerDown = true;
        } else if (e.getAction() == MotionEvent.ACTION_UP) {  
        	fingerX = 0;
            fingerY = 0;
            fingerDown = false;
        }
		
    	fingerX = e.getX();
    	fingerY = e.getY();
    	
    	synchronized (lock) {
			try {
				lock.wait(10);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
        return true;
    }
	
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		GameContext.context.start();
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		//viewManager.showMainMenu();
	}

}
