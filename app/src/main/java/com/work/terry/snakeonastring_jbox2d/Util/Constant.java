package com.work.terry.snakeonastring_jbox2d.Util;

/**
 * Created by Terry on 2017/12/23.
 */
import com.work.terry.snakeonastring_jbox2d.auto.*;

public class Constant {
    //屏幕自适应相关
    public static int SCREEN_WIDTH ;
    public static int SCREEN_HEIGHT;
    public static ScreenScaleResult ssr;

    //文件前缀
    public static String picDirectoryPrefix = "pic/";

    //JBOX2D相关
    public static final float RATE = 10;//屏幕到现实世界的比例 10px：1m;
    public static final float JBOX2D_TIME_STEP = 1.0f/60.0f;//模拟的的频率
    public static final int JBOX2D_ITERA = 10;//迭代越大，模拟约精确，但性能越低

        //蛇的刚体参数
        public static final float snakeHeadAngularDampingRate = 0.3f;
        public static final float snakeHeadLinearDampingRate = 0.07f;
        public static final float snakeHeadDensity = 1.0f;
        public static final float snakeHeadFriction = 0.04f;
        public static final float snakeHeadRestitution = 0.6f;

        public static final float snakeHeadSpeed = 10f;
        public static final int snakeHeadSpeedFactor = 20;

        public static final float snakeHeadSpeedUponDead = 0.4f;
        public static final float snakeBodySpeedUponDead = 0.4f;

        public static final float snakeBodyLinearDampingRate = 0.070f;
        public static final float snakeBodyLinearDampingRateFactorInter = 0.02f;
        public static final float snakeBodyDensity = 0.10f;
        public static final float snakeBodyFriction = 0.04f;
        public static final float snakeBodyRestitution = 0.6f;
    //public static final float snakeBodyAngularDampingRate = 0f;

    //蛇整体
    public static final int SnakeBodyDefaultLength = 6;
    public static final int SnakeDefaultHeight = 24;
    public static final int SnakeDownLittleHeight = 7;
    public static final float SnakeDownLittleColorFactor = 0.87f;

    public static final int DistanceOffset = 30;
    public static final float JumpMathFactor = 4;

    //蛇头
    public static String snakeHeadID = "snakeHead";
    public static String snakeHeadHeadImg = "snake_head_head.png";
    public static String snakeHeadEyesImg = "snake_head_eyes_version_2.png";
    public static String snakeHeadDeadEyesImg = "snake_head_eyes_dead_version_2.png";
    public static String snakeHeadDizzyEyesImg = "snake_head_eyes_dizzy_version_2.png";
    public static String snakeHeadEyesBallImg = "snake_head_eyesBalls_version_2.png";
    public static final float SnakeHeadRatio = (float) (800*1.0/1024);
    public static final int headEyesDiameter = 110;
    public static final int headTopRadius = 48;
    public static final int headRadius = 50;

    public static final int SnakeEyesDownLittleHeight = 5;

    public static final float SnakeEyesDownLittleColorFactor = 0.85f;
    public static final float SnakeHeightColorFactor = 0.75f;
    public static final float SnakeFloorColorFactor = 0.50f;

    public static float snakeHeadRotateStepAngle =3.0f/5f;//Degree
    //蛇身体
    public static String snakeBodyID = "snakeBody";
    public static String snakeBodyImg = "snake_body.png";
    public static String snakeBodyHeightImg ="snake_body_height.png";
    public static final int bodyTopRadius = 33;
    public static final int bodyRadius = 35;

    //碰撞块
    public static float buttonBlockDownLittleHeight = 5;
    public static float buttonBlockDefaultHeight = 24;

    //绘制相关
    public static final float FloorShadowFactorX = 0.3f;
    public static final float FloorShadowFactorY = 0.5f;

    public static String backgroundImg = "Strips_background.png";
    public static String buttonImgCircle = snakeBodyImg;
    public static String buttonImgRect = snakeBodyHeightImg;

    public static String axisImg = "axis_with_direction.png";
    public static int axisWidth = headRadius;

    public static final int COLOR_DEFAULT = 0;
    public static final int C0LOR_WHITE = 1;
    public static final int COLOR_ORANGE = 2;
    public static final int C0LOR_CYAN = 3;
}
