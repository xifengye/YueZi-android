package com.moregood.yuezi.entity;

import com.moregood.yuezi.entity.Period.Type;

public class Lunch extends Period {

	public Lunch(int... dishIds) {
		super(dishIds);
		name=LUNCH;
		type = Type.Lunch;
	}

}
