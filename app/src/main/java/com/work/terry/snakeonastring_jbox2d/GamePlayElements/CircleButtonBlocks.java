package com.work.terry.snakeonastring_jbox2d.GamePlayElements;

import com.work.terry.snakeonastring_jbox2d.JBox2DElements.MyBody;
import com.work.terry.snakeonastring_jbox2d.JBox2DElements.MyBox2DRevoluteJoint;
import com.work.terry.snakeonastring_jbox2d.JBox2DElements.MyJoint;
import com.work.terry.snakeonastring_jbox2d.JBox2DElements.MyWeldJoint;
import com.work.terry.snakeonastring_jbox2d.JBox2DElements.RectBody;
import com.work.terry.snakeonastring_jbox2d.SurfaceViewAndActivity.GamePlay;
import com.work.terry.snakeonastring_jbox2d.Util.Constant;
import com.work.terry.snakeonastring_jbox2d.Util.TexDrawer;

import org.jbox2d.common.Vec2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.work.terry.snakeonastring_jbox2d.Util.Constant.RATE;

/**
 * Created by Terry on 2018/3/14.
 */

public class CircleButtonBlocks extends MyBody {
    float centerRadius;
    float buttonBlocksDiameter;

    float motorSpeed;//马达速度n*Math.PI
    float maxMotorTorque;//马达扭矩

    List<ButtonBlock> buttonBlocks = new ArrayList<>();
    List<RectBody> rectBodies = new ArrayList<>();
    List<MyJoint> joints = new ArrayList<>();
    RectBody nail;

    public CircleButtonBlocks(
            GamePlay gamePlay,
            int id,
            float x, float y,
            float centerRadius,
            float[] colorFloats255,
            float defaultHeight,
            float topOffset,
            float topOffsetColorFactor,
            float heightColorFactor,
            float floorShadowColorFactor,

            float motorSpeed,//马达速度n*Math.PI
            float maxMotorTorque//马达扭矩
    ) {
        super(
                gamePlay,
                "CircleButtonBlocks"+id,
                x, y,
                centerRadius*2, centerRadius*2,
                0,
                defaultHeight,
                topOffset,
                topOffsetColorFactor,
                heightColorFactor,
                floorShadowColorFactor,

                0,
                0,
                0,
                0,0,0,
                false,
                null
        );
        this.motorSpeed = motorSpeed/RATE;
        this.maxMotorTorque = maxMotorTorque;
        this.setColorFloats255(colorFloats255);
        this.centerRadius = centerRadius;

        this.buttonBlocksDiameter = (float)(Math.toRadians(35)*centerRadius);
    }

    public void initButtonBlocks(){
        nail = new RectBody(
                gamePlay,
                "CircleButtonBlockNail ",
                x,y,
                0,
                2,
                2,
                0,
                0,
                0,
                0,0,0,

                1,1,1,
                null,
                true
        );

        float angle= 90;
        for(int i=0;i<8;i++){
            angle -=i*45;
            float angleRadians = (float)Math.toRadians(angle);
            ButtonBlock bb = new ButtonBlock(
                    gamePlay,
                    "CircleButtonBlockButtonBlock "+i,
                    x+centerRadius*((float)Math.cos(angleRadians)),
                    y+centerRadius*((float)Math.sin(angleRadians)),
                    buttonBlocksDiameter,
                    buttonBlocksDiameter*1.3f,

                    defaultHeight,
                    angle,
                    false,
                    colorFloats
            );
            bb.setColorFloats(colorFloats);
            bb.setTopOffset(TopOffset);
            bb.setDrawArgs(
                    TopOffsetColorFactor,
                    HeightColorFactor,
                    FloorShadowColorFactor
            );
            buttonBlocks.add(bb);
        }

        float angleAxis = 90;
        for(int i=0;i<4;i++){
            float angleRadians = (float)Math.toRadians(angleAxis-i*45);
            rectBodies.add(
                    new RectBody(
                            gamePlay,
                            "CircleButtonBlockRect "+i,
                            x,y,
                            angleRadians,
                            2,
                            centerRadius,
                            0,
                            0,
                            0,
                            0,0,0,

                            10,1,1,
                            Constant.SnakeBodyHeightImg,
                            false
                    )
            );
        }
    }
    @Override
    public boolean testTouch(float touchX,float touchY){
        return  touchX>x-width-scaleWidth/2
                &&touchX<x+width+scaleWidth/2
                &&touchY>y-height-scaleHeight/2
                &&touchY<y+height+(jumpHeight+defaultHeight)+scaleHeight/2
                ;
    }
    public void initJoints(){
        for(int i=0;i<4;i++){
            RectBody rectBody = rectBodies.get(i);

            joints.add(
                    new MyBox2DRevoluteJoint(
                            "CircleButtonBlockRevoluteJoint "+i,
                            gamePlay,
                            false,
                            nail,rectBody,
                            new Vec2(x/RATE,y/RATE),
                            false,
                            0.0f,
                            0.0f,
                            true,
                            motorSpeed,
                            maxMotorTorque
                    )
            );
            for (int j=i+1;j<4;j++){
                RectBody rectBody2 = rectBodies.get(i);
                joints.add(
                        new MyBox2DRevoluteJoint(
                                "CircleButtonBlockRevoluteJoint "+i+j,
                                gamePlay,
                                false,
                                rectBody2,rectBody,
                                new Vec2(x/RATE,y/RATE),
                                false,0.0f, 0.0f, false,0.0f, 0.0f
                        )
                );
            }

            ButtonBlock bb1 = buttonBlocks.get(i);
            ButtonBlock bb2 = buttonBlocks.get(i+4);
            joints.add(
//                    new MyWeldJoint(
//                            "CircleButtonBlockWeldJoint "+i,
//                            gamePlay,
//                            false,
//                            rectBody,
//                            bb1,
//                            new Vec2(bb1.x/RATE,bb1.y/RATE),
//                            //circleBody1.body.getPosition(),
//                            0.0f,
//                            0.0f
//                    )
            new MyBox2DRevoluteJoint(
                    "CircleButtonBlockRevoluteJoint "+i,
                    gamePlay,
                    false,
                    bb1,rectBody,
                    new Vec2(bb1.x/RATE,bb1.y/RATE),
                    false,0.0f, 0.0f, false,0.0f, 0.0f
            )
            );
            joints.add(
//                    new MyWeldJoint(
//                            "CircleButtonBlockWeldJoint "+i,
//                            gamePlay,
//                            false,
//                            rectBody,
//                            bb2,
//                            new Vec2(bb2.x/RATE,bb2.y/RATE),
//                            //circleBody1.body.getPosition(),
//                            0.0f,
//                            0.0f
//                    )
                    new MyBox2DRevoluteJoint(
                            "CircleButtonBlockRevoluteJoint "+i,
                            gamePlay,
                            false,
                            bb2,rectBody,
                            new Vec2(bb2.x/RATE,bb2.y/RATE),
                            false,0.0f, 0.0f, false,0.0f, 0.0f
                    )
            );

            for(MyJoint mj:joints)mj.createJoint();
            joints.clear();
        }
    }
    @Override
    public void createBody() {
        initButtonBlocks();

        nail.createBody();
        for(ButtonBlock bb:buttonBlocks)bb.createBody();
        for(RectBody rb:rectBodies)rb.createBody();

        initJoints();
        //for(MyJoint mj:joints)mj.createJoint();

        created = true;
    }
    @Override
    public void deleteBody(){
        if(created&&!destroyed){
            for(ButtonBlock bb:buttonBlocks)bb.deleteBody();
            for(RectBody rb:rectBodies)rb.deleteBody();
            //for(MyJoint mj:joints)mj.deleteJoint();
            destroyed=true;
        }
    }
    @Override
    public void drawSelf(TexDrawer painter){
        if(!created)return;
        buttonBlocks.stream()
                .forEach(
                        x -> {
                            x.synchronizeAngle();
                            x.drawOffset(painter);
                        }
                );
        buttonBlocks.stream()
                .forEach(
                        x -> {
                            //x.synchronizeAngle();
                            x.drawTop(painter);
                        }
                );
//        rectBodies.stream()
//                .sorted(Comparator.comparing(x -> x.y))
//                .forEach(
//                        x -> {
//                            x.drawSelf(painter);
//                        }
//                );

    }
    @Override
    public void drawHeight(TexDrawer painter){
        if(created)
        buttonBlocks.stream()
                .sorted(Comparator.comparing(x -> x.y))
                .forEach(
                        x -> {
                            x.drawHeight(painter);
                        }
                );
    }
    @Override
    public void drawFloorShadow(TexDrawer painter){
        if(created)
        for(ButtonBlock bb:buttonBlocks)bb.drawFloorShadow(painter);
    }
}
