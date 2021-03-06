package com.work.terry.snakeonastring_jbox2d.GamePlayElements;

import com.work.terry.snakeonastring_jbox2d.JBox2DElements.CircleBody;
import com.work.terry.snakeonastring_jbox2d.JBox2DElements.MyBody;
import com.work.terry.snakeonastring_jbox2d.JBox2DElements.MyWeldJoint;
import com.work.terry.snakeonastring_jbox2d.JBox2DElements.RectBody;
import com.work.terry.snakeonastring_jbox2d.SnakeElements.Snake;
import com.work.terry.snakeonastring_jbox2d.SurfaceViewAndActivity.GamePlay;
import com.work.terry.snakeonastring_jbox2d.Util.Constant;
import com.work.terry.snakeonastring_jbox2d.Util.DrawUtil;
import com.work.terry.snakeonastring_jbox2d.Util.TexDrawer;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.work.terry.snakeonastring_jbox2d.Util.Constant.ButtonBlockDefaultHeight;
import static com.work.terry.snakeonastring_jbox2d.Util.Constant.ButtonBlockFloorColorFactor;
import static com.work.terry.snakeonastring_jbox2d.Util.Constant.ButtonBlockHeightColorFactor;
import static com.work.terry.snakeonastring_jbox2d.Util.Constant.ButtonBlockTopOffSet;
import static com.work.terry.snakeonastring_jbox2d.Util.Constant.ButtonBlockTopOffSetColorFactor;
import static com.work.terry.snakeonastring_jbox2d.Util.Constant.ButtonImgRect;
import static com.work.terry.snakeonastring_jbox2d.Util.Constant.SnakeBodyDensity;
import static com.work.terry.snakeonastring_jbox2d.Util.Constant.SnakeBodyFriction;
import static com.work.terry.snakeonastring_jbox2d.Util.Constant.SnakeBodyLinearDampingRate;
import static com.work.terry.snakeonastring_jbox2d.Util.Constant.SnakeBodyLinearDampingRateFactorInter;
import static com.work.terry.snakeonastring_jbox2d.Util.Constant.SnakeBodyRestitution;
import static com.work.terry.snakeonastring_jbox2d.Util.VectorUtil.Mul2D;
import static com.work.terry.snakeonastring_jbox2d.Util.VectorUtil.calRotateAngleDegrees;
import static com.work.terry.snakeonastring_jbox2d.Util.VectorUtil.minusV2D;
import static com.work.terry.snakeonastring_jbox2d.Util.VectorUtil.plusV2D;

/**
 * Created by Terry on 2018/1/25.
 */

public class ButtonBlockCircle extends CircleBody{
    public ButtonBlockCircle(
            GamePlay gamePlay,
            float x, float y,
            float radius,
            float[] colorFloats,
            boolean isStatic,
            float defaultHeight,
            int id
    ){
        super(
                gamePlay,
                "ButtonBlockCircle "+id,
                x,y,
                0,
                radius,

                0,
                defaultHeight,

                Constant.SnakeDownLittleHeight,
                Constant.SnakeDownLittleColorFactor,
                Constant.SnakeHeightColorFactor,
                Constant.SnakeFloorColorFactor,

                0,
                0,
                SnakeBodyDensity,
                SnakeBodyFriction,
                SnakeBodyRestitution,
                isStatic,

                Constant.SnakeBodyImg
        );
        this.setColorFloats255(colorFloats);
        this.setIsPureColor(true);
        setTopRatio(Constant.ButtonBlockTopRatio);
    }
}
