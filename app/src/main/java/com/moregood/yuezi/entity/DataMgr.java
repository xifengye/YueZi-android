package com.moregood.yuezi.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;

import com.moregood.yuezi.utils.AssetsUtils;

public class DataMgr {
	Map<Integer, Dish> maps = null;
	Map<Integer, Material> materialList = null;
	Map<Integer,Float> materialAmountMap = null;
	
	private class MaterialComparator implements Comparator<Material> {

		@Override
		public int compare(Material object1, Material object2) {
			if (object1.id > object2.id)
				return 1;
			else if (object1.id < object2.id)
				return -1;
			return 0;
		}

	}
	
	public Map<Integer,Float> getMaterialAmountMap(){
		if(materialAmountMap==null){
			materialAmountMap = new HashMap<Integer, Float>();
			for(int i=0;i<28;i++){
				OneDay oneDay = getOneDay(i);
				for(Period period:oneDay.getPeriods()){
					for(int dishId:period.getDisheIds()){
						Dish dish = getDishById(dishId);
						for(SkuMaterial sku:dish.materials){
							float value = sku.getAmount();
							if(materialAmountMap.containsKey(sku.material.id)){
								if(value<0.01f){
									materialAmountMap.put(sku.material.id,-1f);
								}else{
									
									materialAmountMap.put(sku.material.id, materialAmountMap.get(sku.material.id)+value);
								}
							}else{
								materialAmountMap.put(sku.material.id, value);
							}
						}
					}
				}
			}
		}
		return materialAmountMap;
	}
	
	public ArrayList<Material> getMaterialList() {
		ArrayList<Material> materials = new ArrayList<Material>();
		for(int id:materialList.keySet()){
			materials.add(materialList.get(id));
		}
		Collections.sort(materials, new MaterialComparator());
		return materials;
	}

	public Map<Integer, Dish> getMaps() {
		return maps;
	}
	
	public Dish getDishById(int id){
		return maps.get(id);
	}
	public Material getMaterialById(int id){
		return materialList.get(id);
	}
	
	public ArrayList<Dish> getDishes(){
		ArrayList<Dish> dishes = new ArrayList<Dish>();
		for (Integer id : maps.keySet()) {
			dishes.add(maps.get(id));
		}
		return dishes;
	}

	private static DataMgr instance = new DataMgr();

	public static DataMgr getInstance() {
		return instance;
	}
	
	public void initData(Context context,Map<Integer, Material> materialList) throws Exception{
		this.materialList = materialList;
			maps = AssetsUtils.parseDishFromAssets(context,
					materialList);
	}
	
	public OneDay getOneDay(int position){
		OneDay oneDay = new OneDay(position);
		Period breakfast = null;
		Period breakfastAfter= null;
		Period lunch= null;
		Period lunchAfter= null;
		Period supper= null;
		Period supperAfter= null;
		switch (position+1) {
		case 1:
			
			breakfast = new Breakfast(41,2,8,7,42);
			breakfastAfter = new BreakfastAfter(6,45);
			
			lunch = new Lunch(41,3,8,42);
			lunchAfter = new LunchAfter(7,5);
			
			supper = new Supper(41,15,42);
			supperAfter = new SupperAfter(6,45);
			
			break;
		case 2:
			breakfast = new Breakfast(41,2,9,7,42);
			breakfastAfter = new BreakfastAfter(6,45);
			
			lunch = new Lunch(41,4,9,42);
			lunchAfter = new LunchAfter(6,5);
			
			supper = new Supper(41,13,42);
			supperAfter = new SupperAfter(7,45);
			
			break;
		case 3:
			breakfast = new Breakfast(41,2,10,7,42);
			breakfastAfter = new BreakfastAfter(6,45);
			
			lunch = new Lunch(41,3,10,42);
			lunchAfter = new LunchAfter(7,5);
			
			supper = new Supper(41,12,42);
			supperAfter = new SupperAfter(6,45);
			
			break;
		case 4:
			breakfast = new Breakfast(41,2,8,7,42);
			breakfastAfter = new BreakfastAfter(6,45);
			
			lunch = new Lunch(41,18,8,42);
			lunchAfter = new LunchAfter(6,5);
			
			supper = new Supper(41,17,42);
			supperAfter = new SupperAfter(7,45);
			 break;
		case 5:
			breakfast = new Breakfast(41,2,11,7,42);
			breakfastAfter = new BreakfastAfter(6,45);
			
			lunch = new Lunch(41,21,11,42);
			lunchAfter = new LunchAfter(7,5);
			
			supper = new Supper(41,14,42);
			supperAfter = new SupperAfter(6,45);
			break;
		case 6:
			breakfast = new Breakfast(41,2,9,7,42);
			breakfastAfter = new BreakfastAfter(6,45);
			
			lunch = new Lunch(41,19,9,42);
			lunchAfter = new LunchAfter(6,5);
			
			supper = new Supper(41,46,42);
			supperAfter = new SupperAfter(7,45);
			
			break;
		case 7:
			breakfast = new Breakfast(41,2,8,7,42);
			breakfastAfter = new BreakfastAfter(6,45);
			
			lunch = new Lunch(41,20,8,42);
			lunchAfter = new LunchAfter(7,5);
			
			supper = new Supper(41,16,42);
			supperAfter = new SupperAfter(6,45);
			break;
			
			
			

		case 8:
			breakfast = new Breakfast(41,22,8,7,42);
			breakfastAfter = new BreakfastAfter(6,45);
			
			lunch = new Lunch(41,30,8,32,42);
			lunchAfter = new LunchAfter(28,5);
			
			supper = new Supper(41,15,32,42);
			supperAfter = new SupperAfter(6,45);
			break;
		case 9:
			breakfast = new Breakfast(41,23,9,7,42);
			breakfastAfter = new BreakfastAfter(6,45);
			
			lunch = new Lunch(41,27,9,32,42);
			lunchAfter = new LunchAfter(28,5);
			
			supper = new Supper(41,13,32,42);
			supperAfter = new SupperAfter(6,45);
			break;
		case 10:
			breakfast = new Breakfast(41,26,10,7,42);
			breakfastAfter = new BreakfastAfter(6,45);
			
			lunch = new Lunch(41,29,10,32,42);
			lunchAfter = new LunchAfter(28,5);
			
			supper = new Supper(41,12,32,42);
			supperAfter = new SupperAfter(6,45);
			break;
		case 11:
			breakfast = new Breakfast(41,22,8,7,42);
			breakfastAfter = new BreakfastAfter(6,45);
			
			lunch = new Lunch(41,19,8,32,42);
			lunchAfter = new LunchAfter(28,5);
			
			supper = new Supper(41,17,32,42);
			supperAfter = new SupperAfter(6,45);
			break;
		case 12:
			breakfast = new Breakfast(41,25,11,7,42);
			breakfastAfter = new BreakfastAfter(6,45);
			
			lunch = new Lunch(41,21,11,32,42);
			lunchAfter = new LunchAfter(28,5);
			
			supper = new Supper(41,14,32,42);
			supperAfter = new SupperAfter(6,45);
			break;
		case 13:
			breakfast = new Breakfast(41,24,9,7,42);
			breakfastAfter = new BreakfastAfter(6,45);
			
			lunch = new Lunch(41,23,9,32,42);
			lunchAfter = new LunchAfter(28,5);
			
			supper = new Supper(41,46,32,42);
			supperAfter = new SupperAfter(6,45);
			
			break;
		case 14:
			breakfast = new Breakfast(41,22,8,7,42);
			breakfastAfter = new BreakfastAfter(6,45);
			
			lunch = new Lunch(41,20,8,32,42);
			lunchAfter = new LunchAfter(28,5);
			
			supper = new Supper(41,16,32,42);
			supperAfter = new SupperAfter(6,45);
			break;
			
			
			
			
		case 15:
		case 22:
			breakfast = new Breakfast(33,8,7,42);
			breakfastAfter = new BreakfastAfter(6,45);
			
			lunch = new Lunch(30,28,32,42);
			lunchAfter = new LunchAfter(40,47);
			
			supper = new Supper(15,8,32,42);
			supperAfter = new SupperAfter(7,45);
			break;
		case 16:
		case 23:
			breakfast = new Breakfast(33,9,7,42);
			breakfastAfter = new BreakfastAfter(6,45);
			
			lunch = new Lunch(38,28,32,42);
			lunchAfter = new LunchAfter(40,47);
			
			supper = new Supper(13,9,32,42);
			supperAfter = new SupperAfter(7,45);
			break;
		case 17:
		case 24:
			breakfast = new Breakfast(33,10,7,42);
			breakfastAfter = new BreakfastAfter(7,45);
			
			lunch = new Lunch(37,28,32,42);
			lunchAfter = new LunchAfter(40,47);
			
			supper = new Supper(12,10,32,42);
			supperAfter = new SupperAfter(6,45);
			break;
		case 18:
		case 25:
			breakfast = new Breakfast(33,8,7,42);
			breakfastAfter = new BreakfastAfter(6,45);
			
			lunch = new Lunch(31,28,32,42);
			lunchAfter = new LunchAfter(40,47);
			
			supper = new Supper(17,8,32,42);
			supperAfter = new SupperAfter(7,45);
			break;
		case 19:
		case 26:
			breakfast = new Breakfast(33,11,7,42);
			breakfastAfter = new BreakfastAfter(7,45);
			
			lunch = new Lunch(36,28,32,42);
			lunchAfter = new LunchAfter(40,47);
			
			supper = new Supper(14,11,32,42);
			supperAfter = new SupperAfter(6,45);
			break;
		case 20:
		case 27:
			breakfast = new Breakfast(33,9,7,42);
			breakfastAfter = new BreakfastAfter(6,45);
			
			lunch = new Lunch(34,28,32,42);
			lunchAfter = new LunchAfter(40,47);
			
			supper = new Supper(46,9,32,42);
			supperAfter = new SupperAfter(7,45);
			
			break;
		case 21:
		case 28:
			breakfast = new Breakfast(33,8,7,42);
			breakfastAfter = new BreakfastAfter(7,45);
			
			lunch = new Lunch(35,28,32,42);
			lunchAfter = new LunchAfter(40,47);
			
			supper = new Supper(16,8,32,42);
			supperAfter = new SupperAfter(6,45);
			break;
		default:
			break;
		}
		oneDay.addPeriod(breakfast,breakfastAfter,lunch,lunchAfter,supper,supperAfter);
		return oneDay;
	}
	
	

}
