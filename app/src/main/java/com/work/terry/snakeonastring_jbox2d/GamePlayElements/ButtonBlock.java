package com.work.terry.snakeonastring_jbox2d.GamePlayElements;

import android.util.Log;

import com.work.terry.snakeonastring_jbox2d.JBox2DElements.CircleBody;
import com.work.terry.snakeonastring_jbox2d.JBox2DElements.MyBody;
import com.work.terry.snakeonastring_jbox2d.JBox2DElements.MyJoint;
import com.work.terry.snakeonastring_jbox2d.JBox2DElements.MyWeldJoint;
import com.work.terry.snakeonastring_jbox2d.JBox2DElements.RectBody;
import com.work.terry.snakeonastring_jbox2d.SurfaceViewAndActivity.GamePlay;
import com.work.terry.snakeonastring_jbox2d.UI.GameElement;
import com.work.terry.snakeonastring_jbox2d.Util.ColorManager;
import com.work.terry.snakeonastring_jbox2d.Util.Constant;
import com.work.terry.snakeonastring_jbox2d.Util.DrawUtil;
import com.work.terry.snakeonastring_jbox2d.Util.TexDrawer;
import com.work.terry.snakeonastring_jbox2d.Util.TexManager;

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

public class ButtonBlock extends MyBody {
    World world;
    CircleBody circleBody1;
    CircleBody circleBody2;
    RectBody rectBody;

    DrawUtil drawUtil;
    List<GameElement> drawSequence;

    private float circleDiameter;
    private float rectLength;
    private float rotateAngleCBRadian;
    private float rotateAngleRectRadian;
    private float rotateAngleCB1Radian;
    private float rotateAngleCB2Radian;

    public ButtonBlock(
            GamePlay gamePlay,
            String id,
            float x,float y,
            float circleDiameter,
            float totalLength,

            float defaultHeight,
            float rotateAngleDegrees,//0 horizontal   90 vertical  clockwise
            boolean isStatic,
            float[] colorFloats
    ){
        super(
                gamePlay,
                id,
                x,y,
                totalLength,
                circleDiameter,

                0,
                defaultHeight,
                ButtonBlockTopOffSet,
                ButtonBlockTopOffSetColorFactor,
                ButtonBlockHeightColorFactor,
                ButtonBlockFloorColorFactor,

                0,0,0,0,0,0,isStatic,
                ""
        );
        setColorFloats255(colorFloats);
        this.world = gamePlay.world;
        this.x = x;
        this.y = y;
        this.setTopRatio(Constant.ButtonBlockTopRatio);

        this.rotateAngleGameElements = rotateAngleDegrees;
        this.rotateAngleRectRadian = (float)Math.toRadians(rotateAngleDegrees-90);

        this.rotateAngleCBRadian = (float)Math.toRadians(rotateAngleDegrees);

        this.rotateAngleCB1Radian = (float)Math.toRadians(rotateAngleDegrees);
        this.rotateAngleCB2Radian = (float)Math.toRadians(rotateAngleDegrees+90);
        this.circleDiameter = circleDiameter;
        rectLength = totalLength-circleDiameter;
        this.isStatic = isStatic;

        drawSequence = new ArrayList<>();

        setDoDraw(false);
    }

    public ButtonBlock(
            GamePlay gamePlay,
            int id,
            float x,float y,
            float circleDiameter,
            float totalLength,

            float defaultHeight,
            float rotateAngleDegrees,//0 horizontal   90 vertical  clockwise
            boolean isStatic,
            float[] colorFloats
            ){
        this(
                gamePlay,
                "ButtonBlock "+id,
                x,y,
                circleDiameter,
                totalLength,


                defaultHeight,
                rotateAngleDegrees,
                isStatic,
                colorFloats
        );
    }
    @Override
    public void createBody() {
        initBody(gamePlay);
        setDoDraw(true);
    }
    @Override
    public void deleteBody(){
        rectBody.deleteBody();
        circleBody1.deleteBody();
        circleBody2.deleteBody();
    }
    public void initBody(GamePlay gamePlay){
        rectBody = new RectBody(
                gamePlay,
                id+((isStatic)?"Static":"Dynamic"+id+"RectBody"),
                x,y,
                rotateAngleRectRadian,
                circleDiameter/2,
                rectLength/2,

                color,
                defaultHeight,
                TopOffset,
                ButtonBlockTopOffSetColorFactor,
                ButtonBlockHeightColorFactor,
                ButtonBlockFloorColorFactor,

                0.0f,
                0.01f,
                0.1f,
                ButtonImgRect,
                isStatic
        );
        rectBody.createBody();
        this.body = rectBody.body;
        rectBody.setColorFloats(colorFloats);
        rectBody.setIsPureColor(true);
        //rectBody.setTopRatio(TopRatio);

        Vec2 angleVector = Mul2D(
                new Vec2(
                        (float) Math.cos(rotateAngleCBRadian),
                        (float) Math.sin(rotateAngleCBRadian)
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
                gamePlay,
                id+((isStatic)?"Static":"Dynamic"+id+"CircleBody"),
                circleBody1XY.x,circleBody1XY.y,
                rotateAngleCB1Radian,
                circleDiameter/2,

                this.color,
                defaultHeight,
                TopOffset,
                ButtonBlockTopOffSetColorFactor,
                ButtonBlockHeightColorFactor,
                ButtonBlockFloorColorFactor,

                0,
                0,
                0.0f,
                0.01f,
                0.1f,
                isStatic,
                Constant.ButtonImgCircleDown
        );
        circleBody1.createBody();
        circleBody1.setColorFloats(colorFloats);
        circleBody1.setIsPureColor(true);
        //circleBody1.setTopRatio(TopRatio);
        circleBody2 = new CircleBody(
                gamePlay,
                id+((isStatic)?"Static":"Dynamic"+id+"CircleBody"),
                circleBody2XY.x,circleBody2XY.y,
                rotateAngleCB2Radian,
                circleDiameter/2,

                this.color,
                defaultHeight,
                TopOffset,
                ButtonBlockTopOffSetColorFactor,
                ButtonBlockHeightColorFactor,
                ButtonBlockFloorColorFactor,

                0,
                0,
                0.0f,
                0.01f,
                0.1f,
                isStatic,
                Constant.ButtonImgCircleUp
        );
        circleBody2.createBody();
        circleBody2.setIsPureColor(true);
        circleBody2.setColorFloats(colorFloats);
        //circleBody2.setTopRatio(TopRatio);

        drawSequence.add(rectBody);
        drawSequence.add(circleBody1);
        drawSequence.add(circleBody2);

        MyJoint tempt = new MyWeldJoint(
                id+"WeldJoint1",
                gamePlay,
                true,
                rectBody,
                circleBody1,
                rectBody.body.getPosition(),
                //circleBody1.body.getPosition(),
                1.0f,
                1.0f
        );
        tempt.createJoint();
        tempt = new MyWeldJoint(
                id+"WeldJoint2",
                gamePlay,
                true,
                rectBody,
                circleBody2,
                rectBody.body.getPosition(),
                //circleBody2.body.getPosition(),
                1.0f,
                1.0f
        );
        tempt.createJoint();
    }
    @Override
    public void drawSelf(TexDrawer painter){
        synchronizeAngle();
        drawSequence.stream()
                .sorted(Comparator.comparing(x -> x.y))
                .forEach(
                        x -> {
                            x.drawSelf(painter);
                        }
                );
    }
    public void synchronizeAngle(){
        circleBody1.rotateAngleGameElements = 180-(float)Math.toDegrees( rectBody.body.getAngle());
//        circleBody1.drawSelf(painter);
//
        circleBody2.rotateAngleGameElements = 180-(float)Math.toDegrees( rectBody.body.getAngle());
//        circleBody2.drawSelf(painter);
        rotateAngleGameElements = -(float)Math.toDegrees( rectBody.body.getAngle());
        rectBody.rotateAngleGameElements = rotateAngleGameElements;

        //if(!isStatic)Log.d(id,"rotateAngle"+rectBody.rotateAngleGameElements%360);

        rectBody.jumpHeight = jumpHeight;
        circleBody1.jumpHeight = jumpHeight;
        circleBody2.jumpHeight = jumpHeight;
    }
    public void drawOffset(TexDrawer painter){
//        rectBody.drawSelf(painter);
//        circleBody1.drawSelf(painter);
//        circleBody2.drawSelf(painter);
        //float height = calDistance(minusV2D(circleBody1.getBodyXY(),circleBody2.getBodyXY()));
        painter.drawColorFactorTex(
                TexManager.getTex(rectBody.Img),
                colorFloats,
                rectBody.x,
                rectBody.y - jumpHeight - rectBody.defaultHeight+rectBody.TopOffset,
                (rectBody.TopWidth+rectBody.scaleWidth)*((TopRatio==0)?1:TopRatio),
                (rectBody.height+rectBody.scaleHeight)+circleBody1.radius*(1-TopRatio)+circleBody2.radius*(1-TopRatio),
                rectBody.rotateAngleGameElements,
                rectBody.TopOffsetColorFactor
        );
        //drawSelf

        painter.drawColorFactorTex(
                TexManager.getTex(SnakeBodyImg),
                colorFloats,
                circleBody1.x,
                circleBody1.y - jumpHeight - circleBody1.defaultHeight+circleBody1.TopOffset,
                (circleBody1.TopWidth+circleBody1.scaleWidth)*((TopRatio==0)?1:TopRatio),
                (circleBody1.TopHeight+circleBody1.scaleHeight)*((TopRatio==0)?1:TopRatio),
                circleBody1.rotateAngleGameElements,
                circleBody1.TopOffsetColorFactor
        );

        painter.drawColorFactorTex(
                TexManager.getTex(SnakeBodyImg),
                colorFloats,
                circleBody2.x,
                circleBody2.y - jumpHeight - circleBody2.defaultHeight+circleBody2.TopOffset,
                (circleBody2.TopWidth+circleBody2.scaleWidth)*((TopRatio==0)?1:TopRatio),
                (circleBody2.TopHeight+circleBody2.scaleHeight)*((TopRatio==0)?1:TopRatio),
                circleBody2.rotateAngleGameElements,
                circleBody2.TopOffsetColorFactor
        );
    }
    public void drawTop(TexDrawer painter){
        drawSequence.stream()
                //.sorted(Comparator.comparing(x -> x.y))
                .forEach(
                        ge -> {
                            //x.rotateAngleGameElements = (float)Math.toDegrees( rectBody.body.getAngle());
                            //x.drawSelf(painter);
                            painter.drawColorSelf(
                                    TexManager.getTex(ge.TopImg==null?ge.Img:ge.TopImg),
                                    ge.colorFloats,
                                    ge.x,
                                    ge.y - ge.jumpHeight - ge.defaultHeight,
                                    (ge.TopWidth+ge.scaleWidth)*((ge.TopRatio==0)?1:ge.TopRatio),
                                    (ge.TopHeight+ge.scaleHeight)*((ge.TopRatio==0)?1:ge.TopRatio),
                                    ge.rotateAngleGameElements
                            );
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
                            x.jumpHeight = jumpHeight;
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
