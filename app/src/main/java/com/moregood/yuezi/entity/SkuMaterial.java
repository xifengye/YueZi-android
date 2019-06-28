package com.moregood.yuezi.entity;

public class SkuMaterial {
	public Material material;
	
	private float amount;
	
	public SkuMaterial(Material material, float amount) {
		super();
		this.material = material;
		this.amount = amount;
	}
	
	public float getAmount(){
		return amount;
	}
	
	
	

	@Override
	public String toString() {
		return "SkuMaterial [material=" + material + ", amount=" + amount + "]";
	}
	
	
}
