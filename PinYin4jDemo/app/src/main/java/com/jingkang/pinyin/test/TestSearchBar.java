package com.jingkang.pinyin.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;

import com.jingkang.pinyin.R;
import com.jingkang.pinyin.util.SearchBar;
import com.jingkang.pinyin.util.SearchBar.OnSearchBar;

public class TestSearchBar extends AppCompatActivity implements OnSearchBar {

    private static final String TAG = "TestSearchBar";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);
        SearchBar searchBar = (SearchBar) findViewById(R.id.search_bar);
        searchBar.setHint("搜索联系人");
        searchBar.setInputType(InputType.TYPE_CLASS_TEXT);
        searchBar.setOnSearchBar(this);
    }

    @Override
    public void onSearchTextChanged(String searchString) {
        Log.i(TAG, searchString);
    }
}
