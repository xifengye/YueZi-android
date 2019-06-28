package com.moregood.yuezi.entity;

import java.util.List;

public class Dish extends BaseEntity{
	public String cookMethod;
	public List<SkuMaterial> materials;
	@Override
	public String toString() {
		return "Dish [id=" + id + ", name=" + name + ", image=" + image
				+ ", desc=" + desc + "cookMethod=" + cookMethod + ", materials=" + materialsToString()
				+ "]";
	}
	
	private String materialsToString(){
		StringBuilder builder = new StringBuilder();
		if(materials!=null){
			for(SkuMaterial sku:materials){
				builder.append(sku.toString());
			}
		}
		return builder.toString();
	}
	
}
