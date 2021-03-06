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
    public static String PicDirectoryPrefix = "compressed_imgs/";
    public static String NumbersPicDirectoryPrefix = "numbers/";
    public static String LettersPicDirectoryPrefix = "letters/";
    public static String SnakeSkinDirectoryPrefix = "snake_skin/";
    public static String SnakeSkinPicDirectoryPrefix = SnakeSkinDirectoryPrefix+"compressed_skin_imgs/";
    public static final String OriginalPlayDirectoryPrefix = "game_level/original_play/";
    public static final String EndlessPlayDirectoryPrefix = "game_level/endless_play/";

    public static String Number0Img = NumbersPicDirectoryPrefix+"number_zero.pkm";
    public static String Number1Img = NumbersPicDirectoryPrefix+"number_one.pkm";
    public static String Number2Img = NumbersPicDirectoryPrefix+"number_two.pkm";
    public static String Number3Img = NumbersPicDirectoryPrefix+"number_three.pkm";
    public static String Number4Img = NumbersPicDirectoryPrefix+"number_four.pkm";
    public static String Number5Img = NumbersPicDirectoryPrefix+"number_five.pkm";
    public static String Number6Img = NumbersPicDirectoryPrefix+"number_six.pkm";
    public static String Number7Img = NumbersPicDirectoryPrefix+"number_seven.pkm";
    public static String Number8Img = NumbersPicDirectoryPrefix+"number_eight.pkm";
    public static String Number9Img = NumbersPicDirectoryPrefix+"number_nine.pkm";

    public static String LetterOriginal = LettersPicDirectoryPrefix+"original.pkm";
    public static String LetterEndless = LettersPicDirectoryPrefix+"endless.pkm";
    public static String LetterS = LettersPicDirectoryPrefix+"letter_s.pkm";
    public static String LetterN = LettersPicDirectoryPrefix+"letter_n.pkm";
    public static String LetterA = LettersPicDirectoryPrefix+"letter_a.pkm";
    public static String LetterK = LettersPicDirectoryPrefix+"letter_k.pkm";
    public static String LetterE = LettersPicDirectoryPrefix+"letter_e.pkm";
    public static String LetterO = LettersPicDirectoryPrefix+"letter_o.pkm";
    public static String LetterT = LettersPicDirectoryPrefix+"letter_t.pkm";
    public static String LetterR = LettersPicDirectoryPrefix+"letter_r.pkm";
    public static String LetterI = LettersPicDirectoryPrefix+"letter_i.pkm";
    public static String LetterG = LettersPicDirectoryPrefix+"letter_g.pkm";


    public static String RoundEdgeCube = "round_edge_cube.pkm";
    public static String ArrowsSwitchImg = "arrows_switch_horizontal.pkm";
    public static String SoundAltImg = "sound_alt.pkm";
    public static String SoundOffImg = "sound_off.pkm";
    public static String SoundOutloud = "sound_outloud.pkm";

    public static String StatisticsBars = "bars.pkm";
    public static String StartImg = "start.pkm";
    public static String InfiniteImg = "infinite.pkm";

    public static String StarFavoriteImg = "star_favorite.pkm";
    public static String RateImg = "thumbs_up.pkm";
    public static String LikeImg = "love.pkm";

    public static String LuckImg = "luck.pkm";
    public static String ShapeImg = "shape.pkm";
    public static String ArrowBackImg = "arrow_back.pkm";
    //中文
    public static final String SelectChineseImg = "select_chinese.pkm";
    public static final String UnlockChineseImg = "unlock_chinese.pkm";
    public static final String ChangeSkinChineseImg = "change_skin_chinese.pkm";
    public static final String ResumeImg = "resume_chinese.pkm";
    public static final String RestartChineseImg = "restart_chinese.pkm";
    public static final String BestChineseImg = "best_chinese.pkm";
    public static final String EndlessChineseImg = "endless_chinese.pkm";

    public static String RoundRectSector = "round_rect_sector1.pkm";


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

        public static final int SnakeHeadSpeedFactor = 10;

        public static final float SnakeHeadSpeedUponDead = 0.2f;
        public static final float SnakeBodySpeedUponDead = 0.2f;

        public static final float SnakeBodyAngularDampingRate = SnakeHeadAngularDampingRate;
        public static final float SnakeBodyLinearDampingRate = 0.50f;
        public static final float SnakeBodyLinearDampingRateFactorInter = 0.02f;
        public static final float SnakeBodyDensity = 1.0f;
        public static final float SnakeBodyFriction = 0.4f;
        public static final float SnakeBodyRestitution = 0.6f;
    //public static final float snakeBodyAngularDampingRate = 0f;

    //蛇的食物
    public static final String SnakeFoodImg = "snake_food.pkm";
    //炸弹
    public static final String BombImg = "my_bomb.pkm";
    //Speed
    public static final String SpeedImg = "speed.pkm";
    //menu
    public static final String MenuImg = "menu.pkm";
    //钉子
    public static final String NailVerticalImg = "nail_vertical.pkm";
    public static final String NailShadowImg = "nail_shadow.pkm";
    //锁定
    public static final String LockDownImg = "circle_drashed.pkm";

    //按钮
    public static final String PauseButtonImg = "pause.pkm";
    public static final String DeleteImg = "delete.pkm";
    public static final String RestartImg = "restart.pkm";


    //磁铁
    public static final String FoodMagnetImg = "magnet.pkm";
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
    public static final int SnakeBodyDefaultImgCode = -1;
    public static final int SnakeHeadDeadImgCode = -2;
    public static final int SnakeHeadDizzyImgCode = -3;
    public static final int SnakeHeadEatingOpenImgCode = -4;
    public static final int SnakeHeadEatingCloseImgCode = -5;

    public static String SnakeHeadID = "snakeHead";
    public static String SnakeHeadEyesImg = "snake_head_eyes_version_2.pkm";
    public static String SnakeHeadDeadEyesImg = "snake_head_eyes_dead_version_2.pkm";
    public static String SnakeHeadDizzyEyesImg = "snake_head_eyes_dizzy_version_2.pkm";
    public static String SnakeHeadEyesBallImg = "snake_head_eyesBalls_version_2.pkm";
    public static final float SnakeHeadRatio = (float) (800*1.0/1024);
    public static final int SnakeHeadRadius = 38;

    public static float SnakeHeadRotateStepAngle =3.0f/5f;//Degree
    //蛇身体
    public static String SnakeBodyID = "snakeBody";
    public static String SnakeBodyImg = "snake_body.pkm";
    public static String SnakeBodyHeightImg ="snake_body_height.pkm";
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
    public static final float FloorShadowFactorX = 0.6f;
    public static final float FloorShadowFactorY = 0.8f;

    public static String BackgroundImg = "Strips_background.pkm";
    public static String LockImg = "lock.pkm";

    public static String ButtonImgCircleUp = "button_circle_up.pkm";
    public static String ButtonImgCircleDown = "button_circle_down.pkm";
    public static String ButtonImgRect = SnakeBodyHeightImg;

    public static String EntranceImg = "entrance.pkm";

    public static String AxisImg = "axis_with_direction.pkm";
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
    public static final int COLOR_DEEP_PINK = 11;
    public static final int COLOR_SKINISH = 12;
    public static final int COLOR_WINE_RED = 13;

    public static final int LAYER_TOP = 0;
    public static final int LAYER_CENTER = 1;
    public static final int LAYER_FLOOR= 2;

    public static final int GAME_MODE_ORIGINAL = 0;
    public static final int GAME_MODE_ENDLESS = 1;

    public static final int MUSIC_MODE_ALL_ON = 0;
    public static final int MUSIC_MODE_BG_OFF = 1;
    public static final int MUSIC_MODE_ALL_OFF = 2;
}
