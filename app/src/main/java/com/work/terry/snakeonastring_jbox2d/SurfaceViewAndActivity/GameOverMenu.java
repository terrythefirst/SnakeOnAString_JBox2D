package com.work.terry.snakeonastring_jbox2d.SurfaceViewAndActivity;

import android.view.MotionEvent;

import com.work.terry.snakeonastring_jbox2d.Animation.UniformMotionAnimation;
import com.work.terry.snakeonastring_jbox2d.UI.Button;
import com.work.terry.snakeonastring_jbox2d.UI.GameElement;
import com.work.terry.snakeonastring_jbox2d.UI.ImgButton;
import com.work.terry.snakeonastring_jbox2d.UI.RoundEdgeRect;
import com.work.terry.snakeonastring_jbox2d.UI.RoundEdgeRectButton;
import com.work.terry.snakeonastring_jbox2d.UI.Score;
import com.work.terry.snakeonastring_jbox2d.UI.ScoreBoard;
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
    float menuFloorShadowFactorX = 0;
    float menuFloorShadowFactorY = 4f;
    float menuWidth = 900;
    int gameModeAndLevel;
    int gameMode;


    MyMenu scoreResultBand;
    RoundEdgeRectButton retryButton;


    MyMenu retryBand;
    GameElement levelNameBoard;
    ScoreBoard levelNumberBoard;
    ScoreBoard finalScoreBoard;
    GameElement bestBoard;
    ScoreBoard bestScoreBoard;
    ImgButton statisticsButton;


    MyMenu bottomBand;

    public GameOverMenu(
            GamePlay gamePlay
    ) {
        super(
                gamePlay.gamePlayView,
                "gameOverMenu",
                720,1280,Constant.SCREEN_WIDTH,Constant.SCREEN_HEIGHT,0,
                Constant.COLOR_SKINISH,
                20,
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
        bottomBand = new BottomMenu(
                gamePlay,
                this,
                menuWidth,
                defaultHeight,
                floorShadowFactorX,
                floorShadowFactorY
        );

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
        scoreResultBand.setFloorShadowFactorX(menuFloorShadowFactorX);
        scoreResultBand.setFloorShadowFactorY(menuFloorShadowFactorY);

        scoreResultBand.closeButton.setButtonListener(
                ()->{
                    Thread thread = scoreResultBand.backToTop();
                    try {
                        thread.join();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
        );

        Score finalScore = gamePlay.score;
        gamePlay.gamePlayView.yellowStars+=finalScore.getScore()/10;
        finalScoreBoard = new ScoreBoard(
                finalScore,
                0,scoreResultBand.height/2-scoreResultBand.defaultHeight-10,
                400,160,
                Constant.COLOR_RED,
                0,
                0,
                Constant.ButtonBlockTopOffSetColorFactor,
                Constant.ButtonBlockHeightColorFactor,
                Constant.ButtonBlockFloorColorFactor
        );
        finalScoreBoard.setDoDrawFloorShadow(false);
        finalScoreBoard.setDoDrawHeight(false);
        scoreResultBand.addToMenu(finalScoreBoard);


        float levelLineY = 60;
        levelNameBoard = new GameElement(
                "levelNameBoard ",
                -30,levelLineY,
                scoreResultBand.width-scoreResultBand.angleRadius*2-30,160,
                Constant.COLOR_GREY,
                0,
                0,0,0,0,
                Constant.EndlessChineseImg
        );
        levelNameBoard.setDoDrawHeight(false);
        levelNameBoard.setDoDrawFloorShadow(false);
        scoreResultBand.addToMenu(levelNameBoard);

        Score gameLevel = new Score(gameModeAndLevel%10);
        levelNumberBoard = new ScoreBoard(
                gameLevel,
                200,levelLineY-8,
                100,80,
                Constant.COLOR_GREY,
                0,
                10,
                Constant.ButtonBlockTopOffSetColorFactor,
                Constant.ButtonBlockHeightColorFactor,
                Constant.ButtonBlockFloorColorFactor
        );
        levelNumberBoard.setDoDrawFloorShadow(false);
        levelNumberBoard.setDoDrawHeight(false);
        scoreResultBand.addToMenu(levelNumberBoard);



        float bestLineY = scoreResultBand.height-120-scoreResultBand.defaultHeight;
        bestBoard = new GameElement(
                "bestBoard  ",
                -100,bestLineY,
                240,140,
                Constant.COLOR_GREEN,
                0,
                0,0,0,0,
                Constant.BestChineseImg
        );
        bestBoard.setDoDrawHeight(false);
        bestBoard.setDoDrawFloorShadow(false);
        scoreResultBand.addToMenu(bestBoard);

        int bestScores = gamePlay.gamePlayView.maxScore;
        if(bestScores<finalScore.getScore()){
            gamePlay.gamePlayView.maxScore = finalScore.getScore();
            bestScores = finalScore.getScore();
        }
        Score bestScore = new Score(bestScores);
        bestScoreBoard = new ScoreBoard(
                bestScore,
                120,bestLineY,
                360,100,
                Constant.COLOR_GREEN,
                0,
                0,
                Constant.ButtonBlockTopOffSetColorFactor,
                Constant.ButtonBlockHeightColorFactor,
                Constant.ButtonBlockFloorColorFactor
        );
        bestScoreBoard.setDoDrawHeight(false);
        bestScoreBoard.setDoDrawFloorShadow(false);
        scoreResultBand.addToMenu(bestScoreBoard);

        statisticsButton = new ImgButton(
                0,
                -280,bestLineY-20,
                160,160,
                Constant.COLOR_GREEN,
                20,
                0,
                0,
                Constant.ButtonBlockHeightColorFactor,
                Constant.ButtonBlockFloorColorFactor,
                Constant.StatisticsBars,
                Constant.SnakeBodyImg
        );
        scoreResultBand.addButton(statisticsButton);

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
        retryBand.setFloorShadowFactorX(menuFloorShadowFactorX);
        retryBand.setFloorShadowFactorY(menuFloorShadowFactorY);
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

        retryButton = new RoundEdgeRectButton(
                0,
                0,200,
                640,200,
                100,
                Constant.COLOR_GOLDEN,
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
        retryBand.addButton(retryButton);
    }
    @Override
    public void onTouchEvent(MotionEvent event, float x, float y){
        if(scoreResultBand.testTouch(x,y))scoreResultBand.onTouchEvent(event,x,y);
        else if(retryBand.testTouch(x,y))retryBand.onTouchEvent(event,x,y);
        else if(bottomBand.testTouch(x,y))bottomBand.onTouchEvent(event, x, y);
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
