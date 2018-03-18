package com.work.terry.snakeonastring_jbox2d.Util;

import static com.work.terry.snakeonastring_jbox2d.Util.Constant.*;

/**
 * Created by Terry on 2017/12/23.
 */

public class ImgManager {
    public static String[] picName = {
            SnakeBodyImg,
            BackgroundImg,
            EntranceImg,
            AxisImg,
            SnakeBodyHeightImg,
            SnakeHeadEyesImg,
            SnakeHeadEyesBallImg,
            SnakeHeadDeadEyesImg,
            SnakeHeadDizzyEyesImg,
            ButtonImgCircleUp,
            ButtonImgCircleDown,
            ButtonImgRect,
            SnakeFoodImg,
            BombImg,

            Number0Img,
            Number1Img,
            Number2Img,
            Number3Img,
            Number4Img,
            Number5Img,
            Number6Img,
            Number7Img,
            Number8Img,
            Number9Img,

            LockDownImg,

            LetterOriginal,
            LetterEndless,
            LetterS,
            LetterN,
            LetterA,
            LetterK,
            LetterE,
            LetterO,
            LetterT,
            LetterR,
            LetterI,
            LetterG,

            NailVerticalImg,
            NailShadowImg,

            PauseButtonImg,
            DeleteImg,

            StarFavoriteImg,
            LikeImg,
            RateImg,

            RestartImg,


            LuckImg,
            ShapeImg,
            ArrowBackImg,

            SpeedImg,
            MenuImg,

            SelectChineseImg,
            UnlockChineseImg,
            ChangeSkinChineseImg,
            RestartChineseImg,
            BestChineseImg,
            EndlessChineseImg,


            LockImg,
            RoundRectSector,

            RoundEdgeCube,
            ArrowsSwitchImg,
            SoundAltImg,
            SoundOffImg,
            SoundOutloud,

            StatisticsBars,
            StartImg,
            InfiniteImg,

            FoodMagnetImg,
    };

    public static String getNumberImgName(int x){
        String res;
        switch (x){
            case 0:res = Number0Img;break;
            case 1:res = Number1Img;break;
            case 2:res = Number2Img;break;
            case 3:res = Number3Img;break;
            case 4:res = Number4Img;break;
            case 5:res = Number5Img;break;
            case 6:res = Number6Img;break;
            case 7:res = Number7Img;break;
            case 8:res = Number8Img;break;
            case 9:res = Number9Img;break;
            default:res = null;
        }
        return res;
    }
}
