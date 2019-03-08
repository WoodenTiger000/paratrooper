package com.boluomiyu.miyueng.collection;
/**
 * 名称: MaxIndexArray
 * 职责：基于数组的可扩展容器
 * $菠萝蜜语$ 荣誉出品  2013 版权所有.
 * @author 邹彦虎    2013-6-24
 * @version 1.0
 */
public class MaxIndexList<T> {

	/** 最大索引，目前填充数组的最大值 */
	private int maxIndex = 0;
	
	/** 增长大小 */
	private int growLength = 50;
	
	private Object[] array;
	
	public MaxIndexList(int length) {
		array = new Object[length];
	}
	
	public MaxIndexList(int length, int growLength) {
		array = new Object[length];
		this.growLength = growLength;
	}
	
	/** 添加一个新的元素 */
	public synchronized void add(Object o) {
		
		// 长度不够时进行扩展
		if (maxIndex >= array.length-1) {
			Object[] newArray = new Object[array.length + growLength];
			System.arraycopy(array, 0, newArray, 0, array.length);
			array = newArray;
		}
		// 查询空位
		for (int i=0; i<array.length; i++) {
			if (array[i] == null) {
				array[i] = o;
				if (i > maxIndex) {
					maxIndex = i;
				}
				break;
			}
		}
	}
	
	/** 循环遍历最大索引 maxIndex + 1 */
	public int getMaxCount() {
		return maxIndex + 1;
	}
	
	/** 获取数组大小 */
	public int size() {
		int count = 0;
		for (int i=0; i<array.length; i++) {
			if (array[i] != null) {
				count++;
			}
		}
		return count;
	}
	
	public Object[] getArray() {
		return array;
	}
	
	@SuppressWarnings("unchecked")
	public T get(int i) {
		return (T)array[i];
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i=0; i<array.length; i++) {
			if (array[i] != null) {
				sb.append(array[i]);
				sb.append(',');
			}
		}
		if (sb.length() > 0) {
			sb.deleteCharAt(sb.length()-1);
		}
		return sb.toString();
	}
	
	/** 删除索引位置的元素 */
	public void remove(int index) {
		array[index] = null;
		if (index == maxIndex) {
			maxIndex -= 1;
		}
	}
	
	public void set(int index, Object o) {
		array[index] = o;
	}

}
