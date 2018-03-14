package com.work.terry.snakeonastring_jbox2d.JBox2DElements;

import android.util.Log;

import com.work.terry.snakeonastring_jbox2d.SurfaceViewAndActivity.GamePlay;
import com.work.terry.snakeonastring_jbox2d.SurfaceViewAndActivity.GamePlayView;

import org.jbox2d.callbacks.ContactFilter;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.Fixture;

public class MyContactFilter extends ContactFilter//碰撞过滤相关类
{
	GamePlay gamePlay;
	public MyContactFilter(GamePlay gamePlay){this.gamePlay = gamePlay;	}//构造器
	@Override
	public boolean shouldCollide(Fixture fixtureA, Fixture fixtureB)///检验是否碰撞的方法
	{
		Body bodyA= fixtureA.getBody();
		Body bodyB= fixtureB.getBody();
		String idA = bodyA.getUserData().toString();
		String idB = bodyB.getUserData().toString();
		int idANum = 0;
		int idBNum = 0;

		if(idA.contains("snakeFood")||idB.contains("snakeFood")) {

			if(idA.contains("snakeFood")){
//				Log.d("ContactFilter",idA+" contains snakeFood");
				idANum = Integer.parseInt(idA.split(" ")[1]);
				if(gamePlay.getFood(idANum).eatean){
					return false;
				}else if(idB.contains("snakeBody")){
					return false;
				}
			}else if(idB.contains("snakeFood")){
//				Log.d("ContactFilter",idB+" contains snakeFood");
				idBNum = Integer.parseInt(idB.split(" ")[1]);
				if(gamePlay.getFood(idBNum).eatean){
					return false;
				}else if(idA.contains("snakeBody")){
					return false;
				}
			}

		}else if(idA.contains("Bomb")||idB.contains("Bomb")){

			if(idA.contains("Bomb")){
//				Log.d("ContactFilter",idA+" contains snakeFood");
				idANum = Integer.parseInt(idA.split(" ")[1]);
				if(gamePlay.getBomb(idANum).eatean){
					return false;
				}else if(idB.contains("snakeBody")){
					return false;
				}
			}else if(idB.contains("Bomb")){
//				Log.d("ContactFilter",idB+" contains snakeFood");
				idBNum = Integer.parseInt(idB.split(" ")[1]);
				if(gamePlay.getBomb(idBNum).eatean){
					return false;
				}else if(idA.contains("snakeBody")){
					return false;
				}
			}
		}else if(idA.contains("FoodMagnet")||idB.contains("FoodMagnet")){

			if(idA.contains("FoodMagnet")){
//				Log.d("ContactFilter",idA+" contains snakeFood");
				idANum = Integer.parseInt(idA.split(" ")[1]);
				if(gamePlay.getFoodMagnet(idANum).eatean){
					return false;
				}else if(idB.contains("snakeBody")){
					return false;
				}
			}else if(idB.contains("FoodMagnet")){
//				Log.d("ContactFilter",idB+" contains snakeFood");
				idBNum = Integer.parseInt(idB.split(" ")[1]);
				if(gamePlay.getFoodMagnet(idBNum).eatean){
					return false;
				}else if(idA.contains("snakeBody")){
					return false;
				}
			}

		}else if(idA.contains("CircleButtonBlock")&&idB.contains("CircleButtonBlock")){
			return false;
		}
		else if(idA.contains("snakeBody")&&idB.contains("Wall")){
			return false;
		}

		return true;
	}
}
