package com.work.terry.snakeonastring_jbox2d.SurfaceViewAndActivity;

import android.content.SharedPreferences;
import android.util.Log;
import android.view.MotionEvent;

import com.work.terry.snakeonastring_jbox2d.Animation.BreathAnimation;
import com.work.terry.snakeonastring_jbox2d.Animation.ListJiggleAnimation;
import com.work.terry.snakeonastring_jbox2d.SnakeElements.SnakeHead;
import com.work.terry.snakeonastring_jbox2d.SnakeElements.SnakeNode;
import com.work.terry.snakeonastring_jbox2d.SnakeElements.SnakeSkinManager;
import com.work.terry.snakeonastring_jbox2d.UI.Button;
import com.work.terry.snakeonastring_jbox2d.UI.GameElement;
import com.work.terry.snakeonastring_jbox2d.UI.ImgButton;
import com.work.terry.snakeonastring_jbox2d.UI.RoundEdgeRectButton;
import com.work.terry.snakeonastring_jbox2d.UI.Score;
import com.work.terry.snakeonastring_jbox2d.UI.ScoreBoard;
import com.work.terry.snakeonastring_jbox2d.Util.Constant;
import com.work.terry.snakeonastring_jbox2d.Util.DrawUtil;
import com.work.terry.snakeonastring_jbox2d.Util.MyMath;

import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.work.terry.snakeonastring_jbox2d.Util.Constant.BackgroundImg;
import static com.work.terry.snakeonastring_jbox2d.Util.Constant.SelectChineseImg;


/**
 * Created by Terry on 2018/3/5.
 */

public class SkinChangingView extends MyView {
    private String backgroundImg = BackgroundImg;
    private ScoreBoard yellowStarScoreboard;
    private GameElement yellowStar;
    GamePlayView gamePlayView;

    float SnakePickAreaStartY = 300;
    float SnakePickAreaEndY = 1200;
    float SnakePickTouchRatio = 0.9f;
    float SnakePickMaxScaleRate = 0.5f;
    float SnakePickSelectSpeed = 5;
    float previousX;

    Map<Integer,List<GameElement>> snakes = new HashMap<>();
    float SnakeXInterval = 400;
    float SnakeXSpan = 90;
    float SnakeYSpan = 80;
    float SnakeY = 800;

    int nowSelect = -1;

    ImgButton returnButton;
    RoundEdgeRectButton selectButton;

    public SkinChangingView(GamePlayView gamePlayView,String backgroundImg){
        this.gamePlayView = gamePlayView;
        if(backgroundImg!=null)this.backgroundImg = backgroundImg;
        drawUtil = new DrawUtil(this.backgroundImg);
        initReturnButton();
        returnButton.setButtonListener(
                ()->{
                    gamePlayView.setNowView(Constant.START_VIEW);
                }
        );
        initOthers();
    }
    public SkinChangingView(GamePlayView gamePlayView,String backgroundImg,int gameMode,int gameLevel){
        this.gamePlayView = gamePlayView;
        if(backgroundImg!=null)this.backgroundImg = backgroundImg;
        drawUtil = new DrawUtil(this.backgroundImg);
        initReturnButton();
        int gameModeNumber = (gameMode==Constant.GAME_MODE_ENDLESS)?Constant.GAMEPLAY_VIEW_ENDLESS:Constant.GAMEPLAY_VIEW_ENDLESS;
        returnButton.setButtonListener(
                ()->{
                    gamePlayView.setNowView(gameModeNumber+gameLevel);
                }
        );
        initOthers();
    }
    public void initOthers(){
        initScoreBoard();
        initSelectButton();
        initSnakes();
    }
    public void initSnakes(){
        nowSelect = gamePlayView.getSnakeSkinNumber();//SnakeSkinManager.SKIN_DEFAULT;

        for(int x:SnakeSkinManager.skinMap.keySet()){
            initSnake(x);
        }
    }
    public void initSnake(int skinNumber){
        List<GameElement> snake = new ArrayList<>();

        int NodeIndex = 0;
        while (NodeIndex < 8){
            SnakeNode snakeNode = new SnakeNode(
                    720+(skinNumber-nowSelect)*SnakeXInterval+SnakeXSpan*((float)Math.sin(0.25*Math.PI*NodeIndex)),SnakeY+SnakeYSpan*(NodeIndex-4),
                    0,0,
                    SnakeSkinManager.getSkin(skinNumber,8-NodeIndex),
                    Constant.SnakeDefaultHeight,
                    NodeIndex++
            );
            drawUtil.addToCenterLayer(snakeNode);
            snake.add(snakeNode);
        }

        SnakeHead snakeHead = new SnakeHead(
                720+(skinNumber-nowSelect)*SnakeXInterval+SnakeXSpan*((float)Math.sin(0.25*Math.PI*NodeIndex)),SnakeY+SnakeYSpan*(NodeIndex-4),
                1,1,
                SnakeSkinManager.getSkin(skinNumber,0),
                Constant.SnakeDefaultHeight
        );
        drawUtil.addToCenterLayer(snakeHead);
        snake.add(snakeHead);

        Collections.reverse(snake);
        new ListJiggleAnimation(
                snake,
                40,
                0.3f,
                2000,
                false,
                0,
                true
        ).start();

        snakes.put(skinNumber,snake);
        for(Integer integer:snakes.keySet()){
            scaleSnakeByX(integer,0);
        }
    }
    public void initSelectButton(){
        selectButton = new RoundEdgeRectButton(
                0,
                720,2560-300,
                600,160,
                80,
                Constant.COLOR_GOLDEN,
                20,

                0,
                0,
                Constant.ButtonBlockHeightColorFactor,
                0.9f,
                SelectChineseImg,
                null
        );
        selectButton.setButtonListener(
                ()->{
                    gamePlayView.setSnakeSkinNumber(nowSelect);
                    returnButton.doButtonStuff();
                }
        );
        buttons.add(selectButton);
        drawUtil.addToCenterLayer(selectButton);
        new Thread(){
            @Override
            public void run(){
                while(true){
                    Thread thread = new BreathAnimation(
                            selectButton,
                            false,
                            30,
                            true,
                            0.2f,
                            2f
                    );

                    thread.start();
                    try {
                        thread.join();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }
    public void initScoreBoard(){
        yellowStar = new Button(
                0,
                800,140,
                150,150,
                Constant.COLOR_GOLDEN,
                30,
                10,
                Constant.ButtonBlockTopOffSetColorFactor,
                Constant.ButtonBlockHeightColorFactor,
                Constant.ButtonBlockFloorColorFactor,
                Constant.StarFavoriteImg
        ) ;
        yellowStar.setDoDrawHeight(false);
        drawUtil.addToCenterLayer(yellowStar);

        Score yellowStarScore = new Score(33);
        yellowStarScoreboard = new ScoreBoard(
                yellowStarScore,
                1000,140,
                400,150,
                Constant.COLOR_WHITE,
                30,
                10,
                Constant.ButtonBlockTopOffSetColorFactor,
                Constant.ButtonBlockHeightColorFactor,
                Constant.ButtonBlockFloorColorFactor
        );
        drawUtil.addToCenterLayer(yellowStarScoreboard);

    }
    public void initReturnButton(){
        returnButton = new ImgButton(
                0,
                160,140,
                160,160,
                Constant.COLOR_GREEN,
                20,
                0,
                0,
                Constant.ButtonBlockHeightColorFactor,
                0.9f,
                Constant.ArrowBackImg,
                Constant.SnakeBodyImg
        );
        returnButton.setTopImgRatio(0.8f);
        returnButton.setFloorShadowFactorX(0.8f);
        returnButton.setFloorShadowFactorY(0.5f);
        buttons.add(returnButton);
        drawUtil.addToCenterLayer(returnButton);
    }
    @Override
    public void onTouchEvent(MotionEvent event, float x, float y) {
        switch (event.getAction()){
            case MotionEvent.ACTION_MOVE:
                if(y>SnakePickAreaStartY&&y<SnakePickAreaEndY){
                    float dx = x-previousX;
                    whenMoveMoveSnakes(dx);
                }
            case MotionEvent.ACTION_DOWN:
                if(y>SnakePickAreaStartY&&y<SnakePickAreaEndY){
                    previousX = x;
                    break;
                }
                Button tempt = nowPressedButton;
                nowPressedButton = whichButtonTouched(x,y);
                if(tempt!=null&&nowPressedButton!=tempt)tempt.whenReleased(false);
                if(nowPressedButton!=null)nowPressedButton.whenPressed();
                break;
            case MotionEvent.ACTION_UP:
                // if(pauseButton.testTouch(x,y))pauseButton
                whenUpMoveSnake();
                whenUp(x,y);
                break;
        }
    }
    public void whenUpMoveSnake(){

    }
    public void scaleSnakeByX(int skinNumber,float dx){
        List<GameElement> list = snakes.get(skinNumber);

        float dx720 = Math.abs(list.get(8).x-720);

        int NodeIndex = 0;
        while (NodeIndex <= 8){
            GameElement ge = list.get(NodeIndex);
            float scaleRate =0;
            if(dx720 <SnakeXInterval/2) {
                scaleRate =MyMath.smoothStep(0.5f,1,( 1 - dx720 / SnakeXInterval)) * SnakePickMaxScaleRate;
                Log.e("ScaleRate",""+scaleRate);
                ge.scaleWidth = ge.width * scaleRate;
                ge.scaleHeight = ge.height * scaleRate;
            }else {
                ge.scaleWidth = 0;
                ge.scaleHeight = 0;
            }
            ge.setXY(
                    720+(skinNumber-nowSelect)*SnakeXInterval-SnakeXSpan*(1+scaleRate)*((float)Math.sin(0.25*Math.PI*NodeIndex))+dx,
                    SnakeY+SnakeYSpan*(4-NodeIndex)*(1+scaleRate)
            );
            NodeIndex++;
        }
    }
    public void whenMoveMoveSnakes(float dx){
        dx*=SnakePickTouchRatio;
        int minSkinNumber = Collections.min(snakes.keySet());
        int maxSkinNumber = Collections.max(snakes.keySet());
        Log.e("dx",dx+"");
        Log.e("nowSelect",nowSelect+"");
        Log.e("whenMove","snakes.get(maxSkinNumber).get(4).x = "+snakes.get(maxSkinNumber).get(4).x);
        Log.e("whenMove","snakes.get(minSkinNumber).get(4).x = "+snakes.get(minSkinNumber).get(4).x);

//        if(nowSelect==minSkinNumber&&dx>0)return;
//        if(nowSelect==maxSkinNumber&&dx<0)return;
        if((snakes.get(maxSkinNumber).get(4).x<=720&&dx<=0)
                ||(snakes.get(minSkinNumber).get(4).x>=720&&dx>=0))return;
//        if(Math.abs(dx)>SnakeXInterval/3){
//            nowSelect+=(dx>0)?-1:1;
//
//            if(nowSelect<minSkinNumber)nowSelect = minSkinNumber;
//            else if (nowSelect>maxSkinNumber)nowSelect = maxSkinNumber;
//        }

        for(Integer index:snakes.keySet()){
            scaleSnakeByX(index,dx);
            if(Math.abs(snakes.get(index).get(8).x-720)<SnakeXInterval/2){
                nowSelect = index;
            }
        }

    }
    @Override
    public void onResume() {

    }

    @Override
    public void onPause(SharedPreferences.Editor editor) {

    }
}
