package com.sssta.ropetest;

/**
 * Created by cauchywei on 15/3/23.
 */
public class Vector {
    public float x,y;

    public Vector(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector() {
        //this(0,0);
    }

    public Vector add(Vector vector){
        return new Vector(vector.x+x,vector.y+y);
    }

    public void addQ(Vector vector){
        x+=vector.x;
        y+=vector.y;
    }

    public Vector minus(Vector vector){
        return new Vector(x-vector.x,y-vector.y);
    }

    public void minusQ(Vector vector){
       x-=vector.x;
       y-=vector.y;
    }

    public Vector div(float n){
        return new Vector(x/n,y/n);
    }

    public void divQ(float n){
       x/=n;
       y/=n;
    }

    public Vector times(float n){
        return new Vector(x*n,y*n);
    }

    public void timesQ(float n){
        x*=n;
        y*=n;
    }

    public float distance(Vector vector){
        return (float) Math.sqrt((x-vector.x)*(x-vector.x)+(y-vector.y)*(y-vector.y));
    }
}
