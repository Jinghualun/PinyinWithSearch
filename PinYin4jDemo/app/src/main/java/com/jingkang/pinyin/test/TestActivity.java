package com.jingkang.pinyin.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.jingkang.pinyin.R;
import com.jingkang.pinyin.util.PinyinSideBar;
import com.jingkang.pinyin.util.SearchAdapter;
import com.jingkang.pinyin.util.SearchBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestActivity extends AppCompatActivity implements PinyinSideBar.OnPinyinSideBar,
        SearchBar.OnSearchBar {

    private SearchAdapter adapter;
    private ListView lv;
    private TextView tvShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        tvShow = (TextView) findViewById(R.id.tv_show);
        SearchBar searchBar = (SearchBar) findViewById(R.id.search_bar);
        searchBar.setHint("搜索联系人");
        searchBar.setInputType(InputType.TYPE_CLASS_TEXT);
        searchBar.setOnSearchBar(this);
        PinyinSideBar pinyinSideBar = (PinyinSideBar) findViewById(R.id.pinyin_bar);
        pinyinSideBar.setOnPinyinSideBar(this);

        List<TestModel> testModels = new ArrayList<TestModel>();
        for (int i = 0;i < NAMES.length; i++) {
            TestModel testModel = new TestModel(NAMES[i], "1367156666" + i);
            testModels.add(testModel);
        }
        lv = (ListView) findViewById(R.id.lv_search);
        Collections.sort(testModels, TestModel.mAscComparator);
        adapter = new SearchAdapter(this, testModels);
        pinyinSideBar.setSectionIndexer(adapter);
        lv.setAdapter(adapter);
    }

    @Override
    public void onPinyinSideBarDown(char selectChar) {
        Log.i("onPinyinSideBarDown", String.valueOf(selectChar));
        tvShow.setText(String.valueOf(selectChar));
        tvShow.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPinyinSideBarUp(char selectChar) {
        Log.i("onPinyinSideBarUp", String.valueOf(selectChar));
        tvShow.setVisibility(View.GONE);
    }

    @Override
    public void onSelectedPosition(int position) {
        Log.i("onSelectedPosition", String.valueOf(position));
        lv.setSelection(position);
    }

    @Override
    public void onSearchTextChanged(String searchString) {
        Log.i("onSearchTextChanged", searchString);
        adapter.getFilter().filter(searchString);
    }

    private static String[] NAMES = {"孙杨", "林丹", "姚明", "刘翔", "李娜", "Kobe", "MJ",
            "钟跃民", "Shark", "007", "@#!..."};

}
