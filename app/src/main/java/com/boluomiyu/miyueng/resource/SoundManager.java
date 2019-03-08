package com.boluomiyu.miyueng.resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.boluomiyu.miyueng.GameContext;
import com.boluomiyu.miyueng.util.Logger;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.SoundPool;

/**
 * 类 SoundManager
 * 描述：音频处理
 * $菠萝蜜语$ 荣誉出品  2013 版权所有.
 * @author 邹彦虎    2013-2-14
 * @version 1.0
 */
@SuppressLint("UseSparseArrays")
public class SoundManager {
	
	private static final Logger logger = Logger.getLogger(SoundManager.class);
	
	private SoundPool soundPool;
	
	private Map<Integer, Sound> soundMap;
	
	private static SoundManager soundManager;
	
	/** 获取单例 */
	public static SoundManager getInstance() {
		if(soundManager == null){
			soundManager = new SoundManager();
		}
		return soundManager;
	}
	
	private SoundManager() {
		
		// TODO 音效池初始化，音效初始化还未完成
		
		//初始化音频句柄Map
		soundMap = new HashMap<Integer, Sound>();
		//初始化soundPool对象,第一个参数是允许有多少个声音流同时播放,第2个参数是声音类型,第三个参数是声音的品质
		soundPool = new SoundPool(20, AudioManager.STREAM_MUSIC, 0);
		
		Sound sPressed = new Sound(70);
		sPressed.soundId = soundPool.load(this.getPath("btn_pressed.wav"), 1);
		soundMap.put(SoundList.BTN_PRESSED, sPressed);
		
		Sound sBladeMove1 = new Sound(400);
		sBladeMove1.soundId = soundPool.load(this.getPath("blade_move.ogg"), 1);
		soundMap.put(SoundList.BLADE_MOVE, sBladeMove1);
		
		Sound sBladeOver = new Sound(400);
		sBladeOver.soundId = soundPool.load(this.getPath("blade_over.wav"), 1);
		soundMap.put(SoundList.BLADE_OVER, sBladeOver);
		
		Sound sMainGunShoot = new Sound(400);
		sMainGunShoot.soundId = soundPool.load(this.getPath("gunshoot.ogg"), 1);
		soundMap.put(SoundList.MAINGUN_SHOOT, sMainGunShoot);
		
	}
	/**
	 * 播放声音
	 * @param soundId
	 * @param loopCount
	 */
	public void playSound(int soundId, int loopCount){
		//实例化AudioManager对象
		AudioManager am = (AudioManager) GameContext.context.getSystemService(Context.AUDIO_SERVICE);
		//返回最大音量值
		float audioMaxVolumn = am.getStreamMaxVolume(AudioManager.STREAM_SYSTEM);
		//返回当前音量
		float audioVolumn = am.getStreamVolume(AudioManager.STREAM_SYSTEM);
		//音频率
		float volumnRatio = 1;
		Sound sound = this.soundMap.get(soundId);
		//logger.info("MaxVolumn: " + audioMaxVolumn + "volumn: " + audioVolumn);
		soundPool.play(sound.soundId, volumnRatio, volumnRatio, 4, loopCount, 1);
	}
	
	/**
	 * 播放音频
	 * @param soundId
	 */
	public void playSound(int soundId){
		this.playSound(soundId, 0);
	}

	public Sound getSound(int soundId) {
		return this.soundMap.get(soundId);
	}
	
	public AssetFileDescriptor getPath(String fileName){
		try {
			return GameContext.context.getAssets().openFd("sounds/" + fileName);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}
