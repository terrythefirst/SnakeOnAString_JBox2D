package com.work.terry.snakeonastring_jbox2d.Util;

/**
 * Created by Terry on 2017/12/23.
 */
import com.work.terry.snakeonastring_jbox2d.SnakeElements.Snake;
import com.work.terry.snakeonastring_jbox2d.auto.*;

public class Constant {
    public final static String SharedPreferencesName = "com.work.terry.snakeonastring_jbox2d";
    //屏幕自适应相关
    public static int SCREEN_WIDTH ;
    public static int SCREEN_HEIGHT;
    public static ScreenScaleResult ssr;

    //界面标志
    public static final int CHANGE_BY_DEFAULT_VIEW = -1;
    public static final int MAIN_VIEW = 0;
    public static final int MENU_VIEW = 1;
    public static final int GAMEPLAY_VIEW = 2;

    //文件前缀
    public static String PicDirectoryPrefix = "pic/";

    public static String Number0Img = "number_zero.png";
    public static String Number1Img = "number_one.png";
    public static String Number2Img = "number_two.png";
    public static String Number3Img = "number_three.png";
    public static String Number4Img = "number_four.png";
    public static String Number5Img = "number_five.png";
    public static String Number6Img = "number_six.png";
    public static String Number7Img = "number_seven.png";
    public static String Number8Img = "number_eight.png";
    public static String Number9Img = "number_nine.png";

    public static String ArrowsSwitchImg = "arrows_switch_horizontal.png";

    //JBOX2D相关1
    public static final float RATE = 10;//屏幕到现实世界的比例 10px：1m;
    public static final float JBOX2D_TIME_STEP = 2.0f/60.0f;//模拟的的频率
    public static final int JBOX2D_ITERA = 10;//迭代越大，模拟约精确，但性能越低

        //蛇的刚体参数
        public static final float SnakeHeadAngularDampingRate = 0.3f;
        public static final float SnakeHeadLinearDampingRate = 0.2f;
        public static final float SnakeHeadDensity = 10.0f;
        public static final float SnakeHeadFriction = 0.04f;
        public static final float SnakeHeadRestitution = 0.6f;

        public static final float SnakeHeadSpeed = 0.2f;
        public static final int SnakeHeadSpeedFactor = 10;

        public static final float SnakeHeadSpeedUponDead = 0.2f;
        public static final float SnakeBodySpeedUponDead = 0.2f;

        public static final float SnakeBodyLinearDampingRate = 0.10f;
        public static final float SnakeBodyLinearDampingRateFactorInter = 0.02f;
        public static final float SnakeBodyDensity = 0.10f;
        public static final float SnakeBodyFriction = 0.04f;
        public static final float SnakeBodyRestitution = 0.6f;
    //public static final float snakeBodyAngularDampingRate = 0f;

    //蛇的食物
    public static final String SnakeFoodImg = "snake_food.png";
    //炸弹
    public static final String BombImg = "my_bomb.png";
    //钉子
    public static final String NailVerticalImg = "nail_vertical.png";
    public static final String NailShadowImg = "nail_shadow.png";

    //按钮
    public static final String PauseButtonImg = "pause.png";

    //磁铁
    public static final String FoodMagnetImg = "magnet.png";
    //蛇整体
    public static final int SnakeBodyDefaultLength = 3;

    public static final int SnakeDefaultHeight = 24;

    public static final int SNAKE_ANIMATION_APPEND = 0;
    public static final int SNAKE_ANIMATION_REMOVE = 1;


    public static final int SnakeEyesDownLittleHeight = 3;
    public static final float SnakeEyesDownLittleColorFactor = 0.85f;

    public static final int SnakeDownLittleHeight = 7;
    public static final float SnakeDownLittleColorFactor = 0.85f;
    public static final float SnakeHeightColorFactor = 0.75f;
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

    public static String ButtonImgCircleUp = SnakeBodyImg;//"button_circle_up.png";
    public static String ButtonImgCircleDown = SnakeBodyImg;//"button_circle_down.png";
    public static String ButtonImgRect = SnakeBodyHeightImg;

    public static String AxisImg = "axis_with_direction.png";
    public static int AxisWidth = SnakeHeadRadius;

    public static final int COLOR_WHITE= 0;
    public static final int COLOR_BLACK=1;
    public static final int COLOR_GREY = 2;
    public static final int C0LOR_SNAKE_WHITE = 3;
    public static final int COLOR_ORANGE = 4;
    public static final int C0LOR_CYAN = 5;
}
