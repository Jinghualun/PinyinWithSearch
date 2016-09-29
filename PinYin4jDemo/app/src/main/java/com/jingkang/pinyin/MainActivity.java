package com.jingkang.pinyin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.jingkang.pinyin.test.TestActivity;
import com.jingkang.pinyin.test.TestPinyinSideBar;
import com.jingkang.pinyin.test.TestSearchBar;
import com.jingkang.pinyin.test.TestSearchAdapter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);
        findViewById(R.id.btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn:
                startActivity(TestActivity.class);
                break;
            case R.id.btn1:
                startActivity(TestPinyinSideBar.class);
                break;
            case R.id.btn2:
                startActivity(TestSearchBar.class);
                break;
            case R.id.btn3:
                startActivity(TestSearchAdapter.class);
                break;
        }
    }

    private void startActivity(Class cls) {
        startActivity(new Intent(this, cls));
    }
}
