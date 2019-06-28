package com.moregood.yuezi.entity;

import com.moregood.yuezi.entity.Period.Type;

public class LunchAfter extends Period {

	public LunchAfter(int... dishIds) {
		super(dishIds);
		name=LUNCHAFTER;
		type = Type.LunchAfter;
	}

}
