package com.sharingfuelcard.sharingfuelcard.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.sharingfuelcard.sharingfuelcard.R;
import com.sharingfuelcard.sharingfuelcard.base.BaseActivity;
import com.sharingfuelcard.sharingfuelcard.utils.FileUtils;
import com.sharingfuelcard.sharingfuelcard.utils.ToastUtils;
import com.sharingfuelcard.sharingfuelcard.utils.Utils;

/**
 * Created by 鹏祺 on 2017/9/7.
 */

public class ApplyFuelCardActivity extends BaseActivity {
    private Button btnSubmit;
    private ImageView ivPhoto1, ivPhoto2;
    private String path;
    private Bitmap bitmap;

    public static void gotoApplyFurlCard(Context context) {
        Intent intent = new Intent(context, ApplyFuelCardActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Utils.PIC_FROM_PHOTO:
                if (resultCode != RESULT_OK) {
                    ToastUtils.showShort("获取图片失败");
                    break;
                }
                Uri uri = data.getData();
                path = FileUtils.getPathByUri(uri, this);
                bitmap = Utils.compressBitmap(path, 1024, 1024);
                ivPhoto1.setImageBitmap(bitmap);
//                progressBarPic.setVisibility(View.VISIBLE);
//                Utils.saveImageViaAsyncTask(this, bitmap, "temp.jpg", new Utils().OnSaveImageCallback() {
//                    @Override
//                    public void callback(String path) {
//                        showPic(true);
//                        if (null == bitmap)
//                            return;
//                        cacheBitmap = Bitmap.createBitmap(bitmap);
//                        imgPicContent.setImageBitmap(bitmap);
//                    }
//                });

                break;
            case Utils.PIC_FROM_CAMERA:
                if (resultCode != RESULT_OK) {
                    ToastUtils.showShort("拍照失败");
                    break;
                }
                Intent intent = new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(Utils.imgUri, "image/*");
                intent.putExtra("scale", true);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Utils.imgUri);
                ApplyFuelCardActivity.this.startActivityForResult(intent, Utils.CROP_PHOTO);
                break;
            case Utils.CROP_PHOTO:
                if (resultCode != RESULT_OK) {
                    ToastUtils.showShort("剪裁失败");
                    break;
                }
                path = FileUtils.getPathByUri(Utils.imgUri, this);
                bitmap = Utils.compressBitmap(path, 1024, 1024);
//                    bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(Tools.imgUri));
                ivPhoto1.setImageBitmap(bitmap);
                break;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_fuel_card);
        initView();
        initData();
    }

    @Override
    protected void initView() {
        super.initView();
        btnSubmit = (Button) findViewById(R.id.btn_submit);
        ivPhoto1 = (ImageView) findViewById(R.id.iv_photo1);
        ivPhoto2 = (ImageView) findViewById(R.id.iv_photo2);

        ivPhoto1.setOnClickListener(this);
        ivPhoto2.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        super.initData();
        setTitle("申请油卡");
        showBack();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_submit:
                MainActivity.gotoMainActivity(getContext());
                break;
            case R.id.iv_photo1:
                Utils.showChoosePicDialog(ApplyFuelCardActivity.this);
                break;
            case R.id.iv_photo2:
                Utils.showChoosePicDialog(ApplyFuelCardActivity.this);
                break;
        }
    }
}
