package com.work.terry.snakeonastring_jbox2d.JBox2DElements;//声明包名

import android.util.Log;

import com.work.terry.snakeonastring_jbox2d.SnakeElements.FoodMagnet;
import com.work.terry.snakeonastring_jbox2d.SnakeElements.SnakeFood;
import com.work.terry.snakeonastring_jbox2d.SnakeElements.SnakeNode;
import com.work.terry.snakeonastring_jbox2d.SurfaceViewAndActivity.GamePlay;
import com.work.terry.snakeonastring_jbox2d.SnakeElements.SnakeHead;
import com.work.terry.snakeonastring_jbox2d.Thread.FoodMagnetSearchThread;
import com.work.terry.snakeonastring_jbox2d.Util.JBox2DUtil;
import com.work.terry.snakeonastring_jbox2d.Util.VectorUtil;

import static com.work.terry.snakeonastring_jbox2d.Util.VectorUtil.*;
import static com.work.terry.snakeonastring_jbox2d.Util.Constant.*;
import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.contacts.Contact;



public class MyContactListener implements ContactListener {
	GamePlay gamePlay;

	public MyContactListener(GamePlay gamePlay) {
		this.gamePlay = gamePlay;
	}

	public void changeSnakeVelocityUponDead(){//(Body head,Body body){
		for(CircleBody circleBody:gamePlay.snake.snakeBodies){
			Body bb = circleBody.body;
			circleBody.body.setLinearDamping(0.01f);
			circleBody.body.setAngularDamping(0.01f);
			if(circleBody instanceof SnakeHead){
						bb.getLinearVelocity().set(
						Mul2D(
								normalize2D(bb.getLinearVelocity()),
								SnakeHeadSpeedUponDead
						)
				);
			}else {
						bb.getLinearVelocity().set(
						Mul2D(
								normalize2D(((SnakeNode)circleBody).getFrontV2D()),
								SnakeBodySpeedUponDead
						)
				);
			}
		}
	}
	@Override
	public void beginContact(Contact contact){
		Body bodyA = contact.m_fixtureA.getBody();
		Body bodyB = contact.m_fixtureB.getBody();
		String idA = bodyA.getUserData().toString();
		String idB = bodyB.getUserData().toString();
		Log.d("Contact","BodyA:"+idA+"  BodyB:"+idB);

		if(!gamePlay.snake.isDead()){
			if(idA.equals("snakeHead")){
				if(idB.contains("snakeFood")){
					Log.d("ContactListener",idA+" equals snakeHead "+idB+" contains snakeFood");
					SnakeFood sf = gamePlay.getFood(Integer.parseInt(idB.split(" ")[1]));
					sf.setEaten();
					gamePlay.scoreBoard.plusScore(sf.score);
					gamePlay.snake.startAnAddJumpAnimationThread();
					gamePlay.snake.plusOneSnakeAjaxLength();
				}else  if(idB.contains("Bomb")){
					gamePlay.getBomb(Integer.parseInt(idB.split(" ")[1])).setEaten();
					gamePlay.snake.startARemoveJumpAnimationThread();
					gamePlay.snake.minusOneSnakeAjaxLength();
				}else if(idB.contains("FoodMagnet")){
					FoodMagnet foodMagnet = gamePlay.getFoodMagnet(Integer.parseInt(idB.split(" ")[1]));
					foodMagnet.setEaten();
					gamePlay.snake.whenEatFoodMagnet(foodMagnet);
				}else if(!idB.equals("snakeBody 1")){
//					if(contact.getManifold().type == Manifold.ManifoldType.CIRCLES){
//
//					}
//					Vec2 localNormal = contact.getManifold().localNormal.skew();
//					Vec2 localPoint = contact.getManifold().localPoint;
//
//					float dotMul = dotMul2D(
//							plusV2D(localNormal,localPoint),
//							gamePlay.snake.snakeHead.getBodyVelocityNormalized()
//					);
//					Log.d("dotMul",dotMul+"");
					float v = VectorUtil.calDistance(gamePlay.snake.snakeHead.body.getLinearVelocity());
					Log.d("velocity",v+"");
					if(v<=1.5f){
						gamePlay.snake.setDizzy();
					}else {
						gamePlay.snake.setDead();
						changeSnakeVelocityUponDead();
					}
				}
			}else if(idB.toString().equals("snakeHead")){
				if(idA.contains("snakeFood")){
					Log.d("ContactListener",idB+" equals snakeHead "+idA+" contains snakeFood");
					SnakeFood sf = gamePlay.getFood(Integer.parseInt(idA.split(" ")[1]));
					sf.setEaten();
					gamePlay.scoreBoard.plusScore(sf.score);
					gamePlay.snake.startAnAddJumpAnimationThread();
					gamePlay.snake.plusOneSnakeAjaxLength();
				}else  if(idA.contains("Bomb")){
					gamePlay.getBomb(Integer.parseInt(idA.split(" ")[1])).setEaten();
					gamePlay.snake.startARemoveJumpAnimationThread();
					gamePlay.snake.minusOneSnakeAjaxLength();
				}else if(idA.contains("FoodMagnet")){
					FoodMagnet foodMagnet = gamePlay.getFoodMagnet(Integer.parseInt(idA.split(" ")[1]));
					foodMagnet.setEaten();
					gamePlay.snake.whenEatFoodMagnet(foodMagnet);
				}else if(!idA.equals("snakeBody 1")){
//					Vec2 localNormal = contact.getManifold().localNormal;
//					float dotMul = dotMul2D(localNormal,gamePlay.snake.snakeHead.getBodyVelocityNormalized());
//					Log.d("dotMul",dotMul+"");
					float v = VectorUtil.calDistance(gamePlay.snake.snakeHead.body.getLinearVelocity());
					Log.d("velocity",v+"");
					if(v<=1.5f){
						gamePlay.snake.setDizzy();
					}else {
						gamePlay.snake.setDead();
						changeSnakeVelocityUponDead();
					}
				}
			}
		}
	}
	@Override
	public void endContact(Contact contact){}
	@Override
	public void preSolve(Contact contact, Manifold oldManifold){}
	@Override
	public void postSolve(Contact contact, ContactImpulse impulse){}
}
