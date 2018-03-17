package com.work.terry.snakeonastring_jbox2d.SurfaceViewAndActivity;

import com.work.terry.snakeonastring_jbox2d.UI.ImgButton;
import com.work.terry.snakeonastring_jbox2d.UI.RoundEdgeRect;
import com.work.terry.snakeonastring_jbox2d.Util.Constant;
import com.work.terry.snakeonastring_jbox2d.Util.DrawUtil;
import com.work.terry.snakeonastring_jbox2d.Util.TexDrawer;

/**
 * Created by Terry on 2018/3/17.
 */

public class GameOverMenu extends MyMenu {
    float menuWidth = 600;
    DrawUtil drawUtil;


    MyMenu scoreResultBand;


    MyMenu retryBand;


    MyMenu bottomBand;
    ImgButton mainMenuButton;
    ImgButton preLevelButton;
    ImgButton nextLevelButton;
    ImgButton musicButton;

    public GameOverMenu(
            GamePlayView gamePlayView,
            String id,
            int color,
            float defaultHeight,
            float topOffset
    ) {
        super(
                gamePlayView,
                id,
                0,0,0,0,0,
                color,
                defaultHeight,
                topOffset,
                Constant.ButtonBlockTopOffSetColorFactor,
                Constant.ButtonBlockHeightColorFactor,
                Constant.ButtonBlockFloorColorFactor,
                null,
                false
        );
        drawUtil = gamePlayView.nowView.drawUtil;
        initScoreResultBand();
        initRetryBand();
        initBottomBand();
    }
    public void initScoreResultBand(){
        scoreResultBand = new MyMenu(
                gamePlayView,
                "scoreResultBand",
                720,600,
                menuWidth,400,
                100,
                Constant.COLOR_SKINISH,
                defaultHeight,
                TopOffset,
                Constant.ButtonBlockTopOffSetColorFactor,
                Constant.ButtonBlockHeightColorFactor,
                Constant.ButtonBlockFloorColorFactor,
                null,
                false
        );
    }
    public void initRetryBand(){
        retryBand = new MyMenu(
                gamePlayView,
                "retryBand",
                720,600,
                menuWidth,1280,
                100,
                Constant.COLOR_SKINISH,
                defaultHeight,
                TopOffset,
                Constant.ButtonBlockTopOffSetColorFactor,
                Constant.ButtonBlockHeightColorFactor,
                Constant.ButtonBlockFloorColorFactor,
                null,
                false
        );
    }
    public void initBottomBand(){
        bottomBand = new MyMenu(
                gamePlayView,
                "bottomBand",
                720,Constant.SCREEN_HEIGHT-200,
                menuWidth,100,
                50,
                Constant.COLOR_SKINISH,
                defaultHeight,
                TopOffset,
                Constant.ButtonBlockTopOffSetColorFactor,
                Constant.ButtonBlockHeightColorFactor,
                Constant.ButtonBlockFloorColorFactor,
                null,
                false
        );

        musicButton = new ImgButton(
                0,
                100,140,
                160,160,
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
