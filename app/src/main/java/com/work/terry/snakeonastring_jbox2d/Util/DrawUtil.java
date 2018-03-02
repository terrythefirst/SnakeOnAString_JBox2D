package com.work.terry.snakeonastring_jbox2d.Util;

import com.work.terry.snakeonastring_jbox2d.SurfaceViewAndActivity.MyMenu;
import com.work.terry.snakeonastring_jbox2d.UI.GameElement;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Terry on 2018/1/24.
 */

public class DrawUtil {
    String backgroundImg = "";
    public List<GameElement> floorLayerDrawSequence = null;
    public List<GameElement> centerLayerDrawSequence = null;
    public List<GameElement> animationLayerDrawSequence = null;
    public List<GameElement> topLayerDrawSequence = null;

    public List<GameElement> removeSequence = null;

    public MyMenu myMenu;


    public DrawUtil(String backgroundImg) {
        this.backgroundImg = backgroundImg;
        floorLayerDrawSequence = new ArrayList<>();
        centerLayerDrawSequence = new ArrayList<>();
        animationLayerDrawSequence = new ArrayList<>();
        topLayerDrawSequence = new ArrayList<>();

        removeSequence = new ArrayList<>();
    }
    public void setMenu(MyMenu menu){
        this.myMenu = menu;
    }
    public synchronized void addToTopLayer(GameElement gameElement) {
        synchronized (topLayerDrawSequence) {
            topLayerDrawSequence.add(gameElement);
        }
    }

    public void addToCenterLayer(GameElement gameElement) {
        synchronized (centerLayerDrawSequence){
            centerLayerDrawSequence.add(gameElement);
        }
    }

    public void addToAnimationLayer(GameElement gameElement){
        synchronized (animationLayerDrawSequence){
            animationLayerDrawSequence.add(gameElement);
        }
    }

    public void addToFloorLayer(GameElement gameElement) {
        synchronized (floorLayerDrawSequence){
            floorLayerDrawSequence.add(gameElement);
        }
    }
    public void addToRemoveSequence(GameElement gameElement){
        synchronized (removeSequence){
            removeSequence.add(gameElement);
        }
    }
    private void deleteElement(GameElement gameElement) {
        synchronized (animationLayerDrawSequence){
            animationLayerDrawSequence.remove(gameElement);
        }
        synchronized (centerLayerDrawSequence){
            centerLayerDrawSequence.remove(gameElement);
        }
        synchronized (animationLayerDrawSequence){
            animationLayerDrawSequence.remove(gameElement);
        }
        synchronized (floorLayerDrawSequence){
            floorLayerDrawSequence.remove(gameElement);
        }
    }

    public void stepDraw(TexDrawer painter) {
        //先画背景
        if(backgroundImg!=null&&!backgroundImg.contains("null"))
            painter.drawTex(
                    TexManager.getTex(backgroundImg),
                    Constant.SCREEN_WIDTH / 2,
                    Constant.SCREEN_HEIGHT / 2,
                    Constant.SCREEN_WIDTH,
                    Constant.SCREEN_HEIGHT,
                    0
            );

        drawFloorAndCenterLayer(painter);
        drawAnimationLayer(painter);
        drawTopLayer(painter);

        if(myMenu!=null){
            synchronized (myMenu){
                myMenu.drawFloorShadow(painter);
                myMenu.drawHeight(painter);
                myMenu.drawSelf(painter);
            }
        }

        cleanNotDraw();
    }
    public void cleanNotDraw(){
        synchronized (removeSequence){
            removeSequence.stream().forEach(
                    x->deleteElement(x)
            );
            removeSequence.clear();
        }
    }
    public void drawAnimationLayer(TexDrawer painter){
        synchronized (animationLayerDrawSequence){
            animationLayerDrawSequence.stream().forEach(
                    x->x.drawSelf(painter)
            );
        }
    }
    public void drawFloorAndCenterLayer(TexDrawer painter){
        //背景上的影子
        synchronized (animationLayerDrawSequence){
            animationLayerDrawSequence.stream()
                    .forEach(
                            x->{
                                x.drawFloorShadow(painter);
                            }
                    );
        }
        synchronized (centerLayerDrawSequence){
            centerLayerDrawSequence.stream()
                    .filter(x->x.doDraw)
                    .forEach(
                            x -> {
                                x.drawFloorShadow(painter);
                            }
                    );
        }
        synchronized (floorLayerDrawSequence){
            floorLayerDrawSequence.stream()
                    .filter(x->x.doDraw)
                    .sorted(Comparator.comparing(x -> x.y))
                    .forEach(
                            x -> {
                                x.drawFloorShadow(painter);
                            }
                    );
            //底层元素
            floorLayerDrawSequence.stream()
                    .filter(x->x.doDraw)
                    .sorted(Comparator.comparing(x -> x.y))
                    .forEach(
                            x -> {
                                x.drawHeight(painter);
                                x.drawSelf(painter);
                            }
                    );
        }


        //中层元素
        synchronized (centerLayerDrawSequence){
            centerLayerDrawSequence.stream()
                    .filter(x->x.doDraw)
                    .sorted(Comparator.comparing(x -> x.y))
                    .forEach(
                            x -> {
                                x.drawHeight(painter);
                                x.drawSelf(painter);
                            }
                    );
        }
    }
    public void drawTopLayer(TexDrawer painter){
        //上层元素
        synchronized (topLayerDrawSequence){
            for (GameElement g : topLayerDrawSequence) {
                g.drawFloorShadow(painter);
            }
            //top
            topLayerDrawSequence.stream()
                    .filter(x->x.doDraw)
                    .sorted(Comparator.comparing(x -> x.y))
                    .forEach(
                            x -> {
                                x.drawHeight(painter);
                                x.drawSelf(painter);
                            }
                    );
        }
    }

}

