package com.work.terry.snakeonastring_jbox2d.Thread;

import android.util.Log;

import com.work.terry.snakeonastring_jbox2d.JBox2DElements.CircleBody;
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
    public static byte[] JBox2DLock = new byte[0];
    GamePlay gamePlay;

    public JBox2DThread(GamePlay gamePlay){
        this.gamePlay = gamePlay;
    }
    @Override
    public void run(){
        while(gamePlay.IS_PLAYING)//&&!gamePlayView.snake.isDead())
        {
            synchronized (JBox2DLock){
                gamePlay.world.step(JBOX2D_TIME_STEP, JBOX2D_ITERA, JBOX2D_ITERA);//开始模拟
            }

            synchronized (JBox2DUtil.Bodies) {
                for (MyBody mb : JBox2DUtil.Bodies) {
                    mb.popXYfromBody();

                    if (mb instanceof SnakeFood) {
                        if (((SnakeFood) mb).eatean) {
                                gamePlay.drawUtil.deleteElement(mb);
                            gamePlay.removeFood(mb.getId());
                            //mb.setDoDraw(false);

                            gamePlay.world.destroyBody(mb.body);
                        }
                    }
                }
            }
            gamePlay.checkShouldAddFood();
            if (gamePlay.snake.isDead()) {
                gamePlay.snake.doAfterDead();
            }else {
                gamePlay.snake.checkLength();
            }
        }
    }
}
