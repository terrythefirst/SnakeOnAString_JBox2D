package com.work.terry.snakeonastring_jbox2d;

import android.widget.*;

import com.work.terry.snakeonastring_jbox2d.JBox2DElements.CircleBody;
import com.work.terry.snakeonastring_jbox2d.JBox2DElements.MyWeldJoint;
import com.work.terry.snakeonastring_jbox2d.JBox2DElements.RectBody;
import com.work.terry.snakeonastring_jbox2d.Util.Constant;
import com.work.terry.snakeonastring_jbox2d.Util.TexDrawer;
import com.work.terry.snakeonastring_jbox2d.Util.VectorUtil;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import static com.work.terry.snakeonastring_jbox2d.Util.VectorUtil.*;

/**
 * Created by Yearn on 2018/1/21.
 */

public class ButtonBlock extends GameElements{
    World world;
    CircleBody circleBody1;
    CircleBody circleBody2;
    RectBody rectBody;

    boolean isStatic;
    public int Color = 1;

    private float circleDiameter;
    private float rectLength;
    private float rotateAngleRadian;

    public ButtonBlock(
            World world,
            float x,float y,
            float circleDiameter,
            float totalLength,
            float jumpHeight,
            float rotateAngleDegrees,
            boolean isStatic,
            int Color){
        super(
                x,y,
                totalLength,circleDiameter,
                Constant.SnakeDownHeight,
                "",
                jumpHeight
        );
        this.world = world;
        this.x = x;
        this.y = y;
        this.Color = Color;
        this.rotateAngleRadian = (float)Math.toRadians(rotateAngleDegrees);
        this.circleDiameter = circleDiameter;
        rectLength = totalLength-circleDiameter;
        this.isStatic = isStatic;
        initBody();
    }
    public void initBody(){
        rectBody = new RectBody(
                world,
                (isStatic)?"Static":"Dynamic"+"ButtonBlock"+"RectBody",
                x,y,
                rotateAngleRadian,
                0,0,
                circleDiameter/2,
                rectLength/2,
                1.0f,
                0.01f,
                0.1f,
                "",
                isStatic
        );
        Vec2 angleVector = Mul2D(
                new Vec2(
                        (float) Math.cos(rotateAngleRadian),
                        (float) Math.sin(rotateAngleRadian)
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
                0,
                0,0,
                circleDiameter/2,0,
                0,
                1.0f,
                0.01f,
                0.1f,
                isStatic,
                ""
        );
        circleBody2 = new CircleBody(
                world,
                (isStatic)?"Static":"Dynamic"+"ButtonBlock"+"CircleBody",
                circleBody2XY.x,circleBody2XY.y,
                0,
                0,0,
                circleDiameter/2,0,
                0,
                1.0f,
                0.01f,
                0.1f,
                isStatic,
                ""
        );

        new MyWeldJoint(
                "ButtonBlock"+"WeldJoint1",
                world,
                true,
                rectBody,
                circleBody1,
                //rectBody.body.getPosition(),
                circleBody1.body.getPosition(),
                0.0f,
                0.0f
        );
        new MyWeldJoint(
                "ButtonBlock"+"WeldJoint2",
                world,
                true,
                rectBody,
                circleBody2,
                //rectBody.body.getPosition(),
                circleBody2.body.getPosition(),
                0.0f,
                0.0f
        );
    }
    public void drawSelf(TexDrawer painter, float[] color){

    }
    public void drawHeightShadow(TexDrawer painter,float[] color){

    }
    public void drawFloorShadow(TexDrawer painter,float[] color){

    }
}
