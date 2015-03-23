package com.sssta.ropetest;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cauchywei on 15/3/23.
 */
public class Rope implements Constants {

    Paint paint;

    private List<Node> nodes = new ArrayList<Node>();
    public static final int SIZE = 2;
    public static final int GAP = 1;


    public Rope() {

        initPaint();
    }

    public Rope(@NonNull List<Node> nodes) {

        this.nodes = nodes;
        if (nodes.size() == 0){
            throw new IllegalArgumentException("nodes size must > 0");
        }

        initNodes();
        initPaint();
    }

    private void initNodes(){
        Node pre = null;
        for (Node node:nodes){
            if (pre!=null)
                pre.setNext(node);
            node.setPre(pre);
            pre = node;
        }
    }

    private Node getNextNode(int index){

        if (index < 0 || index >= nodes.size() -1)
            return null;

        return nodes.get(index+1);
    }

    private Node getPreNode(int index){

        if (index <= 0 || index >= nodes.size())
            return null;
        return nodes.get(index-1);
    }

    private void initPaint(){
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);

    }

    public void draw(Canvas canvas){

        for (Node node:nodes){
            if (node.isStable()){
                paint.setColor(Color.BLACK);
            }else{
                paint.setColor(Color.RED);
            }

            canvas.drawCircle(node.getX()+SIZE, node.getY()+SIZE, SIZE, paint);
        }
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    public void updateLocation(float dt) {

//        for (Node node:nodes) {
//            node.setForce(new Vector());
//        }
        for (Node node:nodes) {
            node.implicated();
        }

        for (Node node:nodes){

            if (!node.isStable()){

                Node next = node.getNext();
                Node pre  = node.getPre();

                node.setForce(Node.GRIVATY.times(node.getMass()));

                node.addForce(node.getVelocity().times(-1).times(0.02f));


            }else{

            }

//            Vector ds = new Vector();
//
//            ds.y += GRAVITY;
//
//            Node pre = node.getPre();
//            if (pre!=null){
//                pre.implicated(node, ds);
//            }
//
//
//            if (!node.isStable()){
//                node.inc(ds);
//            }
        }

        for (Node node:nodes) {
            node.calulate(dt);
        }

        for (Node node:nodes) {
            if (!node.isStable()){
                Node preNode = node.getPre();
                if (preNode!=null){
                    float s = node.getPosition().distance(preNode.getPosition());
//                if (s>GAP){
//                    s = GAP;
//                }
                    float ds = 1-GAP/s;
//                    if(preNode.isStable()){
//
                        Vector dp = new Vector((node.getX()-preNode.getX())*ds,(node.getY()-preNode.getY())*ds);
                        node.getPosition().minusQ(dp);

                        preNode.getVelocity().addQ(dp.times(-0.05f));
//                    }else{
//                        Vector dp = new Vector((preNode.getX()-node.getX())*ds,(preNode.getY()-node.getY())*ds);
//                        preNode.getPosition().minusQ(dp);
////
////                       // node.getVelocity().addQ(dp.times(-0.05f));
////
//                    }

//                    Vector dp = new Vector((node.getX()-preNode.getX())*ds,(node.getY()-preNode.getY())*ds);
//                    node.getPosition().minusQ(dp);
//                    preNode.getVelocity().addQ(dp.times(-0.05f));
                }



                Node nextNode = node.getNext();
                if (nextNode!=null){
                    float s = node.getPosition().distance(nextNode.getPosition());
//                if (s>GAP){
//                    s = GAP;
                    float ds = 1-GAP/s;

                    if (nextNode.isStable()){
//                        Vector dp = new Vector((nextNode.getX()-node.getX())*ds,(nextNode.getY()-node.getY())*ds);
//                        nextNode.getPosition().minusQ(dp);
                    }else{
//                        Vector dp = new Vector((node.getX()-nextNode.getX())*ds,(node.getY()-nextNode.getY())*ds);
//                        nextNode.getPosition().minusQ(dp);
                    }


//                    nextNode.getVelocity().addQ(dp.times(-0.05f));
                }
            }
        }

    }
}
