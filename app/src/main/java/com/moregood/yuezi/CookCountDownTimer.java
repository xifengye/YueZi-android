package com.moregood.yuezi;

import java.text.DecimalFormat;

import android.os.CountDownTimer;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.TextView;

public class CookCountDownTimer extends CountDownTimer {
	private CountDownListener listener;
	private TextView view;

	public CookCountDownTimer(TextView view,long millisInFuture,CountDownListener listener) {
		this(millisInFuture, 1000);
		this.listener = listener;
		this.view = view;
		view.setVisibility(View.VISIBLE);
	}

	private CookCountDownTimer(long millisInFuture, long countDownInterval) {
		super(millisInFuture, countDownInterval);
	}

	@Override
	public void onTick(long millisUntilFinished) {
		long time = 7200000 - millisUntilFinished;
		if (view != null) {
			int h = (int) time / 3600000;
			int m = (int) (time - 3600000 * h) / 60000;
			int s = (int) (time - (3600000 * h + m * 60000)) / 1000;
			//int ms = (int) (millisUntilFinished - (3600000 * h + m * 60000 + s * 1000)) / 100;
			String text = "煮了 %d 分 %02d 秒";
			view.setText(String.format(text, m, s));
			if(h>0){
				text = "煮了 %d 小时 %d 分 %02d 秒";
				view.setText(String.format(text, h, m, s));
			}
		}
	}

	@Override
	public void onFinish() {
		if(listener!=null){
			listener.onCountDownFinish();
		}
	}

	public interface CountDownListener{
		public void onCountDownFinish() ;
	}
}
