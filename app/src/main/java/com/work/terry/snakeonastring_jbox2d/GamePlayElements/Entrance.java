package com.work.terry.snakeonastring_jbox2d.GamePlayElements;

import com.work.terry.snakeonastring_jbox2d.JBox2DElements.MyBody;
import com.work.terry.snakeonastring_jbox2d.JBox2DElements.RectBody;
import com.work.terry.snakeonastring_jbox2d.SnakeElements.Snake;
import com.work.terry.snakeonastring_jbox2d.SurfaceViewAndActivity.GamePlay;
import com.work.terry.snakeonastring_jbox2d.UI.GameElement;
import com.work.terry.snakeonastring_jbox2d.Util.ColorManager;
import com.work.terry.snakeonastring_jbox2d.Util.Constant;

import org.jbox2d.dynamics.World;

import static com.work.terry.snakeonastring_jbox2d.Util.Constant.ButtonBlockDefaultHeight;
import static com.work.terry.snakeonastring_jbox2d.Util.Constant.ButtonBlockFloorColorFactor;
import static com.work.terry.snakeonastring_jbox2d.Util.Constant.ButtonBlockHeightColorFactor;
import static com.work.terry.snakeonastring_jbox2d.Util.Constant.ButtonBlockTopOffSet;
import static com.work.terry.snakeonastring_jbox2d.Util.Constant.ButtonBlockTopOffSetColorFactor;
import static com.work.terry.snakeonastring_jbox2d.Util.Constant.ButtonImgRect;

/**
 * Created by Terry on 2018/3/3.
 */

public class Entrance extends MyBody {
    public RectBody rectBody1;
    public RectBody rectBody2;
    public boolean Enterred;
    public Snake snake;
    public Entrance(GamePlay gamePlay, float x, float y, float width, float height) {
        super(
                gamePlay,
                "Entrance",
                x, y,
                width, height,
                0,
                0,0,0,0,0,
                0,0,0,0,0,0,true,
                Constant.EntranceImg
        );
        this.setIsPureColor(false);
        this.setDoDrawHeight(false);
        this.setDoDrawFloorShadow(false);
        initRects(gamePlay);
    }
    public void initRects(GamePlay gamePlay){
        rectBody1 = new RectBody(
                gamePlay,
                id+"RectBody 1",
                x-width/2,y,
                0,
                1,
                height/2,

                color,
                ButtonBlockDefaultHeight,
                TopOffset,
                ButtonBlockTopOffSetColorFactor,
                ButtonBlockHeightColorFactor,
                ButtonBlockFloorColorFactor,

                1.0f,
                0.01f,
                0.1f,
                ButtonImgRect,
                true
        );
        rectBody2 = new RectBody(
                gamePlay,
                id+"RectBody 1",
                x+width/2,y,
                0,
                1,
                height/2,

                color,
                ButtonBlockDefaultHeight,
                TopOffset,
                ButtonBlockTopOffSetColorFactor,
                ButtonBlockHeightColorFactor,
                ButtonBlockFloorColorFactor,

                1.0f,
                0.01f,
                0.1f,
                ButtonImgRect,
                true
        );
    }

    @Override
    public void createBody() {
        rectBody1.createBody();
        rectBody2.createBody();
    }
    @Override
    public void deleteBody(){
        rectBody1.deleteBody();
        rectBody2.deleteBody();
    }
}
