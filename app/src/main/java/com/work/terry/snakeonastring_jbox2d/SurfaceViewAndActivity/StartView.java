package com.work.terry.snakeonastring_jbox2d.SurfaceViewAndActivity;

import android.content.SharedPreferences;
import android.view.MotionEvent;

import com.work.terry.snakeonastring_jbox2d.SnakeElements.Snake;
import com.work.terry.snakeonastring_jbox2d.SnakeElements.SnakeHead;
import com.work.terry.snakeonastring_jbox2d.SnakeElements.SnakeNode;
import com.work.terry.snakeonastring_jbox2d.UI.Button;
import com.work.terry.snakeonastring_jbox2d.UI.GameElements;
import com.work.terry.snakeonastring_jbox2d.UI.ImgButton;
import com.work.terry.snakeonastring_jbox2d.UI.JiggleAnimation;
import com.work.terry.snakeonastring_jbox2d.UI.ListJiggleAnimation;
import com.work.terry.snakeonastring_jbox2d.UI.ScoreBoard;
import com.work.terry.snakeonastring_jbox2d.Util.Constant;
import com.work.terry.snakeonastring_jbox2d.Util.DrawUtil;
import com.work.terry.snakeonastring_jbox2d.Util.TexDrawer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

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

    private List<GameElements> letterSnakeList = new ArrayList<>();
    private List<GameElements> letterOnAStringList = new ArrayList<>();
    private List<GameElements> snake = new ArrayList<>();

    float letterSnakeDefaultHeight = 30;
    float letterSnakeBeginX = 250;
    float letterSnakeSpan = 40;
    float letterSnakeWidth = 200;
    float letterSnakeHeight = 200;
    float letterSnakeY = 500;

    float letterSnake_OnAStringSpanY = 200;
    float letterOnAStringY = letterSnakeY+letterSnake_OnAStringSpanY;
    float letterOnaStringStartX = 200;
    float letterDefaultHeight = 30;
    float letterOnAStringWidth = 100;
    float letterOnAStringHeight = 100;
    float letterOnAStringSpanX = 10;
    float letterOnAStringGapX = 100;

    float HeadRadius = 50;
    float NodeRadius = 50;
    float SnakeY = 1400;
    float SnakeYSpan = 100;
    float SnakeXSpan = 90;
    float SnakeXTotalSpan = 8*SnakeXSpan;

    private float threeButtonsY = 2260;
    private float threeButtonsSpan = 20;



    public StartView(
        String backgroundImg
    ){
        if(backgroundImg!=null)this.backgroundImg = backgroundImg;
        drawUtil = new DrawUtil(this.backgroundImg);
        initButtons();
        initLetterSnake();
        initOnAString();
        initSnake();
    }
    public void initSnake(){

        int NodeIndex = 0;

        while (NodeIndex < 8){
            SnakeNode snakeNode = new SnakeNode(
                    720+SnakeXSpan*(NodeIndex-4),SnakeY+SnakeYSpan*((float)Math.sin(0.25*Math.PI*NodeIndex)),
                    NodeRadius,
                    Constant.C0LOR_SNAKE_WHITE,
                    Constant.SnakeDefaultHeight,
                    NodeIndex++
            );
            snakeNode.setTopRatio(0.95f);
            drawUtil.addToTopLayer(snakeNode);
            snake.add(snakeNode);
        }

        SnakeHead snakeHead = new SnakeHead(
                720+SnakeXSpan*(NodeIndex-4),SnakeY+SnakeYSpan*((float)Math.sin(0.25*Math.PI*NodeIndex)),
                1,1,
                NodeRadius,
                Constant.C0LOR_SNAKE_WHITE,
                Constant.SnakeDefaultHeight
        );
        snakeHead.setTopRatio(0.95f);
        drawUtil.addToTopLayer(snakeHead);
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
    }
    public void initLetterSnake(){
        Button tempt = new Button(
                5,
                letterSnakeBeginX,letterSnakeY,
                letterSnakeWidth,letterSnakeHeight,
                Constant.C0LOR_SNAKE_WHITE,
                letterSnakeDefaultHeight,
                10,
                Constant.ButtonBlockTopOffSetColorFactor,
                0,
                Constant.ButtonBlockFloorColorFactor,
                Constant.LetterS
        );
        tempt.setDoDrawHeight(false);
        letterSnakeList.add(tempt);
        drawUtil.addToTopLayer(tempt);

        tempt = new Button(
                5,
                letterSnakeBeginX+letterSnakeWidth+letterSnakeSpan,letterSnakeY,
                letterSnakeWidth,letterSnakeHeight,
                Constant.C0LOR_SNAKE_WHITE,
                letterSnakeDefaultHeight,
                10,
                Constant.ButtonBlockTopOffSetColorFactor,
                0,
                Constant.ButtonBlockFloorColorFactor,
                Constant.LetterN
        );
        tempt.setDoDrawHeight(false);
        letterSnakeList.add(tempt);
        drawUtil.addToTopLayer(tempt);

        tempt = new Button(
                5,
                letterSnakeBeginX+(letterSnakeWidth+letterSnakeSpan)*2,letterSnakeY,
                letterSnakeWidth,letterSnakeHeight,
                Constant.C0LOR_SNAKE_WHITE,
                letterSnakeDefaultHeight,
                10,
                Constant.ButtonBlockTopOffSetColorFactor,
                0,
                Constant.ButtonBlockFloorColorFactor,
                Constant.LetterA
        );
        tempt.setDoDrawHeight(false);
        letterSnakeList.add(tempt);
        drawUtil.addToTopLayer(tempt);

        tempt = new Button(
                5,
                letterSnakeBeginX+(letterSnakeWidth+letterSnakeSpan)*3,letterSnakeY,
                letterSnakeWidth,letterSnakeHeight,
                Constant.C0LOR_SNAKE_WHITE,
                letterSnakeDefaultHeight,
                10,
                Constant.ButtonBlockTopOffSetColorFactor,
                0,
                Constant.ButtonBlockFloorColorFactor,
                Constant.LetterK
        );
        tempt.setDoDrawHeight(false);
        letterSnakeList.add(tempt);
        drawUtil.addToTopLayer(tempt);

        tempt = new Button(
                5,
                letterSnakeBeginX+(letterSnakeWidth+letterSnakeSpan)*4,letterSnakeY,
                letterSnakeWidth,letterSnakeHeight,
                Constant.C0LOR_SNAKE_WHITE,
                letterSnakeDefaultHeight,
                10,
                Constant.ButtonBlockTopOffSetColorFactor,
                0,
                Constant.ButtonBlockFloorColorFactor,
                Constant.LetterE
        );
        tempt.setDoDrawHeight(false);
        letterSnakeList.add(tempt);
        drawUtil.addToTopLayer(tempt);

        new ListJiggleAnimation(
                letterSnakeList,
                40,
                0.3f,
                2000,
                true,
                0.3f,
                true
        ).start();
    }
    public void initOnAString(){
        Button tempt = new Button(
                9,
                letterOnaStringStartX,letterOnAStringY,
                letterOnAStringWidth,letterOnAStringHeight,
                Constant.C0LOR_SNAKE_WHITE,
                letterDefaultHeight,
                10,
                Constant.ButtonBlockTopOffSetColorFactor,
                0,
                Constant.ButtonBlockFloorColorFactor,
                Constant.LetterO
        );
        tempt.setDoDrawHeight(false);
        letterOnAStringList.add(tempt);
        drawUtil.addToTopLayer(tempt);

        tempt = new Button(
                9,
                letterOnAStringList.get(letterOnAStringList.size()-1).x+(letterOnAStringWidth+letterOnAStringSpanX),letterOnAStringY,
                letterOnAStringWidth,letterOnAStringHeight,
                Constant.C0LOR_SNAKE_WHITE,
                letterDefaultHeight,
                10,
                Constant.ButtonBlockTopOffSetColorFactor,
                0,
                Constant.ButtonBlockFloorColorFactor,
                Constant.LetterN
        );
        tempt.setDoDrawHeight(false);
        letterOnAStringList.add(tempt);
        drawUtil.addToTopLayer(tempt);

        tempt = new Button(
                9,
                letterOnAStringList.get(letterOnAStringList.size()-1).x+(letterOnAStringWidth+letterOnAStringGapX),letterOnAStringY,
                letterOnAStringWidth,letterOnAStringHeight,
                Constant.C0LOR_SNAKE_WHITE,
                letterDefaultHeight,
                10,
                Constant.ButtonBlockTopOffSetColorFactor,
                0,
                Constant.ButtonBlockFloorColorFactor,
                Constant.LetterA
        );
        tempt.setDoDrawHeight(false);
        letterOnAStringList.add(tempt);
        drawUtil.addToTopLayer(tempt);


        tempt = new Button(
                9,
                letterOnAStringList.get(letterOnAStringList.size()-1).x+(letterOnAStringWidth+letterOnAStringGapX),letterOnAStringY,
                letterOnAStringWidth,letterOnAStringHeight,
                Constant.C0LOR_SNAKE_WHITE,
                letterDefaultHeight,
                10,
                Constant.ButtonBlockTopOffSetColorFactor,
                0,
                Constant.ButtonBlockFloorColorFactor,
                Constant.LetterS
        );
        tempt.setDoDrawHeight(false);
        letterOnAStringList.add(tempt);
        drawUtil.addToTopLayer(tempt);

        tempt = new Button(
                9,
                letterOnAStringList.get(letterOnAStringList.size()-1).x+(letterOnAStringWidth+letterOnAStringSpanX),letterOnAStringY,
                letterOnAStringWidth,letterOnAStringHeight,
                Constant.C0LOR_SNAKE_WHITE,
                letterDefaultHeight,
                10,
                Constant.ButtonBlockTopOffSetColorFactor,
                0,
                Constant.ButtonBlockFloorColorFactor,
                Constant.LetterT
        );
        tempt.setDoDrawHeight(false);
        letterOnAStringList.add(tempt);
        drawUtil.addToTopLayer(tempt);

        tempt = new Button(
                9,
                letterOnAStringList.get(letterOnAStringList.size()-1).x+(letterOnAStringWidth+letterOnAStringSpanX),letterOnAStringY,
                letterOnAStringWidth,letterOnAStringHeight,
                Constant.C0LOR_SNAKE_WHITE,
                letterDefaultHeight,
                10,
                Constant.ButtonBlockTopOffSetColorFactor,
                0,
                Constant.ButtonBlockFloorColorFactor,
                Constant.LetterR
        );
        tempt.setDoDrawHeight(false);
        letterOnAStringList.add(tempt);
        drawUtil.addToTopLayer(tempt);

        tempt = new Button(
                9,
                letterOnAStringList.get(letterOnAStringList.size()-1).x+(letterOnAStringWidth+letterOnAStringSpanX),letterOnAStringY,
                letterOnAStringWidth,letterOnAStringHeight,
                Constant.C0LOR_SNAKE_WHITE,
                letterDefaultHeight,
                10,
                Constant.ButtonBlockTopOffSetColorFactor,
                0,
                Constant.ButtonBlockFloorColorFactor,
                Constant.LetterI
        );
        tempt.setDoDrawHeight(false);
        letterOnAStringList.add(tempt);
        drawUtil.addToTopLayer(tempt);

        tempt = new Button(
                9,
                letterOnAStringList.get(letterOnAStringList.size()-1).x+(letterOnAStringWidth+letterOnAStringSpanX),letterOnAStringY,
                letterOnAStringWidth,letterOnAStringHeight,
                Constant.C0LOR_SNAKE_WHITE,
                letterDefaultHeight,
                10,
                Constant.ButtonBlockTopOffSetColorFactor,
                0,
                Constant.ButtonBlockFloorColorFactor,
                Constant.LetterN
        );
        tempt.setDoDrawHeight(false);
        letterOnAStringList.add(tempt);
        drawUtil.addToTopLayer(tempt);

        tempt = new Button(
                9,
                letterOnAStringList.get(letterOnAStringList.size()-1).x+(letterOnAStringWidth+letterOnAStringSpanX),letterOnAStringY,
                letterOnAStringWidth,letterOnAStringHeight,
                Constant.C0LOR_SNAKE_WHITE,
                letterDefaultHeight,
                10,
                Constant.ButtonBlockTopOffSetColorFactor,
                0,
                Constant.ButtonBlockFloorColorFactor,
                Constant.LetterG
        );
        tempt.setDoDrawHeight(false);
        letterOnAStringList.add(tempt);
        drawUtil.addToTopLayer(tempt);

        new ListJiggleAnimation(
                letterOnAStringList,
                20,
                1f,
                1500,
                true,
                0.2f,
                false
        ).start();
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
        buttons.add(musicButton);

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
        buttons.add(statisticsButton);

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
        buttons.add(originalPlayButton);
        new Thread(){
            @Override
            public void run(){
                while(true){
                    Thread thread = new JiggleAnimation(
                            originalPlayButton,
                            20,
                            3f,
                            true,
                            0.4f,
                            false
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
        buttons.add(endlessPlayButton);
        new Thread(){
            @Override
            public void run(){
                while(true){
                    Thread thread = new JiggleAnimation(
                            endlessPlayButton,
                            20,
                            3f,
                            true,
                            0.4f,
                            false
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
        buttons.add(favoriteButton);

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
        buttons.add(rateButton);

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
        buttons.add(likeButton);
    }

    @Override
    public void onTouchEvent(MotionEvent event, int x, int y) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                Button tempt = whichButtonTouched(x,y);
                if(tempt!=null)tempt.whenPressed();
                break;
            case MotionEvent.ACTION_UP:
                // if(pauseButton.testTouch(x,y))pauseButton
                whenReleased();
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
