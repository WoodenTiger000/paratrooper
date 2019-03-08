package com.boluomiyu.miyueng.resource;

import java.util.ArrayList;
import java.util.List;

/**
 * 剧本
 * @author 邹彦虎
 * 俱乐部  --- 
 */
public class PlayBook {
	
	/** 当前剧本执行位置 */
	public int current = 0;
	/** 剧本语句 */
	public List<String> statementList;
	/** 语句延迟 */
	public long delay;
	
	public PlayBook(String fileContext) {
		statementList = new ArrayList<String>();
		String[] statementArray = fileContext.split("\n");
		for(int i=0; i<statementArray.length; i++){
			statementList.add(statementArray[i]);
		}
	}
	/** 获取下一条剧本，如果没有了返回null */
	public String getNext() {
		//总语句数小于1，则表示没有下一条
		if(statementList.size() < 1){
			return null;
		} else {
			if(current < statementList.size()){
				String rs = statementList.get(current);
				current++;
				return rs;
			} else{
				return null;
			}
		}
	}
	
	
	
	
	
}
