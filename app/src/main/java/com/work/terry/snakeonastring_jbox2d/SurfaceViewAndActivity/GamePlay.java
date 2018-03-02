package com.work.terry.snakeonastring_jbox2d.SurfaceViewAndActivity;

import android.content.SharedPreferences;
import android.view.MotionEvent;

import com.work.terry.snakeonastring_jbox2d.GamePlayElements.ButtonBlock;
import com.work.terry.snakeonastring_jbox2d.SnakeElements.FoodMagnet;
import com.work.terry.snakeonastring_jbox2d.Animation.BreathAnimation;
import com.work.terry.snakeonastring_jbox2d.UI.Button;
import com.work.terry.snakeonastring_jbox2d.GamePlayElements.ButtonBlockCircle;
import com.work.terry.snakeonastring_jbox2d.JBox2DElements.MyContactFilter;
import com.work.terry.snakeonastring_jbox2d.JBox2DElements.MyContactListener;
import com.work.terry.snakeonastring_jbox2d.JBox2DElements.RectBody;
import com.work.terry.snakeonastring_jbox2d.SnakeElements.Bomb;
import com.work.terry.snakeonastring_jbox2d.SnakeElements.Snake;
import com.work.terry.snakeonastring_jbox2d.SnakeElements.SnakeFood;
import com.work.terry.snakeonastring_jbox2d.Thread.JBox2DThread;
import com.work.terry.snakeonastring_jbox2d.Animation.JiggleAnimation;
import com.work.terry.snakeonastring_jbox2d.UI.GameElement;
import com.work.terry.snakeonastring_jbox2d.UI.Score;
import com.work.terry.snakeonastring_jbox2d.UI.ScoreBoard;
import com.work.terry.snakeonastring_jbox2d.Util.Constant;
import com.work.terry.snakeonastring_jbox2d.Util.DrawUtil;
import com.work.terry.snakeonastring_jbox2d.Util.JBox2DUtil;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import java.util.HashMap;
import java.util.Map;

import static com.work.terry.snakeonastring_jbox2d.Util.Constant.*;


/**
 * Created by Terry on 2018/1/25.
 */

public class GamePlay extends MyView{
    public boolean IS_PLAYING = true;
    public World world;
    public JBox2DThread jBox2DThread = null;

    public Snake snake ;

//    public int Score ;
//    private byte[] scoreLock = new byte[0];
    public ScoreBoard scoreBoard;
    public Score score = new Score(0);

    public int gameMode;

    public Map<Integer,SnakeFood> snakeFoodMap = new HashMap<>();
    public Map<Integer,Bomb> snakeBombMap = new HashMap<>();
    public Map<Integer,FoodMagnet> foodMagnetMap = new HashMap<>();

    public int foodIndex = 0;
    public int bombIndex = 0;
    public int foodMagnetIndex = 0;

    public Button pauseButton;

    public GamePlay (int gameMode){
        drawUtil = new DrawUtil(null);
        //setDrawUtilAndBacktoundImg(BackgroundImg);
        this.gameMode = gameMode;
        Vec2 gravity = new Vec2(0,0);
        world = new World(gravity);
        constructWallsAndStaticBody();

        world.setContactFilter(new MyContactFilter(GamePlay.this));
        MyContactListener myContactListener = new MyContactListener(GamePlay.this);
        world.setContactListener(myContactListener);

        addPauseButton();
        if(gameMode==Constant.GAME_MODE_ENDLESS)
            addScoreBoard();

//        float dia = 20+(float)Math.random()*80;
//        float rotateAngle = (float)Math.random();
//            drawUtil.addToCenterLayer(
//                    new ButtonBlock(
//                            world,
//                            "",
//                            720,400,
//                            dia,
//                            rotateAngle*400+dia,
//                            0.95f,
//                            ButtonBlockDefaultHeight,
//                            rotateAngle*360,
//                            true,
//                            Constant.COLOR_WHITE
//                    )
//            );

//        ButtonBlockCircle buttonBlockCircle = new ButtonBlockCircle(
//                world,
//                720,2000,
//                60,
//                Constant.C0LOR_CYAN,
//
//                true,
//                ButtonBlockDefaultHeight,
//                1
//        );
//        drawUtil.addToCenterLayer(buttonBlockCircle);
//        new BreathAnimation(
//                buttonBlockCircle,
//                40,
//                true,
//                2f
//        ).start();

    }
    public void startGame(){
        jBox2DThread = new JBox2DThread(GamePlay.this);
        addBomb();

        snake.moving();
        jBox2DThread.start();
    }
    public void plusScore(int x){
        score.plusScore(x);
    }
    public void addScoreBoard(){
        scoreBoard = new ScoreBoard(
                score,
                200,120,
                280,150,
                Constant.C0LOR_SNAKE_WHITE,
                20,
                10,
                ButtonBlockTopOffSetColorFactor,
                0,
                ButtonBlockFloorColorFactor
        );
    }
    public void addPauseButton(){
        pauseButton = new Button(
                0,
                1440-120,200/2+30,
                200,200,
                COLOR_WHITE,
                20,
                6,
                ButtonBlockTopOffSetColorFactor,
                ButtonBlockHeightColorFactor,
                ButtonBlockFloorColorFactor,
                PauseButtonImg
        );
        pauseButton.setDoDrawHeight(false);
        buttons.add(pauseButton);
        drawUtil.addToTopLayer(pauseButton);
    }
    public void addGameElements(GameElement gameElement, int layer){
        switch (layer){
            case LAYER_TOP:drawUtil.addToTopLayer(gameElement);break;
            case LAYER_CENTER:drawUtil.addToCenterLayer(gameElement);break;
            case LAYER_FLOOR:drawUtil.addToFloorLayer(gameElement);break;

        }
    }
    public void setSnake(Snake snake){
        this.snake = snake;
        addGameElements(snake.snakeHead,Constant.LAYER_CENTER);
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
        if(foodMagnetMap!=null){
            boolean allEaten = true;
            for(int x:foodMagnetMap.keySet()){
                if(!foodMagnetMap.get(x).eatean){
                    allEaten = false;
                    break;
                }
            }
            if(allEaten)addFoodMagnet();
        }
    }
    public void addFoodMagnet(){
        float rx = (float) Math.random()*800+100;
        float ry = (float) Math.random()*1800+200;
        //Vec2 loc = snakeFoodLocationMap.get(foodLocationCount++);
        //Log.d("foodCount",foodCount+"");
        FoodMagnet tempt = new FoodMagnet(
                getDrawUtil(),
                world,
                foodMagnetIndex,
                rx,ry,
                45,
                4,
                1000,
                FoodMagnetImg
        );
        drawUtil.addToFloorLayer(tempt);
        foodMagnetMap.put(foodMagnetIndex,tempt);
        foodMagnetIndex++;
    }
    public FoodMagnet getFoodMagnet(int index){
        return foodMagnetMap.get(index);
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
                    35,
                    0,
                    10,
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
                    65,
                    0,
                    8
            );
        drawUtil.addToFloorLayer(tempt);

        new Thread(){
            @Override
            public void run(){
                while (!tempt.eatean){
                    new JiggleAnimation(
                            tempt,
                            60,
                            0.6f,

                            true,
                            0.5f,
                            true
                    ).run();
                    try {
                        sleep(1000);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }.start();

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
            case MotionEvent.ACTION_MOVE:
                nowPressedButton = whichButtonTouched(x,y);
                if(nowPressedButton!=null)nowPressedButton.whenPressed();
                else {
                    snake.whenMotionDown(x,y);
                }
                break;
            case MotionEvent.ACTION_UP:
               // if(pauseButton.testTouch(x,y))pauseButton
                whenUp(x,y);
                snake.whenMotionUp();
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
