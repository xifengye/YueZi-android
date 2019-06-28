package com.moregood.yuezi.entity;

public class BreakfastAfter extends Period {

	public BreakfastAfter(int... dishIds) {
		super(dishIds);
		name=BREAKFASTAFTER;
		type = Type.BreakfastAfter;
	}
	

}
