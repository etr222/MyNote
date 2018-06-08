package com.example.mynote;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.DhcpInfo;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextPaint;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.TimeZone;


public class AddNoteActivity extends AppCompatActivity {
    private static final int CHOOSE_PHOTO = 2;
    private LinearLayout addActivityLinearLayout;
    private ImageView ivBack;
    private TextView tvMonthDay;
    private TextView tvTime;
    private EditText etContent;
    private LinearLayout focusLayout;
    private HorizontalScrollView horizontalScrollView;
    private ImageView ivImage;
    private ImageView ivClothesOnFocus;
    private ImageView ivCenter;
    private ImageView ivBold;
    private ImageView ivQingXie;
    private ImageView ivAa;
    private ImageView ivShare;
    private ImageView ivClothes;
    private ImageView ivDelete;
    private ImageView ivMore;
    private boolean ifClick=true;


    private TextView takePhoto;
    private TextView choosePhoto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        ivBack=findViewById(R.id.iv_Back);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        tvMonthDay=findViewById(R.id.tv_monthDay);
        tvTime=findViewById(R.id.tv_time);
        etContent=findViewById(R.id.et_content);
        horizontalScrollView=findViewById(R.id.scrollView);
        focusLayout=findViewById(R.id.focusLayout);

        ivImage=findViewById(R.id.iv_image);
        ivClothesOnFocus=findViewById(R.id.iv_clothes_onFocus);
        ivCenter=findViewById(R.id.iv_center);
        ivBold=findViewById(R.id.iv_bold);
        ivQingXie=findViewById(R.id.iv_qingxie);
        ivAa=findViewById(R.id.iv_Aa);
        ivShare=findViewById(R.id.iv_share);
        ivClothes=findViewById(R.id.iv_clothes);
        ivDelete=findViewById(R.id.iv_delete);
        ivMore=findViewById(R.id.iv_more);
        addActivityLinearLayout=findViewById(R.id.addActivityLinearLayout);

        horizontalScrollView.setVisibility(View.GONE);
        getTime();

        etContent.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    setBottomLayoutOnFocus();
                }else{
                    setBottomLayoutNotFocus();
                }
            }
        });

        ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDialog();

            }
        });

        ivClothesOnFocus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBackground();
            }
        });

        ivCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ifClick=!ifClick;
                Log.e("ifClick_1",ifClick+"");
                if (ifClick){
                    Log.e("ifClick_2",ifClick+"");
                    ivCenter.setBackgroundColor(Color.parseColor("#EEAA00"));
                    etContent.setText("");
                    etContent.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                    ifClick=false;
                }else {
                    Log.e("ifClick_3",ifClick+"");
                    etContent.setText("");
                    etContent.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    ivCenter.setBackgroundColor(Color.TRANSPARENT);
                    ifClick=true;
                }
//                ifClick=!ifClick;
            }
        });

        ivBold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ifClick=!ifClick;
                TextPaint tp = etContent.getPaint();
                if (ifClick){
                    ivBold.setBackgroundColor(Color.parseColor("#EEAA00"));
                    tp.setFakeBoldText(true);
                }else {
                    ivBold.setBackgroundColor(Color.TRANSPARENT);
                    tp.setFakeBoldText(false);
                }
                ifClick=!ifClick;
            }
        });
    }

    private void setBackground() {

    }

    private void setDialog() {
        BottomSheetDialog dialog = new BottomSheetDialog(AddNoteActivity.this);
        View view = getLayoutInflater().inflate(R.layout.iv_image, null);
        takePhoto=view.findViewById(R.id.tv_takePhoto);
        choosePhoto=view.findViewById(R.id.tv_choosePhoto);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        dialog.show();

        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto.setBackgroundColor(Color.parseColor("#CCCCCCCC"));
            }
        });
        choosePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePhoto.setBackgroundColor(Color.parseColor("#CCCCCCCC"));
//                打开相册。
//                第一步：判断是否已申请权限。ContextCompat.checkSelfPermission(Activity类,Manifest.permission.权限名)!= PackageManager.PERMISSION_GRANTED
//                第二步：申请权限。ActivityCompat.requestPermissions(Activity类,new String[]{Manifest.permission.权限名},请求码 );
//                第三步：创建Intent对象。通过new Intent("android.intent.action.GET_CONTENT");
//                第四步：设置Intent对象的搜索文件名。Intent对象.setType("image/*");
//                第五步：打开相册。startActivityForResult(Intent对象，唯一识别该Intent对象的数字);
//                第六步：
                if (ContextCompat.checkSelfPermission(AddNoteActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(AddNoteActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1 );
                }else {
                    openAlbum();
                }
            }
        });


    }

    private void openAlbum() {
        Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent,CHOOSE_PHOTO);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if (grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    openAlbum();
                }else {
                    Toast.makeText(this, "请让程序申请该权限，否则将不可读取相册", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case CHOOSE_PHOTO:
                Uri uri=data.getData();
                Cursor cursor=getContentResolver().query(uri,null,null,null,null,null);
                String path="";
                if (cursor!=null){
                    if(cursor.moveToFirst()){
                        path=cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                    }
                    cursor.close();
                }
                displayImage(path);
        }
    }

    private void displayImage(String path) {
        if (path!=null){
            Bitmap bitmap=BitmapFactory.decodeFile(path);

        }
    }

    private void setBottomLayoutOnFocus() {
        focusLayout.setVisibility(View.GONE);
        horizontalScrollView.setVisibility(View.VISIBLE);
    }

    private void setBottomLayoutNotFocus() {
        
    }

    private void getTime() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        String month=String.valueOf(cal.get(Calendar.MONTH)+1);
        String day=String.valueOf(cal.get(Calendar.DATE));
        tvMonthDay.setText(month+"月"+day+"日");

        String am="";
        if(Calendar.AM_PM==0){
            am="上午";
        }else {
            am="下午";
        }
        String hour=String.valueOf(cal.get(Calendar.HOUR_OF_DAY));
        String minute=String.valueOf(cal.get(Calendar.MINUTE));
        String time=hour+":"+minute;
        tvTime.setText(am+time);
    }


}
