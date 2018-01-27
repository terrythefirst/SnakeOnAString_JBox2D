package com.work.terry.snakeonastring_jbox2d;

import com.work.terry.snakeonastring_jbox2d.JBox2DElements.CircleBody;
import com.work.terry.snakeonastring_jbox2d.JBox2DElements.MyPrismaticJoint;
import com.work.terry.snakeonastring_jbox2d.JBox2DElements.MyWeldJoint;
import com.work.terry.snakeonastring_jbox2d.JBox2DElements.RectBody;
import com.work.terry.snakeonastring_jbox2d.Util.Constant;
import com.work.terry.snakeonastring_jbox2d.Util.DrawUtil;
import com.work.terry.snakeonastring_jbox2d.Util.JBox2DUtil;
import com.work.terry.snakeonastring_jbox2d.Util.TexDrawer;
import com.work.terry.snakeonastring_jbox2d.Util.VectorUtil;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.work.terry.snakeonastring_jbox2d.Util.Constant.*;
import static com.work.terry.snakeonastring_jbox2d.Util.VectorUtil.*;

/**
 * Created by Yearn on 2018/1/21.
 */

public class ButtonBlock extends GameElements{
    World world;
    CircleBody circleBody1;
    CircleBody circleBody2;
    RectBody rectBody;

    DrawUtil drawUtil;
    List<GameElements> drawSequence;

    boolean isStatic;

    private float circleDiameter;
    private float rectLength;
    private float rotateAngleRectRadian;
    private float rotateAngleCB1Radian;
    private float rotateAngleCB2Radian;
    private float TopRatio;

    public ButtonBlock(
            World world,
            String id,
            float x,float y,
            float circleDiameter,
            float totalLength,

            float TopRatio,

            float defaultHeight,
            float rotateAngleDegrees,
            boolean isStatic,
            int color
            ){
        super(
                id,
                x,y,
                totalLength,circleDiameter,

                color,
                defaultHeight,
                ButtonBlockTopOffSet,
                ButtonBlockTopOffSetColorFactor,
                ButtonBlockHeightColorFactor,
                ButtonBlockFloorColorFactor,

                ""
        );
        this.world = world;
        this.x = x;
        this.y = y;
        this.TopRatio = TopRatio;
        this.rotateAngleRectRadian = (float)Math.toRadians(rotateAngleDegrees);
        this.rotateAngleCB1Radian = (float)Math.toRadians(rotateAngleDegrees);
        this.rotateAngleCB2Radian = (float)Math.toRadians(rotateAngleDegrees+90);
        this.circleDiameter = circleDiameter;
        rectLength = totalLength-circleDiameter;
        this.isStatic = isStatic;

        drawSequence = new ArrayList<>();
        initBody();

    }
    public void initBody(){
        rectBody = new RectBody(
                world,
                (isStatic)?"Static":"Dynamic"+"ButtonBlock"+"RectBody",
                x,y,
                rotateAngleRectRadian,
                0,0,
                circleDiameter/2,
                rectLength/2,

                this.color,
                ButtonBlockDefaultHeight,
                TopOffset,
                ButtonBlockTopOffSetColorFactor,
                ButtonBlockHeightColorFactor,
                ButtonBlockFloorColorFactor,

                1.0f,
                0.01f,
                0.1f,
                ButtonImgRect,
                isStatic
        );
        rectBody.setTopRatio(TopRatio);

        Vec2 angleVector = Mul2D(
                new Vec2(
                        (float) Math.cos(rotateAngleRectRadian),
                        (float) Math.sin(rotateAngleRectRadian)
                ),
                rectLength/2
                );
        Vec2 circleBody1XY = minusV2D(
                rectBody.getBodyXY(),
                angleVector
        );
        Vec2 circleBody2XY = plusV2D(
                rectBody.getBodyXY(),
                angleVector
        );
        circleBody1 = new CircleBody(
                world,
                (isStatic)?"Static":"Dynamic"+"ButtonBlock"+"CircleBody",
                circleBody1XY.x,circleBody1XY.y,
                rotateAngleCB1Radian,
                0,0,
                circleDiameter/2,

                this.color,
                ButtonBlockDefaultHeight,
                TopOffset,
                ButtonBlockTopOffSetColorFactor,
                ButtonBlockHeightColorFactor,
                ButtonBlockFloorColorFactor,

                0,
                0,
                1.0f,
                0.01f,
                0.1f,
                isStatic,
                Constant.ButtonImgCircleDown
        );
        circleBody1.setTopRatio(TopRatio);
        circleBody2 = new CircleBody(
                world,
                (isStatic)?"Static":"Dynamic"+"ButtonBlock"+"CircleBody",
                circleBody2XY.x,circleBody2XY.y,
                rotateAngleCB2Radian,
                0,0,
                circleDiameter/2,

                3,
                ButtonBlockDefaultHeight,
                TopOffset,
                ButtonBlockTopOffSetColorFactor,
                ButtonBlockHeightColorFactor,
                ButtonBlockFloorColorFactor,

                0,
                0,
                1.0f,
                0.01f,
                0.1f,
                isStatic,
                Constant.ButtonImgCircleUp
        );
        circleBody2.setTopRatio(TopRatio);

        drawSequence.add(rectBody);
        drawSequence.add(circleBody1);
        drawSequence.add(circleBody2);

        new MyWeldJoint(
                "ButtonBlock"+"WeldJoint1",
                world,
                true,
                rectBody,
                circleBody1,
                rectBody.body.getPosition(),
                //circleBody1.body.getPosition(),
                0.0f,
                0.0f
        );
        new MyWeldJoint(
                "ButtonBlock"+"WeldJoint2",
                world,
                true,
                rectBody,
                circleBody2,
                rectBody.body.getPosition(),
                //circleBody2.body.getPosition(),
                0.0f,
                0.0f
        );

//        new MyPrismaticJoint(
//                "",
//                world,
//                true,
//                JBox2DUtil.staticBody,
//                rectBody.body,
//                rectBody.getBodyXY(),
//                new Vec2(1,0),
//                0,
//                true,
//                -400/RATE,
//                400/RATE,
//                true,
//                5,
//                200
//        );
    }
    @Override
    public void drawSelf(TexDrawer painter){
//        circleBody1.rotateAngleGameElements = (float)Math.toDegrees( rectBody.body.getAngle())-90;
//        circleBody1.drawSelf(painter);
//
//        circleBody2.rotateAngleGameElements = (float)Math.toDegrees( rectBody.body.getAngle())+90;
//        circleBody2.drawSelf(painter);
        drawSequence.stream()
                .sorted(Comparator.comparing(x -> x.y))
                .forEach(
                        x -> {
                            //x.rotateAngleGameElements = (float)Math.toDegrees( rectBody.body.getAngle());
                            x.drawSelf(painter);
                        }
                );
    }
    @Override
    public void drawHeight(TexDrawer painter){
        drawSequence.stream()
                .sorted(Comparator.comparing(x -> x.y))
                .forEach(
                        x -> {
                            //x.rotateAngleGameElements = (float)Math.toDegrees( rectBody.body.getAngle());
                            x.drawHeight(painter);
                        }
                );

    }
    @Override
    public void drawFloorShadow(TexDrawer painter){
        //rectBody.rotateAngleGameElements = (float)Math.toDegrees( rectBody.body.getAngle())+90;
        drawSequence.stream()
                .sorted(Comparator.comparing(x -> x.y))
                .forEach(
                        x -> {
                            //x.rotateAngleGameElements = (float)Math.toDegrees( rectBody.body.getAngle());
                            x.drawFloorShadow(painter);
                        }
                );
    }
}
