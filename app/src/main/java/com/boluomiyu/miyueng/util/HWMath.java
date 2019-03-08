package com.boluomiyu.miyueng.util;

/**
 * 类 HWMath
 * 描述：
 * $菠萝蜜语$ 荣誉出品  2013 版权所有.
 * @author 邹彦虎    2013-2-15
 * @version 1.0
 */
public class HWMath {
	
	public static final double RADDEGREE_MID = 180/3.14;
	
	/**
	 * 根据方程的abc求解方程的解
	 * @param a
	 * @param b
	 * @param c
	 */
	public static double[] quadraticequation(double a, double b, double c){
		double x1=0, x2=0;
		double d = b * b - 4 * a * c;
		if (d < 0){
			
		} else if (d == 0) {
			x1 = x2 = ((-b) / (2 * a));
		} else {
			x1 = (((-b) + Math.sqrt(d)) / (2 * a));
			x2 = (((-b) - Math.sqrt(d)) / (2 * a));
		}
		
		return  new double[]{x1, x2};
	}
	
	/**
	 * 取在degree+-delta范围内，最接近degree的值
	 * @param degree
	 * @param delta
	 * @return
	 */
	public static int round(float degree, int delta) {
		float d = degree%delta;
		if (d >= delta) {
			return (int)(degree - d + delta);
		} else {
			return (int)(degree - d);
		}
	}
	
	
	/** 弧度转角度 */
	public static float rad2degree(double rad) {
		return (float)(HWMath.RADDEGREE_MID * rad);
	}
	/** 角度转弧度 */
	public static double degree2rad(float degree) {
		return (float) (degree/HWMath.RADDEGREE_MID);
	}
	
	/** 生成浮点型随机数 */
	public static float rand(float start, float end) {
		return (float)(start + Math.random()*(end-start));
	}
	/** 生成整形随机数 */
	public static int rand(int start, int end) {
		return (int)(start + Math.random()*(end-start));
	}
	
	
	
	
}
