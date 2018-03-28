package com.work.terry.snakeonastring_jbox2d.Util;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import work.terry.com.snakeonastring_jbox2d.R;

/**
 * Created by Terry on 2018/3/25.
 */

public class SoundPoolManager {
    public static int backgroundMusicMain = 0;
    public static int snakeBodyPopUpSound = 1;
    public static int snakeBodyExplodeSound = 2;
    public static int snakeEatingSound = 3;
    public static int menuSlideSound = 4;
    public static int menuSlideSoundReverse = 5;
    public static int buttonRelease = 6;
    public static int snakeFountainAnimationSound = 7;
    public static int backgroundMusicGamePlay = 8;
    public static int snakeCrushBreakingSound = 9;
    public static int snakeRemoveExplodeSound = 10;
    public static int fireFuseSound = 11;

    private static   Context context;
    //音效的音量
    private static int streamVolume;
    //定义SoundPool 对象
    private static SoundPool soundPool;
    //定义HASH表
    private static Map<Integer, Integer> soundPoolMap;

    /*************************************************************** * Function: initSounds();

     * Parameters: null

     * Returns: None.

     * Description: 初始化声音系统

     * Notes: none.

     ***************************************************************/

    public static void initSoundPool(Context context) {
        //初始化soundPool 对象,第一个参数是允许有多少个声音流同时播放,第2个参数是声音类型,第三个参数是声音的品质
        SoundPool.Builder builder = new SoundPool.Builder();
        builder.setMaxStreams(100);
        soundPool = builder.build();//new SoundPool(100, AudioManager.STREAM_MUSIC, 100);
        SoundPoolManager.context = context;
        //初始化HASH表
        soundPoolMap = new HashMap<Integer, Integer>();

        //获得声音设备和设备音量
        AudioManager mgr = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);

        streamVolume = 1;//mgr.getStreamVolume(AudioManager.STREAM_MUSIC);
        initSounds();
    }
    private static void initSounds(){
        SoundPoolManager.loadSfx(R.raw.bobo_short,snakeBodyPopUpSound);
        SoundPoolManager.loadSfx(R.raw.booom,snakeBodyExplodeSound);
        SoundPoolManager.loadSfx(R.raw.eat_potate_chips,snakeEatingSound);
        SoundPoolManager.loadSfx(R.raw.wet_spoosh_slap,snakeFountainAnimationSound);
        SoundPoolManager.loadSfx(R.raw.thin_whoos,menuSlideSound);
        SoundPoolManager.loadSfx(R.raw.thin_whoos_reverse,menuSlideSoundReverse);
        SoundPoolManager.loadSfx(R.raw.button_release,buttonRelease);
        SoundPoolManager.loadSfx(R.raw.glass_breaking,snakeCrushBreakingSound);
        SoundPoolManager.loadSfx(R.raw.booom,snakeRemoveExplodeSound);
        SoundPoolManager.loadSfx(R.raw.fire_tsi_tsi,fireFuseSound);
    }
    /*************************************************************** * Function: loadSfx();

     * Parameters: null

     * Returns: None.

     * Description: 加载音效资源

     * Notes: none.

     ***************************************************************/

    private static void loadSfx(int raw, int ID) {
        //把资源中的音效加载到指定的ID(播放的时候就对应到这个ID播放就行了)
        int loadId = soundPool.load(context, raw, 1);
        soundPoolMap.put(ID, loadId);
    }

    /*************************************************************** * Function: play();

     * Parameters: sound:要播放的音效的ID, loop:循环次数

     * Returns: None.

     * Description: 播放声音

     * Notes: none.

     ***************************************************************/

    public static void play(int sound, int uLoop) {
        soundPool.play(soundPoolMap.get(sound), streamVolume, streamVolume, 1, uLoop, 1f);
    }
    public static void setVolume(int x){
        streamVolume = x;
    }
    public static void release(){
        if(soundPool!=null)
            soundPool.release();
    }
}
