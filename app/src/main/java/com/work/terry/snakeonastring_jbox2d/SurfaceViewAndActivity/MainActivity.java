package com.work.terry.snakeonastring_jbox2d.SurfaceViewAndActivity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.work.terry.snakeonastring_jbox2d.Util.Constant;
import static com.work.terry.snakeonastring_jbox2d.Util.Constant.*;

import com.work.terry.snakeonastring_jbox2d.Util.ShaderUtil;
import com.work.terry.snakeonastring_jbox2d.Util.SoundPoolManager;
import com.work.terry.snakeonastring_jbox2d.auto.ScreenScaleUtil;

import java.util.Date;

import work.terry.com.snakeonastring_jbox2d.R;

public class MainActivity extends Activity {
    private GamePlayView gamePlayView;
    private MediaPlayer mp;
    private int currentPlayMusicRawInt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        WindowManager manager = this.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        Constant.SCREEN_WIDTH = outMetrics.widthPixels;
        Constant.SCREEN_HEIGHT = outMetrics.heightPixels;

        Constant.ssr = ScreenScaleUtil.calScale(Constant.SCREEN_WIDTH,Constant.SCREEN_HEIGHT);

        Log.d("ssr",Constant.ssr.toString());

        SoundPoolManager.initSoundPool(this);

        gamePlayView = new GamePlayView(this);
        setContentView(gamePlayView);
        gamePlayView.requestFocus();
        gamePlayView.setFocusableInTouchMode(true);
    }
    public void setBackgroundMusicVolume(int x){
        mp.setVolume(x,x);
    }
    public void setBackgroundMusic(int raw){
        if(raw==currentPlayMusicRawInt){
            if(!mp.isPlaying())mp.start();
            return;
        }
        if(mp!=null&&mp.isPlaying()){
            mp.pause();
        }
        mp = MediaPlayer.create(this,raw);
        currentPlayMusicRawInt = raw;
        try {
            mp.setLooping(true);
            //mp.prepare();
            mp.start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    protected void onResume(){
        super.onResume();
        gamePlayView.onResume();
        if(mp!=null)
            mp.start();
    }
    @Override
    protected void onPause(){
        super.onPause();
        if(mp!=null)
            mp.pause();
        SharedPreferences.Editor sharedata = getSharedPreferences(SharedPreferencesName, 0).edit();
        gamePlayView.onPause(sharedata);
        sharedata.commit();
    }
    @Override
    protected void onStop(){
        super.onStop();
        gamePlayView.onStop();
        SoundPoolManager.release();
    }
}
