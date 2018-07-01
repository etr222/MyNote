package com.example.mynote;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.DhcpInfo;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.TimeZone;


public class AddNoteActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int CHOOSE_PHOTO = 2;
    private static final int TAKE_PHOTO = 1;

    private LinearLayout addActivityLinearLayout;

    private ImageView ivBack;
    private TextView tvMonthDay;
    private TextView tvTime;
    private EditText etContent;
    //    private MyEditText etContent;
//    private mEditText etContent;
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

    private boolean ifClick = true;


    private TextView takePhoto;
    private TextView choosePhoto;
    private Button btnCancel;
    private Button btnDelete;


    private BottomSheetDialog dialog;

    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        
        
        initViews();
        initEvents();
        horizontalScrollView.setVisibility(View.GONE);
        getTime();
        dialog = new BottomSheetDialog(AddNoteActivity.this);

//        etContent=new mEditText(AddNoteActivity.this);

//        etContent.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                Log.e("EditActivity","1");
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                setBottomLayoutOnFocus();
//                Log.e("EditActivity",etContent.getContentList().toString());
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                Log.e("EditActivity","3");
//            }
//        });
    }

    private void initEvents() {
        ivBack.setOnClickListener(this);
        etContent.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    setBottomLayoutOnFocus();
                } else {
                    setBottomLayoutNotFocus();
                }
            }
        });
        ivImage.setOnClickListener(this);
        ivClothesOnFocus.setOnClickListener(this);
        ivCenter.setOnClickListener(this);
        ivBold.setOnClickListener(this);
        ivQingXie.setOnClickListener(this);
        ivAa.setOnClickListener(this);
        ivShare.setOnClickListener(this);
        ivClothes.setOnClickListener(this);
        ivDelete.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_Back:
                finish();
                break;
            case R.id.iv_image:
                setDialog();
                break;
            case R.id.iv_clothes_onFocus:
                setBackground();
                break;
            case R.id.iv_center:
                setTextCenter();
                break;
            case R.id.iv_bold:
                setTextBold();
                break;
            case R.id.iv_qingxie:
                setTextQingXie();
                break;
            case R.id.iv_Aa:
                setTextAa();
                break;
            case R.id.iv_share:
                shareNote();
                break;
            case R.id.iv_clothes:
                setBackground();
                break;
            case R.id.iv_delete:
                deleteNote();
                break;
        }
    }

    private void setTextAa() {
        if (ifClick) {
            ivAa.setBackgroundColor(Color.parseColor("#EEAA00"));
            etContent.setTextSize(20);
        } else {
            ivAa.setBackgroundColor(Color.TRANSPARENT);
            etContent.setTextSize(16);
        }
        ifClick = !ifClick;
    }

    private void setTextQingXie() {
        if (ifClick) {
            ivQingXie.setBackgroundColor(Color.parseColor("#EEAA00"));
            etContent.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));
        } else {
            ivQingXie.setBackgroundColor(Color.TRANSPARENT);
            etContent.setTypeface(Typeface.DEFAULT);
        }
        ifClick = !ifClick;
    }

    private void setTextBold() {
        TextPaint tp = etContent.getPaint();
        if (ifClick) {
            ivBold.setBackgroundColor(Color.parseColor("#EEAA00"));
            tp.setFakeBoldText(true);
        } else {
            ivBold.setBackgroundColor(Color.TRANSPARENT);
            tp.setFakeBoldText(false);
        }
        ifClick = !ifClick;
    }

    private void setTextCenter() {
        Log.e("ifClick_1", ifClick + "");
        if (ifClick) {
            Log.e("ifClick_2", ifClick + "");
            ivCenter.setBackgroundColor(Color.parseColor("#EEAA00"));
            etContent.setText("");
//                    etContent.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            ifClick = false;
        } else {
            Log.e("ifClick_3", ifClick + "");
            etContent.setText("");
//                    etContent.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            ivCenter.setBackgroundColor(Color.TRANSPARENT);
            ifClick = true;
        }
//                ifClick=!ifClick;
    }

    private void initViews() {
        ivBack = findViewById(R.id.iv_Back);

        tvMonthDay = findViewById(R.id.tv_monthDay);
        tvTime = findViewById(R.id.tv_time);

        etContent = findViewById(R.id.et_content);

        horizontalScrollView = findViewById(R.id.scrollView);
        focusLayout = findViewById(R.id.focusLayout);

        ivImage = findViewById(R.id.iv_image);
        ivClothesOnFocus = findViewById(R.id.iv_clothes_onFocus);
        ivCenter = findViewById(R.id.iv_center);
        ivBold = findViewById(R.id.iv_bold);
        ivQingXie = findViewById(R.id.iv_qingxie);
        ivAa = findViewById(R.id.iv_Aa);
        ivShare = findViewById(R.id.iv_share);
        ivClothes = findViewById(R.id.iv_clothes);
        ivDelete = findViewById(R.id.iv_delete);
        ivMore = findViewById(R.id.iv_more);
        addActivityLinearLayout = findViewById(R.id.addActivityLinearLayout);

        ivShare = findViewById(R.id.iv_share);
        ivClothes = findViewById(R.id.iv_clothes);

        ivDelete = findViewById(R.id.iv_delete);
        btnCancel = findViewById(R.id.btn_cancel);
        btnDelete = findViewById(R.id.btn_delete);

        ivMore = findViewById(R.id.iv_more);
    }

    private void deleteNote() {
        int resource = R.layout.iv_delete;
        View view = setBottomSheetDialog(resource);
        btnCancel = view.findViewById(R.id.btn_cancel);
        btnDelete = view.findViewById(R.id.btn_delete);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                删除该便签的逻辑实现。
            }
        });
    }

    private void shareNote() {
        int resource = R.layout.iv_share;
        View view = setBottomSheetDialog(resource);
    }

    private View setBottomSheetDialog(int resource) {
//        底部对话框BottomSheetDialog的使用：
//        第一步：创建BottomSheetDialog对象。通过new BottomSheetDialog(Context类);
//        第二步：动态加载布局。通过getLayoutInflater().inflate(布局文件, null);返回值是View对象。
//        第三步：设置内容布局。通过BottomSheetDialog对象.setContentView(View对象);
//        第四步：显示BottomSheetDialog对象。BottomSheetDialog对象.show();
//        BottomSheetDialog dialog = new BottomSheetDialog(AddNoteActivity.this);
        View view = getLayoutInflater().inflate(resource, null);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        return view;
    }


    private void setBackground() {
        int resource = R.layout.iv_clothes;
        View view = setBottomSheetDialog(resource);
    }

    private void setDialog() {
//        底部对话框BottomSheetDialog的使用：
//        第一步：创建BottomSheetDialog对象。通过new BottomSheetDialog(Context类);
//        第二步：动态加载布局。通过getLayoutInflater().inflate(布局文件, null);返回值是View对象。
//        第三步：设置内容布局。通过BottomSheetDialog对象.setContentView(View对象);
//        第四步：显示BottomSheetDialog对象。BottomSheetDialog对象.show();
        int resource = R.layout.iv_image;
        View view = setBottomSheetDialog(resource);

        takePhoto = view.findViewById(R.id.tv_takePhoto);
        choosePhoto = view.findViewById(R.id.tv_choosePhoto);
        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto.setBackgroundColor(Color.parseColor("#CCCCCCCC"));
                File file = new File(getExternalCacheDir(), "sp.jpg");
                try {
                    if (file.exists()) {
                        file.delete();
                    }
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (Build.VERSION.SDK_INT >= 24) {
                    imageUri = FileProvider.getUriForFile(AddNoteActivity.this, "com.example.mynote", file);
                } else {
                    imageUri = Uri.fromFile(file);
                }
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, TAKE_PHOTO);
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
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(AddNoteActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    openAlbum();
                }
            }
        });


    }

    private void openAlbum() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    Toast.makeText(this, "请让程序申请该权限，否则将不可读取相册", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    String path=imageUri.getPath();
                    insertEdit(data);
                }
                dialog.cancel();
                break;
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= 19) {
                        handleImageOnKitKat(data);
                    } else {
                        handleImageBeforeKitKat(data);
                    }
                }
                dialog.cancel();
                break;
            default:
                break;

        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this, uri)) {
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            imagePath = uri.getPath();
        }
        insertEdit(imagePath,data);
    }

    private String getImagePath(Uri externalContentUri, String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query(externalContentUri, null, selection, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        insertEdit(imagePath,data);
    }

//    设置底部功能。
    private void setBottomLayoutOnFocus() {
        focusLayout.setVisibility(View.GONE);
        horizontalScrollView.setVisibility(View.VISIBLE);
    }

    private void setBottomLayoutNotFocus() {

    }
//    获得当前时间。
    private void getTime() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        String month = String.valueOf(cal.get(Calendar.MONTH) + 1);
        String day = String.valueOf(cal.get(Calendar.DATE));
        tvMonthDay.setText(month + "月" + day + "日");

        String am = "";
        if (Calendar.AM_PM == 0) {
            am = "上午";
        } else {
            am = "下午";
        }
        String hour = String.valueOf(cal.get(Calendar.HOUR_OF_DAY));
        String minute = String.valueOf(cal.get(Calendar.MINUTE));
        String time = hour + ":" + minute;
        tvTime.setText(am + time);
    }



    public void insertEdit(Intent intent){
        Bitmap bitmap = null;
        try {
            Bitmap originalBitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
//            bitmap=resizeImg(path);
//            bitmap = resizeImage(originalBitmap, 200, 200);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if(bitmap != null) {
            //根据Bitmap对象创建ImageSpan对象
            ImageSpan imageSpan = new ImageSpan(AddNoteActivity.this, bitmap);
            //创建一个SpannableString对象，以便插入用ImageSpan对象封装的图像
            SpannableString spannableString = new SpannableString("[local]" + 1 + "[/local]");
            //  用ImageSpan对象替换face
            spannableString.setSpan(imageSpan, 0, "[local]1[local]".length() + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            //将选择的图片追加到EditText中光标所在位置
            int index = etContent.getSelectionStart(); //获取光标所在位置
            Editable edit_text = etContent.getEditableText();
            if (index < 0 || index >= edit_text.length()) {
                edit_text.append(spannableString);
            } else {
                edit_text.insert(index, spannableString);
            }
        }
    }
    public void insertEdit(String path,Intent intent) {
        Bitmap bitmap = null;
        bitmap=resizeImg(path);
        if(bitmap != null) {
            Log.e("1","1");
            //根据Bitmap对象创建ImageSpan对象
            ImageSpan imageSpan = new ImageSpan(AddNoteActivity.this, bitmap);
            //创建一个SpannableString对象，以便插入用ImageSpan对象封装的图像
            SpannableString spannableString = new SpannableString("[local]" + 1 + "[/local]");
            //  用ImageSpan对象替换face
            spannableString.setSpan(imageSpan, 0, "[local]1[local]".length() + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            //将选择的图片追加到EditText中光标所在位置
            int index = etContent.getSelectionStart(); //获取光标所在位置
            Editable edit_text = etContent.getEditableText();
            if (index < 0 || index >= edit_text.length()) {
                edit_text.append(spannableString);
            } else {
                edit_text.insert(index, spannableString);
            }
        }

    }

    private Bitmap resizeImg(String path) {
        BitmapFactory.Options options=new BitmapFactory.Options();
        options.inJustDecodeBounds=true;

        BitmapFactory.decodeFile(path,options);
        double ratio=Math.max(options.outWidth*1.0d/1024f,options.outHeight*1.0d/1024f);
        options.inSampleSize= (int) Math.ceil(ratio);
        options.inJustDecodeBounds=false;

        return BitmapFactory.decodeFile(path, options);

    }

    private Bitmap resizeImage(Bitmap originalBitmap, int newWidth, int newHeight) {


        int width = originalBitmap.getWidth();
        int height = originalBitmap.getHeight();
        //定义欲转换成的宽、高
//            int newWidth = 200;
//            int newHeight = 200;
        //计算宽、高缩放率
        float scanleWidth = (float)newWidth*1.0f/1024f;
        float scanleHeight = (float)newHeight*1.0f/1024f;
        //创建操作图片用的matrix对象 Matrix
        Matrix matrix = new Matrix();
        // 缩放图片动作
        matrix.postScale(scanleWidth,scanleHeight);
        //旋转图片 动作
        //matrix.postRotate(45);
        // 创建新的图片Bitmap
        Bitmap resizedBitmap = Bitmap.createBitmap(originalBitmap,0,0,width,height,matrix,true);
        return resizedBitmap;

    }

}
