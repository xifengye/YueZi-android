package com.moregood.yuezi.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateUtil {
	public static int getDayCountOfMonth(int year, int month) {
		Calendar rightNow = Calendar.getInstance();
		SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy/MM");
		try {
			rightNow.setTime(simpleDate.parse(String.format("%d/%d", year,month))); // 要计算你想要的月份，改变这里即可
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return rightNow.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
}
