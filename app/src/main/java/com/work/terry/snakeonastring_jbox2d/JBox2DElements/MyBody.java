package com.work.terry.snakeonastring_jbox2d.JBox2DElements;

import android.content.SharedPreferences;

import com.work.terry.snakeonastring_jbox2d.UI.GameElements;
import com.work.terry.snakeonastring_jbox2d.Util.JBox2DUtil;
import com.work.terry.snakeonastring_jbox2d.Util.VectorUtil;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;

import static com.work.terry.snakeonastring_jbox2d.Util.Constant.RATE;

/**
 * Created by Terry on 2018/1/14.
 */

public abstract class MyBody extends GameElements
{
    public boolean destroyed = false;

    public World world;
    public Body body;//对应物理引擎中的刚体
    public MyBody(
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
                id,
                x,y,
                width,height,
                color,
                defaultHeight,
                topOffset,
                topOffsetColorFactor,
                heightColorFactor,
                floorShadowColorFactor,
                Img);
        JBox2DUtil.Bodies.add(this);
        this.world = world;
    }
    public Vec2 getBodyVelocityNormalized(){
        return VectorUtil.normalize2D(new Vec2(body.getLinearVelocity().x*100,body.getLinearVelocity().x*100));
    }
    public void setBodyVelocity(float vx,float vy){
        body.setLinearVelocity(new Vec2(vx/RATE,vy/RATE));
    }
    public void pushXYintoBody(){
        body.getPosition().set(this.x / RATE, this.y / RATE);
            //body.setTransform(new Vec2(this.x / RATE, this.y / RATE), 0);
    }
    public void popXYfromBody(){
        if(body==null)return;
        Vec2 v = getBodyXY();
        this.x = v.x;
        this.y = v.y;
    }
    public void setBodyXY(float setX,float setY){
        body.setTransform(new Vec2(setX/RATE,setY/RATE),0);
    }
    public Vec2 getBodyXY(){
        return new Vec2(body.getPosition().x * RATE, body.getPosition().y * RATE);
    }
    public void destroySelf(){
        if (!destroyed){
            world.destroyBody(body);
            destroyed = true;
        }
    }
    public int getId(){
        return Integer.parseInt(body.getUserData().toString().split(" ")[1]);
    }
    @Override
    public String toString(){
        return body.getUserData().toString()+"\n"
                +"bodyX = "+body.getPosition().x* RATE+" bodyY = "+body.getPosition().y*RATE
                +"\nvX = "+body.getLinearVelocity().x+" vY = "+body.getLinearVelocity().y;
    }
    @Override
    public void onPause(SharedPreferences.Editor editor){
        super.onPause(editor);
        if(body!=null){
            Vec2 v = getBodyXY();
            Vec2 vv = getBodyVelocityNormalized();
            editor.putFloat(body.getUserData().toString()+"x",v.x);
            editor.putFloat(body.getUserData().toString()+"y",v.y);
            editor.putFloat(body.getUserData().toString()+"vx",vv.x);
            editor.putFloat(body.getUserData().toString()+"vy",vv.y);
            editor.putFloat(body.getUserData().toString()+"width",width);
            editor.putFloat(body.getUserData().toString()+"height",height);
            editor.putFloat(id+"angle",body.getAngle());
            editor.putFloat(body.getUserData().toString()+"color",color);
        }
    }
    @Override
    public void onResume(){

    }
}