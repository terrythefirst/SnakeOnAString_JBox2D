package com.work.terry.snakeonastring_jbox2d.SurfaceViewAndActivity;

import android.content.SharedPreferences;
import android.util.Log;
import android.view.MotionEvent;

import com.work.terry.snakeonastring_jbox2d.ButtonBlockCircle;
import com.work.terry.snakeonastring_jbox2d.GameElements;
import com.work.terry.snakeonastring_jbox2d.JBox2DElements.MyContactFilter;
import com.work.terry.snakeonastring_jbox2d.JBox2DElements.MyContactListener;
import com.work.terry.snakeonastring_jbox2d.JBox2DElements.RectBody;
import com.work.terry.snakeonastring_jbox2d.SnakeElements.Bomb;
import com.work.terry.snakeonastring_jbox2d.SnakeElements.Snake;
import com.work.terry.snakeonastring_jbox2d.SnakeElements.SnakeFood;
import com.work.terry.snakeonastring_jbox2d.Thread.JBox2DThread;
import com.work.terry.snakeonastring_jbox2d.Util.Constant;
import com.work.terry.snakeonastring_jbox2d.Util.DrawUtil;
import com.work.terry.snakeonastring_jbox2d.Util.JBox2DUtil;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import java.io.StreamCorruptedException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static com.work.terry.snakeonastring_jbox2d.Util.Constant.BackgroundImg;
import static com.work.terry.snakeonastring_jbox2d.Util.Constant.BombImg;
import static com.work.terry.snakeonastring_jbox2d.Util.Constant.ButtonBlockDefaultHeight;
import static com.work.terry.snakeonastring_jbox2d.Util.Constant.SCREEN_HEIGHT;
import static com.work.terry.snakeonastring_jbox2d.Util.Constant.SCREEN_WIDTH;
import static com.work.terry.snakeonastring_jbox2d.Util.Constant.SnakeBodyImg;
import static com.work.terry.snakeonastring_jbox2d.Util.Constant.SnakeFoodImg;

/**
 * Created by Terry on 2018/1/25.
 */

public class GamePlay extends MyView{
    public boolean IS_PLAYING = true;
    public World world;
    public JBox2DThread jBox2DThread = null;
    public Snake snake ;

    public int Score ;

    public Map<Integer,SnakeFood> snakeFoodMap = new HashMap<>();
    public Map<Integer,Bomb> snakeBombMap = new HashMap<>();
    //public Map<Integer,Vec2> snakeFoodLocationMap = new HashMap<>();
    public int foodLocationCount = 0;
    public int foodIndex = 0;
    public int bombIndex = 0;

    public GamePlay (){
        setDrawUtilAndBacktoundImg(BackgroundImg);

        Vec2 gravity = new Vec2(0,0);
        world = new World(gravity);
        constructWallsAndStaticBody();

        world.setContactFilter(new MyContactFilter(GamePlay.this));
        MyContactListener myContactListener = new MyContactListener(GamePlay.this);
        world.setContactListener(myContactListener);

        snake = new Snake(world, Constant.C0LOR_WHITE,drawUtil);
//            drawUtil.addToCenterLayer(
//                    new ButtonBlock(
//                            world,
//                            720,2000,
//                            80,
//                            200,
//                            0.8f,
//                            ButtonBlockDefaultHeight,
//                            100,
//                            true,
//                            Constant.C0LOR_WHITE
//                    )
//            );
//        snakeFoodLocationMap.put(0,new Vec2(200,600));
//        snakeFoodLocationMap.put(1,new Vec2(200,1800));
//        snakeFoodLocationMap.put(2,new Vec2(1200,600));
//        snakeFoodLocationMap.put(3,new Vec2(1200,1800));

        drawUtil.addToCenterLayer(
                new ButtonBlockCircle(
                        world,
                        720,2000,
                        60,
                        Constant.C0LOR_WHITE,

                        true,
                        ButtonBlockDefaultHeight,
                        1
                )
        );


        jBox2DThread = new JBox2DThread(GamePlay.this);
        addBomb();

        snake.moving();
        jBox2DThread.start();

    }
    public void checkShouldAddFoodOrBomb(){
        if (snakeFoodMap != null){
            boolean allEaten = true;
            for(int x:snakeFoodMap.keySet()){
                if(!snakeFoodMap.get(x).eatean){
                    allEaten = false;
                    break;
                }
            }
            if(allEaten)addFood();
        }
        if(snakeBombMap != null){
            boolean allEaten = true;
            for(int x:snakeBombMap.keySet()){
                if(!snakeBombMap.get(x).eatean){
                    allEaten = false;
                    break;
                }
            }
            if(allEaten)addBomb();
        }


    }
    public void addFood(){
        float rx = (float) Math.random()*800+100;
        float ry = (float) Math.random()*1800+200;
        //Vec2 loc = snakeFoodLocationMap.get(foodLocationCount++);
        //Log.d("foodCount",foodCount+"");
        SnakeFood tempt = new SnakeFood(
                    getDrawUtil(),
                    world,
                    foodIndex,
                    rx,ry,
                    60,60,
                    0,
                    8,
                    SnakeFoodImg
            );

        snakeFoodMap.put(foodIndex,tempt);
        foodIndex++;
//        if(foodLocationCount>3){
//            foodLocationCount=0;
//        }
    }
    public SnakeFood getFood(int index){
        return snakeFoodMap.get(index);
    }
    public void addBomb(){
        float rx = (float) Math.random()*800+100;
        float ry = (float) Math.random()*1800+200;
        Bomb tempt  = new Bomb(
                    drawUtil,
                    world,
                    bombIndex,
                    rx,ry,
                    100,100,
                    0,
                    8,
                    BombImg
            );
        drawUtil.addToFloorLayer(tempt);

        snakeBombMap.put(bombIndex++,tempt);
    }
    public Bomb getBomb(int bombIndex){
        return snakeBombMap.get(bombIndex);
    }
    public void constructWallsAndStaticBody(){
        RectBody upWall = new RectBody(
                world,
                "upWall",
                SCREEN_WIDTH/2,-8,
                0,
                0,0,
                SCREEN_WIDTH/2,5,

                0,0,0, 0,0,0,

                10,0.1f,0.1f,
                "",
                true
        );
        RectBody leftWall = new RectBody(
                world,
                "leftWall",
                -8,SCREEN_HEIGHT/2,
                0,
                0,0,
                5,SCREEN_HEIGHT/2,

                0,0, 0, 0,0,0,

                10,0.1f,0.1f,
                "",
                true
        );
        RectBody rightWall = new RectBody(
                world,
                "rightWall",
                SCREEN_WIDTH+8,SCREEN_HEIGHT/2,
                0,
                0,0,
                5,SCREEN_HEIGHT/2,

                0,0, 0, 0,0,0,

                10,0.1f,0.1f,
                "",
                true
        );
        RectBody buttonWall = new RectBody(
                world,
                "buttomWall",
                SCREEN_WIDTH/2,SCREEN_HEIGHT+8,
                0,
                0,0,
                SCREEN_WIDTH/2,5,

                0,0,0, 0,0,0,

                10,0.1f,0.1f,
                "",
                true
        );

        JBox2DUtil.staticBody = buttonWall.body;
    }
    @Override
    public void onTouchEvent(MotionEvent event,int x,int y){
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                snake.whenMotionDown(x,y);
                break;
            case MotionEvent.ACTION_MOVE:
                snake.whenMotionDown(x,y);
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
    }
    @Override
    public void onResume(){
        IS_PLAYING = true;
        jBox2DThread = new JBox2DThread(GamePlay.this);
        snake.moving();
        jBox2DThread.start();

        if(snake!=null)snake.onResume();
    }
    @Override
    public void onPause(SharedPreferences.Editor editor){
        IS_PLAYING = false;
        if(snake!=null)snake.onPause(editor);
    }
}
