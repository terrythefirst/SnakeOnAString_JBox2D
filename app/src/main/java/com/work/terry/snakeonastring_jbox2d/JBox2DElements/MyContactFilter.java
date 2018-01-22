package com.work.terry.snakeonastring_jbox2d.JBox2DElements;

import android.app.Activity;

import com.work.terry.snakeonastring_jbox2d.GamePlayView;

import org.jbox2d.callbacks.ContactFilter;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.Fixture;

public class MyContactFilter extends ContactFilter//碰撞过滤相关类
{
	GamePlayView gamePlayView;
	public MyContactFilter(GamePlayView gamePlayView){this.gamePlayView = gamePlayView;	}//构造器
	@Override
	public boolean shouldCollide(Fixture fixtureA, Fixture fixtureB)///检验是否碰撞的方法
	{
		Body bodyA= fixtureA.getBody();
		Body bodyB= fixtureB.getBody();
		String idA = bodyA.getUserData().toString();
		String idB = bodyB.getUserData().toString();
		if(idA.matches("snakeBody.Rect")||idB.matches("snakeBody.Rect"))
		{
			if (idB.matches("^snake(Head)|(Body[0-9])&")){
				if (idA.equals("snakeBody1")&&idB.equals("snakeHead")){
					return false;
				}
			}
			return false;
		}else
		{
			return true;
		}
		//return true;
	}
}
