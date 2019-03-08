package com.boluomiyu.miyueng.resource;
/**
 * 声音
 * @author 邹彦虎
 * 俱乐部  --- 
 */
public class Sound {

	
	public Sound(long length) {
		this.length = length;
	}
	
	public int soundId;
	
	public boolean isPlaying = false;
	/** 总长度 */
	public long length = 0;
	/** 游标 */
	public long cursor = 0;
	
	public void play(){
		SoundManager.getInstance().playSound(soundId);
	}
	
}
