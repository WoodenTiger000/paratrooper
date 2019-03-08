package com.boluomiyu.miyueng.view;

import java.util.Vector;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.NinePatch;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.boluomiyu.miyueng.event.EventListener;
import com.boluomiyu.miyueng.resource.ImageManager;
import com.boluomiyu.miyueng.util.RenderUtil;

/**
 * 基础UI组件
 *
 * @author 邹彦虎
 * 俱乐部  --- 
 */
public abstract class GameView {
	
	public static final int HORIZONTAL_CENTER = -1;
	
	public int x;
	public int y;
	
	public int width;
	public int height;
	
	public boolean visible = true;
	/** 是否按下 */
	protected boolean pressed = false;
	/** 按下背景 */
	protected Bitmap pressedBG;
	/** 普通状态的背景 */
	protected Bitmap normalBG;
	/** 普通九宫格 */
	private NinePatch normalNP;
	/** 按下九宫格 */
	private NinePatch pressedNP;
	/** 按下背景是否是九宫格+加速 */
	private boolean pressedIsNP = false;
	/** 普通状态时否是九宫格+加速 */
	private boolean normalIsNP = false;
	/** 是否启用事件 */
	public boolean enable = true;
	/** 子视图 */
	public Vector<GameView> childList = new Vector<GameView>();
	/** 模态，事件是否全屏中断 */
	public boolean isMode = false;
	/** 模态，事件是否穿透该视图，向下传递 */
	public boolean isPierced = false;
	
	private EventListener eventListener;
	
	public void addEventListener(EventListener eventListener) {
		this.eventListener = eventListener;
	}
	/**
	 * 当碰到菜单，返回true
	 * @param e
	 * @return
	 */
	public boolean onTouchEvent(MotionEvent e){

		if(enable && this.visible){
			//传递子元素事件
			for (GameView v : childList) {
				if (v.onTouchEvent(e)) {
					return true;
				}
			}
			
			int eventType = MotionEvent.ACTION_CANCEL;
			// 多点
			if (e.getPointerCount() > 1) {
				eventType = e.getAction()&MotionEvent.ACTION_MASK;
			// 单点
			} else {
				eventType = e.getAction();
			}

			if(eventType == MotionEvent.ACTION_POINTER_DOWN || eventType==MotionEvent.ACTION_DOWN){
				if(inArea(e)){
					pressed = true;
					if (eventListener != null) {
						eventListener.pressed(e);
					}
					if (!this.isPierced) {
						return true;
					}
				}
			} else if(eventType == MotionEvent.ACTION_POINTER_UP || eventType==MotionEvent.ACTION_UP){
				pressed = false;
				if(inArea(e)){
					if(eventListener != null){
						eventListener.click(e);
					}
				}
			}
		}
		if (this.isMode == true) {
			return true;
		} else {
			return false;
		}
	}
	
	/** 渲染 除背景 以外的其他部件 */
	public abstract void render(Canvas canvas, Paint paint);
	
	/** 重置场景 */
	public abstract void reset();
	
	/** 添加子元素到x,y菜单的位置 */
	public void addView(GameView view, int x, int y){
		if (x == HORIZONTAL_CENTER) {
			view.x = this.x + this.width/2 - view.width/2;
		} else {
			view.x = this.x + x;
		}
		
		view.y = this.y + y;
		this.childList.add(view);
	}
	
	/** 添加子元素到中间，进行垂直居中布局 */
	public void addViewToCenter(GameView view){
		this.childList.add(view);
		view.x = x + this.width/2 - view.width/2;
		if(this.childList.size() == 1){
			view.y = y + this.height/2 - view.height/2;
		} else {
			int totalHeight = 0;
			int deltaHeight = 0;
			int deltaY = 15; //
			for(GameView v : this.childList){
				totalHeight += v.height + deltaY;
			}
			for(int i=0; i<this.childList.size(); i++){
				if(i == 0){
					this.childList.get(i).y = totalHeight/2;
				} else {
					this.childList.get(i).y = totalHeight/2 + deltaHeight; 
				}
				deltaHeight += this.childList.get(i).height + 5;
			}
		}
		
	}
	
	/**
     * 判断是否点中图片按钮
     * @param x
     * @param y
     */
    private boolean inArea(MotionEvent e) {
        boolean inArea = false;
        // 默认单触点
        if (e.getX()>x && e.getX()< x+width && e.getY()>y && e.getY()<y + height) {
        	inArea = true;
        }

        // 多触点
        if (e.getPointerCount() > 1) {
	        // 0：一触点，1：二触点
        	for (int i=1; i<e.getPointerCount(); i++) {
	        	if (e.getX(i)>x && e.getX(i)< x+width && e.getY(i)>y && e.getY(i)<y + height) {
		        	inArea = true;
		        }
	        }
        }
        
        return inArea;
    }
    
    /** 仅显示操作 */
    public void show(){
    	this.visible = true;
    }
    
    /** 仅隐藏操作 */
    public void hide(){
    	this.visible = false;
    }
    
	/** 设置按下的背景 .png, 支持九宫格 */
    public void setPressedBG(String fileName){
    	pressedBG = ImageManager.getInstance().loadImage(fileName);
    	if(pressedBG != null){
    		if(NinePatch.isNinePatchChunk(pressedBG.getNinePatchChunk())){
    			pressedNP = new NinePatch(pressedBG, pressedBG.getNinePatchChunk(), null);
    			pressedIsNP = true;
    		}
    	}
    }
    
    /** 设置普通的背景 .png, 支持九宫格 */
    public void setNormalBG(String fileName){
    	normalBG = ImageManager.getInstance().loadImage(fileName);
    	if(normalBG != null){
    		if(NinePatch.isNinePatchChunk(normalBG.getNinePatchChunk())){
    			normalNP = new NinePatch(normalBG, normalBG.getNinePatchChunk(), null);
    			normalIsNP = true;
    		}
    	}
    }
    
    /** 基础渲染，支持九宫格 */
    public void renderView(Canvas canvas, Paint paint){
    	if(this.visible == true){
    		
    		// 按下时
			if(pressed && pressedBG!=null){
				if(pressedIsNP){
    				pressedNP.draw(canvas, RenderUtil.getRect(x, y, x + width, y + height));
    			} else {
    				canvas.drawBitmap(pressedBG, RenderUtil.getRect(0, 0, pressedBG.getWidth(), pressedBG.getHeight()), 
    											RenderUtil.getRect2(x, y, x + width, y + height), paint);
    			}
    		// 放开时
			} else {
				if(normalBG != null){
					if(normalIsNP){
	    				normalNP.draw(canvas, RenderUtil.getRect(x, y, x + width, y + height));
	    			} else {
	    				canvas.drawBitmap(normalBG, RenderUtil.getRect(0, 0, normalBG.getWidth(), normalBG.getHeight()), 
	    											RenderUtil.getRect2(x, y, x + width, y + height), paint);
	    			}
				}
			}
			// 扩展渲染
    		render(canvas, paint);
    		// 渲染子元素
    		for(GameView v : childList){
    			v.renderView(canvas, paint);
    		}
    	}
    }

	
}
