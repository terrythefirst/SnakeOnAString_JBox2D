package com.work.terry.snakeonastring_jbox2d.SnakeElements;


import android.content.SharedPreferences;
import android.util.Log;

import com.work.terry.snakeonastring_jbox2d.Animation.FountainAnimation;
import com.work.terry.snakeonastring_jbox2d.Animation.SnakeEatingHeadAnimation;
import com.work.terry.snakeonastring_jbox2d.SurfaceViewAndActivity.GamePlay;
import com.work.terry.snakeonastring_jbox2d.Thread.FoodMagnetMoveThread;
import com.work.terry.snakeonastring_jbox2d.Thread.FoodMagnetSearchThread;
import com.work.terry.snakeonastring_jbox2d.Thread.SnakeJumpAnimationThread;
import com.work.terry.snakeonastring_jbox2d.JBox2DElements.CircleBody;
import com.work.terry.snakeonastring_jbox2d.JBox2DElements.MyJoint;
import com.work.terry.snakeonastring_jbox2d.Thread.SnakeNodeAppendAnimateThread;
import com.work.terry.snakeonastring_jbox2d.Thread.SnakeNodeRemoveAnimateThread;
import com.work.terry.snakeonastring_jbox2d.Util.ColorManager;
import com.work.terry.snakeonastring_jbox2d.Util.DrawUtil;


import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.CopyOnWriteArrayList;

import com.work.terry.snakeonastring_jbox2d.Util.Constant;
import com.work.terry.snakeonastring_jbox2d.Util.SoundPoolManager;
import com.work.terry.snakeonastring_jbox2d.Util.VectorUtil;

import static com.work.terry.snakeonastring_jbox2d.Util.Constant.*;

/**
 * Created by Terry on 2017/12/28.
 */

public class Snake {
    public Queue<Thread> addJumpAnimationThreads = new LinkedList<Thread>();
    public Queue<Thread> removeJumpAnimationThreads = new LinkedList<Thread>();

    public Thread foodMagnetSearchThread = null;
    public Thread snakeEatingHeadAnimationThread = null;
    public float MagneticDuration = 0;
    public float snakeMaxMagneticDuration = 4;
    public byte[] MagneticDurationLock = new byte[0];
    public boolean isMagnetic = false;

    public byte[] snakeAjaxLengthLock = new byte[0];
    public int SnakeAjaxLength ;

    public int Skin;
    public float speed = 0;
    public float luck = 0;

    public boolean initFinished = false;
    public boolean entered = false;

    public byte[] addAnimationLock = new byte[0];
    public boolean addAnimating = false;

    public World world;
    public GamePlay gamePlay;
    public SnakeHead snakeHead;


    public float searchRadius = 300;

    public List<CircleBody> snakeBodies = null;//包括头！！！
    //private int color = Constant.C0LOR_SNAKE_WHITE;
    public boolean paused = false;
    public boolean isDead = false;
    private DrawUtil drawUtil;

    public float scaleRatio;

    public Snake(
            GamePlay gamePlay,
            Vec2 headLoc,
            Vec2 headV,
            float scaleRatio,
            int totalLength,
            int skin
    ){
        this.gamePlay = gamePlay;
        this.world = gamePlay.world;
        //this.color = color;
        this.Skin = skin;
        SnakeSkin snakeSkin = SnakeSkinManager.getSnakeSkin(skin);
        speed = snakeSkin.speed;
        luck = snakeSkin.luck;

        this.drawUtil = gamePlay.getDrawUtil();
        this.scaleRatio = scaleRatio;

        snakeBodies = new CopyOnWriteArrayList<>();

        float x = headLoc.x;
        float y = headLoc.y;
        float vx = headV.x;
        float vy = headV.y;
        float jumpHeight = Constant.SnakeDefaultHeight*scaleRatio;

        for(int index=0;index <=totalLength;index++){
            if(index==0){
                snakeHead = new SnakeHead(
                        this,
                        gamePlay,
                        x,y,
                        vx,vy,
                        SnakeSkinManager.getSkinNodeInfo(Skin,SnakeHeadImgCode),
                        scaleRatio,
                        jumpHeight
                );
                snakeHead.createBody();
                snakeBodies.add(snakeHead);
                drawUtil.addToCenterLayer(snakeHead);
            }else {
                addBody();
            }
            gamePlay.jBox2DThread.stepTasks();
        }
//        snakeHead = new SnakeHead(this,world,720,1280,0,1,this.color,null,Constant.SnakeDefaultHeight);
//        snakeBodies.add(snakeHead);
//        drawUtil.addToCenterLayer(snakeHead);
//
//        for(int i = 1;i<=Constant.SnakeBodyDefaultLength;i++){
//            addBody();
//        }
        SnakeAjaxLength = getLength();

        //animateThread = new AnimateThread();
       // animateThread.start();
        initFinished = true;
    }
    public boolean checkEntered(){
        if(entered)return entered;

        SnakeNode snakeNode = (SnakeNode) snakeBodies.get(getLength()-1);
        entered = (snakeNode.y< Constant.SCREEN_HEIGHT-snakeNode.radius);
        return entered;
    }
    public int getSkinNumber(){
        return Skin;
    }
    public void addBody(){
        SnakeNode tempt;
        int index = snakeBodies.size();

        if(index==1){
            tempt = new SnakeNode(
                    this,
                    gamePlay,
                    snakeHead,
                    SnakeSkinManager.getSkinNodeInfo(Skin,index),
                    scaleRatio,
                    index);
        }else{
            tempt = new SnakeNode(
                    this,
                    gamePlay,
                    (SnakeNode) snakeBodies.get(index-1),
                    SnakeSkinManager.getSkinNodeInfo(Skin,index),
                    scaleRatio,
                    index);
        }

        //snakeBodies.add(tempt);

        if(initFinished){
            tempt.setDoDraw(false);
            tempt.sendCreateTask();
            new SnakeNodeAppendAnimateThread(tempt,drawUtil,getAnEarliestAddJumpAnimationThread()).start();
        }else {
            tempt.createBody();
            snakeBodies.add(tempt);
        }
        drawUtil.addToCenterLayer(tempt);
    }
    public void setDizzy(){
        new Thread(){
            @Override
            public void run() {
                //Log.e("SnakeSkinManager.skinMap.get(Skin)",SnakeSkinManager.skinMap.get(Skin).skinInfo.get(SnakeHeadDizzyImgCode).getImg()+"");
                //Log.e("SnakeSkinManager.skinMap.get(Skin).skinInfo.get(SnakeHeadDizzyImgCode)",SnakeSkinManager.skinMap.get(Skin).skinInfo.get(SnakeHeadDizzyImgCode)==null?"null":"not null");
                snakeHead.changeFace(SnakeSkinManager.getSkinNodeInfo(Skin,SnakeHeadDizzyImgCode).getImg());
                try {
                    sleep(800);
                }catch (Exception e){
                    e.printStackTrace();
                }
                if(!isDead())
                    snakeHead.changeFace(SnakeSkinManager.getSkinNodeInfo(Skin,SnakeHeadImgCode).getImg());
            }
        }.start();
    }
    public void setDead(){
        Log.d("snake","DEAD!");
        isDead = true;
        snakeHead.target.setDoDraw(false);
        new FountainAnimation(
                gamePlay.getDrawUtil(),
                1,
                snakeHead.x,snakeHead.y,
                40,
                50,
                200,
                5,
                40,
                0.5F,
                ColorManager.getColor(Constant.COLOR_GREY)
        );
        snakeHead.changeFace(SnakeSkinManager.getSkinNodeInfo(Skin,SnakeHeadDeadImgCode).getImg());//Constant.SnakeHeadDizzyEyesImg);
        gamePlay.gameOver();
    }
    public boolean isDead(){
        return isDead;
    }

    public boolean isPaused(){return paused;}

    public void setIsMagnetic(boolean x){
        this.isMagnetic = x;
    }

    public void searchWithin(){
        if(!isMagnetic)return;

        Vec2 headLoc =snakeHead.getBodyXY();//设置爆炸中心点
        float blastRadius = searchRadius;//爆炸半径
        for(Integer i:gamePlay.snakeFoodMap.keySet()){
            SnakeFood sf = gamePlay.snakeFoodMap.get(i);
            if(sf.body!=null){
                Vec2 foodLoc = sf.getBodyXY();

                float distance = VectorUtil.calDistance(VectorUtil.minusV2D(foodLoc,headLoc));
                if(distance<=snakeHead.radius+blastRadius){
                    if(!sf.eatean&&!sf.moving)
                        new FoodMagnetMoveThread(sf,snakeHead).start();
                }
            }
        }
    }
    public void startAnAddJumpAnimationThread(){
        Thread thread = new SnakeJumpAnimationThread(this,getLength());
        addJumpAnimationThreads.offer(thread);
        thread.start();
    }
    public Thread getAnEarliestAddJumpAnimationThread(){
        Thread thread = addJumpAnimationThreads.poll();
        return thread;
    }
    public void whenEatBomb(){
        SoundPoolManager.play(SoundPoolManager.fireFuseSound,0);
        startARemoveJumpAnimationThread();
        minusOneSnakeAjaxLength();
    }
    public void whenEatSnakeFood(SnakeFood sf){
        if(snakeEatingHeadAnimationThread==null){
            new Thread(){
                @Override
                public void run(){
                    snakeEatingHeadAnimationThread = new SnakeEatingHeadAnimation(
                            snakeHead,
                            3
                    );
                    snakeEatingHeadAnimationThread.start();
                    try {
                        snakeEatingHeadAnimationThread.join();
                        snakeEatingHeadAnimationThread = null;
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            }.start();
        }

        gamePlay.plusScore(sf.score);
        startAnAddJumpAnimationThread();
        plusOneSnakeAjaxLength();
    }
    public void whenEatFoodMagnet(FoodMagnet foodMagnet){
        addDuration(foodMagnet.duration);
        if(foodMagnetSearchThread==null||!foodMagnetSearchThread.isAlive()){
            setIsMagnetic(true);
            foodMagnetSearchThread = new FoodMagnetSearchThread(
                    this
            );
            foodMagnetSearchThread.start();
        }
    }
    public void addDuration(float x){
        synchronized (MagneticDurationLock){
            MagneticDuration += x;
        }
    }
    public float getMagneticDurationSetZero(){
        synchronized (MagneticDurationLock){
            float tempt = MagneticDuration;
            MagneticDuration = 0;
            return tempt;
        }
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
        if(getLength()==1+SnakeBodyDefaultLength)return;
        SnakeNode tempt = (SnakeNode) snakeBodies.get(getLength()-1);

        new SnakeNodeRemoveAnimateThread(tempt,drawUtil,getAnEarliestRemoveJumpAnimationThread()).start();
    }


    public int getLength(){
       // Log.d("getLength",""+snakeBodies.size());
        return snakeBodies.size();
    }
    public void moving(){
        snakeHead.startMoving();
        drawUtil.addToFloorLayer(snakeHead.target);
    }
    public void whenMotionDown( float x, float y){
        if(isDead()||isPaused())return;
        snakeHead.whenMotionDown(x,y);
    }
    public void whenMotionUp(){
        if(isDead()||isPaused())return;
        snakeHead.whenMotionUp();
    }
    public void setSkin(int index){
       this.Skin = index;
    }
    public void doAfterDead(){
        synchronized (snakeBodies){
            for (CircleBody sn:snakeBodies){
                if(sn instanceof SnakeNode){
                    if(((SnakeNode) sn).rectBody!=null)
                        ((SnakeNode) sn).rectBody.sendDeleteTask();
                    for (MyJoint mj : ((SnakeNode) sn).joints) {
                        mj.sendDeleteTask();
                    }
                }
            }
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
        if(getLength()==1+SnakeBodyDefaultLength)return;
        synchronized (snakeAjaxLengthLock){
            SnakeAjaxLength-=1;
        }
    }
}
