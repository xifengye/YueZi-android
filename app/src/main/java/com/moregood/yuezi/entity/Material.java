package com.moregood.yuezi.entity;

public class Material extends BaseEntity{
	public String unit;
	
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public boolean tobuy ;
	
	public boolean isToBuy(){
		return tobuy;
	}
	@Override
	public String toString() {
		return "Material [id=" + id + ", name=" + name + ", image=" + image
				+ ", desc=" + desc + ", tobuy=" + tobuy + "]";
	}
	
	
	
	
	
}
