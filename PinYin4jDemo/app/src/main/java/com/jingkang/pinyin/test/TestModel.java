package com.jingkang.pinyin.test;

import com.pinyinsearch.model.PinyinUnit;
import com.pinyinsearch.util.PinyinUtil;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/**
 * Created by jingkang
 */
public class TestModel {

    private String name;
    private String mobile;
    private String sortKey;
    private List<PinyinUnit> pinyinUnit;

    public TestModel(String name, String mobile) {
        this.pinyinUnit = new ArrayList<PinyinUnit>();
        setName(name);
        this.mobile = mobile;
    }

    public TestModel() {
        this.pinyinUnit = new ArrayList<PinyinUnit>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        PinyinUtil.chineseStringToPinyinUnit(name, pinyinUnit);
        String sortKey = PinyinUtil.getSortKey(pinyinUnit).toUpperCase();
        setPinyinUnit(pinyinUnit);
        setSortKey(sortKey);
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSortKey() {
        return sortKey;
    }

    public void setSortKey(String sortKey) {
        this.sortKey = sortKey;
    }

    public List<PinyinUnit> getPinyinUnit() {
        return pinyinUnit;
    }

    public void setPinyinUnit(List<PinyinUnit> pinyinUnit) {
        this.pinyinUnit = pinyinUnit;
    }

    private static Comparator<Object> mChineseComparator = Collator.getInstance(Locale.CHINA);

    public static Comparator<TestModel> mDesComparator = new Comparator<TestModel>() {
        @Override
        public int compare(TestModel lhs, TestModel rhs) {
            return mChineseComparator.compare(rhs.sortKey, lhs.sortKey);
        }
    };

    public static Comparator<TestModel> mAscComparator = new Comparator<TestModel>() {
        @Override
        public int compare(TestModel lhs, TestModel rhs) {
            return mChineseComparator.compare(lhs.sortKey, rhs.sortKey);
        }
    };

    @Override
    public String toString() {
        return  name + " " + mobile + " " + sortKey;
    }
}
