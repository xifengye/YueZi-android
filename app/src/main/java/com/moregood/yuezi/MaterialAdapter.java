package com.moregood.yuezi;

import java.util.ArrayList;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.moregood.yuezi.entity.BitmapCache;
import com.moregood.yuezi.entity.DataMgr;
import com.moregood.yuezi.entity.Material;

public class MaterialAdapter extends BaseAdapter {

	ArrayList<Material> materials;
	Context context;
	Map<Integer,Float> materialAmountMap;
	
	public MaterialAdapter(Context context,ArrayList<Material> materials) {
		this.context = context;
		this.materials = materials;
		this.materialAmountMap = DataMgr.getInstance().getMaterialAmountMap();
	}

	@Override
	public int getCount() {
		return materials.size();
	}

	@Override
	public Object getItem(int position) {
		return materials.get(position);
	}

	@Override
	public long getItemId(int position) {
		return materials.get(position).id;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder = null;
		Material material = materials.get(position);
		if (convertView == null) {
			holder = new Holder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.listitem_material, null);
			holder.index = (TextView) convertView
					.findViewById(R.id.textView1);
			holder.icon = (ImageView) convertView
					.findViewById(R.id.imageView1);
			holder.name = (TextView) convertView
					.findViewById(R.id.textView2);
			holder.amount = (TextView) convertView
					.findViewById(R.id.textView3);
			holder.desc = (TextView) convertView
					.findViewById(R.id.textView4);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		holder.index.setText(String.valueOf(material.id));
		holder.name.setText(material.name);
		holder.desc.setText(material.desc);
		holder.amount.setText(materialAmountMap.get(material.id)+material.unit);
		Bitmap bmp = BitmapCache.getInstance().getBitmap(
				String.format("%s/%s", Material.class.getSimpleName(),
						material.image), context);
		if (bmp != null) {
			holder.icon.setImageBitmap(bmp);
		} else {
			holder.icon.setImageResource(R.mipmap.unload);
		}
		return convertView;
	}

	class Holder {
		TextView index;
		ImageView icon;
		TextView name;
		TextView desc;
		TextView amount;
	}
}