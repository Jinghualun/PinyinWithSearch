package com.jingkang.pinyin.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.jingkang.pinyin.R;
import com.jingkang.pinyin.util.PinyinSideBar;
import com.jingkang.pinyin.util.PinyinSideBar.OnPinyinSideBar;

public class TestPinyinSideBar extends AppCompatActivity implements OnPinyinSideBar {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test1);
        PinyinSideBar pinyinSideBar = (PinyinSideBar) findViewById(R.id.sideBar);
        pinyinSideBar.setOnPinyinSideBar(this);
    }

    @Override
    public void onPinyinSideBarDown(char selectChar) {
        Log.i("onPinyinSideBarDown", String.valueOf(selectChar));
    }

    @Override
    public void onPinyinSideBarUp(char selectChar) {
        Log.i("onPinyinSideBarUp", String.valueOf(selectChar));
    }

    @Override
    public void onSelectedPosition(int position) {
        Log.i("onSelectedPosition", String.valueOf(position));
    }
}
