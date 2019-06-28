package com.moregood.yuezi;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.moregood.yuezi.entity.DataMgr;
import com.moregood.yuezi.entity.Material;
import com.moregood.yuezi.utils.PopWindowUtil;

public class MaterialListFragment extends Fragment implements OnItemClickListener {

	public static MaterialListFragment newInstance() {
		MaterialListFragment f = new MaterialListFragment();
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ListView listview = (ListView) inflater.inflate(
				R.layout.fragment_listview, null);
		listview.setOnItemClickListener(this);
		MaterialAdapter adapter = new MaterialAdapter(getActivity(),DataMgr.getInstance()
				.getMaterialList());
		listview.setAdapter(adapter);
		return listview;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		PopupWindow popWindow = PopWindowUtil.getImageViewPopWindows(getActivity(), String.format("%s/%s",Material.class.getSimpleName(), DataMgr.getInstance().getMaterialById((int)id).image));
		popWindow.showAsDropDown(view,300,0);
	}


}
