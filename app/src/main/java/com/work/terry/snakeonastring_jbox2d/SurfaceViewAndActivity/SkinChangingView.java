package com.work.terry.snakeonastring_jbox2d.SurfaceViewAndActivity;

import android.content.SharedPreferences;
import android.view.MotionEvent;

import com.work.terry.snakeonastring_jbox2d.Animation.BreathAnimation;
import com.work.terry.snakeonastring_jbox2d.UI.Button;
import com.work.terry.snakeonastring_jbox2d.UI.GameElement;
import com.work.terry.snakeonastring_jbox2d.UI.ImgButton;
import com.work.terry.snakeonastring_jbox2d.UI.RoundEdgeRectButton;
import com.work.terry.snakeonastring_jbox2d.UI.Score;
import com.work.terry.snakeonastring_jbox2d.UI.ScoreBoard;
import com.work.terry.snakeonastring_jbox2d.Util.Constant;
import com.work.terry.snakeonastring_jbox2d.Util.DrawUtil;

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
    public void onTouchEvent(MotionEvent event, int x, int y) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                Button tempt = nowPressedButton;
                nowPressedButton = whichButtonTouched(x,y);
                if(tempt!=null&&nowPressedButton!=tempt)tempt.whenReleased(false);
                if(nowPressedButton!=null)nowPressedButton.whenPressed();
                break;
            case MotionEvent.ACTION_UP:
                // if(pauseButton.testTouch(x,y))pauseButton
                whenUp(x,y);
                break;
        }
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause(SharedPreferences.Editor editor) {

    }
}
