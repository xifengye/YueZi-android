package com.moregood.yuezi.entity;

import com.moregood.yuezi.entity.Period.Type;

public class SupperAfter extends Period {

	public SupperAfter(int... dishIds) {
		super(dishIds);
		name=SUPPERAFTER;
		type = Type.SupperAfter;
	}

}
