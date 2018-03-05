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
    public static final int START_VIEW = 0;
    public static final int GAMEPLAY_VIEW_ORIGINAL = 10;
    public static final int GAMEPLAY_VIEW_ENDLESS = 20;
    public static final int SKIN_CHANGING_VIEW = 30;

    //文件前缀
    public static String PicDirectoryPrefix = "pic/";
    public static String NumbersPicDirectoryPrefix = "numbers/";
    public static String LettersPicDirectoryPrefix = "letters/";
    public static String SnakeSkinDirectoryPrefix = "snake_skin/";
    public static String SnakeSkinPicDirectoryPrefix = SnakeSkinDirectoryPrefix+"skin_img/";
    public static final String OriginalPlayDirectoryPrefix = "game_level/original_play/";
    public static final String EndlessPlayDirectoryPrefix = "game_level/endless_play/";

    public static String Number0Img = NumbersPicDirectoryPrefix+"number_zero.png";
    public static String Number1Img = NumbersPicDirectoryPrefix+"number_one.png";
    public static String Number2Img = NumbersPicDirectoryPrefix+"number_two.png";
    public static String Number3Img = NumbersPicDirectoryPrefix+"number_three.png";
    public static String Number4Img = NumbersPicDirectoryPrefix+"number_four.png";
    public static String Number5Img = NumbersPicDirectoryPrefix+"number_five.png";
    public static String Number6Img = NumbersPicDirectoryPrefix+"number_six.png";
    public static String Number7Img = NumbersPicDirectoryPrefix+"number_seven.png";
    public static String Number8Img = NumbersPicDirectoryPrefix+"number_eight.png";
    public static String Number9Img = NumbersPicDirectoryPrefix+"number_nine.png";

    public static String LetterOriginal = LettersPicDirectoryPrefix+"original.png";
    public static String LetterEndless = LettersPicDirectoryPrefix+"endless.png";
    public static String LetterS = LettersPicDirectoryPrefix+"letter_s.png";
    public static String LetterN = LettersPicDirectoryPrefix+"letter_n.png";
    public static String LetterA = LettersPicDirectoryPrefix+"letter_a.png";
    public static String LetterK = LettersPicDirectoryPrefix+"letter_k.png";
    public static String LetterE = LettersPicDirectoryPrefix+"letter_e.png";
    public static String LetterO = LettersPicDirectoryPrefix+"letter_o.png";
    public static String LetterT = LettersPicDirectoryPrefix+"letter_t.png";
    public static String LetterR = LettersPicDirectoryPrefix+"letter_r.png";
    public static String LetterI = LettersPicDirectoryPrefix+"letter_i.png";
    public static String LetterG = LettersPicDirectoryPrefix+"letter_g.png";


    public static String RoundEdgeCube = "round_edge_cube.png";
    public static String ArrowsSwitchImg = "arrows_switch_horizontal.png";
    public static String SoundAltImg = "sound_alt.png";
    public static String SoundOffImg = "sound_off.png";
    public static String SoundOutloud = "sound_outloud.png";

    public static String StatisticsBars = "bars.png";
    public static String StartImg = "start.png";
    public static String InfiniteImg = "infinite.png";

    public static String StarFavoriteImg = "star_favorite.png";
    public static String RateImg = "thumbs_up.png";
    public static String LikeImg = "love.png";

    public static String LuckImg = "luck.png";
    public static String ShapeImg = "shape.png";
    public static String ArrowBackImg = "arrow_back.png";


    //JBOX2D相关1
    public static final float RATE = 10;//屏幕到现实世界的比例 10px：1m;
    public static final float JBOX2D_TIME_STEP = 2.0f/60.0f;//模拟的的频率
    public static final int JBOX2D_ITERA = 10;//迭代越大，模拟约精确，但性能越低

        //蛇的刚体参数
        public static final float SnakeHeadAngularDampingRate = 0.3f;
        public static final float SnakeHeadLinearDampingRate = 0.5f;
        public static final float SnakeHeadDensity = 1.0f;
        public static final float SnakeHeadFriction = 0.04f;
        public static final float SnakeHeadRestitution = 0.6f;

        public static final float SnakeHeadSpeed = 0.2f;
        public static final int SnakeHeadSpeedFactor = 10;

        public static final float SnakeHeadSpeedUponDead = 0.2f;
        public static final float SnakeBodySpeedUponDead = 0.2f;

        public static final float SnakeBodyLinearDampingRate = 0.50f;
        public static final float SnakeBodyLinearDampingRateFactorInter = 0.02f;
        public static final float SnakeBodyDensity = 1.0f;
        public static final float SnakeBodyFriction = 0.4f;
        public static final float SnakeBodyRestitution = 0.6f;
    //public static final float snakeBodyAngularDampingRate = 0f;

    //蛇的食物
    public static final String SnakeFoodImg = "snake_food.png";
    //炸弹
    public static final String BombImg = "my_bomb.png";
    //Speed
    public static final String SpeedImg = "speed.png";
    //钉子
    public static final String NailVerticalImg = "nail_vertical.png";
    public static final String NailShadowImg = "nail_shadow.png";
    //锁定
    public static final String LockDownImg = "circle_drashed.png";

    //按钮
    public static final String PauseButtonImg = "pause.png";
    public static final String DeleteImg = "delete.png";

    //磁铁
    public static final String FoodMagnetImg = "magnet.png";
    //蛇整体
    public static final int SnakeBodyDefaultLength = 4;

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
    public static final int SnakeHeadImgCode = 0;
    public static final int SnakeBodyDeafaltImgCode = -1;
    public static final int SnakeHeadDeadImgCode = -2;
    public static final int SnakeHeadDizzyImgCode = -3;
    public static final int SnakeHeadEatingOpenImgCode = -4;
    public static final int SnakeHeadEatingCloseImgCode = -5;

    public static String SnakeHeadID = "snakeHead";
    public static String SnakeHeadEyesImg = "snake_head_eyes_version_2.png";
    public static String SnakeHeadDeadEyesImg = "snake_head_eyes_dead_version_2.png";
    public static String SnakeHeadDizzyEyesImg = "snake_head_eyes_dizzy_version_2.png";
    public static String SnakeHeadEyesBallImg = "snake_head_eyesBalls_version_2.png";
    public static final float SnakeHeadRatio = (float) (800*1.0/1024);
    public static final int SnakeHeadRadius = 38;

    public static float SnakeHeadRotateStepAngle =3.0f/5f;//Degree
    //蛇身体
    public static String SnakeBodyID = "snakeBody";
    public static String SnakeBodyImg = "snake_body.png";
    public static String SnakeBodyHeightImg ="snake_body_height.png";
    public static final int SnakeBodyTopRadius = 33;
    public static final int SnakeBodyRadius = 35;

    //碰撞块
    public static float ButtonBlockTopRatio = 0.95f;
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

    public static String EntranceImg = "entrance.png";

    public static String AxisImg = "axis_with_direction.png";
    public static int AxisWidth = SnakeHeadRadius;

    public static final int COLOR_WHITE= 0;
    public static final int COLOR_BLACK=1;
    public static final int COLOR_GREY = 2;
    public static final int C0LOR_SNAKE_WHITE = 3;
    public static final int C0LOR_CYAN = 4;
    public static final int COLOR_ORANGE = 5;
    public static final int COLOR_SKYBLUE = 6;
    public static final int COLOR_GOLDEN = 7;
    public static final int COLOR_RED = 8;
    public static final int COLOR_BLACKBLUE = 9;
    public static final int COLOR_GREEN = 10;

    public static final int LAYER_TOP = 0;
    public static final int LAYER_CENTER = 1;
    public static final int LAYER_FLOOR= 2;

    public static final int GAME_MODE_ORIGINAL = 0;
    public static final int GAME_MODE_ENDLESS = 1;
}
