package com.moregood.yuezi.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.moregood.yuezi.R;
import com.moregood.yuezi.entity.BitmapCache;

public class PopWindowUtil {

	public static PopupWindow getListPopWindows(Context context,final BaseAdapter adapter) {

		LayoutInflater mLayoutInflater = (LayoutInflater) context
				.getSystemService(context.LAYOUT_INFLATER_SERVICE);
		/* 设置显示menu布局 view子VIEW */
		ListView listview = (ListView) mLayoutInflater.inflate(
				R.layout.fragment_listview, null);

		/* 第一个参数弹出显示view 后两个是窗口大小 */
		final PopupWindow mPopupWindow = new PopupWindow(listview,LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
		/* 设置背景显示 */
		mPopupWindow.setBackgroundDrawable(context.getResources().getDrawable(
				R.mipmap.popupwindow_bg));
		/* 设置触摸外面时消失 */
		mPopupWindow.setOutsideTouchable(true);
		/* 设置系统动画 */
		mPopupWindow.setAnimationStyle(android.R.style.Animation_InputMethod);
		mPopupWindow.update();
		mPopupWindow.setTouchable(true);
		/* 设置点击menu以外其他地方以及返回键退出 */
		mPopupWindow.setFocusable(true);
		listview.setAdapter(adapter);
		return mPopupWindow;

	}

	public static PopupWindow getImageViewPopWindows(Context context,final String image) {

		ImageView imageView  = new ImageView(context);

		/* 第一个参数弹出显示view 后两个是窗口大小 */
		final PopupWindow mPopupWindow = new PopupWindow(imageView,LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		/* 设置背景显示 */
		mPopupWindow.setBackgroundDrawable(context.getResources().getDrawable(
				R.mipmap.popupwindow_bg));
		/* 设置触摸外面时消失 */
		mPopupWindow.setOutsideTouchable(true);
		/* 设置系统动画 */
		mPopupWindow.setAnimationStyle(android.R.style.Animation_InputMethod);
		mPopupWindow.update();
		mPopupWindow.setTouchable(true);
		/* 设置点击menu以外其他地方以及返回键退出 */
		mPopupWindow.setFocusable(true);
		imageView.setImageBitmap(BitmapCache.getInstance().getBitmap(image, context));
		return mPopupWindow;

	}

}
