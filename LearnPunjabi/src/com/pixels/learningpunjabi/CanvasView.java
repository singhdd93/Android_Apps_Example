package com.pixels.learningpunjabi;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class CanvasView extends View {
  private Paint paint = new Paint();
  private Path path = new Path();
  public boolean cc = false;
  final int screenSize = getResources().getConfiguration().screenLayout &
	        Configuration.SCREENLAYOUT_SIZE_MASK;
	final int smallScreen = Configuration.SCREENLAYOUT_SIZE_SMALL;
	final int normalScreen = Configuration.SCREENLAYOUT_SIZE_NORMAL;
	final int largeScreen = Configuration.SCREENLAYOUT_SIZE_LARGE;
	final int xlargeScreen = Configuration.SCREENLAYOUT_SIZE_XLARGE;
	final int undefScreen = Configuration.SCREENLAYOUT_LAYOUTDIR_UNDEFINED;
	
  public CanvasView(Context context, AttributeSet attrs) {
    super(context, attrs);

    paint.setAntiAlias(true);
    paint.setDither(true);
    if(screenSize == smallScreen)
    {
    	paint.setStrokeWidth(10f);
    }
    else if(screenSize == normalScreen)
    {
    	paint.setStrokeWidth(16f);
    }
    else if(screenSize == largeScreen)
    {
    	paint.setStrokeWidth(25f);
    }
    else if(screenSize == xlargeScreen)
    {
    	paint.setStrokeWidth(35f);
    }
    paint.setColor(Color.DKGRAY);
    paint.setStyle(Paint.Style.STROKE);
    paint.setStrokeJoin(Paint.Join.ROUND);
    paint.setStrokeCap(Paint.Cap.ROUND);
  }

  @SuppressLint("DrawAllocation")
@Override
  protected void onDraw(Canvas canvas) {
	  if(cc)
	  {
		  Log.d("Clear-Canavs", "Clear Canavs started");
		  //canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
		  //Paint paint = new Paint();
		  //paint.setXfermode(new PorterDuffXfermode(Mode.CLEAR));
		  //canvas.drawPaint(paint);
		  //paint.setXfermode(new PorterDuffXfermode(Mode.SRC));
		 // paint = new Paint ();
		  path  = new Path();
		  Paint clearPaint = new Paint();
		  clearPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
		  canvas.drawRect(0, 0, 0, 0, clearPaint); 
		  cc = false;
		  Log.d("Clear-Canavs", "Clear Canavs done");
	  }
	  else
	  {
		  
    canvas.drawPath(path, paint);
	  }
  }
  
  public void clearCanvas()
  {
	  cc = true;
	  invalidate();
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    float eventX = event.getX();
    float eventY = event.getY();

    switch (event.getAction()) {
    case MotionEvent.ACTION_DOWN:
      path.moveTo(eventX, eventY);
      return true;
    case MotionEvent.ACTION_MOVE:
      path.lineTo(eventX, eventY);
      break;
    case MotionEvent.ACTION_UP:
      // nothing to do
      break;
    default:
      return false;
    }

    // Schedules a repaint.
    invalidate();
    return true;
  }
} 