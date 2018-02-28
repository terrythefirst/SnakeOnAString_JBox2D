package com.work.terry.snakeonastring_jbox2d.UI;

/**
 * Created by Terry on 2018/2/28.
 */

public class Score {
    int score = 0;
    private byte[] scoreLock = new byte[0];

    public Score(int x){
        score = x;
    }
    public void plusScore(int x){
        synchronized (scoreLock){
            score+=x;
        }
    }
    public int getScore(){
        return score;
    }
}
