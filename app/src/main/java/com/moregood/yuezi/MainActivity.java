/*
 * Copyright (C) 2013 Andreas Stuetz <andreas.stuetz@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.moregood.yuezi;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.moregood.yuezi.entity.DataMgr;
import com.moregood.yuezi.entity.Dish;
import com.moregood.yuezi.entity.Material;
import com.moregood.yuezi.entity.OneDay;
import com.moregood.yuezi.entity.Period;
import com.moregood.yuezi.entity.SkuMaterial;
import com.moregood.yuezi.utils.AssetsUtils;
import com.moregood.yuezi.utils.DateUtil;
import com.moregood.yuezi.utils.PopWindowUtil;
import com.moregood.yuezi.utils.SharedPreferencesUtil;
import com.moregood.yuezi.view.PagerSlidingTabStrip;

public class MainActivity extends FragmentActivity implements
		OnItemClickListener, OnClickListener {

	private PagerSlidingTabStrip tabs;
	private ViewPager pager;
	private MyPagerAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		try {
			Map<Integer, Material> materialList = AssetsUtils.parseFromAssets(
					getApplicationContext(), Material.class, "Material");
			DataMgr.getInstance().initData(getApplicationContext(),
					materialList);
		} catch (Exception e) {
			e.printStackTrace();
		}

		setContentView(R.layout.activity_main);

		tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
		pager = (ViewPager) findViewById(R.id.pager);

		String beginDay = SharedPreferencesUtil.getInstance().getString(
				"beginDay");
		boolean save = false;
		if ("".equals(beginDay)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd");
			beginDay = sdf.format(new Date(System.currentTimeMillis()));
			save = true;
		}

		String[] ymd = beginDay.split(":");
		int year = Integer.valueOf(ymd[0]);
		int month = Integer.valueOf(ymd[1]);
		int day = Integer.valueOf(ymd[2]);
		onBeginDayChange(year, month, day,save);

	}

	public void setAdatper(ArrayList<String> items) {
		adapter = new MyPagerAdapter(getSupportFragmentManager(), items);

		pager.setAdapter(adapter);

		final int pageMargin = (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
						.getDisplayMetrics());
		pager.setPageMargin(pageMargin);

		tabs.setViewPager(pager);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
	}

	public class MyPagerAdapter extends FragmentPagerAdapter {

		ArrayList<String> items = null;;

		public MyPagerAdapter(FragmentManager fm, ArrayList<String> items) {
			super(fm);
			this.items = items;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return items.get(position);
		}

		@Override
		public int getCount() {
			return items.size();
		}

		@Override
		public Fragment getItem(int position) {
			switch (position) {
				case 0:
					return SettingFragment.newInstance();
				case 1:
					return MaterialListFragment.newInstance();
				case 2:
					return DishListFragment.newInstance();

				default:
					return OneDayFragment.newInstance(position - 3);
			}

		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
							long id) {
		Intent intent = new Intent(this, DishActivity.class);
		intent.putExtra("dish_id", id);
		startActivity(intent);
	}

	@Override
	public void onClick(View v) {
		OneDay day = (OneDay) v.getTag();
		if (day == null) {
			return;
		}

		Map<Integer, Float> data = new HashMap<Integer, Float>();
		for (Period period : day.getPeriods()) {
			for (int id : period.getDisheIds()) {
				Dish dish = DataMgr.getInstance().getDishById(id);
				for (SkuMaterial sku : dish.materials) {
					if (data.containsKey(sku.material.id)) {
						data.put(sku.material.id, data.get(sku.material.id)
								+ sku.getAmount());
					} else {
						data.put(sku.material.id, sku.getAmount());
					}
				}
			}
		}
		MyAdapter adapter = new MyAdapter(data);
		PopupWindow popupWindow = PopWindowUtil
				.getListPopWindows(this, adapter);
		popupWindow.showAsDropDown(v);
	}

	private class MyAdapter extends BaseAdapter {

		Map<Integer, Float> data;
		Integer materialIds[];
		DecimalFormat df = new DecimalFormat("#.#");

		public MyAdapter(Map<Integer, Float> data) {
			this.data = data;
			materialIds = new Integer[data.size()];
			data.keySet().toArray(materialIds);
		}

		@Override
		public int getCount() {
			return data.size();
		}

		@Override
		public Object getItem(int position) {
			return data.get(materialIds[position]);
		}

		@Override
		public long getItemId(int position) {
			return materialIds[position];
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Holder holder = null;
			int id = materialIds[position];
			Material material = DataMgr.getInstance().getMaterialById(id);
			if (convertView == null) {
				holder = new Holder();
				convertView = LayoutInflater.from(getApplicationContext())
						.inflate(R.layout.pop_listitem_material, null);
				holder.name = (TextView) convertView
						.findViewById(R.id.textView1);
				holder.amount = (TextView) convertView
						.findViewById(R.id.textView2);
				convertView.setTag(holder);
			} else {
				holder = (Holder) convertView.getTag();
			}
			if (material.isToBuy()) {
				holder.name.setTextColor(Color.RED);
				holder.amount.setTextColor(Color.RED);
			} else {
				holder.name.setTextColor(Color.WHITE);
				holder.amount.setTextColor(Color.WHITE);
			}
			holder.name.setText(material.name);
			holder.amount.setText(data.get(id) < 0.01 ? "适量" : df.format(data
					.get(id)) + material.unit);
			return convertView;
		}

		class Holder {
			TextView name;
			TextView amount;
		}
	}

	@Override
	protected void onStop() {
		super.onStop();
		SharedPreferencesUtil.getInstance().put("current_page",
				pager.getCurrentItem());
	}

	@Override
	protected void onResume() {
		super.onResume();
		pager.setCurrentItem(
				SharedPreferencesUtil.getInstance().getInt("current_page"),
				true);
	}

	public void onBeginDayChange(int year, int month, int dayOfMonth,boolean save) {
		if(save){
			SharedPreferencesUtil.getInstance().put("beginDay", String.format("%d:%d:%d", year,month,dayOfMonth));
		}
		int days = DateUtil.getDayCountOfMonth(year, month);
		String format = "%d月%d日";
		ArrayList<String> items = new ArrayList<String>();
		for (int i = dayOfMonth; i <=days; i++) {
			if (items.size() > 28) {
				break;
			}
			items.add(String.format(format, month, i));
		}
		month = (month + 1) % 12;
		if (items.size() < 29) {
			int previewMonthSize = items.size();
			for (int i = 1; i < 29 - previewMonthSize; i++) {
				if (items.size() > 28) {
					break;
				}
				items.add(String.format(format, month, i));
			}
		}
		items.add(0, "菜单");
		items.add(0, "材料清单");
		items.add(0, "设置");
		setAdatper(items);
	}

}