package com.work.terry.snakeonastring_jbox2d.SnakeElements;


import android.util.Log;

import com.work.terry.snakeonastring_jbox2d.GameElements;
import com.work.terry.snakeonastring_jbox2d.JBox2DElements.CircleBody;
import com.work.terry.snakeonastring_jbox2d.Util.ColorManager;
import com.work.terry.snakeonastring_jbox2d.Util.Constant;
import com.work.terry.snakeonastring_jbox2d.Util.MyMath;
import com.work.terry.snakeonastring_jbox2d.Util.TexDrawer;

import org.jbox2d.dynamics.World;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Terry on 2017/12/28.
 */

public class Snake {
    public boolean initSelfFinished = false;

    public World world;
    private SnakeHead snakeHead;
    public List<CircleBody> snakeBodies = null;//包括头！！！
    private AnimateThread animateThread = null;
    private static float[] Color = ColorManager.getColor(Constant.C0LOR_WHITE);
    private boolean isDead = false;
    private List<GameElements> drawSequence = null;

    public Snake(World world){
        this.world = world;
        snakeHead = new SnakeHead(this,world,720,1280,0,1);
        snakeBodies = new ArrayList<>();
        snakeBodies.add(snakeHead);
        for(int i = 1;i<=Constant.SnakeBodyDefaultLength;i++){
            addBody();
        }
        drawSequence = new ArrayList<GameElements>();
        for(int i = 0;i<snakeBodies.size();i++){
            GameElements tempt = snakeBodies.get(i);
            drawSequence.add(tempt);
        }
        //calDrawSequence();
        animateThread = new AnimateThread();
        animateThread.start();
    }
    public int getSize(){
        return snakeBodies.size();
    }
    private class AnimateThread extends Thread{
        @Override
        public void run(){
            int time = 0;
            int index = -1;
            int timeLimit = (int) (2*Constant.JumpMathFactor);
            while (true){
                if(index==-1)
                snakeHead.jumpHeight = MyMath.JumpMath(Constant.SnakeDownHeight + 10 ,Constant.JumpMathFactor,time);
                else {
                    snakeBodies.get(index).jumpHeight =  MyMath.JumpMath(Constant.SnakeDownHeight + 10 ,Constant.JumpMathFactor,time);
                }

                if(time<timeLimit){
                    time++;
                }else {
                    time = 0;
                    index++;
                    if(index>=snakeBodies.size())index = -1;
                }

                try {
                    sleep(50);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

        }
    }
    public void setDead(){
        Log.d("snake","DEAD!");isDead = true;
    }
    public boolean isDead(){
        return isDead;
    }
    public void addBody(){
        int index = snakeBodies.size();
        if(index==1){
            snakeBodies.add(new SnakeNode(this,world,snakeHead,index));
        }else{
            snakeBodies.add(new SnakeNode(this,world,(SnakeNode) snakeBodies.get(index-1),index));
        }
    }
    public void drawSelf(TexDrawer painter) {
        snakeBodies.stream()
                .sorted(Comparator.comparing(x -> x.y))
                .forEach(
                        x -> {
                            x.drawFloorShadow(painter, Color);
                        }
                );
        snakeBodies.stream()
                .sorted(Comparator.comparing(x -> x.y))
                .forEach(
                        x -> {
                            x.drawHeightShadow(painter, Color);
                            x.drawSelf(painter, Color);
                        }
                );
    }
    public void moving(){
        snakeHead.startMoving();
    }
    public void whenMotionDown(int x,int y){
        snakeHead.whenMotionDown(x,y);
    }

    public void setColor(int index){
        Color = ColorManager.getColor(index);
    }


    public void onResume(){

    }
    public void onPause(){

    }

}
