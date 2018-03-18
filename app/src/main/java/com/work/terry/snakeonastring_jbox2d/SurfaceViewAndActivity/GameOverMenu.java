package com.work.terry.snakeonastring_jbox2d.SurfaceViewAndActivity;

import android.view.MotionEvent;

import com.work.terry.snakeonastring_jbox2d.Animation.UniformMotionAnimation;
import com.work.terry.snakeonastring_jbox2d.UI.Button;
import com.work.terry.snakeonastring_jbox2d.UI.ImgButton;
import com.work.terry.snakeonastring_jbox2d.UI.RoundEdgeRect;
import com.work.terry.snakeonastring_jbox2d.Util.Constant;
import com.work.terry.snakeonastring_jbox2d.Util.DrawUtil;
import com.work.terry.snakeonastring_jbox2d.Util.LoadGameUtil;
import com.work.terry.snakeonastring_jbox2d.Util.TexDrawer;

import org.jbox2d.common.Vec2;

/**
 * Created by Terry on 2018/3/17.
 */

public class GameOverMenu extends MyMenu {
    GamePlay gamePlay;
    float menufloorShadowFactorX = 0;
    float menufloorShadowFactorY = 4f;
    float menuWidth = 900;
    DrawUtil drawUtil;


    MyMenu scoreResultBand;

    int gameModeAndLevel;

    MyMenu retryBand;


    MyMenu bottomBand;
    ImgButton mainMenuButton;
    ImgButton preLevelButton;
    ImgButton nextLevelButton;
    ImgButton musicButton;

    public GameOverMenu(
            GamePlay gamePlay,
            String id,
            int color,
            float defaultHeight,
            float topOffset
    ) {
        super(
                gamePlay.gamePlayView,
                id,
                720,1280,Constant.SCREEN_WIDTH,Constant.SCREEN_HEIGHT,0,
                color,
                defaultHeight,
                topOffset,
                Constant.ButtonBlockTopOffSetColorFactor,
                Constant.ButtonBlockHeightColorFactor,
                Constant.ButtonBlockFloorColorFactor,
                null,
                false
        );
        this.gamePlay = gamePlay;
        gameModeAndLevel = gamePlay.gameModeAndLevel;
        drawUtil = gamePlayView.nowView.drawUtil;
        this.closeButton.setButtonListener(
                ()->{
                    new Thread(){
                        @Override
                        public void run(){
                            Thread thread1 = new Thread(){
                                @Override
                                public void run(){
                                    retryBand.closeButton.doButtonStuff();
                                }
                            };
                            Thread thread2 = new Thread(){
                                @Override
                                public void run(){
                                    scoreResultBand.closeButton.doButtonStuff();
                                }
                            };
                            Thread thread3 = new Thread(){
                                @Override
                                public void run(){
                                    bottomBand.closeButton.doButtonStuff();
                                }
                            };

                            thread1.start();
                            thread2.start();
                            thread3.start();

                            try {
                                thread1.join();
                                thread2.join();
                                thread3.join();

                                gamePlayView.setNowMenu(null);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }.start();
                }
        );
        initScoreResultBand();
        initRetryBand();
        initBottomBand();

        scoreResultBand.popFromTop();
        retryBand.popFromBottom();
        bottomBand.popFromBottom();
    }
    public void initScoreResultBand(){
        scoreResultBand = new MyMenu(
                gamePlayView,
                "scoreResultBand",
                720,500,
                menuWidth,500,
                200,
                Constant.COLOR_SKINISH,
                defaultHeight,
                TopOffset,
                Constant.ButtonBlockTopOffSetColorFactor,
                Constant.ButtonBlockHeightColorFactor,
                Constant.ButtonBlockFloorColorFactor,
                null,
                false
        );
        scoreResultBand.setFloorShadowFactorX(menufloorShadowFactorX);
        scoreResultBand.setFloorShadowFactorY(menufloorShadowFactorY);

        scoreResultBand.closeButton.setButtonListener(
                ()->{
                    Thread thread = scoreResultBand.backToBottom();
                    try {
                        thread.join();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
        );
    }
    public void initRetryBand(){
        retryBand = new MyMenu(
                gamePlayView,
                "retryBand",
                720,1280,
                menuWidth,600,
                240,
                Constant.COLOR_SKINISH,
                defaultHeight,
                TopOffset,
                Constant.ButtonBlockTopOffSetColorFactor,
                Constant.ButtonBlockHeightColorFactor,
                Constant.ButtonBlockFloorColorFactor,
                null,
                false
        );
        retryBand.setFloorShadowFactorX(menufloorShadowFactorX);
        retryBand.setFloorShadowFactorY(menufloorShadowFactorY);
        retryBand.closeButton.setButtonListener(
                ()->{
                    Thread thread = retryBand.backToBottom();
                    try {
                        thread.join();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
        );
    }
    public void initBottomBand(){
        float bottomBandHeight = 300;
        float bottomBandButtonsWidth = 160;
        float bottomBandButtonsHeight = bottomBandButtonsWidth;
        float bottomBandButtonsRelativeY = bottomBandButtonsHeight/2+40;
        float bottomBandButtonsSpanX = 40;

        bottomBand = new MyMenu(
                gamePlayView,
                "bottomBand",
                720,Constant.SCREEN_HEIGHT-350,
                menuWidth,bottomBandHeight,
                150,
                Constant.COLOR_SKINISH,
                defaultHeight,
                TopOffset,
                Constant.ButtonBlockTopOffSetColorFactor,
                Constant.ButtonBlockHeightColorFactor,
                Constant.ButtonBlockFloorColorFactor,
                null,
                false
        );
        bottomBand.setFloorShadowFactorX(menufloorShadowFactorX);
        bottomBand.setFloorShadowFactorY(menufloorShadowFactorY);
        bottomBand.closeButton.setButtonListener(
                ()->{
                    Thread thread = bottomBand.backToBottom();
                    try {
                        thread.join();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
        );

        mainMenuButton = new ImgButton(
                0,
                -(bottomBandButtonsSpanX+bottomBandButtonsWidth)*(2+1)/2,bottomBandButtonsRelativeY,
                bottomBandButtonsWidth,bottomBandButtonsHeight,
                Constant.COLOR_RED,
                20,
                0,
                0,
                Constant.ButtonBlockHeightColorFactor,
                Constant.ButtonBlockFloorColorFactor,
                Constant.MenuImg,
                Constant.SnakeBodyImg
        );
        bottomBand.addButton(mainMenuButton);
        mainMenuButton.setTopImgRatio(0.5f);
        mainMenuButton.setButtonListener(
                ()->{
                    gamePlayView.setNowView(Constant.START_VIEW);
                    MyMenu endlessPlayMenu = new EndlessPlayMenu(gamePlayView);
                    gamePlayView.setNowMenu(endlessPlayMenu);
                }
        );


        preLevelButton = new ImgButton(
                0,
                -(bottomBandButtonsSpanX+bottomBandButtonsWidth)*(1)/2,bottomBandButtonsRelativeY,
                bottomBandButtonsWidth,bottomBandButtonsHeight,
                Constant.COLOR_GREEN,
                20,
                0,
                0,
                Constant.ButtonBlockHeightColorFactor,
                Constant.ButtonBlockFloorColorFactor,
                Constant.ArrowBackImg,
                Constant.SnakeBodyImg
        );
        preLevelButton.setButtonListener(
                ()->{
                    gamePlayView.setNowView(gameModeAndLevel-1);
                    closeButton.doButtonStuff();
                }
        );
        bottomBand.addButton(preLevelButton);
        addToMenu(preLevelButton);

        nextLevelButton = new ImgButton(
                0,
                (bottomBandButtonsSpanX+bottomBandButtonsWidth)*(1)/2,bottomBandButtonsRelativeY,
                bottomBandButtonsWidth,bottomBandButtonsHeight,
                Constant.COLOR_GREEN,
                20,
                0,
                0,
                Constant.ButtonBlockHeightColorFactor,
                Constant.ButtonBlockFloorColorFactor,
                Constant.ArrowBackImg,
                Constant.SnakeBodyImg
        );
        nextLevelButton.setButtonListener(
                ()->{
                    gamePlayView.setNowView(gameModeAndLevel+1);
                    closeButton.doButtonStuff();
                }
        );
        nextLevelButton.setTopImgRotateDegrees(180);
        bottomBand.addButton(nextLevelButton);
        addToMenu(nextLevelButton);


        if(gameModeAndLevel>Constant.GAMEPLAY_VIEW_ENDLESS&&gameModeAndLevel<Constant.GAMEPLAY_VIEW_ENDLESS+10){
            if(!LoadGameUtil.doLevelExist(Constant.GAME_MODE_ENDLESS,gameModeAndLevel%10-1,gamePlayView.getResources()))
                preLevelButton.setDisabled(true);
            if(!LoadGameUtil.doLevelExist(Constant.GAME_MODE_ENDLESS,gameModeAndLevel%10+1,gamePlayView.getResources()))
               nextLevelButton.setDisabled(true);
        }else if(gameModeAndLevel>Constant.GAMEPLAY_VIEW_ORIGINAL&&gameModeAndLevel<Constant.GAMEPLAY_VIEW_ORIGINAL+10){
            if(!LoadGameUtil.doLevelExist(Constant.GAME_MODE_ORIGINAL,gameModeAndLevel%10-1,gamePlayView.getResources()))
                preLevelButton.setDisabled(true);
            if(!LoadGameUtil.doLevelExist(Constant.GAME_MODE_ORIGINAL,gameModeAndLevel%10+1,gamePlayView.getResources()))
                nextLevelButton.setDisabled(true);
        }

        musicButton = new ImgButton(
                0,
                (bottomBandButtonsSpanX+bottomBandButtonsWidth)*(2+1)/2,bottomBandButtonsRelativeY,
                bottomBandButtonsWidth,bottomBandButtonsHeight,
                Constant.COLOR_SKYBLUE,
                20,
                0,
                0,
                Constant.ButtonBlockHeightColorFactor,
                Constant.ButtonBlockFloorColorFactor,
                Constant.SoundOutloud,
                Constant.SnakeBodyImg
        );
        bottomBand.addButton(musicButton);
        addToMenu(musicButton);
    }
    @Override
    public void onTouchEvent(MotionEvent event, float x, float y){
        if(scoreResultBand.testTouch(x,y))scoreResultBand.onTouchEvent(event,x,y);
        else if(retryBand.testTouch(x,y))retryBand.onTouchEvent(event,x,y);
        else if(bottomBand.testTouch(x,y))bottomBand.onTouchEvent(event, x, y);
//        switch (event.getAction()){
//            case MotionEvent.ACTION_DOWN:
//            case MotionEvent.ACTION_MOVE:
//                Button tempt = nowPressedButton;
//                nowPressedButton = whichButtonTouched(x,y);
//                if(tempt!=null&&nowPressedButton!=tempt)tempt.whenReleased(false);
//                if(nowPressedButton!=null)nowPressedButton.whenPressed();
//                break;
//            case MotionEvent.ACTION_UP:
//                // if(pauseButton.testTouch(x,y))pauseButton
//                whenUp(x,y);
//                break;
//        }
    }
    @Override
    public void drawSelf(TexDrawer painter){


        scoreResultBand.drawSelf(painter);
        retryBand.drawSelf(painter);
        bottomBand.drawSelf(painter);
    }
    @Override
    public void drawHeight(TexDrawer painter){
        scoreResultBand.drawHeight(painter);
        retryBand.drawHeight(painter);
        bottomBand.drawHeight(painter);
    }
    @Override
    public void drawFloorShadow(TexDrawer painter){
        scoreResultBand.drawFloorShadow(painter);
        retryBand.drawFloorShadow(painter);
        bottomBand.drawFloorShadow(painter);
    }
}
