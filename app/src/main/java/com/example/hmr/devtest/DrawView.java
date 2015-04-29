package com.example.hmr.devtest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


/**
 * Created by hmr on 27-04-2015.
 */
public class DrawView extends View {
    private static final int DEFAULT_BRUSH_COLOR= Color.RED;
    private static final int DEFAULT_PATTERN_COLOR= Color.BLACK;
    private static final Paint.Style DEFAULT_BRUSH_STYLE=Paint.Style.STROKE;
    private Paint paint = new Paint(Paint.DITHER_FLAG);
    private Paint paint1 = new Paint(Paint.DITHER_FLAG);
    private Path path = new Path();
    private boolean firstDraw;

    public DrawView(Context context, AttributeSet attributes){
        super(context, attributes);

        paint1.setAntiAlias(true);
        paint1.setColor(DEFAULT_PATTERN_COLOR);
        paint1.setStrokeJoin(Paint.Join.ROUND);
        paint1.setStyle(DEFAULT_BRUSH_STYLE);
        paint1.setStrokeWidth(0);

        paint.setAntiAlias(true);
        paint.setColor(DEFAULT_BRUSH_COLOR);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStyle(DEFAULT_BRUSH_STYLE);
        paint.setStrokeWidth(5f);
        firstDraw = true;
    }

    @Override
    protected void onDraw(Canvas canvas){
        if(firstDraw){
           // for(int i = 0; i < 100; i+=20) {
             //   path.addRect(0, i, 20, 20, Path.Direction.CCW);

               // canvas.drawPath(path, paint1);
            //}
            firstDraw = false;
            return;
        }
        canvas.drawPath(path,paint);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        float xPos = event.getX();
        float yPos = event.getY();

        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                path.moveTo(xPos,yPos);
                return true;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(xPos,yPos);
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                return false;
        }
        invalidate();
        return true;
    }
}
