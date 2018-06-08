package com.example.mynote;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private FloatingActionButton floatingActionButton;
    private EditText et_search;
    private LinearLayout layoutDefault;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        使用Toolbar：
//        第一步：将<Toolbar>标签写入布局文件。并设置android:layout_height="?attr/actionBarSize"属性。
//              android:background="?attr/colorPrimary"属性。
//              android:theme="@style/ThemeOverlay.AppCompat.Dark.ActionBar"属性。
//              app:popupTheme="@style/ThemeOverlay.AppCompat.Light"属性。
//        第二步：在styles.xml文件中，将<style>标签的parent属性设置为"Theme.AppCompat.Light.NoActionBar"
//        第二步：绑定控件。
//        第三步：setSupportActionBar(Toolbar对象);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        ActionBar actionBar=getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.file);
        }


        floatingActionButton=findViewById(R.id.floatButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,AddNoteActivity.class);
                startActivity(intent);
            }
        });

//        et_search=findViewById(R.id.et_search);
//        layoutDefault=findViewById(R.id.layout_default);
//        et_search.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (!hasFocus){
//                    if ("".equals(et_search.getText().toString())){
//                        layoutDefault.setVisibility(View.VISIBLE);
//                    }
//                }else {
//                    layoutDefault.setVisibility(View.GONE);
//                }
//            }
//        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu:{
                setLinearLayout();
            }
        }
        return true;
    }

    private void setLinearLayout() {

    }
}
