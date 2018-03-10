package com.work.terry.snakeonastring_jbox2d.SurfaceViewAndActivity;

import android.content.SharedPreferences;
import android.util.Log;
import android.view.MotionEvent;

import com.work.terry.snakeonastring_jbox2d.Animation.DisappearAnimation;
import com.work.terry.snakeonastring_jbox2d.Animation.ListJiggleAnimation;
import com.work.terry.snakeonastring_jbox2d.SnakeElements.SnakeHead;
import com.work.terry.snakeonastring_jbox2d.SnakeElements.SnakeNode;
import com.work.terry.snakeonastring_jbox2d.SnakeElements.SnakeSkinManager;
import com.work.terry.snakeonastring_jbox2d.Thread.StoppableBreathAnimationThread;
import com.work.terry.snakeonastring_jbox2d.Thread.WhenUpMoveSnakeThread;
import com.work.terry.snakeonastring_jbox2d.UI.Button;
import com.work.terry.snakeonastring_jbox2d.UI.GameElement;
import com.work.terry.snakeonastring_jbox2d.UI.ImgButton;
import com.work.terry.snakeonastring_jbox2d.UI.RoundEdgeRectButton;
import com.work.terry.snakeonastring_jbox2d.UI.Score;
import com.work.terry.snakeonastring_jbox2d.UI.ScoreBoard;
import com.work.terry.snakeonastring_jbox2d.Util.Constant;
import com.work.terry.snakeonastring_jbox2d.Util.DrawUtil;
import com.work.terry.snakeonastring_jbox2d.Util.MyMath;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.work.terry.snakeonastring_jbox2d.Util.Constant.BackgroundImg;
import static com.work.terry.snakeonastring_jbox2d.Util.Constant.SelectChineseImg;
import static com.work.terry.snakeonastring_jbox2d.Util.Constant.UnlockChineseImg;


/**
 * Created by Terry on 2018/3/5.
 */

public class SkinChangingView extends MyView {
    private String backgroundImg = BackgroundImg;
    private ScoreBoard yellowStarScoreboard;
    private GameElement yellowStar;
    private GameElement lock;
    private ScoreBoard lockStarNumber;
    private GameElement lockStar;
    GamePlayView gamePlayView;

    public float SnakePickAreaStartY = 300;
    public float SnakePickAreaEndY = 1400;
    public float SnakePickTouchRatio = 0.8f;
    public float SnakePickMaxScaleRate = 0.5f;
    public float SnakePickSelectSpeed = 2;
    public float previousX;

    public Map<Integer,List<GameElement>> snakes = new HashMap<>();
    public float SnakeXInterval = 400;
    public float SnakeXSpan = 90;
    public float SnakeYSpan = 80;
    public float SnakeY = 1200;

    public int nowSelect = -1;

    ImgButton returnButton;
    RoundEdgeRectButton selectButton;
    StoppableBreathAnimationThread selectButtonBreathAnimationThread = null;
    StoppableBreathAnimationThread lockBreathAnimationThread = null;

    WhenUpMoveSnakeThread whenUpMoveSnakeThread = null;


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
        //initSelectButton();
        //initLockAndLockStar();
        checkNowSelectByX();
        initSnakes();
    }
    public void initSnakes(){
        setNowSelect(gamePlayView.getSnakeSkinNumber());//SnakeSkinManager.SKIN_DEFAULT;

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
    public boolean isPossessedSkin(int number){
        boolean ans = true;
        if(number==1){
            ans = false;
        }
        Log.e("isPossessedSkin",((ans)?"has":"has not")+" skin"+number);
        return ans;
    }
    public boolean haveMoney(int price){
        return (yellowStarScoreboard.score.getScore()>=price);
    }
    private void initSelectButton(){
        if(selectButton!=null){
            drawUtil.addToRemoveSequence(selectButton);
            buttons.remove(selectButton);
        }

        boolean isPossessed = (isPossessedSkin(nowSelect));
        selectButton = new RoundEdgeRectButton(
                1212,
                720,2560-300,
                600,160,
                80,
                (isPossessed)?Constant.COLOR_GOLDEN:Constant.COLOR_GREEN,
                20,

                0,
                0,
                Constant.ButtonBlockHeightColorFactor,
                0.9f,
                (isPossessed)?SelectChineseImg:UnlockChineseImg,
                null
        );
        if(isPossessed)
            selectButton.setButtonListener(
                    ()->{
                        gamePlayView.setSnakeSkinNumber(nowSelect);
                        returnButton.doButtonStuff();
                    }
            );
        else
            selectButton.setButtonListener(
                    ()->{
                        Log.e("selectButton","unlock!!!!");
                    }
            );
        buttons.add(selectButton);
        drawUtil.addToCenterLayer(selectButton);

        new Thread(){
            @Override
            public void run(){
                Thread thread = new DisappearAnimation(
                        selectButton,
                        false,
                        0.5f
                );
                thread.start();

                try {
                    thread.join();
                }catch (Exception e){
                    e.printStackTrace();
                }
                if(nowSelect!=gamePlayView.getSnakeSkinNumber()){
                    if(selectButtonBreathAnimationThread!=null){
                        selectButtonBreathAnimationThread.setShouldDie();
                        selectButtonBreathAnimationThread = null;
                    }
                    selectButtonBreathAnimationThread = new StoppableBreathAnimationThread(
                            selectButton,
                            30,
                            true,
                            0.2f,
                            false,
                            2f
                    );
                    selectButtonBreathAnimationThread.start();
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
        yellowStarScoreboard.setDoDrawFloorShadow(true);
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
    public void initLockAndLockStar(){
        boolean isPossessed = (isPossessedSkin(nowSelect));
        if(isPossessed){
            return;
        }
        if(lock!=null)
            drawUtil.addToRemoveSequence(lock);
        if(lockStar!=null)
            drawUtil.addToRemoveSequence(lockStar);
        if(lockStarNumber!=null)
            drawUtil.addToRemoveSequence(lockStarNumber);

        lock = new Button(
                3030,
                720,SnakeY,
                200,200,
                Constant.COLOR_WHITE,
                5,
                5,
                Constant.ButtonBlockTopOffSetColorFactor,
                Constant.ButtonBlockHeightColorFactor,
                Constant.ButtonBlockFloorColorFactor,
                Constant.LockImg
        ) ;
        lock.setDoDrawHeight(false);
        drawUtil.addToTopLayer(lock);

        new Thread(){
            @Override
            public void run(){
                Thread thread = new DisappearAnimation(
                        lock,
                        false,
                        0.5f
                );
                thread.start();
                try {
                    thread.join();
                }catch (Exception e){
                    e.printStackTrace();
                }
                if(nowSelect!=gamePlayView.getSnakeSkinNumber()){
                    if(lockBreathAnimationThread!=null){
                        lockBreathAnimationThread.setShouldDie();
                        lockBreathAnimationThread = null;
                    }
                    lockBreathAnimationThread = new StoppableBreathAnimationThread(
                            lock,
                            30,
                            true,
                            0.1f,
                            false,
                            2f
                    );
                    lockBreathAnimationThread.start();
                }
            }
        }.start();

        Score score = new Score(80);
        boolean haveMoney = haveMoney(score.getScore());

        float lockStarNumberWidth = 150;
        float lockStarNumberHeight = 100;
        float lockStarNumberX = 720-lockStarNumberWidth/2;
        float lockStarNumberY = lock.y+lock.height/2+60;
        float lockStarNumberDefaultHeight = 20;

        lockStarNumber = new ScoreBoard(
                score,
                lockStarNumberX,lockStarNumberY,
                lockStarNumberWidth,lockStarNumberHeight,
                0,
                lockStarNumberDefaultHeight,
                5,
                Constant.ButtonBlockTopOffSetColorFactor,
                0,
                Constant.ButtonBlockFloorColorFactor
        );
        if(haveMoney)lockStarNumber.setDoDraw(false);
        drawUtil.addToTopLayer(lockStarNumber);
        new DisappearAnimation(
                lockStarNumber,
                false,
                0.5f
        ).start();

        float lockStarWidth = 120;
        float lockStarHeight = lockStarWidth;
        float lockStarX = 720 + lockStarWidth/2+30;
        float lockStarDefaultHeight = lockStarNumberDefaultHeight;
        float lockStarY = lockStarNumberY;
        lockStar = new Button(
                0,
                lockStarX,lockStarY,
                lockStarWidth,lockStarHeight,
                Constant.COLOR_GOLDEN,
                lockStarDefaultHeight,
                5,
                Constant.ButtonBlockTopOffSetColorFactor,
                Constant.ButtonBlockHeightColorFactor,
                Constant.ButtonBlockFloorColorFactor,
                Constant.StarFavoriteImg
        ) ;
        if(haveMoney)lockStar.setDoDraw(false);
        lockStar.setDoDrawHeight(false);
        drawUtil.addToTopLayer(lockStar);
        new DisappearAnimation(
                lockStar,
                false,
                0.5f
        ).start();
    }
    public void whenTouchDownPickSnake(){
        Thread thread = new Thread(){
            @Override
            public void run(){
                if (whenUpMoveSnakeThread!=null){
                    whenUpMoveSnakeThread.setShouldDie();
                    try {
                        whenUpMoveSnakeThread.join();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    whenUpMoveSnakeThread=null;
                }

                if(selectButtonBreathAnimationThread!=null){
                    selectButtonBreathAnimationThread.setShouldDie();
                    try {
                        selectButtonBreathAnimationThread.join();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    selectButtonBreathAnimationThread = null;
                }

                if(lockBreathAnimationThread!=null){
                    lockBreathAnimationThread.setShouldDie();
                    try {
                        lockBreathAnimationThread.join();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    lockBreathAnimationThread = null;
                }
            }
        };
        thread.start();
        try {
            thread.join();
        }catch (Exception e){
            e.printStackTrace();
        }


        if(selectButton!=null){
            GameElement tempt = selectButton;
            selectButton=null;

            new Thread(){
                @Override
                public void run(){
                    Thread thread = new DisappearAnimation(
                            tempt,
                            true,
                            0.5f
                    );
                    thread.start();
                    try {
                        thread.join();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    drawUtil.addToRemoveSequence(tempt);
                }
            }.start();
            buttons.remove(selectButton);
        }

        if(lock!=null){
            GameElement tempt = lock;
            lock = null;

            new Thread(){
                @Override
                public void run(){
                    Thread thread = new DisappearAnimation(
                            tempt,
                            true,
                            0.5f
                    );
                    thread.start();
                    try {
                        thread.join();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    drawUtil.addToRemoveSequence(tempt);
                }
            }.start();
        }
        if(lockStar!=null){
            GameElement tempt = lockStar;
            lockStar = null;
            new Thread(){
                @Override
                public void run(){
                    Thread thread = new DisappearAnimation(
                            tempt,
                            true,
                            0.5f
                    );
                    thread.start();
                    try {
                        thread.join();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    drawUtil.addToRemoveSequence(tempt);
                }
            }.start();
        }
        if(lockStarNumber!=null){
            GameElement tempt = lockStarNumber;
            lockStarNumber = null;
            new Thread(){
                @Override
                public void run(){
                    Thread thread = new DisappearAnimation(
                            tempt,
                            true,
                            0.5f
                    );
                    thread.start();
                    try {
                        thread.join();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    drawUtil.addToRemoveSequence(tempt);
                }
            }.start();
        }
    }
    @Override
    public void onTouchEvent(MotionEvent event, float x, float y) {
        switch (event.getAction()){
            case MotionEvent.ACTION_MOVE:
                if(y>SnakePickAreaStartY&&y<SnakePickAreaEndY){
                    float dx = x-previousX;
                    whenMoveMoveSnakes(dx);
                    whenTouchDownPickSnake();
                    break;
                }
            case MotionEvent.ACTION_DOWN:
                if(y>SnakePickAreaStartY&&y<SnakePickAreaEndY){
                    previousX = x;
                    whenTouchDownPickSnake();
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
    public void checkNowSelectByX(){
        for(Integer index:snakes.keySet()){
            if(Math.abs(snakes.get(index).get(8).x-720)<SnakeXInterval/2){
                setNowSelect(index);
            }
        }
    }
    public void whenUpMoveSnake(){
        checkNowSelectByX();
        if (whenUpMoveSnakeThread!=null){
            whenUpMoveSnakeThread.setShouldDie();
            whenUpMoveSnakeThread=null;
        }
        whenUpMoveSnakeThread = new WhenUpMoveSnakeThread(this);
        whenUpMoveSnakeThread.start();
    }
    public void setNowSelect(int x){
        this.nowSelect = x;
        initSelectButton();
        initLockAndLockStar();
    }
    public void scaleSnakeByX(int skinNumber,float dx){
        List<GameElement> list = snakes.get(skinNumber);

        float dx720 = Math.abs(list.get(8).x-720);

        int NodeIndex = 0;
        while (NodeIndex <= 8){
            GameElement ge = list.get(NodeIndex);
            float scaleRate =0;
            if(dx720 <SnakeXInterval) {
                scaleRate =(1-MyMath.smoothStep(0,1,dx720 / SnakeXInterval) )* SnakePickMaxScaleRate;
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

//        Log.e("whenMove","snakes.get(maxSkinNumber).get(4).x = "+snakes.get(maxSkinNumber).get(4).x);
//        Log.e("whenMove","snakes.get(minSkinNumber).get(4).x = "+snakes.get(minSkinNumber).get(4).x);

        if((snakes.get(maxSkinNumber).get(4).x<=720&&dx<=0)
                ||(snakes.get(minSkinNumber).get(4).x>=720&&dx>=0))return;
        if(Math.abs(dx)>SnakeXInterval)dx=SnakeXInterval;


        for(Integer index:snakes.keySet()){
            scaleSnakeByX(index,dx);
        }

    }
    @Override
    public void onResume() {

    }

    @Override
    public void onPause(SharedPreferences.Editor editor) {

    }
}
