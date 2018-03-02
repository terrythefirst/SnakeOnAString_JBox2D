package com.work.terry.snakeonastring_jbox2d.SnakeElements;


import android.content.SharedPreferences;
import android.util.Log;

import com.work.terry.snakeonastring_jbox2d.SurfaceViewAndActivity.GamePlay;
import com.work.terry.snakeonastring_jbox2d.Thread.FoodMagnetMoveThread;
import com.work.terry.snakeonastring_jbox2d.Thread.FoodMagnetSearchThread;
import com.work.terry.snakeonastring_jbox2d.Thread.SnakeJumpAnimationThread;
import com.work.terry.snakeonastring_jbox2d.UI.GameElement;
import com.work.terry.snakeonastring_jbox2d.JBox2DElements.CircleBody;
import com.work.terry.snakeonastring_jbox2d.JBox2DElements.MyJoint;
import com.work.terry.snakeonastring_jbox2d.Thread.SnakeNodeAppendAnimateThread;
import com.work.terry.snakeonastring_jbox2d.Thread.SnakeNodeRemoveAnimateThread;
import com.work.terry.snakeonastring_jbox2d.Util.ColorManager;
import com.work.terry.snakeonastring_jbox2d.Util.DrawUtil;
import com.work.terry.snakeonastring_jbox2d.Util.JBox2DUtil;


import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.work.terry.snakeonastring_jbox2d.Util.Constant;
import com.work.terry.snakeonastring_jbox2d.Util.VectorUtil;

import static com.work.terry.snakeonastring_jbox2d.Util.Constant.*;

/**
 * Created by Terry on 2017/12/28.
 */

public class Snake {
    public Queue<Thread> addJumpAnimationThreads = new LinkedList<Thread>();
    public Queue<Thread> removeJumpAnimationThreads = new LinkedList<Thread>();

    public Thread foodMagnetSearchThread = null;
    public float MagneticDuration = 0;
    public float snakeMaxMagneticDuration = 4;
    public byte[] MagneticDurationLock = new byte[0];
    public boolean isMagnetic = false;

    public byte[] snakeAjaxLengthLock = new byte[0];
    public int SnakeAjaxLength ;

    private int Skin;

    public boolean initFinished = false;

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
        this.drawUtil = gamePlay.getDrawUtil();

        snakeBodies = new ArrayList<>();

        float x = headLoc.x;
        float y = headLoc.y;
        float vx = headV.x;
        float vy = headV.y;
        float jumpHeight = Constant.SnakeDefaultHeight*scaleRatio;

        for(int index=0;index <=totalLength;index++){
            SnakeNodeSkinInfo snakeNodeSkinInfo = SnakeSkinManager.getSkin(Skin,0);
            float[] color = ColorManager.getColorByRGB255(snakeNodeSkinInfo.getColor255());
            String picName = snakeNodeSkinInfo.getImg();
            float[] radii = snakeNodeSkinInfo.getRadii();

            if(index==0){
                snakeHead = new SnakeHead(
                        this,
                        world,
                        x,y,
                        vx,vy,
                        scaleRatio*radii[1],
                        color,
                        picName,
                        jumpHeight
                );
                snakeBodies.add(snakeHead);
                drawUtil.addToCenterLayer(snakeHead);
            }else {
                addBody();
            }
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
    public void addBody(){
        SnakeNode tempt;
        int index = snakeBodies.size();

        SnakeNodeSkinInfo snakeNodeSkinInfo = SnakeSkinManager.getSkin(Skin,index);
        float[] color = ColorManager.getColorByRGB255(snakeNodeSkinInfo.getColor255());
        String picName = snakeNodeSkinInfo.getImg();
        float[] radii = snakeNodeSkinInfo.getRadii();

        if(index==1){
            tempt = new SnakeNode(
                    this,world,
                    snakeHead,
                    color,
                    radii[1],
                    picName,
                    index);
        }else{
            tempt = new SnakeNode(
                    this,
                    world,
                    (SnakeNode) snakeBodies.get(index-1),
                    color,
                    radii[1],
                    picName,
                    index);
        }
        //snakeBodies.add(tempt);

        if(initFinished){
            tempt.setDoDraw(false);
            new SnakeNodeAppendAnimateThread(tempt,drawUtil,getAnEarliestAddJumpAnimationThread()).start();
        }else {
            snakeBodies.add(tempt);
        }
        drawUtil.addToCenterLayer(tempt);
    }
    public void setDead(){
        Log.d("snake","DEAD!");
        isDead = true;
        snakeHead.changeFace(Constant.SnakeHeadDeadEyesImg);//Constant.SnakeHeadDizzyEyesImg);
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
            Vec2 foodLoc = sf.getBodyXY();

            float distance = VectorUtil.calDistance(VectorUtil.minusV2D(foodLoc,headLoc));
            if(distance<=snakeHead.radius+blastRadius){
                if(!sf.eatean&&!sf.moving)
                    new FoodMagnetMoveThread(sf,snakeHead).start();
            }
        }
//        for (int i=0;i<numRays;i++)//遍历numRays条光线
//        {
//            float angle = (i/(float)numRays)*360*0.01745329f;//光线旋转角
//            Vec2 rayDir=new Vec2((float)Math.sin(angle),(float)Math.cos(angle));
//            Vec2 rayEnd =center.add(rayDir.mul(blastRadius));
//            callback = new RayCastClosestCallback();
//            world.raycast(callback, center, rayEnd);//物理世界调用光线投射方法
//            if (
//                    callback.body!=null
//                            &&callback.body.getUserData().toString().contains("snakeFood")
//                    )//若光线遇到了刚体
//            {
//                SnakeFood snakeFood = gamePlay.getFood(
//                        Integer.parseInt( callback.body.getUserData().toString().split(" ")[1])
//                );
//                new FoodMagnetMoveThread(snakeFood,snakeHead).start();
//            }
//        }

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


    public void setDizzy(){
        new Thread(){
            @Override
            public void run() {
                snakeHead.changeFace(Constant.SnakeHeadDizzyEyesImg);
                try {
                    sleep(800);
                }catch (Exception e){
                    e.printStackTrace();
                }
                if(!isDead())
                    snakeHead.changeFace(Constant.SnakeHeadEyesImg);
            }
        }.start();
    }

    public int getLength(){
       // Log.d("getLength",""+snakeBodies.size());
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
    public void setSkin(int index){
       this.Skin = index;
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
        if(getLength()==1+SnakeBodyDefaultLength)return;
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
