package com.work.terry.snakeonastring_jbox2d.SnakeElements;

/**
 * Created by Terry on 2018/3/2.
 */

public class SnakeNodeSkinInfo{
    float[] color255;
    String img;
    float[] radii;

    float TopRatio;

    public SnakeNodeSkinInfo(
            float[] color255,
            String img,
            float[] radii
    ){
        this.color255 = color255;
        this.img = img;
        this.radii = radii;
        this.TopRatio = radii[0]/radii[1];
    }
    public float[] getColor255(){
        return color255;
    }
    public String getImg(){
        return img;
    }
    public float[] getRadii(){
        return radii;
    }
    public float getTopRatio(){
        return TopRatio;
    }
}
