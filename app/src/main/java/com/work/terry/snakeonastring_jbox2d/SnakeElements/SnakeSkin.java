package com.work.terry.snakeonastring_jbox2d.SnakeElements;

import java.util.List;
import java.util.Map;

/**
 * Created by Terry on 2018/3/1.
 */

public class SnakeSkin {
    String SkinName;
    Map<Integer,List<Object>> skinInfo;

    int cycle;

    public SnakeSkin(
            String SkinName,
            Map<Integer,List<Object>> skinInfo
    ){
        this.SkinName = SkinName;
        this.skinInfo = skinInfo;
        cycle = Integer.MIN_VALUE;
        for(Object x:skinInfo.keySet()){
            Integer k = (Integer)x;
            if(cycle<k)cycle = k;
        }
    }
    public Map<Integer,List<Object>> getSkinInfo(){
        return skinInfo;
    }
    public List<Object> getSkin(int nodeIndex){
        if(nodeIndex == 0){
            return skinInfo.get(0);
        }else if(cycle == 0){
            return skinInfo.get(-1);
        }else {
            if(cycle == 1)cycle++;

            nodeIndex %= cycle;
            try {
                return skinInfo.get(nodeIndex==0?cycle:nodeIndex);
            }catch (Exception e){
                return skinInfo.get(-1);
            }
        }
    }
    public String getSkinName(){
        return SkinName;
    }
}
