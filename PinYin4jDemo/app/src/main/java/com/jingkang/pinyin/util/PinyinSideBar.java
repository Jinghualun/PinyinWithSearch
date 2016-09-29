package com.jingkang.pinyin.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.SectionIndexer;

import com.jingkang.pinyin.R;

/**
 * Created by jingkang
 */
public class PinyinSideBar extends View {
    public static final char DEFAULT_INDEX_CHARACTER = '#';
    private static char[] mSelectCharacters = {DEFAULT_INDEX_CHARACTER, 'A',
            'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
            'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    private SectionIndexer mSectionIndexer;
    private char mCurrentSelectedChar;

    private OnPinyinSideBar mOnPinyinSideBar;

    private Paint mCurrentIndexPaint;
    private Paint mOtherIndexPaint;

    public PinyinSideBar(Context context) {
        super(context);
        init();
    }

    public PinyinSideBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PinyinSideBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mCurrentIndexPaint = new Paint();
        mCurrentIndexPaint.setColor(Color.BLUE);
        mCurrentIndexPaint.setTextSize(24);
        mCurrentIndexPaint.setTypeface(Typeface.DEFAULT_BOLD);
        mCurrentIndexPaint.setTextAlign(Paint.Align.CENTER);

        mOtherIndexPaint = new Paint();
        mOtherIndexPaint.setColor(Color.BLACK);
        mOtherIndexPaint.setTextSize(24);
        mOtherIndexPaint.setTypeface(Typeface.DEFAULT_BOLD);
        mOtherIndexPaint.setTextAlign(Paint.Align.CENTER);
    }

    public void setSectionIndexer(SectionIndexer sectionIndexer) {
        this.mSectionIndexer = sectionIndexer;
    }

    public void setOnPinyinSideBar(OnPinyinSideBar onPinyinSideBar) {
        this.mOnPinyinSideBar = onPinyinSideBar;
    }

    public static char[] getSelectCharacters() {
        return mSelectCharacters;
    }

    public static void setSelectCharacters(char[] selectCharacters) {
        PinyinSideBar.mSelectCharacters = selectCharacters;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float xPos = getMeasuredWidth() / 2;
        float yPos;
        if (mSelectCharacters.length > 0) {
            float singleHeight = getMeasuredHeight() / mSelectCharacters.length;
            for (int i = 0; i < mSelectCharacters.length; i++) {
                yPos = (i + 1) * singleHeight;
                canvas.drawText(String.valueOf(mSelectCharacters[i]), xPos, yPos, mSelectCharacters[i] == mCurrentSelectedChar ? mCurrentIndexPaint
                        : mOtherIndexPaint);
            }
        }
        invalidate();
        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        int currentIndex = getCurrentIndex(event);
        mCurrentSelectedChar = mSelectCharacters[currentIndex];
        if ((event.getAction() == MotionEvent.ACTION_DOWN) || (event.getAction() == MotionEvent.ACTION_MOVE)) {
            setBackgroundColor(getResources().getColor(R.color.light_blue));
            if (null != mOnPinyinSideBar) {
                mOnPinyinSideBar.onPinyinSideBarDown(mSelectCharacters[currentIndex]);
            }
            if (null != mSectionIndexer) {
                int position = mSectionIndexer.getPositionForSection(mSelectCharacters[currentIndex]);
                if (position < 0) {
                    return true;
                }
                if (null != mOnPinyinSideBar) {
                    mOnPinyinSideBar.onSelectedPosition(position);
                }
            }
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            setBackgroundColor(getResources().getColor(R.color.transparent));
            if (null != mOnPinyinSideBar) {
                // hide selected textview
                mOnPinyinSideBar.onPinyinSideBarUp(mSelectCharacters[currentIndex]);
            }
        }
        return true;
    }

    private int getCurrentIndex(MotionEvent event) {
        if (null == event) {
            return 0;
        }
        int y = (int) event.getY();
        int index = y / (getMeasuredHeight() / mSelectCharacters.length);
        if (index < 0) {
            index = 0;
        } else if (index >= mSelectCharacters.length) {
            index = mSelectCharacters.length - 1;
        }
        return index;
    }

    public interface OnPinyinSideBar {
        void onPinyinSideBarDown(char selectChar);

        void onPinyinSideBarUp(char selectChar);

        void onSelectedPosition(int position);
    }
}
