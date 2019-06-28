package com.moregood.yuezi.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;

public class BitmapUtil {

	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float r1,
			float r2, float r3, float r4) {

		GradientDrawable drawable = new GradientDrawable();
		drawable.setShape(GradientDrawable.RECTANGLE);
		drawable.setColor(Color.WHITE);
		drawable.setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());
		drawable.setCornerRadii(new float[] { r1, r1, r2, r2, r3, r3, r4, r4 });

		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		drawable.draw(canvas);

		final Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, 0, 0, paint);

		return output;
	}

	public static Bitmap getRoundedCornerBitmapWithRoundedBackground(
			Bitmap bitmap, int width, int height, int frameWidth,
			int frameColor, float rOut, float rInner) {
		try {
			Bitmap temp = Bitmap.createBitmap(width, height, Config.ARGB_8888);
			Canvas canvas = new Canvas(temp);
			canvas.drawColor(frameColor);
			bitmap = getRoundedCornerBitmap(bitmap, rInner, rInner, rInner,
					rInner);
			canvas.drawBitmap(bitmap, null, new Rect(frameWidth, frameWidth,
					width - frameWidth, height - frameWidth), null);
			return getRoundedCornerBitmap(temp, rOut, rOut, rOut, rOut);
		} catch (OutOfMemoryError e) {
			return bitmap;
		}

	}

	private static void copyNIO(FileChannel source, File destination,
			long startOffset, long length) throws IOException {
		FileChannel destinationChannel = null;
		try {
			destinationChannel = new FileOutputStream(destination).getChannel();
			source.transferTo(startOffset, length, destinationChannel);
		} finally {

		}
	}

	private static byte[] mBytes = new byte[32 * 1024];

	public static Bitmap compressScrawBitmap(Context res, int id) {
		Bitmap bm = null;
		BitmapFactory.Options bfOptions = new BitmapFactory.Options();
		// Disable Dithering mode
		bfOptions.inDither = false;
		// Tell to gc that whether it needs free memory, the Bitmap can be
		// cleared
		bfOptions.inPurgeable = true;
		// Which kind of reference will be used to recover the Bitmap data
		// after being clear, when it will
		// be used in the future
		bfOptions.inInputShareable = true;
		bfOptions.inTempStorage = mBytes;

		FileInputStream fs = null;
		File file = new File(res.getFilesDir().getAbsolutePath() + "/"
				+ String.valueOf(id));
		try {
			if (file.getTotalSpace() > 0)
				fs = new FileInputStream(file);
			else {
				AssetFileDescriptor ad = res.getResources().openRawResourceFd(
						id);// .openRawResource(id);
				FileChannel sourceChannel = new FileInputStream(
						ad.getFileDescriptor()).getChannel();
				copyNIO(sourceChannel, file, ad.getStartOffset(),
						ad.getLength());
				fs = new FileInputStream(file);
			}
			if (fs != null)
				bm = BitmapFactory.decodeFileDescriptor(fs.getFD(), null,
						bfOptions);
			// bm=BitmapFactory.decodeFileDescriptor(fs.getFD(), null,
			// bfOptions);
		} finally {
			if (fs != null) {
				try {
					fs.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return bm;
		}
	}

	public static Bitmap compressScrawBitmap(Resources res, int id, int width,
			int hight) {
		try {

			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			// ??��??�?�???��?????宽�??�?
			Bitmap bitmap = BitmapFactory.decodeResource(res, id, options); // 此�?��?????bm为空
			options.inJustDecodeBounds = false;
			// ???�???��??大�??
			if (width > 0) {
				options.outWidth = width;
			}
			if (hight > 0) {
				options.outHeight = hight;
			}
			options.inPreferredConfig = Config.RGB_565;
			options.inTempStorage = new byte[16 * 1024];
			options.inPurgeable = true;
			options.inInputShareable = true;
			options.inSampleSize = 2;
			bitmap = BitmapFactory.decodeResource(res, id, options);

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			// �???��????��??
			bitmap.compress(Bitmap.CompressFormat.PNG, 30, baos);
			// �?�???��??
			InputStream isBm = new ByteArrayInputStream(baos.toByteArray());
			Bitmap bmp = BitmapFactory.decodeStream(isBm);
			bitmap.recycle();
			bitmap = null;
			return bmp;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static InputStream Bitmap2IS(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
		InputStream sbs = new ByteArrayInputStream(baos.toByteArray());
		return sbs;
	}

	public static byte[] Bitmap2Bytes(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		return baos.toByteArray();
	}

}
