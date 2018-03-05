package com.work.terry.snakeonastring_jbox2d.SurfaceViewAndActivity;

import android.content.SharedPreferences;
import android.view.MotionEvent;

import com.work.terry.snakeonastring_jbox2d.UI.Button;
import com.work.terry.snakeonastring_jbox2d.UI.GameElement;
import com.work.terry.snakeonastring_jbox2d.UI.ImgButton;
import com.work.terry.snakeonastring_jbox2d.UI.Score;
import com.work.terry.snakeonastring_jbox2d.UI.ScoreBoard;
import com.work.terry.snakeonastring_jbox2d.Util.Constant;


/**
 * Created by Terry on 2018/3/5.
 */

public class SkinChangingView extends MyView {
    private ScoreBoard yellowStarScoreboard;
    private GameElement yellowStar;
    GamePlayView gamePlayView;
    ImgButton returnButton;
    public SkinChangingView(GamePlayView gamePlayView){
        this.gamePlayView = gamePlayView;
        initReturnButton();
        returnButton.setButtonListener(
                ()->{
                    gamePlayView.setNowView(Constant.START_VIEW);
                }
        );
        initOthers();
    }
    public SkinChangingView(GamePlayView gamePlayView,int gameMode,int gameLevel){
        this.gamePlayView = gamePlayView;
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
                100,140,
                160,160,
                Constant.COLOR_GREEN,
                20,
                0,
                0,
                Constant.ButtonBlockHeightColorFactor,
                Constant.ButtonBlockFloorColorFactor,
                Constant.ArrowBackImg,
                Constant.SnakeBodyImg
        );
        returnButton.setTopImgRatio(0.4f);
    }
    @Override
    public void onTouchEvent(MotionEvent event, int x, int y) {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause(SharedPreferences.Editor editor) {

    }
}
