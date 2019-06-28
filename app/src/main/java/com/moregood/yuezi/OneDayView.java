package com.moregood.yuezi;

import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;

import com.moregood.yuezi.entity.Period;

public class OneDayView extends View {
	private OnItemClickListener listener;
	String period;
	String dishes[];
	
	private int dishWidth;
	private int dishHeight;
	private int periodWidth;
	private int periodHeight;
	
	private Rect periodTextBounds;
	private Rect dishTextBounds[];
	
	
	private Paint periodPaint;
	private Paint textPaint;
	private Paint rectPaint;
	
	class MyOnGestureListener extends SimpleOnGestureListener{

		@Override
		public boolean onSingleTapUp(MotionEvent e) {
			for(int i=0;i<dishTextBounds.length;i++){
				if(dishTextBounds[i].contains((int)e.getX(), (int)e.getY()) && listener!=null){
					List<Integer> ids = (List<Integer>)getTag();
					listener.onItemClick(null, OneDayView.this, i, ids.get(i));
				}
			}
			return super.onSingleTapUp(e);
		}

		@Override
		public boolean onDown(MotionEvent e) {
			return true;
		}

		
		
		
		
	}
	
	 private GestureDetector mGestureDetector; 

	public OneDayView(Context context,Period period,String[] dishes) {
		super(context);
		this.dishes = dishes;
		mGestureDetector = new GestureDetector(new MyOnGestureListener());
		init(context, period);
	}
	
	private void init(Context context,Period period){
		periodPaint = new Paint();
		periodPaint.setColor(Color.WHITE);
		periodPaint.setFakeBoldText(true);
		periodPaint.setTextSize(context.getResources().getDimension(R.dimen.dinner_view_period_textsize));
		
		rectPaint = new Paint();
		
		textPaint = new Paint();
		textPaint.setFakeBoldText(true);
		textPaint.setTextSize(context.getResources().getDimension(R.dimen.dinner_view_dish_textsize));
		
		switch (period.getType()) {
		case Breakfast:
			this.period = Period.BREAKFAST;
			rectPaint.setColor(context.getResources().getColor(R.color.breakfast));
			textPaint.setColor(context.getResources().getColor(R.color.breakfast));
			break;
		case BreakfastAfter:
			this.period = Period.BREAKFASTAFTER;
			rectPaint.setColor(context.getResources().getColor(R.color.breakfast_after));
			textPaint.setColor(context.getResources().getColor(R.color.breakfast_after));
			break;
		case Lunch:
			this.period = Period.LUNCH;
			rectPaint.setColor(context.getResources().getColor(R.color.lunch));
			textPaint.setColor(context.getResources().getColor(R.color.lunch));
			break;
		case LunchAfter:
			this.period = Period.LUNCHAFTER;
			rectPaint.setColor(context.getResources().getColor(R.color.lunch_after));
			textPaint.setColor(context.getResources().getColor(R.color.lunch_after));
			break;
		case Supper:
			this.period = Period.SUPPER;
			rectPaint.setColor(context.getResources().getColor(R.color.supper));
			textPaint.setColor(context.getResources().getColor(R.color.supper));
			break;
		case SupperAfter:
			this.period = Period.SUPPERAFTER;
			rectPaint.setColor(context.getResources().getColor(R.color.supper_after));
			textPaint.setColor(context.getResources().getColor(R.color.supper_after));
			break;
		}
		
		periodWidth = (int)context.getResources().getDimension(R.dimen.dinner_view_period_width);
		dishHeight = (int)context.getResources().getDimension(R.dimen.dinner_view_dish_height);;
		periodHeight = dishHeight*dishes.length;
		periodTextBounds = new Rect(0, 0, periodWidth, periodHeight);
		dishTextBounds = new Rect[dishes.length];
		for(int i=0;i<dishes.length;i++){
			dishTextBounds[i] = new Rect();
		}
	}
	
	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
		dishWidth = getWidth()- periodWidth;
		for(int i=0;i<dishes.length;i++){
			dishTextBounds[i].set(periodWidth, dishHeight*i, (periodWidth+dishWidth), dishHeight*(1+i));
		}
		drawPeriod(canvas);
		for(int i=0;i<dishes.length;i++){
			drawTextWithBorder(canvas , dishes[i], dishTextBounds[i]);
		}
	}
	
	private void drawPeriod(Canvas canvas){
		if(period == null)
			return;
		canvas.drawRect(periodTextBounds, rectPaint);
		canvas.drawLine(periodTextBounds.left, periodTextBounds.top, periodTextBounds.right, periodTextBounds.top, textPaint);
		canvas.drawLine(periodTextBounds.left, periodTextBounds.top, periodTextBounds.left, periodTextBounds.bottom, textPaint);
		canvas.drawLine(periodTextBounds.right, periodTextBounds.top, periodTextBounds.right, periodTextBounds.bottom, textPaint);
		canvas.drawLine(periodTextBounds.left, periodTextBounds.bottom, periodTextBounds.right, periodTextBounds.bottom, textPaint);
		Rect rc = new Rect();
		periodPaint.getTextBounds(period, 0, period.length(), rc);
		canvas.drawText(period, periodTextBounds.centerX()-rc.width()/2, periodTextBounds.centerY()+rc.height()/2, periodPaint);
	}
	
	
	private void drawTextWithBorder(Canvas canvas,String text,Rect rect) {
		if(text == null)
			return;
		canvas.drawLine(rect.left, rect.top, rect.right, rect.top, textPaint);
		canvas.drawLine(rect.left, rect.top, rect.left, rect.bottom, textPaint);
		canvas.drawLine(rect.right, rect.top, rect.right, rect.bottom, textPaint);
		canvas.drawLine(rect.left, rect.bottom, rect.right, rect.bottom, textPaint);
		Rect rc = new Rect();
		textPaint.getTextBounds(text, 0, text.length(), rc);
		canvas.drawText(text, rect.centerX()-rc.width()/2, rect.centerY()+rc.height()/2, textPaint);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		return mGestureDetector.onTouchEvent(event);
	}
	
	public void setOnItemClickListener(OnItemClickListener listener){
		this.listener = listener;
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);
		
		int chosenWidth = chooseWidthDimension(widthMode, widthSize);
		int chosenHeight = chooseHeightDimension(heightMode, heightSize);
		setMeasuredDimension(chosenWidth, chosenHeight);
	}
	
	private int chooseWidthDimension(int mode, int size) {
		if (mode == MeasureSpec.EXACTLY) {
			return size;
		} else { 
			return periodWidth+dishWidth;
		} 
	}
	
	private int chooseHeightDimension(int mode, int size) {
		if (mode == MeasureSpec.EXACTLY) {
			return size;
		} else { 
			return dishHeight*dishes.length;
		} 
	}
	

}
