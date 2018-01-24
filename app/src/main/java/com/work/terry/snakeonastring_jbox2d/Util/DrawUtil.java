package com.work.terry.snakeonastring_jbox2d.Util;

import com.work.terry.snakeonastring_jbox2d.GameElements;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Terry on 2018/1/24.
 */

public class DrawUtil {
    String backgroundImg = "";
    public List<GameElements> centerLayerDrawSequence = null;
    public List<GameElements> topLayerDrawSequence = null;

    public DrawUtil(String backgroundImg){
        this.backgroundImg = backgroundImg;
        centerLayerDrawSequence = new ArrayList<>();
        topLayerDrawSequence = new ArrayList<>();
    }

    public void addToTopLayer(GameElements gameElements){
        topLayerDrawSequence.add(gameElements);
    }
    public void addToCenterLayer(GameElements gameElements){
        centerLayerDrawSequence.add(gameElements);
    }

    public void stepDraw(TexDrawer painter){
        //先画背景
        painter.drawTex(
                TexManager.getTex(backgroundImg),
                Constant.SCREEN_WIDTH/2,
                Constant.SCREEN_HEIGHT/2,
                Constant.SCREEN_WIDTH,
                Constant.SCREEN_HEIGHT,
                0
        );

        //中层元素
            //floorShadow
            for (GameElements g:centerLayerDrawSequence){
                g.drawFloorShadow(painter);
            }
            //height
            centerLayerDrawSequence.stream()
                .sorted(Comparator.comparing(x -> x.y))
                .forEach(
                        x -> {
                            x.drawFloorShadow(painter);
                        }
                );

            //top
            centerLayerDrawSequence.stream()
                .sorted(Comparator.comparing(x -> x.y))
                .forEach(
                        x -> {
                            x.drawHeight(painter);
                            x.drawSelf(painter);
                        }
                );

        //上层元素
            //floorShadow
            for (GameElements g:topLayerDrawSequence){
                g.drawFloorShadow(painter);
            }
            //height
            topLayerDrawSequence.stream()
                    .sorted(Comparator.comparing(x -> x.y))
                    .forEach(
                            x -> {
                                x.drawFloorShadow(painter);
                            }
                    );

            //top
            topLayerDrawSequence.stream()
                    .sorted(Comparator.comparing(x -> x.y))
                    .forEach(
                            x -> {
                                x.drawHeight(painter);
                                x.drawSelf(painter);
                            }
                    );
    }
}
