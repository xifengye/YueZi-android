package com.moregood.yuezi.entity;

import java.util.ArrayList;
import java.util.List;

public class Period {
	public enum Type{
		Breakfast,BreakfastAfter,Lunch,LunchAfter,Supper,SupperAfter;
	}
	public static String BREAKFAST = "早餐";
	public static String BREAKFASTAFTER="早点";
	public static String LUNCH = "午餐";
	public static String LUNCHAFTER = "午点";
	public static String SUPPER = "晚餐";
	public static String SUPPERAFTER = "晚点";
	protected String name;
	protected List<Integer> dishes;

	protected Type type;

	public Type getType() {
		return type;
	}

	public Period(int... dishIds) {
		dishes = new ArrayList<Integer>();
		addDish(dishIds);
	}

	public void addDish(int... dishIds){
		for(int id:dishIds){
			dishes.add(id);
		}
	}

	public List<Integer> getDisheIds(){
		return dishes;
	}

	public String getName(){
		return name;
	}

}
