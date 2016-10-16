PinyinWithSearch
=
Search action based on pinyin4j.

Features
-
Support pinyin search<br>
Support English search<br>
Support Chinese character search<br>

![](https://github.com/Jinghualun/PinyinWithSearch/blob/master/PinYin4jDemo/pinyin.gif) 
Depend
-
The library of pinyin4j:http://pinyin4j.sourceforge.net/<br>
pinyin4j-2.5.0.jar<br>
pinyinsearch.jar

Usage
-
###Step1
Add SearchBar and PinyinSideBar in layout xml<br>

<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context="com.jingkang.pinyin.test.TestActivity">

    <com.jingkang.pinyin.util.SearchBar
        android:id="@+id/search_bar"
        android:layout_width="match_parent"
        android:layout_height="wrap_content" />

    <ListView
        android:id="@+id/lv_search"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_below="@id/search_bar" />

    <com.jingkang.pinyin.util.PinyinSideBar
        android:id="@+id/pinyin_bar"
        android:layout_width="34dp"
        android:layout_height="match_parent"
        android:layout_below="@id/search_bar"
        android:layout_alignParentRight="true" />

    <TextView
        android:id="@+id/tv_show"
        android:layout_width="80dp"
        android:layout_height="80dp"
        android:gravity="center"
        android:textColor="@android:color/black"
        android:textSize="22dp"
        android:background="#00ff00"
        android:layout_centerInParent="true"
        android:visibility="gone"/>
</RelativeLayout>

###Step2
Create SearchAdapter, you should extends BaseAdapter and implements Filterable, SectionIndexer.You should update the Model (in this Project is TestModel.java) that represent the data type.

###Step3
Implement PinyinSideBar.OnPinyinSideBar,SearchBar.OnSearchBar where you will search
    
    // This function is fired when you click or move on PinyinSideBar.
    @Override
    public void onPinyinSideBarDown(char selectChar) {
        Log.i("onPinyinSideBarDown", String.valueOf(selectChar));
        tvShow.setText(String.valueOf(selectChar));
        tvShow.setVisibility(View.VISIBLE);
    }

    // This function is fired when you finish clicking or moving on PinyinSideBar.
    @Override
    public void onPinyinSideBarUp(char selectChar) {
        Log.i("onPinyinSideBarUp", String.valueOf(selectChar));
        tvShow.setVisibility(View.GONE);
    }

    // The ListView will scroll to the section for position when this function is fired.
    @Override
    public void onSelectedPosition(int position) {
        Log.i("onSelectedPosition", String.valueOf(position));
        lv.setSelection(position);
    }

    // Show the search result.
    @Override
    public void onSearchTextChanged(String searchString) {
        Log.i("onSearchTextChanged", searchString);
        adapter.getFilter().filter(searchString);
    }
