package com.jingkang.pinyin.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.jingkang.pinyin.R;
import com.jingkang.pinyin.util.SearchAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestSearchAdapter extends AppCompatActivity {

    private List<TestModel> testModels;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test3);
        testModels = new ArrayList<TestModel>();
        for (int i = 0;i < NAMES.length; i++) {
            TestModel testModel = new TestModel(NAMES[i], "13671566" + i);
            testModels.add(testModel);
        }
        ListView lv = (ListView) findViewById(R.id.lv_test);
        Collections.sort(testModels, TestModel.mAscComparator);
        lv.setAdapter(new SearchAdapter(this, testModels));
    }

    private static String[] NAMES = {"孙杨", "林丹", "姚明", "刘翔", "李娜", "Kobe", "MJ",
    "钟跃民", "Shark", "007", "@#!..." };

}
