package com.discount.pigpigroad.discountapp.MinePage.ChangeData;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.discount.pigpigroad.discountapp.BaseActivity;
import com.discount.pigpigroad.discountapp.MainActivity;
import com.discount.pigpigroad.discountapp.MyApplication;
import com.discount.pigpigroad.discountapp.R;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import static com.discount.pigpigroad.discountapp.Api.ApiConnect.URL_BASE;
import static com.discount.pigpigroad.discountapp.Api.ApiConnect.USER_OVERDUE;

/**
 * Created by PIGROAD on 2018/4/18.
 * Email:920015363@qq.com
 */

public class ChangeDataActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout ll_changename, ll_back, ll_changephone, ll_changepho;
    String phone, logintime, login_token;
    private TextView tv_user_name, tv_user_phone;
    private ImageView iv_user_pho;
    private Dialog dialog;
    protected static Uri tempUri;
    private Bitmap mBitmap;
    protected static final int CHOOSE_PICTURE = 0;
    protected static final int TAKE_PICTURE = 1;
    private static final int CROP_SMALL_PICTURE = 2;
    String pho;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.changedata_layout);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        //获取时间戳
        long time = System.currentTimeMillis() / 1000;
        logintime = String.valueOf(time);
        //获取token
        SharedPreferences sp = getSharedPreferences("User", MODE_PRIVATE);
        login_token = sp.getString("user_token", null);
        ll_back = findViewById(R.id.ll_back);
        ll_back.setOnClickListener(this);
        ll_changephone = findViewById(R.id.ll_changephone);
        ll_changephone.setOnClickListener(this);
        ll_changename = findViewById(R.id.ll_changename);
        ll_changename.setOnClickListener(this);
        tv_user_name = findViewById(R.id.tv_user_name);
        tv_user_phone = findViewById(R.id.tv_user_phone);
        iv_user_pho = findViewById(R.id.iv_user_pho);
        ll_changephone = findViewById(R.id.ll_changepho);
        ll_changephone.setOnClickListener(this);
        getUserinfo();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //修改名字
            case R.id.ll_changename:
                Intent intent = new Intent(ChangeDataActivity.this, ChangeNameActivity.class);
                intent.putExtra("name", tv_user_name.getText());
                startActivity(intent);
                break;
            //返回
            case R.id.ll_back:
                finish();
                break;
            //修改手机号
            case R.id.ll_changephone:
                Intent intent1 = new Intent(ChangeDataActivity.this, ChangePhoneActivity.class);
                startActivity(intent1);
                break;
            //修改头像
            case R.id.ll_changepho:
                dialogList();
                break;
        }
    }

    //获取用户id和头像
    public void getUserinfo() {
        OkGo.<String>post(URL_BASE + "?platform=android&time=" + logintime + "&token=" + login_token + "&request=private.user.detail.get")
                .tag(this)
                .headers("header1", "headerValue1")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String responseStr = response.body();//这个就是返回来的结果
                        try {
                            JSONObject jsonObject = new JSONObject(responseStr);
                            sendLog(responseStr);
                            int code = jsonObject.getInt("code");
                            if (code == 0) {
                                JSONObject data = jsonObject.getJSONObject("data");
                                tv_user_name.setText(data.getString("username"));
                                //判断头像是否为空
                                pho = data.getString("pho");
                                phone = data.getString("phone");
                                tv_user_phone.setText(phone.subSequence(0, 3) + "****" + phone.subSequence(6, 11));
                                Glide.with(ChangeDataActivity.this).load(pho).listener(new RequestListener<String, GlideDrawable>() {
                                    @Override
                                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                        sendLog(e+"");
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                        return false;
                                    }
                                }).into(iv_user_pho);
                            } else if (code == 999) {
                                MyApplication.ExitClear(ChangeDataActivity.this);
                                removeALLActivity();
                                sendToast(USER_OVERDUE);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUserinfo();
    }

    /**
     * 列表
     */
    private void dialogList() {
        final String items[] = {"拍照上传", "从手机相册选择"};

        @SuppressLint("ResourceType") AlertDialog.Builder builder = new AlertDialog.Builder(this, 3);
        // builder.setMessage("是否确认退出?"); //设置内容
        // 设置列表显示，注意设置了列表显示就不要设置builder.setMessage()了，否则列表不起作用。
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        Intent openCameraIntent = new Intent(
                                MediaStore.ACTION_IMAGE_CAPTURE);
                        tempUri = Uri.fromFile(new File(Environment
                                .getExternalStorageDirectory(), "temp_image.jpg"));
                        // 将拍照所得的相片保存到SD卡根目录
                        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
                        startActivityForResult(openCameraIntent, TAKE_PICTURE);
                        break;
                    case 1:
                        Intent openAlbumIntent = new Intent(
                                Intent.ACTION_GET_CONTENT);
                        openAlbumIntent.setType("image/*");
                        //用startActivityForResult方法，待会儿重写onActivityResult()方法，拿到图片做裁剪操作
                        startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);
                        break;
                }
            }
        });
        builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        //builder.create().show();
        dialog = builder.create();
        dialog.show();
        dialog.getWindow().setGravity(Gravity.BOTTOM);//可以设置显示的位置setContentView(view);//自定义布局应该在这里添加，要在dialog.show()的后面
        Window window = dialog.getWindow();
        window.setWindowAnimations(R.style.dialog_animation);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
    }

    /**
     * 裁剪图片方法实现
     */
    protected void cutImage(Uri uri) {
        if (uri == null) {
            Log.i("alanjet", "The uri is not exist.");
        }
        tempUri = uri;
        Intent intent = new Intent("com.android.camera.action.CROP");
        //com.android.camera.action.CROP这个action是用来裁剪图片用的
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CROP_SMALL_PICTURE);
    }

    /**
     * 保存裁剪之后的图片数据
     */
    protected void setImageToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            mBitmap = extras.getParcelable("data");
            //这里图片是方形的，可以用一个工具类处理成圆形（很多头像都是圆形，这种工具类网上很多不再详述）
            iv_user_pho.setImageBitmap(mBitmap);//显示图片
            //在这个地方可以写上上传该图片到服务器的代码
            File logo = saveBitmapFile(mBitmap);
            uploadFiles(logo);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == MainActivity.RESULT_OK) {
            switch (requestCode) {
                case TAKE_PICTURE:
                    cutImage(tempUri); // 对图片进行裁剪处理
                    break;
                case CHOOSE_PICTURE:
                    cutImage(data.getData()); // 对图片进行裁剪处理
                    break;
                case CROP_SMALL_PICTURE:
                    if (data != null) {
                        setImageToView(data); // 让刚才选择裁剪得到的图片显示在界面上
                    }
                    break;
            }
        }
    }

    public File saveBitmapFile(Bitmap bitmap) {
        File file = new File("/mnt/sdcard/user_logo.jpg");//将要保存图片的路径
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * 上传用户头像JPG到服务器
     */
    private void uploadFiles(File file) {
        OkGo.<String>post(URL_BASE + "?platform=android&token=" + login_token + "&request=private.source.upload.pic.action&file_dir=pho&file_type=form_data")
                .tag(this)
                .headers("header1", "headerValue1")
                .params("file", file)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        //注意这里已经是在主线程了
                        String responseStr = response.body();//这个就是返回来的结果
                        try {
                            JSONObject jsonObject = new JSONObject(responseStr);
                            sendLog("res"+responseStr);
                            int code = (int) jsonObject.get("code");
                            if (code == 0) {
                                String url = (String) jsonObject.get("url");
                                sendLog(url);
                                updata(url);
                            } else if (code == 1) {
                                String msg = (String) jsonObject.get("msg");
                                sendToast(msg);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }

    /**
     * 把刚刚上传的头像更新到用户
     */
    public void updata(String url) {
        OkGo.<String>post(URL_BASE + "?platform=android&token=" + login_token + "&request=private.user.update.user.pho.action&pho=" + url)
                .tag(this)
                .headers("header1", "headerValue1")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        //注意这里已经是在主线程了
                        String responseStr = response.body();//这个就是返回来的结果
                        try {
                            sendLog("res"+responseStr);
                            JSONObject jsonObject = new JSONObject(responseStr);
                            int code = (int) jsonObject.get("code");
                            String msg = (String) jsonObject.get("msg");
                            if (code == 0) {
                                sendToast(msg);
                            } else if (code == 1) {
                                sendToast(msg);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
