package com.work.terry.snakeonastring_jbox2d.SurfaceViewAndActivity;

import android.content.SharedPreferences;
import android.view.MotionEvent;

import com.work.terry.snakeonastring_jbox2d.UI.Button;
import com.work.terry.snakeonastring_jbox2d.UI.GameElements;
import com.work.terry.snakeonastring_jbox2d.UI.ImgButton;
import com.work.terry.snakeonastring_jbox2d.UI.ScoreBoard;
import com.work.terry.snakeonastring_jbox2d.Util.Constant;
import com.work.terry.snakeonastring_jbox2d.Util.DrawUtil;
import com.work.terry.snakeonastring_jbox2d.Util.TexDrawer;

import static com.work.terry.snakeonastring_jbox2d.Util.Constant.*;

/**
 * Created by Terry on 2018/2/17.
 */

public class StartView extends MyView {
    private String backgroundImg = BackgroundImg;
    private ImgButton musicButton;
    private ImgButton statisticsButton;
    private ScoreBoard yellowStarScoreboard;
    private GameElements yellowStar;
    private ImgButton originalPlayButton;
    private ImgButton endlessPlayButton;
    private ImgButton favoriteButton;
    private ImgButton rateButton;
    private ImgButton likeButton;

    private float threeButtonsY = 2260;
    private float threeButtonsSpan = 20;

    public StartView(
        String backgroundImg
    ){
        if(backgroundImg!=null)this.backgroundImg = backgroundImg;
        drawUtil = new DrawUtil(this.backgroundImg);
        initButtons();
        initLetters();
    }
    public void initLetters(){
        Button tempt = new Button(
                0,
                300,400,
                200,200,
                Constant.C0LOR_SNAKE_WHITE,
                20,
                10,
                Constant.ButtonBlockTopOffSetColorFactor,
                0,
                Constant.ButtonBlockFloorColorFactor,
                Constant.LetterA
        );
        tempt.setDoDrawHeight(false);
        drawUtil.addToTopLayer(tempt);
    }
    public void initButtons(){
        musicButton = new ImgButton(
                0,
                100,140,
                160,160,
                Constant.C0LOR_CYAN,
                20,
                0,
                0,
                Constant.ButtonBlockHeightColorFactor,
                Constant.ButtonBlockFloorColorFactor,
                Constant.SoundOutloud,
                Constant.SnakeBodyImg
        );
        drawUtil.addToTopLayer(musicButton);

        statisticsButton = new ImgButton(
                0,
                280,140,
                160,160,
                Constant.C0LOR_CYAN,
                20,
                0,
                0,
                Constant.ButtonBlockHeightColorFactor,
                Constant.ButtonBlockFloorColorFactor,
                Constant.StatisticsBars,
                Constant.SnakeBodyImg
        );
        drawUtil.addToTopLayer(statisticsButton);

        initThreeButtons();

        originalPlayButton = new ImgButton(
                0,
                500,1850,
                250,250,
                Constant.C0LOR_CYAN,
                60,
                0,
                0,
                Constant.ButtonBlockHeightColorFactor,
                Constant.ButtonBlockFloorColorFactor,
                Constant.StartImg,
                Constant.RoundEdgeCube
        );
        originalPlayButton.setTopImgRatio(0.5f);
        originalPlayButton.setDisabled(true);
        drawUtil.addToTopLayer(originalPlayButton);

        endlessPlayButton= new ImgButton(
                0,
                940,1850,
                250,250,
                Constant.C0LOR_CYAN,
                60,
                0,
                0,
                Constant.ButtonBlockHeightColorFactor,
                Constant.ButtonBlockFloorColorFactor,
                Constant.InfiniteImg,
                Constant.RoundEdgeCube
        );
        endlessPlayButton.setTopImgRatio(0.5f);
        drawUtil.addToTopLayer(endlessPlayButton);
    }
    public void initThreeButtons(){
        threeButtonsY = 2260;
        threeButtonsSpan = 30;
        favoriteButton = new ImgButton(
                0,
                720-160-threeButtonsSpan,threeButtonsY,
                160,160,
                Constant.C0LOR_CYAN,
                20,
                0,
                0,
                Constant.ButtonBlockHeightColorFactor,
                Constant.ButtonBlockFloorColorFactor,
                Constant.StatisticsBars,
                Constant.SnakeBodyImg
        );
        drawUtil.addToTopLayer(favoriteButton);

        rateButton = new ImgButton(
                0,
                720,threeButtonsY,
                160,160,
                Constant.C0LOR_CYAN,
                20,
                0,
                0,
                Constant.ButtonBlockHeightColorFactor,
                Constant.ButtonBlockFloorColorFactor,
                Constant.RateImg,
                Constant.SnakeBodyImg
        );
        rateButton.setTopImgRatio(0.6f);
        drawUtil.addToTopLayer(rateButton);

        likeButton = new ImgButton(
                0,
                720+160+threeButtonsSpan,threeButtonsY,
                160,160,
                Constant.C0LOR_CYAN,
                20,
                0,
                0,
                Constant.ButtonBlockHeightColorFactor,
                Constant.ButtonBlockFloorColorFactor,
                Constant.LikeImg,
                Constant.SnakeBodyImg
        );
        likeButton.setTopImgRatio(0.6f);
        drawUtil.addToTopLayer(likeButton);
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
