package com.work.terry.snakeonastring_jbox2d.Util;

import com.work.terry.snakeonastring_jbox2d.GameElements;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by Terry on 2018/1/24.
 */

public class DrawUtil {
    String backgroundImg = "";
    public List<GameElements> floorLayerDrawSequence = null;
    public List<GameElements> centerLayerDrawSequence = null;
    public List<GameElements> topLayerDrawSequence = null;

    public List<GameElements> removeSequence = null;


    public DrawUtil(String backgroundImg) {
        this.backgroundImg = backgroundImg;
        floorLayerDrawSequence = new ArrayList<>();
        centerLayerDrawSequence = new ArrayList<>();
        topLayerDrawSequence = new ArrayList<>();

        removeSequence = new ArrayList<>();
    }

    public synchronized void addToTopLayer(GameElements gameElements) {
        synchronized (topLayerDrawSequence) {
            topLayerDrawSequence.add(gameElements);
        }
    }

    public void addToCenterLayer(GameElements gameElements) {
        synchronized (centerLayerDrawSequence){
            centerLayerDrawSequence.add(gameElements);
        }
    }

    public void addToFloorLayer(GameElements gameElements) {
        synchronized (floorLayerDrawSequence){
            floorLayerDrawSequence.add(gameElements);
        }
    }
    public void addToremoveSequence(GameElements gameElements){
        synchronized (removeSequence){
            removeSequence.add(gameElements);
        }
    }
    private void deleteElement(GameElements gameElements) {
        topLayerDrawSequence.remove(gameElements);
        centerLayerDrawSequence.remove(gameElements);
        floorLayerDrawSequence.remove(gameElements);
    }

    public void stepDraw(TexDrawer painter) {
        //先画背景
        painter.drawTex(
                TexManager.getTex(backgroundImg),
                Constant.SCREEN_WIDTH / 2,
                Constant.SCREEN_HEIGHT / 2,
                Constant.SCREEN_WIDTH,
                Constant.SCREEN_HEIGHT,
                0
        );

        drawFloorAndCenterLayer(painter);
        drawTopLayer(painter);
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
    public void drawFloorAndCenterLayer(TexDrawer painter){
        //背景上的影子
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
            for (GameElements g : topLayerDrawSequence) {
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

