package com.moregood.yuezi;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.moregood.yuezi.entity.DataMgr;
import com.moregood.yuezi.entity.Dish;

public class DishListFragment extends Fragment{

	public static DishListFragment newInstance() {
		DishListFragment f = new DishListFragment();
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		ListView mGridView = (ListView)inflater.inflate(R.layout.fragment_listview, null);
		mGridView.setOnItemClickListener((OnItemClickListener)getActivity());
		DishAdapter adapter = new DishAdapter(DataMgr.getInstance().getDishes());
		mGridView.setAdapter(adapter);
		return mGridView;
	}
	
	
	private class DishAdapter extends BaseAdapter {

		ArrayList<Dish> dishes;

		public DishAdapter(ArrayList<Dish> dishes) {
			this.dishes = dishes;
		}

		@Override
		public int getCount() {
			return dishes.size();
		}

		@Override
		public Object getItem(int position) {
			return dishes.get(position);
		}

		@Override
		public long getItemId(int position) {
			return dishes.get(position).id;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Holder holder = null;
			Dish dish = dishes.get(position);
			if (convertView == null) {
				holder = new Holder();
				convertView = LayoutInflater.from(getActivity())
						.inflate(R.layout.listitem_dish, null);
				holder.index = (TextView) convertView
						.findViewById(R.id.griditem_index);
				holder.name = (TextView) convertView
						.findViewById(R.id.griditem_name);
				holder.desc = (TextView) convertView
						.findViewById(R.id.griditem_desc);
				convertView.setTag(holder);
			} else {
				holder = (Holder) convertView.getTag();
			}
			holder.index.setText(String.valueOf(position+1));
			holder.name.setText(dish.name);
			holder.desc.setText(dish.desc);
			return convertView;
		}

		class Holder {
			TextView index;
			TextView name;
			TextView desc;
		}
	}
	
	
	

}
