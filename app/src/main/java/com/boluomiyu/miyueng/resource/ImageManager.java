package com.boluomiyu.miyueng.resource;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import com.boluomiyu.miyueng.GameContext;
import com.boluomiyu.miyueng.util.Logger;

/**
 * 图片服务
 * @author 邹彦虎
 * 俱乐部  --- 
 */
public class ImageManager {

	private static ImageManager imageManager;
	
	private static Map<String, Bitmap> bitmapCache;
	
	private Logger logger = Logger.getLogger(ImageManager.class);
	
	public static ImageManager getInstance() {
		if(imageManager == null){
			imageManager = new ImageManager();
			bitmapCache = new HashMap<String, Bitmap>();
		}
		return imageManager;
	}
	
	/** 加载asset目录下的图片 */
	public Bitmap loadImage(String fileName) {
		Bitmap bitmap = bitmapCache.get(fileName);
		if(bitmap == null){
			try {
				bitmap = BitmapFactory.decodeStream(GameContext.context.getAssets().open(fileName));
				bitmapCache.put(fileName, bitmap);
				logger.info("保存缓存图片: " + fileName);
			} catch (IOException e) {
				try {
					bitmap = BitmapFactory.decodeStream(GameContext.context.getAssets().open("null.png"));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				bitmapCache.put(fileName, bitmap);
				logger.error("没有找到图片：" + fileName);
			}
		} else {
			//System.out.println("从缓存中取图片：" + fileName);
		}
		return bitmap;
	}
	
	/** 按百分比缩放 */
	public static Bitmap zoom(Bitmap bitmap, float scale) {
        if (bitmap == null) {
            return null;
        }
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
        return newbmp;
    }
	
	/**
	 * 该旋转方法放弃，无数次尝试，都没能实现绕一固定中心点旋转，晕死，
	 * 使用3dmax导出旋转序列即可。
	 * @param bitmap
	 * @param degrees
	 * @return
	 */
	public static Bitmap rotate(Bitmap bitmap, float degrees) {
		if (bitmap == null) {
			return null;
		}
		//设置旋转中心
		Matrix matrix = new Matrix();
		matrix.reset();
		matrix.setRotate(degrees, bitmap.getWidth()/2, bitmap.getHeight()/2);
        //filter:true能让图片光滑，去锯齿
        Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return newbmp;
	}
	
	/** 从缓存中获取图片，MovieClip分段中会用到 */
	public Bitmap loadCacheImage(String key) {
		return bitmapCache.get(key);
	}
	
	public void putCacheImage(String key, Bitmap bitmap) {
		bitmapCache.put(key, bitmap);
	}
	
}
