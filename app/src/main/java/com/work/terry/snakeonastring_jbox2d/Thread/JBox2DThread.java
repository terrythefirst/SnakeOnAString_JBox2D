package com.work.terry.snakeonastring_jbox2d.Thread;

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
    GamePlayView gamePlayView;

    public JBox2DThread(GamePlayView gamePlayView){
        this.gamePlayView = gamePlayView;
    }
    @Override
    public void run(){
            try{
                sleep(1000);
            }catch (Exception e){
                e.printStackTrace();
            }
        while(gamePlayView.IS_PLAYING)//&&!gamePlayView.snake.isDead())
        {
            //if(!gamePlayView.snake.initSelfFinished)continue;
            gamePlayView.world.step(JBOX2D_TIME_STEP, JBOX2D_ITERA,JBOX2D_ITERA);//开始模拟
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
                    if (gamePlayView.snake.isDead()){
                        mb.body.setLinearDamping(0.08f);
                        if(mb instanceof SnakeNode)
                            gamePlayView.world.destroyBody(((SnakeNode) mb).rectBody.body);
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
            if (gamePlayView.snake.isDead()){
                for (Object o:JBox2DUtil.Joints){
                    if (o instanceof MyBox2DRevoluteJoint){
                        gamePlayView.world.destroyJoint(((MyBox2DRevoluteJoint) o).rjoint);
                    }else if (o instanceof MyBox2DRevoluteJoint){
                        gamePlayView.world.destroyJoint(((MyWeldJoint) o).wj);
                    }
                }
            }

        }
    }
}
