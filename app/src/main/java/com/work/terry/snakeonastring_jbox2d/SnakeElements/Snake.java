package com.work.terry.snakeonastring_jbox2d.SnakeElements;


import android.util.Log;

import com.work.terry.snakeonastring_jbox2d.GameElements;
import com.work.terry.snakeonastring_jbox2d.JBox2DElements.CircleBody;
import com.work.terry.snakeonastring_jbox2d.Util.ColorManager;
import com.work.terry.snakeonastring_jbox2d.Util.Constant;
import com.work.terry.snakeonastring_jbox2d.Util.DrawUtil;
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
    private int color = Constant.C0LOR_WHITE;
    private boolean isDead = false;
    private DrawUtil drawUtil;

    public Snake(World world,int color,DrawUtil drawUtil){
        this.world = world;
        this.color = color;
        this.drawUtil = drawUtil;

        snakeBodies = new ArrayList<>();

        createHead();
        for(int i = 1;i<=Constant.SnakeBodyDefaultLength;i++){
            addBody();
        }

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
                snakeHead.jumpHeight = MyMath.JumpMath(Constant.SnakeDefaultHeight + 10 ,Constant.JumpMathFactor,time);
                else {
                    snakeBodies.get(index).jumpHeight =  MyMath.JumpMath(Constant.SnakeDefaultHeight  + 10 ,Constant.JumpMathFactor,time);
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

    public void createHead(){
        snakeHead = new SnakeHead(this,world,720,1280,0,1,this.color,Constant.SnakeDefaultHeight);
        snakeBodies.add(snakeHead);
        drawUtil.addToCenterLayer(snakeHead);
    }
    public void addBody(){
        int index = snakeBodies.size();
        SnakeNode tempt;
        if(index==1){
            tempt = new SnakeNode(this,world,snakeHead,index);
        }else{
            tempt = new SnakeNode(this,world,(SnakeNode) snakeBodies.get(index-1),index);
        }
        snakeBodies.add(tempt);
        drawUtil.addToCenterLayer(tempt);
    }
    public void moving(){
        snakeHead.startMoving();
    }
    public void whenMotionDown(int x,int y){
        snakeHead.whenMotionDown(x,y);
    }

    public void setColor(int index){
       this.color = index;
       for (GameElements g:snakeBodies){
           g.color = color;
       }
    }


    public void onResume(){

    }
    public void onPause(){

    }

}
