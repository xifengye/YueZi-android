package com.moregood.yuezi.entity;

public class Breakfast extends Period {

	public Breakfast(int... dishIds) {
		super(dishIds);
		name=BREAKFAST;
		type = Type.Breakfast;
		//dishes.add(object)
	}
	

}
