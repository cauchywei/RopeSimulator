package com.sssta.ropetest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;

public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    private SurfaceHolder holder;
    private Rope rope;



    public MySurfaceView(Context context,AttributeSet attr) {
        super(context,attr);
        holder=getHolder();
        holder.addCallback(this);

        List<Node> randomNode = new ArrayList<>();
        for (int i=0;i<550;++i){
            randomNode.add(new Node(getCanvasWidth()/2+i*2*Rope.SIZE,100));
        }
        randomNode.get(0).setStable(true);


        rope = new Rope(randomNode);

        SensorManager sensorMgr = (SensorManager)getContext().getSystemService(Context.SENSOR_SERVICE);

        SensorEventListener lsn = new SensorEventListener() {

            public void onSensorChanged(SensorEvent e) {

//                float x = e.values[SensorManager.DATA_X];
//
//                float y = e.values[SensorManager.DATA_Y];
//
//                float z = e.values[SensorManager.DATA_Z];
//
//                Node.GRIVATY.x = x*10;
//                Node.GRIVATY.y = y*10;
                
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
            }};
        sensorMgr.registerListener(lsn, sensorMgr.getDefaultSensor
                        (Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_UI);
    }

    public int getCanvasWidth(){
       return AppUtil.getScreenWidth()-2*MainApplication.getContext().getResources().getDimensionPixelSize(R.dimen.activity_horizontal_margin);
    }


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        new Thread(new MyLoop()).start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    public void doDraw(Canvas canvas) {

        super.draw(canvas);
        canvas.drawColor(Color.WHITE);//这里是绘制背景

        rope.draw(canvas);

    }

    public void updateLocation(){
        rope.updateLocation(0.2f);
    }

    Node holdNode;
    Vector dVector;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        if(event.getAction()==MotionEvent.ACTION_DOWN){
            for (Node node:rope.getNodes()){
                Vector startVector = new Vector(event.getX(),event.getY());
                dVector = node.getPosition().minus(startVector);
                if (node.contains(startVector)){
                    holdNode = node;
                    holdNode.setStable(true);
                    return true;
                }
            }
        }else if (event.getAction() == MotionEvent.ACTION_MOVE){
            if (holdNode!=null){
                Vector now = new Vector(event.getX(),event.getY());
                holdNode.setPosition(now.add(dVector));
            }
        }else if (event.getAction() == MotionEvent.ACTION_CANCEL || event.getAction() == MotionEvent.ACTION_UP){
            if (holdNode!=null) {
                holdNode.setStable(false);
                holdNode = null;
            }
        }
        return true;
    }

    class MyLoop implements Runnable{
        @Override
        public void run() {
            // TODO Auto-generated method stub
            while(true){
                try{
                    Canvas c=holder.lockCanvas();
                    updateLocation();
                    doDraw(c);
                    holder.unlockCanvasAndPost(c);
                    Thread.sleep(20);
                }catch(Exception e){

                }
            }
        }

    }

}