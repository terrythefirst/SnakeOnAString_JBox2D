package com.work.terry.snakeonastring_jbox2d.SurfaceViewAndActivity;

import android.view.MotionEvent;

import com.work.terry.snakeonastring_jbox2d.UI.GameElement;
import com.work.terry.snakeonastring_jbox2d.UI.ImgButton;
import com.work.terry.snakeonastring_jbox2d.UI.RoundEdgeRectButton;
import com.work.terry.snakeonastring_jbox2d.UI.Score;
import com.work.terry.snakeonastring_jbox2d.UI.ScoreBoard;
import com.work.terry.snakeonastring_jbox2d.Util.Constant;
import com.work.terry.snakeonastring_jbox2d.Util.DrawUtil;
import com.work.terry.snakeonastring_jbox2d.Util.LoadGameUtil;
import com.work.terry.snakeonastring_jbox2d.Util.TexDrawer;

/**
 * Created by Terry on 2018/3/18.
 */

public class PauseMenu extends MyMenu {
    GamePlay gamePlay;
    float menuFloorShadowFactorX = 0;
    float menuFloorShadowFactorY = 4f;
    float menuWidth = 900;
    int gameModeAndLevel;
    int gameMode;

    MyMenu centerBand;
    GameElement levelNameBoard;
    ScoreBoard levelNumberBoard;
    RoundEdgeRectButton resumeButton;
    RoundEdgeRectButton retryButton;
    RoundEdgeRectButton changeSkinButton;

    MyMenu bottomBand;

    public PauseMenu(
            GamePlay gamePlay
    ) {
        super(
                gamePlay.gamePlayView,
                "PauseMenu",
                720,1280, Constant.SCREEN_WIDTH,Constant.SCREEN_HEIGHT,0,
                0,
                Constant.COLOR_SKINISH,
                0,
                Constant.ButtonBlockTopOffSetColorFactor,
                Constant.ButtonBlockHeightColorFactor,
                Constant.ButtonBlockFloorColorFactor,
                null,
                false
        );
        this.gamePlay = gamePlay;
        gameModeAndLevel = gamePlay.gameModeAndLevel;
        if(gameModeAndLevel>Constant.GAMEPLAY_VIEW_ENDLESS&&gameModeAndLevel<Constant.GAMEPLAY_VIEW_ENDLESS+10){
            gameMode = Constant.GAME_MODE_ENDLESS;
        }else if(gameModeAndLevel>Constant.GAMEPLAY_VIEW_ORIGINAL&&gameModeAndLevel<Constant.GAMEPLAY_VIEW_ORIGINAL+10){
            gameMode = Constant.GAME_MODE_ORIGINAL;
        }
        this.closeButton.setButtonListener(
                ()->{
                    new Thread(){
                        @Override
                        public void run(){
                            Thread thread2 = new Thread(){
                                @Override
                                public void run(){
                                    centerBand.closeButton.doButtonStuff();
                                }
                            };
                            Thread thread3 = new Thread(){
                                @Override
                                public void run(){
                                    bottomBand.closeButton.doButtonStuff();
                                }
                            };

                            thread2.start();
                            thread3.start();

                            try {
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
        bottomBand = new BottomMenu(
                gamePlay,
                this,
                menuWidth,
                defaultHeight,
                floorShadowFactorX,
                floorShadowFactorY
        );
        initCenterBand();

        bottomBand.popFromBottom();
        centerBand.popFromBottom();
    }
    public void initCenterBand(){
        centerBand = new MyMenu(
                gamePlayView,
                "centerBand",
                720,1280,
                menuWidth,1200,
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
        centerBand.setFloorShadowFactorX(menuFloorShadowFactorX);
        centerBand.setFloorShadowFactorY(menuFloorShadowFactorY);
        centerBand.closeButton.setButtonListener(
                ()->{
                    Thread thread = centerBand.backToBottom();
                    try {
                        thread.join();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
        );

        float levelLineY = 120;
        levelNameBoard = new GameElement(
                "levelNameBoard ",
                -30,levelLineY,
                centerBand.width-centerBand.angleRadius*2,160,
                Constant.COLOR_GREY,
                0,
                0,0,0,0,
                Constant.EndlessChineseImg
        );
        levelNameBoard.setDoDrawHeight(false);
        levelNameBoard.setDoDrawFloorShadow(false);
        centerBand.addToMenu(levelNameBoard);

        Score gameLevel = new Score(gameModeAndLevel%10);
        levelNumberBoard = new ScoreBoard(
                gameLevel,
                200,levelLineY,
                100,120,
                Constant.COLOR_GREY,
                0,
                0,
                Constant.ButtonBlockTopOffSetColorFactor,
                Constant.ButtonBlockHeightColorFactor,
                Constant.ButtonBlockFloorColorFactor
        );
        levelNumberBoard.setDoDrawFloorShadow(false);
        levelNumberBoard.setDoDrawHeight(false);
        centerBand.addToMenu(levelNumberBoard);

        //threeButtons
        float threeButtonsStartY = 400;
        float threeButtonsYSpan = 40;
        float threeButtonsWidth = 600;
        float threeButtonsHeight = 200;
        float threeButtonsAngleRadius = 100;
        //resumeButton
        resumeButton = new RoundEdgeRectButton(
                0,
                0,threeButtonsStartY,
                threeButtonsWidth,threeButtonsHeight,
                threeButtonsAngleRadius,
                Constant.COLOR_GREEN,
                20,
                0,
                Constant.ButtonBlockTopOffSetColorFactor,
                Constant.ButtonBlockHeightColorFactor,
                Constant.ButtonBlockFloorColorFactor,
                null,
                null
        );

        GameElement tempt = new GameElement(
                "ge",
                -resumeButton.width/2+resumeButton.angleRadius+10,resumeButton.height/2-20,
                resumeButton.height*3/5,resumeButton.height*3/5,
                0,
                0,
                0,0,0,0,
                Constant.StartImg
        );
        tempt.setDoDrawHeight(false);
        tempt.setDoDrawFloorShadow(false);
        resumeButton.addToTopGameElements(tempt);

        tempt = new GameElement(
                "ge",
                60,resumeButton.height*2/5,
                400,resumeButton.height*4/5,
                0,
                0,
                0,0,0,0,
                Constant.ResumeImg
        );
        tempt.setDoDrawHeight(false);
        tempt.setDoDrawFloorShadow(false);
        resumeButton.addToTopGameElements(tempt);

        resumeButton.setButtonListener(
                ()->{
                    closeButton.doButtonStuff();
                    gamePlay.IS_PLAYING = true;
                }
        );
        centerBand.addButton(resumeButton);

        //retryButton
        retryButton = new RoundEdgeRectButton(
                0,
                0,threeButtonsStartY+(threeButtonsYSpan+threeButtonsHeight),
                threeButtonsWidth,threeButtonsHeight,
                threeButtonsAngleRadius,
                Constant.COLOR_GOLDEN,
                20,
                0,
                Constant.ButtonBlockTopOffSetColorFactor,
                Constant.ButtonBlockHeightColorFactor,
                Constant.ButtonBlockFloorColorFactor,
                null,
                null
        );
        tempt = new GameElement(
                "ge",
                -retryButton.width/2+retryButton.angleRadius+10,retryButton.height/2-20,
                retryButton.height*3/5,retryButton.height*3/5,
                0,
                0,
                0,0,0,0,
                Constant.RestartImg
        );
        tempt.setDoDrawHeight(false);
        tempt.setDoDrawFloorShadow(false);
        retryButton.addToTopGameElements(tempt);

        tempt = new GameElement(
                "ge",
                60,retryButton.height*2/5,
                400,retryButton.height*4/5,
                0,
                0,
                0,0,0,0,
                Constant.RestartChineseImg
        );
        tempt.setDoDrawHeight(false);
        tempt.setDoDrawFloorShadow(false);
        retryButton.addToTopGameElements(tempt);

        retryButton.setButtonListener(
                ()->{
                    gamePlayView.setNowView(gamePlay.gameModeAndLevel);
                    closeButton.doButtonStuff();
                }
        );
        centerBand.addButton(retryButton);

        //changeSkinButton
        changeSkinButton = new RoundEdgeRectButton(
                0,
                0,threeButtonsStartY+(threeButtonsYSpan+threeButtonsHeight)*2,
                threeButtonsWidth,threeButtonsHeight,
                threeButtonsAngleRadius,
                Constant.COLOR_WINE_RED,
                20,
                0,
                Constant.ButtonBlockTopOffSetColorFactor,
                Constant.ButtonBlockHeightColorFactor,
                Constant.ButtonBlockFloorColorFactor,
                null,
                null
        );
        tempt = new GameElement(
                "ge",
                -changeSkinButton.width/2+changeSkinButton.angleRadius+10,changeSkinButton.height/2-20,
                changeSkinButton.height*3/5,changeSkinButton.height*3/5,
                0,
                0,
                0,0,0,0,
                Constant.ShapeImg
        );
        tempt.setDoDrawHeight(false);
        tempt.setDoDrawFloorShadow(false);
        changeSkinButton.addToTopGameElements(tempt);

        tempt = new GameElement(
                "ge",
                60,changeSkinButton.height*2/5,
                400,changeSkinButton.height*4/5,
                0,
                0,
                0,0,0,0,
                Constant.ChangeSkinChineseImg
        );
        tempt.setDoDrawHeight(false);
        tempt.setDoDrawFloorShadow(false);
        changeSkinButton.addToTopGameElements(tempt);

        changeSkinButton.setButtonListener(
                ()->{
                    gamePlay.changeSkinButton.doButtonStuff();
                    closeButton.doButtonStuff();
                }
        );
        centerBand.addButton(changeSkinButton);
    }
    @Override
    public void onTouchEvent(MotionEvent event, float x, float y){
        if(centerBand.testTouch(x,y))centerBand.onTouchEvent(event,x,y);
        else if(bottomBand.testTouch(x,y))bottomBand.onTouchEvent(event, x, y);
    }

    @Override
    public void drawSelf(TexDrawer painter){
        centerBand.drawSelf(painter);
        bottomBand.drawSelf(painter);
    }
    @Override
    public void drawHeight(TexDrawer painter){
        centerBand.drawHeight(painter);
        bottomBand.drawHeight(painter);
    }
    @Override
    public void drawFloorShadow(TexDrawer painter){
        centerBand.drawFloorShadow(painter);
        bottomBand.drawFloorShadow(painter);
    }
}
