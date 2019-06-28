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

import com.moregood.yuezi.entity.DataMgr;
import com.moregood.yuezi.entity.OneDay;
import com.moregood.yuezi.entity.Period;

import android.os.Bundle;
import android.provider.ContactsContract.Contacts.Data;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class OneDayFragment extends Fragment{

	private static final String ARG_POSITION = "position";

	private int position;

	public static OneDayFragment newInstance(int position) {
		OneDayFragment f = new OneDayFragment();
		Bundle b = new Bundle();
		b.putInt(ARG_POSITION, position);
		f.setArguments(b);
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		position = getArguments().getInt(ARG_POSITION);
	}
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View oneDayView = inflater.inflate(R.layout.fragment_one_day, null);
		
		OneDay oneDay = DataMgr.getInstance().getOneDay(position);
		
		LinearLayout linearLayout = (LinearLayout)oneDayView.findViewById(R.id.one_day);
		linearLayout.setOrientation(LinearLayout.VERTICAL);

		
		Button btn = (Button)oneDayView.findViewById(R.id.view_material_list);
		btn.setTag(oneDay);
		btn.setOnClickListener((OnClickListener)getActivity());
		
		for(Period period:oneDay.getPeriods()){
			String[] dishNames = new String[period.getDisheIds().size()];
			int i=0;
			for(int id:period.getDisheIds()){
				dishNames[i++] = DataMgr.getInstance().getDishById(id).name;
			}
			OneDayView dinnerView = new OneDayView(getActivity(),period,dishNames);
			dinnerView.setTag(period.getDisheIds());
			dinnerView.setOnItemClickListener((OnItemClickListener)getActivity());
			linearLayout.addView(dinnerView);
		}
		return oneDayView;
	}
	

}