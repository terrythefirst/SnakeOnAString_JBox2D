package com.work.terry.snakeonastring_jbox2d.auto;

public class Main
{
	public static void main(String args[])
	{
		ScreenScaleResult ssr=ScreenScaleUtil.calScale(640,400);
		System.out.println("1280,800");
		System.out.println(ssr);
		
		int[] after=ScreenScaleUtil.touchFromTargetToOrigin(640, 40, ssr);
		System.out.println(after[0]+"|"+after[1]);
	}
}