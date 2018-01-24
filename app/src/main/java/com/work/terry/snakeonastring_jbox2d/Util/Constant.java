package com.work.terry.snakeonastring_jbox2d.Util;

/**
 * Created by Terry on 2017/12/23.
 */
import com.work.terry.snakeonastring_jbox2d.SnakeElements.Snake;
import com.work.terry.snakeonastring_jbox2d.auto.*;

public class Constant {
    //屏幕自适应相关
    public static int SCREEN_WIDTH ;
    public static int SCREEN_HEIGHT;
    public static ScreenScaleResult ssr;

    //文件前缀
    public static String PicDirectoryPrefix = "pic/";

    //JBOX2D相关
    public static final float RATE = 10;//屏幕到现实世界的比例 10px：1m;
    public static final float JBOX2D_TIME_STEP = 1.0f/60.0f;//模拟的的频率
    public static final int JBOX2D_ITERA = 10;//迭代越大，模拟约精确，但性能越低

        //蛇的刚体参数
        public static final float SnakeHeadAngularDampingRate = 0.3f;
        public static final float SnakeHeadLinearDampingRate = 0.07f;
        public static final float SnakeHeadDensity = 1.0f;
        public static final float SnakeHeadFriction = 0.04f;
        public static final float SnakeHeadRestitution = 0.6f;

        public static final float SnakeHeadSpeed = 10f;
        public static final int SnakeHeadSpeedFactor = 20;

        public static final float SnakeHeadSpeedUponDead = 0.4f;
        public static final float SnakeBodySpeedUponDead = 0.4f;

        public static final float SnakeBodyLinearDampingRate = 0.070f;
        public static final float SnakeBodyLinearDampingRateFactorInter = 0.02f;
        public static final float SnakeBodyDensity = 0.10f;
        public static final float SnakeBodyFriction = 0.04f;
        public static final float SnakeBodyRestitution = 0.6f;
    //public static final float snakeBodyAngularDampingRate = 0f;

    //蛇整体
    public static final int SnakeBodyDefaultLength = 6;

    public static final int SnakeDefaultHeight = 24;


    public static final int SnakeEyesDownLittleHeight = 3;
    public static final float SnakeEyesDownLittleColorFactor = 0.85f;

    public static final int SnakeDownLittleHeight = 7;
    public static final float SnakeDownLittleColorFactor = 0.85f;
    public static final float SnakeHeightColorFactor = 0.70f;
    public static final float SnakeFloorColorFactor = 1f;

    public static final int DistanceOffset = 30;
    public static final float JumpMathFactor = 4;

    //蛇头
    public static String SnakeHeadID = "snakeHead";
    public static String SnakeHeadHeadImg = "snake_head_head.png";
    public static String SnakeHeadEyesImg = "snake_head_eyes_version_2.png";
    public static String SnakeHeadDeadEyesImg = "snake_head_eyes_dead_version_2.png";
    public static String SnakeHeadDizzyEyesImg = "snake_head_eyes_dizzy_version_2.png";
    public static String SnakeHeadEyesBallImg = "snake_head_eyesBalls_version_2.png";
    public static final float SnakeHeadRatio = (float) (800*1.0/1024);
    public static final int SnakeHeadEyesDiameter = 110;
    public static final int SnakeHeadTopRadius = 46;
    public static final int SnakeHeadRadius = 50;


    public static float SnakeHeadRotateStepAngle =3.0f/5f;//Degree
    //蛇身体
    public static String SnakeBodyID = "snakeBody";
    public static String SnakeBodyImg = "snake_body.png";
    public static String SnakeBodyHeightImg ="snake_body_height.png";
    public static final int SnakeBodyTopRadius = 32;
    public static final int SnakeBodyRadius = 35;

    //碰撞块
    public static float ButtonBlockDefaultHeight = 40;
    public static float ButtonBlockTopOffSet = 10;
    public static float ButtonBlockTopOffSetColorFactor = SnakeDownLittleColorFactor;
    public static float ButtonBlockHeightColorFactor = SnakeHeightColorFactor;
    public static float ButtonBlockFloorColorFactor = SnakeFloorColorFactor;

    //绘制相关
    public static final float FloorShadowFactorX = 0.3f;
    public static final float FloorShadowFactorY = 0.5f;

    public static String BackgroundImg = "Strips_background.png";

    public static String ButtonImgCircleUp = "button_circle_up.png";
    public static String ButtonImgCircleDown = "button_circle_down.png";
    public static String ButtonImgRect = SnakeBodyHeightImg;

    public static String AxisImg = "axis_with_direction.png";
    public static int AxisWidth = SnakeHeadRadius;

    public static final int COLOR_DEFAULT = 0;
    public static final int COLOR_GREAY = 1;
    public static final int C0LOR_WHITE = 2;
    public static final int COLOR_ORANGE = 3;
    public static final int C0LOR_CYAN = 4;
}
