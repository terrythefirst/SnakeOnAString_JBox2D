package com.work.terry.snakeonastring_jbox2d.SurfaceViewAndActivity;

/**
 * Created by Terry on 2018/3/22.
 */

public class OnPauseSave {
    public static GamePlay gamePlay;
    public static void saveGame(GamePlay x){
        gamePlay = x;
    }
    public static GamePlay getGamePlay(){
        return gamePlay;
    }
}
