
package com.moregood.yuezi.utils;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;

public class DeviceUtil {
	
    public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en
                    .hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr
                        .hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static String getConnectionInfo(Context context) {
        try {
            ConnectivityManager connectionManager = (ConnectivityManager) context
                    .getSystemService(context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectionManager.getActiveNetworkInfo();
            if (networkInfo == null) {
                return null;
            }
            StringBuffer buffer = new StringBuffer();
            buffer.append(networkInfo.getTypeName() + ":");// ??��??�?�?类�?????�?(�?????????��??WIFI?????????MOBILE???)???
            buffer.append(networkInfo.getExtraInfo() + ":");// ??��????????信�?????
            buffer.append(networkInfo.getDetailedState());// ??��??�?�???��?????
            // buffer.append("\n�???�失败�????????=" + networkInfo.getReason());//
            // ??��??�???�失败�???????????
            // buffer.append("\n�?�?类�??=" + networkInfo.getType());//
            // ??��??�?�?类�??(�????为移??��??Wi-Fi)???
            // buffer.append("\n该�??�?????????????=" + networkInfo.isAvailable());//
            // ??��??该�??�????????????��??
            // buffer.append("\n??????已�??�????=" + networkInfo.isConnected());//
            // ??��????????已�??�???��??
            // buffer.append("\n??????已�??�???��??正�?��?????=" +
            // networkInfo.isConnectedOrConnecting());// ??��????????已�??�???��??正�?��????��??
            // buffer.append("\n??????�???�失�?=" + networkInfo.isFailover());//
            // ??��????????�???�失败�??
            // buffer.append("\n??????�?�?=" + networkInfo.isRoaming());// ??��????????�?�?
            // buffer.append("\nIp=" + getLocalIpAddress());
            return buffer.toString();
        } catch (Exception e) {
            return null;
        }
    }

    // Get the device ID
    public static String getIMEI(Context context) {
        String auid = Secure.ANDROID_ID + "android_id";
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        if (tm != null) {
            try {
                auid = tm.getDeviceId();
            } catch (SecurityException e) {
                e.printStackTrace();
            }
            tm = null;
        }
        if (((auid == null) || (auid.length() == 0)) && (context != null))
            auid = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
        if ((auid == null) || (auid.length() == 0))
            auid = null;
        return auid;
    }

    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int getMetrics(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.densityDpi;
    }


   	public String getLocalMacAddress(Context context) {
   		WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
   		WifiInfo info = wifi.getConnectionInfo();
   		return info.getMacAddress();
   	}
   	
   	public static ComponentName getTopActivity(Context context) {
		 ActivityManager activityManager = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);  
	     List<ActivityManager.RunningTaskInfo> runningTasks = activityManager.getRunningTasks(1);
	     if(runningTasks==null || runningTasks.size()<=0){
	    	 return null;
	     }
	     return runningTasks.get(0).topActivity; 
	}
   	
   	public static boolean isApkDebugable(Context context){
   		try {
            ApplicationInfo info= context.getApplicationInfo();
                return (info.flags&ApplicationInfo.FLAG_DEBUGGABLE)!=0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

   	}
}
