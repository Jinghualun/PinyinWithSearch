package com.jingkang.pinyin.util;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jingkang.pinyin.R;

/**
 * Created by jingkang
 */
public class SearchBar extends LinearLayout {

    private ImageView mSearchButton;
    private EditText mSearchView;
    private ImageView mCloseButton;
    private OnSearchBar mOnSearchBar;

    public interface OnSearchBar {
        void onSearchTextChanged(String searchString);
    }

    public SearchBar(Context context) {
        super(context);
        init(context);
    }

    public SearchBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.search_bar, this, true);
        mSearchButton = (ImageView) findViewById(R.id.iv_search);
        mSearchView = (EditText) findViewById(R.id.edt_search);
        mCloseButton = (ImageView) findViewById(R.id.iv_close);

        mSearchView.addTextChangedListener(mSearchWatcher);

        mSearchButton.setOnClickListener(mOnClickListener);
        mSearchView.setOnClickListener(mOnClickListener);
        mCloseButton.setOnClickListener(mOnClickListener);
    }

    public void setHint(String hint) {
        mSearchView.setHint(hint);
    }

    public void setInputType(int inputType) {
        mSearchView.setInputType(inputType);
    }

    private TextWatcher mSearchWatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (null != mOnSearchBar) {
                String inputString = s.toString().trim();
                if (TextUtils.isEmpty(inputString)) {
                    mCloseButton.setVisibility(View.GONE);
                } else {
                    mOnSearchBar.onSearchTextChanged(inputString);
                    mCloseButton.setVisibility(View.VISIBLE);
                }
            }
        }

    };

    public void setOnSearchBar(OnSearchBar onSearchBar) {
        this.mOnSearchBar = onSearchBar;
    }

    /*
     * SearchView can be set expanded before the IME is ready to be shown during
     * initial UI setup. The show operation is asynchronous to account for this.
     */
    private Runnable mShowImeRunnable = new Runnable() {
        public void run() {
            InputMethodManager imm = (InputMethodManager)
                    getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

            if (imm != null) {
                imm.showSoftInput(mSearchView, 0);
            }
        }
    };

    private void setImeVisibility(final boolean visible) {
        if (visible) {
            post(mShowImeRunnable);
        } else {
            removeCallbacks(mShowImeRunnable);
            InputMethodManager imm = (InputMethodManager)
                    getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

            if (imm != null) {
                imm.hideSoftInputFromWindow(getWindowToken(), 0);
            }
        }
    }

    private void onSearchClicked() {
        mSearchView.requestFocus();
        setImeVisibility(true);
    }

    private void onFilterClicked(String inputString) {
        setImeVisibility(true);
        if (!TextUtils.isEmpty(inputString)) {
            mOnSearchBar.onSearchTextChanged(inputString);
        }
    }

    private void onCloseClicked() {
        mSearchView.setText("");
        mCloseButton.setVisibility(View.GONE);
        mSearchView.requestFocus();
        setImeVisibility(true);
    }

    private OnClickListener mOnClickListener = new OnClickListener() {

        public void onClick(View v) {
            if (v == mSearchButton) {
                onSearchClicked();
            } else if (v == mSearchView) {
                onFilterClicked(mSearchView.getText().toString().trim());
            } else if (v == mCloseButton) {
                onCloseClicked();
            }
        }

    };
}
