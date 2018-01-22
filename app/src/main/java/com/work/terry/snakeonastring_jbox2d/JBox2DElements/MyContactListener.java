package com.work.terry.snakeonastring_jbox2d.JBox2DElements;//声明包名

import android.util.Log;

import com.work.terry.snakeonastring_jbox2d.GamePlayView;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.contacts.Contact;

public class MyContactListener implements ContactListener {
	GamePlayView gamePlayView;

	public MyContactListener(GamePlayView gamePlayView) {
		this.gamePlayView = gamePlayView;
	}

	@Override
	public void beginContact(Contact contact){
		Body bodyA = contact.m_fixtureA.getBody();
		Body bodyB = contact.m_fixtureB.getBody();
		String idA = bodyA.getUserData().toString();
		String idB = bodyB.getUserData().toString();
		Log.d("Contact","BodyA:"+bodyA.getUserData().toString()+"  BodyB:"+bodyB.getUserData().toString());

		if(!gamePlayView.snake.isDead()){
			if(idA.equals("snakeHead")){
				if(!idB.equals("snakeBody1")){
					gamePlayView.snake.setDead();
//					bodyA.setLinearDamping(0.07f);
//					bodyB.setLinearDamping(0.10f);
				}
			}else if(idB.toString().equals("snakeHead")){
				if(!idA.equals("snakeBody1")){
					gamePlayView.snake.setDead();
//					bodyA.setLinearDamping(0.10f);
//					bodyB.setLinearDamping(0.07f);
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
