package com.moregood.yuezi;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.moregood.yuezi.CookCountDownTimer.CountDownListener;
import com.moregood.yuezi.entity.BitmapCache;
import com.moregood.yuezi.entity.DataMgr;
import com.moregood.yuezi.entity.Dish;
import com.moregood.yuezi.entity.Material;
import com.moregood.yuezi.entity.SkuMaterial;
import com.moregood.yuezi.utils.PopWindowUtil;
import com.moregood.yuezi.utils.SharedPreferencesUtil;

public class DishActivity extends Activity implements CountDownListener, OnLongClickListener, OnItemClickListener{

	public static final long COUNTER_DEFAULT_TIME = 7200000;

	DecimalFormat df = new DecimalFormat("#.#");
	Button btnCook;
	Dish dish;
	CookCountDownTimer countDownTimer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dish);
		int id =(int) getIntent().getLongExtra("dish_id", 0);
		dish = DataMgr.getInstance().getDishById(id);
		setTitle(dish.name);
		TextView tvDishDesc = (TextView)findViewById(R.id.dish_desc);
		tvDishDesc.setText(dish.desc);
		ListView lvMaterials = (ListView)findViewById(R.id.dish_material_list);
		lvMaterials.setOnItemClickListener(this);
		List<Map<String, String>> data = new ArrayList<Map<String,String>>();
		for(SkuMaterial sku:dish.materials){
			Map<String, String> map = new HashMap<String, String>();
			map.put("image", String.format("%s/%s", Material.class.getSimpleName(),
					sku.material.image));
			map.put("material_name",sku.material.name);
			map.put("unit", sku.getAmount()<0.01?"适量":df.format(sku.getAmount())+sku.material.unit);
			data.add(map);
		}
		SimpleAdapter adapter = new SimpleAdapter(this,data ,R.layout.listitem_dish_material,new String[]{"image","material_name","unit"},new int[]{R.id.imageView1,R.id.textView1,R.id.textView2});
		adapter.setViewBinder(new SimpleAdapter.ViewBinder() {
			@Override
			public boolean setViewValue(View view, Object data, String textRepresentation) {
				switch (view.getId()) {
					case R.id.imageView1:
						ImageView icon = (ImageView)view;
						Bitmap bmp = BitmapCache.getInstance().getBitmap((String)data, getApplicationContext());
						if (bmp != null) {
							icon.setImageBitmap(bmp);
						} else {
							icon.setImageResource(R.mipmap.unload);
						}
						break;

					default:
						((TextView)view).setText((String)data);
						break;

				}
				return true;
			}
		});
		lvMaterials.setAdapter(adapter );
		LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(-2,((int)getResources().getDimension(R.dimen.listview_item_height))*adapter.getCount());
		param.leftMargin = (int)getResources().getDimension(R.dimen.activity_horizontal_margin);
		param.rightMargin = (int)getResources().getDimension(R.dimen.activity_horizontal_margin);
		lvMaterials.setLayoutParams(param);

		TextView tvDishCookStep = (TextView)findViewById(R.id.dish_cook_step);
		tvDishCookStep.setText(dish.cookMethod);

		btnCook = (Button)findViewById(R.id.cook_begin);
		countTimer();

		btnCook.setOnLongClickListener(this);
	}

	public void onBeginCookClicked(View view){
		fristCountTimer();
	}

	private void countTimer(){
		long time = SharedPreferencesUtil.getInstance().getLong(Dish.class.getSimpleName()+dish.id);
		if(time>0){
			if(System.currentTimeMillis()-time>COUNTER_DEFAULT_TIME){
				saveTime(0);
			}else{
				startCounterTime(time);
			}
		}
	}

	private void saveTime(long time){
		SharedPreferencesUtil.getInstance().put(Dish.class.getSimpleName()+dish.id, time);
	}

	private void startCounterTime(long time){
		if(countDownTimer==null){
			countDownTimer = new CookCountDownTimer(btnCook,COUNTER_DEFAULT_TIME - (System.currentTimeMillis()-time) , null);
			countDownTimer.start();
		}
	}

	public void fristCountTimer(){
		long time = SharedPreferencesUtil.getInstance().getLong(Dish.class.getSimpleName()+dish.id);
		if(time==0){
			time = System.currentTimeMillis();
			saveTime( time);
		}
		startCounterTime(time);
	}

	@Override
	public void onCountDownFinish() {
		btnCook.setText(R.string.cook_begin);
		countDownTimer.cancel();
		countDownTimer = null;
	}

	@Override
	public boolean onLongClick(View v) {
		saveTime(0);
		onCountDownFinish();

		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
							long id) {
		PopupWindow popWindow = PopWindowUtil.getImageViewPopWindows(this, String.format("%s/%s",Material.class.getSimpleName(), dish.materials.get(position).material.image));
		popWindow.showAsDropDown(view,300,0);

	}
}
