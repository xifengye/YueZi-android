package com.moregood.yuezi;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

public class SettingFragment extends Fragment implements OnClickListener {
	DatePicker datePicker;

	public static SettingFragment newInstance() {
		SettingFragment f = new SettingFragment();
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(
				R.layout.fragment_setting, null);
		datePicker = (DatePicker)view.findViewById(R.id.datePicker1);
		Button btn = (Button)view.findViewById(R.id.button1);
		btn.setOnClickListener(this);
		return view;
	}

	@Override
	public void onClick(View v) {
		int year = datePicker.getYear();
		int month = datePicker.getMonth();
		int day = datePicker.getDayOfMonth();
		((MainActivity)getActivity()).onBeginDayChange(year, month+1, day, true);
	}




}
