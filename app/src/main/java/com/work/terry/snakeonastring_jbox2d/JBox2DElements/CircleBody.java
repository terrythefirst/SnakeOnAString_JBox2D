package com.work.terry.snakeonastring_jbox2d.JBox2DElements;

import android.content.SharedPreferences;
import android.util.Log;

import com.work.terry.snakeonastring_jbox2d.Thread.JBox2DThread;
import com.work.terry.snakeonastring_jbox2d.Util.Constant;
import com.work.terry.snakeonastring_jbox2d.Util.JBox2DUtil;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.joints.LimitState;

import work.terry.com.snakeonastring_jbox2d.R;

import static com.work.terry.snakeonastring_jbox2d.Util.Constant.RATE;

/**
 * Created by Terry on 2018/1/13.
 */

public class CircleBody extends MyBody{
    public float radius;//声明圆形类物体半径的变量
    public CircleBody(
            World world,
            String id,
            float x,float y,
            float width,float height,

            int color,
            float defaultHeight,
            float topOffset,
            float topOffsetColorFactor,
            float heightColorFactor,
            float floorShadowColorFactor,

            String Img){
        super(
                world,
                id,
                x,y,
                width,height,

                color,
                defaultHeight,
                topOffset,
                topOffsetColorFactor,
                heightColorFactor,
                floorShadowColorFactor,

                Img
        );
        this.radius=width/2;//给圆形类物体半径变量赋值
    }
    public CircleBody(
            World world,
            String id,
            float x,float y,
            float angle,
            float vX,float vY,
            float radius,

            int color,
            float defaultHeight,
            float topOffset,
            float topOffsetColorFactor,
            float heightColorFactor,
            float floorShadowColorFactor,

            float angularDampingRate,
            float linearDampingRate,
            float density,
            float friction,
            float restitution,
            boolean isStaic,
            String img
    )//构造函数
    {
        super(
                world,
                id,
                x,y,
                radius*2,radius*2,

                color,
                defaultHeight,
                topOffset,
                topOffsetColorFactor,
                heightColorFactor,
                floorShadowColorFactor,

                img
        );
        this.radius=radius;//给圆形类物体半径变量赋值

        createCircleBody(world,id,x,y,angle,vX,vY,radius,angularDampingRate,linearDampingRate,density,friction,restitution,isStaic);
    }
    public void createCircleBody(
                                 World world,
                                 String id,
                                 float x,float y,
                                 float angle,
                                 float vX,float vY,
                                 float radius,
                                 float angularDampingRate,
                                 float linearDampingRate,
                                 float density,
                                 float friction,
                                 float restitution,
                                 boolean isStatic
    ){
        BodyDef bd=new BodyDef();//创建刚体描述
        if(isStatic){
            bd.type= BodyType.STATIC;//设置是否为可运动刚体
        }else {
            bd.type= BodyType.DYNAMIC;//设置是否为可运动刚体
        }


        bd.position.set(x/RATE, y/RATE);//设置位置
        bd.linearVelocity.set(new Vec2(vX,vY));
        bd.angle = angle;
        bd.userData = id;

        bd.setAngularDamping(angularDampingRate);
        bd.setLinearDamping(linearDampingRate);
//        while (world.isLocked()){
//            Log.d("world","LOKED!");
//        }
        Body bodyTemp = world.createBody(bd);//在世界中创建刚体

        //Log.d("CircleBody",(bodyTemp==null)?"NULL":"NOT NULL!");
        CircleShape cs=new CircleShape();//创建刚体形状
        cs.m_radius=radius/RATE;//获得物理世界圆的半径
        FixtureDef fd=new FixtureDef();//创建刚体物理描述
        fd.density = density;//2.0f;//设置密度
        fd.friction = friction;//0.1f;//设置摩擦系数
        fd.restitution = restitution;//0.60f;//设置恢复系数
        fd.shape=cs;//设置形状
        bodyTemp.createFixture(fd);//将刚体物理描述与刚体结合
        this.body = bodyTemp;
    }
    @Override
    public void onPause(SharedPreferences.Editor editor){
        super.onPause(editor);
        editor.putFloat(id+"radius",radius);
    }
}
