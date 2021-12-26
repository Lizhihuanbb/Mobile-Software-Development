package com.jnu.booklistmainactivity.view;


import static java.lang.Math.random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import androidx.annotation.NonNull;


import com.jnu.booklistmainactivity.R;
import com.jnu.booklistmainactivity.data.CirleSpriter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class GameView2 extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder surfaceHolder;

    public GameView2(Context context) {
        super(context);
        initView();
    }

    public GameView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public GameView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public GameView2(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        initView();
    }

    private void initView() {
        surfaceHolder=getHolder();
        surfaceHolder.addCallback(this);
    }

    private DrawThread drawThread;
    private boolean isTouched=false;
    private float touchedX=-1;
    private float touchedY=-1;
    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {

        drawThread = new DrawThread();
        drawThread.start();
        GameView2.this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if( MotionEvent.ACTION_DOWN==motionEvent.getAction())
                {
                    touchedX=motionEvent.getX();
                    touchedY=motionEvent.getY();
                    isTouched=true;
                }
                return false;
            }
        });
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
        drawThread.myStop();
        try {
            drawThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private class DrawThread extends Thread
    {
        private final ArrayList<CirleSpriter> spriters=new ArrayList<>();
        private boolean isStopped=false;
        public DrawThread()
        {
            Bitmap bitmap= BitmapFactory.decodeResource(getResources(), R.drawable.mouseeee);
            spriters.add(new CirleSpriter(77,800,bitmap));
        }

        public void myStop()
        {
            isStopped=true;
        }
        @Override
        public void run() {
            super.run();
            Canvas canvas = null;
            int hitCount=0;

            while(!isStopped) {
                try {
                    canvas = surfaceHolder.lockCanvas();

                    Bitmap mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.back);
                    //绘制画布的背景
                    Matrix matrix = new Matrix();
                    matrix.postScale(1.42f, 3.2f);
                    canvas.drawBitmap(mBitmap, matrix, null);
                    if(GameView2.this.isTouched)
                    {
                        for(int index=0;index<spriters.size();index++)
                        {
                            if(spriters.get(index).isShot(GameView2.this.touchedX,GameView2.this.touchedY)){
                                hitCount++;
                            }
                        }

                    }
//                    for(int index=0;index<spriters.size();index++)
//                    {
//                        spriters.get(index).move();
//                    }

                    Random rand = new Random();
                    int i = rand.nextInt(2) + 1;
                    for (int index = 0; index < i; index++) {
                        spriters.get(index).draw(canvas);
                    }

                    GameView2.this.isTouched=false;
                    Paint paint=new Paint();
                    paint.setTextSize(100);
                    paint.setColor(Color.GREEN);
                    canvas.drawText("hit "+hitCount,100,100,paint);


                } catch (Exception e) {

                } finally {
                    if (null != canvas) {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
