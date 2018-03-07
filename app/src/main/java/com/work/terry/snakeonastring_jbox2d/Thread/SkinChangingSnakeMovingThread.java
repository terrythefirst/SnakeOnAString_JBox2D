package com.work.terry.snakeonastring_jbox2d.Thread;

import android.util.Log;

import com.work.terry.snakeonastring_jbox2d.UI.GameElement;

import java.util.List;

/**
 * Created by Terry on 2018/3/7.
 */

public class SkinChangingSnakeMovingThread extends Thread {
    int nowSelect;
    public SkinChangingSnakeMovingThread(int skinNumber){
        nowSelect = skinNumber;
    }

}
