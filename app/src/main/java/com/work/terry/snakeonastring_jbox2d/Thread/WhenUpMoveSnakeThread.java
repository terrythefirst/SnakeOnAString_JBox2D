package com.work.terry.snakeonastring_jbox2d.Thread;

import com.work.terry.snakeonastring_jbox2d.SurfaceViewAndActivity.SkinChangingView;
import com.work.terry.snakeonastring_jbox2d.UI.GameElement;

import java.util.List;

/**
 * Created by Terry on 2018/3/8.
 */

public class WhenUpMoveSnakeThread extends Thread {
    boolean shouldDie = false;
    SkinChangingView skinChangingView;
    public void setShouldDie(){
        shouldDie = true;
    }
    public WhenUpMoveSnakeThread(SkinChangingView skinChangingView){
        this.skinChangingView = skinChangingView;
    }
    @Override
    public void run(){
        List<GameElement> nowSelectSkin = skinChangingView.snakes.get(skinChangingView.nowSelect);
        for (float nowDx = 720-nowSelectSkin.get(8).x;
             !shouldDie&&(Math.abs(nowDx)!=0)&&Math.abs(nowDx)<=skinChangingView.SnakeXInterval;
             nowSelectSkin = skinChangingView.snakes.get(skinChangingView.nowSelect),
             nowDx = 720-nowSelectSkin.get(8).x
                ){
            //Log.e("whenUpMove","nowDx"+nowDx);
            if(Math.abs(nowDx)<=skinChangingView.SnakePickSelectSpeed){
                for(Integer index:skinChangingView.snakes.keySet()){
                    skinChangingView.scaleSnakeByX(index,nowDx/2);
                }
            }else {
                for(Integer index:skinChangingView.snakes.keySet()){
                    skinChangingView.scaleSnakeByX(index,(nowDx>0)?skinChangingView.SnakePickSelectSpeed:-skinChangingView.SnakePickSelectSpeed);
                }
            }

            try {
                sleep(100);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
