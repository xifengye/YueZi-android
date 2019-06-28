package com.moregood.yuezi.entity;

import com.moregood.yuezi.entity.Period.Type;

public class Supper extends Period {

	public Supper(int... dishIds) {
		super(dishIds);
		name=SUPPER;
		type = Type.Supper;
	}

}
