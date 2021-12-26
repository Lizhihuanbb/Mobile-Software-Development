package com.jnu.booklistmainactivity.data;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.Random;

public class CirleSpriter {
    float x,y;
    double direction;
    Bitmap bitmap;
    int X[] = {150, 600, 1000};
    int Y= 800;

    public CirleSpriter(float x, float y, Bitmap bitmap)
    {
        this.x=x;
        this.y=y;
        this.bitmap=bitmap;
    }
    public void draw(Canvas canvas)
    {
        Random rand = new Random();
        int i;
        i= rand.nextInt(3);
        x=X[i];

        canvas.drawBitmap(bitmap,x,Y,null);
    }
    public void move()
    {
        this.x+=20*Math.cos(direction);
        this.y+=20*Math.sin(direction);
    }

    public boolean isShot(float touchedX, float touchedY) {
        return (touchedX > this.x && touchedX < this.x + 180) && (touchedY > this.y && touchedY < this.y + 180);
    }
}
