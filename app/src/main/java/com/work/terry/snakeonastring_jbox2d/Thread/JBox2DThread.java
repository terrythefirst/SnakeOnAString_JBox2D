package com.work.terry.snakeonastring_jbox2d.Thread;

import android.util.Log;

import com.work.terry.snakeonastring_jbox2d.GamePlayElements.ButtonBlockCircle;
import com.work.terry.snakeonastring_jbox2d.JBox2DElements.CircleBody;
import com.work.terry.snakeonastring_jbox2d.JBox2DElements.JBox2dThreadTask;
import com.work.terry.snakeonastring_jbox2d.JBox2DElements.MyJoint;
import com.work.terry.snakeonastring_jbox2d.SnakeElements.Bomb;
import com.work.terry.snakeonastring_jbox2d.SnakeElements.FoodMagnet;
import com.work.terry.snakeonastring_jbox2d.SnakeElements.SnakeFood;
import com.work.terry.snakeonastring_jbox2d.SurfaceViewAndActivity.GamePlay;
import com.work.terry.snakeonastring_jbox2d.JBox2DElements.MyBody;

import org.jbox2d.dynamics.Body;

import java.util.ArrayList;
import java.util.List;

import static com.work.terry.snakeonastring_jbox2d.Util.Constant.*;

/**
 * Created by Terry on 2018/1/13.
 */

public class JBox2DThread extends Thread implements Stoppable{
    public List<MyBody> Bodies= new ArrayList<>();//所有对Bodies的操作要求再 STEP期间做
//    public List<MyBody> removeBodyList = new ArrayList<>();
    public List<MyJoint> Joints = new ArrayList<>();

    public List<JBox2dThreadTask> tasks = new ArrayList<>();

    boolean shouldDie = false;
    GamePlay gamePlay;

    public void setShouldDie(){
        shouldDie = true;
    }
    public JBox2DThread(GamePlay gamePlay){
        this.gamePlay = gamePlay;
    }
    //long timeStamp = 0;
    @Override
    public void run(){
        while (!shouldDie)//&&!gamePlayView.snake.isDead())
        {
            if (!gamePlay.IS_PLAYING) {
                try {
                    sleep(100);
                    continue;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            gamePlay.world.step(JBOX2D_TIME_STEP, JBOX2D_ITERA, JBOX2D_ITERA);//开始模拟

            MyJBox2DStep();
            stepTasks();

            gamePlay.checkShouldAddFoodOrBomb();

            if (gamePlay.snake.isDead()) {
                gamePlay.snake.doAfterDead();
            } else {
                gamePlay.snake.checkLength();
                if (gamePlay.snake.isMagnetic)
                    gamePlay.snake.searchWithin();
            }
            //timeStamp = currTimeStamp;
            try {
                sleep((long)JBOX2D_TIME_STEP*1000);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    public void MyJBox2DStep(){
        for (MyBody mb : Bodies) {
            mb.popXYfromBody();
            //mb.pushWidthHeightIntoBody();
//            if(mb instanceof ButtonBlockCircle){
//                ((ButtonBlockCircle)mb).body.getFixtureList().getShape().setRadius((((ButtonBlockCircle) mb).radius+((ButtonBlockCircle) mb).scaleWidth/2)/RATE);
//            }
            if(mb instanceof CircleBody){
                mb.body.getFixtureList().getShape().setRadius((((CircleBody)mb).radius+((CircleBody)mb).scaleWidth/2)/RATE);
            }
            if (mb instanceof SnakeFood) {
                if (((SnakeFood) mb).eatean) {
                    mb.setDoDraw(false);
                    addToTasks(new JBox2dThreadTask(JBox2dThreadTask.OPERATION_DELETE_BODY,mb));
                }
            }
            if(mb instanceof Bomb){
                if(((Bomb)mb).eatean){
                    mb.setDoDraw(false);
                    addToTasks(new JBox2dThreadTask(JBox2dThreadTask.OPERATION_DELETE_BODY,mb));
                }
            }
            if(mb instanceof FoodMagnet){
                if(((FoodMagnet)mb).eatean){
                    mb.setDoDraw(false);
                    addToTasks(new JBox2dThreadTask(JBox2dThreadTask.OPERATION_DELETE_BODY,mb));
                }
            }
        }

    }
    public void addToTasks(JBox2dThreadTask task){
        synchronized (tasks){
            tasks.add(task);
        }
    }
    public void stepTasks(){
        synchronized (tasks){
            for(JBox2dThreadTask task:tasks){
                if(task.operation==JBox2dThreadTask.OPERATION_DELETE_BODY)Bodies.remove(task.myBody);
                task.doTask();
            }
        }
        tasks.clear();
    }
}
