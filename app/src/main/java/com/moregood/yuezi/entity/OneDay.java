package com.moregood.yuezi.entity;

import java.util.ArrayList;
import java.util.List;

public class OneDay {
	private List<Period> periods;
	public static enum Status{
		READY,COOKING,COMPLETE
	}
	
	private int howDay;
	
	public void addPeriod(Period...periodArr){
		for(Period period:periodArr){
			periods.add(period);
		}
	}

	public OneDay(int howDay) {
		this.howDay = howDay;
		periods = new ArrayList<Period>();
	}
	
	public List<Period> getPeriods(){
		return periods;
	}
}
