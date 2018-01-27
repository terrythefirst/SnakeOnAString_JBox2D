package com.work.terry.snakeonastring_jbox2d.Thread;

import android.util.Log;

import com.work.terry.snakeonastring_jbox2d.SnakeElements.SnakeFood;
import com.work.terry.snakeonastring_jbox2d.SurfaceViewAndActivity.GamePlay;
import com.work.terry.snakeonastring_jbox2d.SurfaceViewAndActivity.GamePlayView;
import com.work.terry.snakeonastring_jbox2d.JBox2DElements.MyBody;
import com.work.terry.snakeonastring_jbox2d.JBox2DElements.MyBox2DRevoluteJoint;
import com.work.terry.snakeonastring_jbox2d.JBox2DElements.MyWeldJoint;
import com.work.terry.snakeonastring_jbox2d.SnakeElements.SnakeHead;
import com.work.terry.snakeonastring_jbox2d.SnakeElements.SnakeNode;
import com.work.terry.snakeonastring_jbox2d.Util.JBox2DUtil;

import static com.work.terry.snakeonastring_jbox2d.Util.Constant.*;

/**
 * Created by Terry on 2018/1/13.
 */

public class JBox2DThread extends Thread{
    GamePlay gamePlay;

    public JBox2DThread(GamePlay gamePlay){
        this.gamePlay = gamePlay;
    }
    @Override
    public void run(){
        while(gamePlay.IS_PLAYING)//&&!gamePlayView.snake.isDead())
        {
            //if(!gamePlayView.snake.initSelfFinished)continue;
            gamePlay.world.step(JBOX2D_TIME_STEP, JBOX2D_ITERA,JBOX2D_ITERA);//开始模拟

            for(MyBody mb:JBox2DUtil.Bodies){
//                    if(gamePlayView.snake.isDead()) mb.popXYfromBody();
//                    else mb.pushXYintoBody();
//                    if(mb instanceof SnakeNode){
//                        if(gamePlayView.snake.isDead()){
//                            mb.popXYfromBody();
//                        }else {
//                            mb.pushXYintoBody();
//                        }
//                    }else if(mb instanceof SnakeHead){
//                        mb.popXYfromBody();
//                    }
                    mb.popXYfromBody();
                    if (mb instanceof SnakeHead){
                        //Log.d("snakeHeadAngle","bodyA="+mb.body.getAngle()+" rotateA="+Math.toRadians(((SnakeHead) mb).rotateAngle));
                    }
                    if (gamePlay.snake.isDead()){
                        mb.body.setLinearDamping(0.08f);
                        if(mb instanceof SnakeNode)
                            gamePlay.world.destroyBody(((SnakeNode) mb).rectBody.body);
                    }
                    if(mb instanceof SnakeFood){
                        if(((SnakeFood)mb).eatean ){
                            gamePlay.drawUtil.deleteElement(mb);
                            gamePlay.world.destroyBody(mb.body);
                        }
                    }
//                    Log.d(
//                            "info"+mb.body.getUserData().toString(),
//                            "x="+mb.body.getPosition().x*RATE
//                                    +" y="+mb.body.getPosition().y*RATE
//                                    +" vX="+mb.getBodyVelocityNormalized().x
//                                    +" vY="+mb.getBodyVelocityNormalized().y
//                    );
                    //if(mb instanceof SnakeHead)((SnakeHead)mb).pushHeadVXYtoBody();
            }
            if (gamePlay.snake.isDead()){
                for (Object o:JBox2DUtil.Joints){
                    if (o instanceof MyBox2DRevoluteJoint){
                        gamePlay.world.destroyJoint(((MyBox2DRevoluteJoint) o).rjoint);
                    }else if (o instanceof MyBox2DRevoluteJoint){
                        gamePlay.world.destroyJoint(((MyWeldJoint) o).wj);
                    }
                }
            }
            while (!gamePlay.snake.isDead()&&gamePlay.snake.SnakeAddLength>0){
                gamePlay.snake.addBody();
                gamePlay.snake.SnakeAddLength--;
            }

        }
    }
}
