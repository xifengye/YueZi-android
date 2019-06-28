package com.moregood.yuezi;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;


public class ActivityProfilePhoto extends Activity{


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image);
	}



	public void onButton(View view) {
		Intent intent = new Intent(Intent.ACTION_PICK, null);
		intent.setDataAndType(
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
				"image/*");
		startActivityForResult(intent, 1);
	}
	public void onUploadClick(View view) {
		try{
			showPicDialog();
		}catch (Exception e) {
			// TODO: handle exception
		}
	}

	private void showPicDialog() {
		new AlertDialog.Builder(this)
				.setTitle("上传头像")
				.setNegativeButton("相册", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();
						Intent intent = new Intent(Intent.ACTION_PICK, null);
						intent.setDataAndType(
								MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
								"image/*");
						startActivityForResult(intent, 1);
					}
				})
				.setPositiveButton("照相", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();
						Intent intent2 = new Intent(
								MediaStore.ACTION_IMAGE_CAPTURE);
						intent2.putExtra(MediaStore.EXTRA_OUTPUT, Uri
								.fromFile(new File(Environment
										.getExternalStorageDirectory(),
										"profile.jpg")));
						startActivityForResult(intent2, 2);
					}

				}).show();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
			case 1:
				if (data != null) {
					startPicCut(data.getData());
				}
				break;
			case 2:
				File temp = new File(Environment.getExternalStorageDirectory()
						+ "/profile.jpg");
				boolean exist = temp.exists();
				if(!exist){

					return;
				}
				startPicCut(Uri.fromFile(temp));
				break;
			case 3:
				if (data != null) {
					saveProfilePhoto(data);
				}
				break;
			default:
				break;
		}
	}

	private void saveProfilePhoto(Intent data) {
		Bundle bundle = data.getExtras();
		if (bundle != null) {
			Bitmap bmpPortrait = bundle.getParcelable("data");
			try {
				saveAdFile(Bitmap2InputStream(bmpPortrait));
			} catch (IOException e) {
				e.printStackTrace();
			}
			ImageView iv = (ImageView)findViewById(R.id.imageView1);
			iv.setImageBitmap(bmpPortrait);
		}
	}

	/**
	 * 瑁??????剧???????规??
	 *
	 * @param uri
	 */
	public void startPicCut(Uri uri) {
		Intent intentCarema = new Intent("com.android.camera.action.CROP");
		intentCarema.setDataAndType(uri, "image/*");
		intentCarema.putExtra("crop", true);
//		intentCarema.putExtra("scale", false);
		intentCarema.putExtra("noFaceDetection", true);// 涓????瑕?浜鸿?歌???????????
		intentCarema.putExtra("circleCrop", true);//璁惧??姝ゆ?规?????瀹???哄??浼???????褰㈠?哄??
		// aspectX aspectY???瀹介??姣?渚?
		intentCarema.putExtra("aspectX", 1);
		intentCarema.putExtra("aspectY", 1);
		// outputX outputY ???瑁??????剧?????瀹介??
		intentCarema.putExtra("outputX", 250);
		intentCarema.putExtra("outputY", 250);
		intentCarema.putExtra("return-data", true);
		startActivityForResult(intentCarema, 3);
	}

	// 将Bitmap转换成InputStream
	public InputStream Bitmap2InputStream(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		InputStream is = new ByteArrayInputStream(baos.toByteArray());
		return is;
	}


	public boolean saveAdFile(InputStream content)throws IOException{
		FileOutputStream fileout = null;
		File file = new File(Environment.getExternalStorageDirectory()
				+ "/a.jpg");
		if(!file.exists()) {
			if(!file.createNewFile()) {
				throw new IOException();
			}
		} else {
			if(!file.delete() || !file.createNewFile()) {
				throw new IOException();
			}
		}
		fileout = new FileOutputStream(file);
		int i = 0;
		byte[] bytes = new byte[10240];
		try {
			while((i = content.read(bytes)) != -1) {
				fileout.write(bytes, 0, i);
			}
		} catch (IOException e){
			file.deleteOnExit();
		} finally {
			fileout.flush();
			fileout.close();
			content.close();
		}
		return true;
	}




}
