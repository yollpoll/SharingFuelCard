package com.sharingfuelcard.sharingfuelcard.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.sharingfuelcard.sharingfuelcard.BuildConfig;
import com.sharingfuelcard.sharingfuelcard.R;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 鹏祺 on 2017/9/13.
 */

public class Utils {
    public static void setMargins(View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }

    public static final int PIC_FROM_PHOTO = 11;
    public static final int PIC_FROM_CAMERA = 12;
    public static final int CROP_PHOTO = 13;
    public static Uri imgUri, filrUri;//一个是from provider,一个是from file

    public static void showChoosePicDialog(final Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        View view = LayoutInflater.from(activity).inflate(R.layout.alert_choose_photo, null);
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        RelativeLayout rlPhoto = (RelativeLayout) view.findViewById(R.id.rl_photo);
        RelativeLayout rlCamera = (RelativeLayout) view.findViewById(R.id.rl_camera);
        rlCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                File file = activity.getExternalCacheDir();
                File imgFile = new File(file, "temp.jpg");
                if (imgFile.exists())
                    imgFile.delete();
                try {
                    imgFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                imgUri = FileProvider.getUriForFile(activity, BuildConfig.APPLICATION_ID + ".fileProvider",
                        imgFile);
                filrUri = Uri.fromFile(imgFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
                activity.startActivityForResult(intent, PIC_FROM_CAMERA);

            }
        });
        rlPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                activity.startActivityForResult(intent, PIC_FROM_PHOTO);
            }
        });
    }

    public static Bitmap compressBitmap(String path, int destWidth, int destHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        int outWidth = options.outWidth;
        int outHeight = options.outHeight;
        int inSampleSize = 1;
        while (outHeight / inSampleSize > destHeight || outWidth / inSampleSize > destWidth) {
            inSampleSize *= 2;
        }
        options.inJustDecodeBounds = false;
        options.inSampleSize = inSampleSize;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        return BitmapFactory.decodeFile(path, options);
    }

    public static int calculateDpToPx(int padding_in_dp, Context context) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (padding_in_dp * scale + 0.5f);
    }

    public static String getDate(long time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd mm:ss");
        Date date = new Date(time);
        return simpleDateFormat.format(date);
    }

    //获取底部导航栏高度
    public static int getNavigationBarHeight(Context context) {

        boolean hasMenuKey = ViewConfiguration.get(context).hasPermanentMenuKey();
        boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
        if (checkDeviceHasNavigationBar(context)) {
            Resources resources = context.getResources();
            int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
            //获取NavigationBar的高度
            int height = resources.getDimensionPixelSize(resourceId);
            return height;
        } else {
            return 0;
        }
    }

    //获取是否存在NavigationBar
    public static boolean checkDeviceHasNavigationBar(Context context) {
        boolean hasNavigationBar = false;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {

        }
        return hasNavigationBar;

    }
}
