package com.work.terry.snakeonastring_jbox2d.SnakeElements;


import android.content.SharedPreferences;
import android.util.Log;

import com.work.terry.snakeonastring_jbox2d.Thread.SnakeJumpAnimationThread;
import com.work.terry.snakeonastring_jbox2d.UI.GameElements;
import com.work.terry.snakeonastring_jbox2d.JBox2DElements.CircleBody;
import com.work.terry.snakeonastring_jbox2d.JBox2DElements.MyJoint;
import com.work.terry.snakeonastring_jbox2d.Thread.SnakeNodeAppendAnimateThread;
import com.work.terry.snakeonastring_jbox2d.Thread.SnakeNodeRemoveAnimateThread;
import com.work.terry.snakeonastring_jbox2d.Util.Constant;
import com.work.terry.snakeonastring_jbox2d.Util.DrawUtil;
import com.work.terry.snakeonastring_jbox2d.Util.JBox2DUtil;
import com.work.terry.snakeonastring_jbox2d.Util.MyMath;

import org.jbox2d.dynamics.World;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 * Created by Terry on 2017/12/28.
 */

public class Snake {
    public Queue<Thread> addJumpAnimationThreads = new LinkedList<Thread>();
    public Queue<Thread> removeJumpAnimationThreads = new LinkedList<Thread>();

    public byte[] snakeAjaxLengthLock = new byte[0];
    public int SnakeAjaxLength ;

    public boolean initFinnished = false;

    public byte[] addAnimationLock = new byte[0];
    public boolean addAnimating = false;

    public World world;
    private SnakeHead snakeHead;
    public List<CircleBody> snakeBodies = null;//包括头！！！
    private int color = Constant.C0LOR_WHITE;
    public boolean paused = false;
    public boolean isDead = false;
    private DrawUtil drawUtil;

    public Snake(World world,int color,DrawUtil drawUtil){
        this.world = world;
        this.color = color;
        this.drawUtil = drawUtil;

        snakeBodies = new ArrayList<>();

        snakeHead = new SnakeHead(this,world,720,1280,0,1,this.color,Constant.SnakeDefaultHeight);
        snakeBodies.add(snakeHead);
        drawUtil.addToCenterLayer(snakeHead);

        for(int i = 1;i<=Constant.SnakeBodyDefaultLength;i++){
            addBody();
        }
        SnakeAjaxLength = getLength();

        //animateThread = new AnimateThread();
       // animateThread.start();

        initFinnished = true;
    }

    public void setDead(){
        Log.d("snake","DEAD!");
        isDead = true;
        snakeHead.changeFace(Constant.SnakeHeadDeadEyesImg);//Constant.SnakeHeadDizzyEyesImg);
    }
    public boolean isDead(){
        return isDead;
    }

    public int getSize(){
        return snakeBodies.size();
    }

    public boolean isPaused(){return paused;}

    public void startAnAddJumpAnimationThread(){
        Thread thread = new SnakeJumpAnimationThread(this,getLength());
        addJumpAnimationThreads.offer(thread);
        thread.start();
    }
    public Thread getAnEarliestAddJumpAnimationThread(){
        Thread thread = addJumpAnimationThreads.poll();
        return thread;
    }

    public void startARemoveJumpAnimationThread(){
        Thread thread = new SnakeJumpAnimationThread(this,getLength()-1);
        removeJumpAnimationThreads.offer(thread);
        thread.start();
    }
    public Thread getAnEarliestRemoveJumpAnimationThread(){
        Thread thread = removeJumpAnimationThreads.poll();
        return thread;
    }

    public boolean getAddAnimating(){
        return addAnimating;
    }
    public void beginAddAnimation(){
        synchronized (addAnimationLock){
            addAnimating = true;
        }
    }
    public void endAddAnimation(){
        synchronized (addAnimationLock){
            addAnimating = false;
        }
    }
    public void removeBody(){
        if(getLength()==1)return;
        SnakeNode tempt = (SnakeNode) snakeBodies.get(getLength()-1);

        new SnakeNodeRemoveAnimateThread(tempt,drawUtil,getAnEarliestRemoveJumpAnimationThread()).start();


    }
    public void addBody(){
        SnakeNode tempt;
        int index = snakeBodies.size();

        if(index==1){
            tempt = new SnakeNode(this,world,snakeHead,index);
        }else{
            tempt = new SnakeNode(this,world,(SnakeNode) snakeBodies.get(index-1),index);
        }
        snakeBodies.add(tempt);

        if(initFinnished){
            tempt.setDoDraw(false);
            new SnakeNodeAppendAnimateThread(tempt,drawUtil,getAnEarliestAddJumpAnimationThread()).start();
        }
        drawUtil.addToCenterLayer(tempt);
    }
    public int getLength(){
        return snakeBodies.size();
    }
    public void moving(){
        snakeHead.startMoving();
        drawUtil.addToFloorLayer(snakeHead.target);
    }
    public void whenMotionDown(int x,int y){
        if(isDead()||isPaused())return;
        snakeHead.whenMotionDown(x,y);
    }
    public void whenMotionUp(){
        if(isDead()||isPaused())return;
        snakeHead.whenMotionUp();
    }
    public void setColor(int index){
       this.color = index;
       for (GameElements g:snakeBodies){
           g.color = color;
       }
    }
    public void doAfterDead(){
        for (CircleBody sn:snakeBodies){
            if(sn instanceof SnakeNode){
               ((SnakeNode) sn).rectBody.destroySelf();
            }
        }
        for (MyJoint mj : JBox2DUtil.Joints) {
            mj.destroySelf();
        }
    }
    public void checkLength(){
        if(!getAddAnimating()){
            if (getSnakeAjaxLength()> getLength()) {
                beginAddAnimation();
                addBody();
            }else if(getSnakeAjaxLength()<getLength()){
                beginAddAnimation();
                removeBody();
            }
        }
    }
    public int getSnakeAjaxLength(){
        synchronized (snakeAjaxLengthLock){
            return SnakeAjaxLength;
        }
    }
    public void plusOneSnakeAjaxLength(){
        synchronized (snakeAjaxLengthLock){
            SnakeAjaxLength+=1;
        }
    }
    public void minusOneSnakeAjaxLength(){
        synchronized (snakeAjaxLengthLock){
            SnakeAjaxLength-=1;
        }
    }
    public void onResume(){
        for (CircleBody c:snakeBodies){
            if(c instanceof SnakeHead){
                ((SnakeHead)c).onResume();
            }else {
                ((SnakeNode)c).onResume();
            }
        }
    }
    public void onPause(SharedPreferences.Editor editor){
        paused = true;
        for (CircleBody c:snakeBodies){
            if(c instanceof SnakeHead){
                ((SnakeHead)c).onPause(editor);
            }else {
                ((SnakeNode)c).onPause(editor);
            }
        }
    }

}
