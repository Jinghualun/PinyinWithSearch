package com.jingkang.pinyin.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.jingkang.pinyin.R;
import com.jingkang.pinyin.test.TestModel;
import com.pinyinsearch.util.QwertyMatchPinyinUnits;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by jingkang
 */
public class SearchAdapter extends BaseAdapter implements Filterable, SectionIndexer {
    /**
     * Lock used to modify the content of {@link #mObjects}. Any write operation
     * performed on the array should be synchronized on this lock. This lock is also
     * used by the filter (see {@link #getFilter()} to make a synchronized copy of
     * the original array of data.
     */
    private final Object mLock = new Object();
    private LayoutInflater mInflater;

    /**
     * Contains the list of objects that represent the data of this ArrayAdapter.
     * The content of this list is referred to as "the array" in the documentation.
     */
    private List<TestModel> mObjects;

    /**
     * Indicates whether or not {@link #notifyDataSetChanged()} must be called whenever
     * {@link #mObjects} is modified.
     */
    private boolean mNotifyOnChange = true;

    private Context mContext;

    // A copy of the original mObjects array, initialized from and then used instead as soon as
    // the mFilter ArrayFilter is used. mObjects will then only contain the filtered values.
    private ArrayList<TestModel> mOriginalValues;
    private ArrayFilter mFilter;

    public SearchAdapter(Context context, List<TestModel> objects) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.mObjects = objects;
    }

    /**
     * Adds the specified object at the end of the array.
     *
     * @param object The object to add at the end of the array.
     */
    public void add(TestModel object) {
        synchronized (mLock) {
            if (mOriginalValues != null) {
                mOriginalValues.add(object);
            } else {
                mObjects.add(object);
            }
        }
        if (mNotifyOnChange) notifyDataSetChanged();
    }

    /**
     * Adds the specified Collection at the end of the array.
     *
     * @param collection The Collection to add at the end of the array.
     */
    public void addAll(Collection<? extends TestModel> collection) {
        synchronized (mLock) {
            if (mOriginalValues != null) {
                mOriginalValues.addAll(collection);
            } else {
                mObjects.addAll(collection);
            }
        }
        if (mNotifyOnChange) notifyDataSetChanged();
    }

    /**
     * Adds the specified items at the end of the array.
     *
     * @param items The items to add at the end of the array.
     */
    public void addAll(TestModel... items) {
        synchronized (mLock) {
            if (mOriginalValues != null) {
                Collections.addAll(mOriginalValues, items);
            } else {
                Collections.addAll(mObjects, items);
            }
        }
        if (mNotifyOnChange) notifyDataSetChanged();
    }

    /**
     * Inserts the specified object at the specified index in the array.
     *
     * @param object The object to insert into the array.
     * @param index  The index at which the object must be inserted.
     */
    public void insert(TestModel object, int index) {
        synchronized (mLock) {
            if (mOriginalValues != null) {
                mOriginalValues.add(index, object);
            } else {
                mObjects.add(index, object);
            }
        }
        if (mNotifyOnChange) notifyDataSetChanged();
    }

    /**
     * Removes the specified object from the array.
     *
     * @param object The object to remove.
     */
    public void remove(TestModel object) {
        synchronized (mLock) {
            if (mOriginalValues != null) {
                mOriginalValues.remove(object);
            } else {
                mObjects.remove(object);
            }
        }
        if (mNotifyOnChange) notifyDataSetChanged();
    }

    /**
     * Remove all elements from the list.
     */
    public void clear() {
        synchronized (mLock) {
            if (mOriginalValues != null) {
                mOriginalValues.clear();
            } else {
                mObjects.clear();
            }
        }
        if (mNotifyOnChange) notifyDataSetChanged();
    }

    /**
     * Sorts the content of this adapter using the specified comparator.
     *
     * @param comparator The comparator used to sort the objects contained
     *                   in this adapter.
     */
    public void sort(Comparator<? super TestModel> comparator) {
        synchronized (mLock) {
            if (mOriginalValues != null) {
                Collections.sort(mOriginalValues, comparator);
            } else {
                Collections.sort(mObjects, comparator);
            }
        }
        if (mNotifyOnChange) notifyDataSetChanged();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        mNotifyOnChange = true;
    }

    /**
     * Control whether methods that change the list ({@link #add},
     * {@link #insert}, {@link #remove}, {@link #clear}) automatically call
     * {@link #notifyDataSetChanged}.  If set to false, caller must
     * manually call notifyDataSetChanged() to have the changes
     * reflected in the attached view.
     * <p>
     * The default is true, and calling notifyDataSetChanged()
     * resets the flag to true.
     *
     * @param notifyOnChange if true, modifications to the list will
     *                       automatically call {@link
     *                       #notifyDataSetChanged}
     */
    public void setNotifyOnChange(boolean notifyOnChange) {
        mNotifyOnChange = notifyOnChange;
    }

    /**
     * Returns the context associated with this array adapter. The context is used
     * to create views from the resource passed to the constructor.
     *
     * @return The Context associated with this adapter.
     */
    public Context getContext() {
        return mContext;
    }

    @Override
    public int getCount() {
        return mObjects.size();
    }

    @Override
    public TestModel getItem(int position) {
        return mObjects.get(position);
    }

    /**
     * Returns the position of the specified item in the array.
     *
     * @param item The item to retrieve the position of.
     * @return The position of the specified item.
     */
    public int getPosition(TestModel item) {
        return mObjects.indexOf(item);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        com.jingkang.pinyin.test.TestModel testModel = mObjects.get(position);
        if (null == convertView) {
            convertView = mInflater.inflate(R.layout.listview_item_pinyin, null);
            holder = new ViewHolder();
            holder.tvAlphbet = (TextView) convertView.findViewById(R.id.tv_alphabet);
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tvMobile = (TextView) convertView.findViewById(R.id.tv_mobile);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        showAlphabetIndex(holder.tvAlphbet, position, testModel);
        holder.tvName.setText(testModel.getName());
        holder.tvMobile.setText(testModel.getMobile());
        return convertView;
    }

    private static class ViewHolder{
        public TextView tvAlphbet;
        public TextView tvName;
        public TextView tvMobile;
    }

    private void showAlphabetIndex(TextView textView, int position, final TestModel testModel) {
        if ((null == textView) || position < 0 || (null == testModel)) {
            return;
        }
        String currentAlphabet = getAlphabet(testModel.getSortKey());
        if (position > 0) {
            com.jingkang.pinyin.test.TestModel preTestModel = getItem(position - 1);
            String preAlphabet = getAlphabet(preTestModel.getSortKey());
            if (currentAlphabet.equals(preAlphabet)) {
                textView.setVisibility(View.GONE);
                textView.setText(currentAlphabet);
            } else {
                textView.setVisibility(View.VISIBLE);
                textView.setText(currentAlphabet);
            }
        } else {
            textView.setVisibility(View.VISIBLE);
            textView.setText(currentAlphabet);
        }
    }

    private String getAlphabet(String str) {
        if ((null == str) || (str.length() <= 0)) {
            return String.valueOf(PinyinSideBar.DEFAULT_INDEX_CHARACTER);
        }
        String alphabet;
        char chr = str.charAt(0);
        if (chr >= 'A' && chr <= 'Z') {
            alphabet = String.valueOf(chr);
        } else if (chr >= 'a' && chr <= 'z') {
            alphabet = String.valueOf((char) ('A' + chr - 'a'));
        } else {
            alphabet = String.valueOf(PinyinSideBar.DEFAULT_INDEX_CHARACTER);
        }
        return alphabet;
    }

    @Override
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new ArrayFilter();
        }
        return mFilter;
    }

    @Override
    public Object[] getSections() {
        return null;
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        TestModel testModel;
        if (PinyinSideBar.DEFAULT_INDEX_CHARACTER == sectionIndex) {
            return 0;
        } else {
            int count = getCount();
            for (int i = 0; i < count; i++) {
                testModel = getItem(i);
                char firstChar = testModel.getSortKey().charAt(0);
                if (firstChar == sectionIndex) {
                    return i;
                }
            }
        }
        return -1;
    }

    @Override
    public int getSectionForPosition(int position) {
        return 0;
    }

    /**
     * <p>An array filter constrains the content of the array adapter with
     * a constrain. Each item that does not contain the supplied constrain
     * is removed from the list.</p>
     */
    protected class ArrayFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constrain) {
            FilterResults results = new FilterResults();

            if (mOriginalValues == null) {
                synchronized (mLock) {
                    mOriginalValues = new ArrayList<TestModel>(mObjects);
                }
            }

            if (constrain == null || constrain.length() == 0) {
                ArrayList<TestModel> list;
                synchronized (mLock) {
                    list = new ArrayList<TestModel>(mOriginalValues);
                }
                results.values = list;
                results.count = list.size();
            } else {
                String constrainString = constrain.toString().toLowerCase();

                ArrayList<TestModel> values;
                synchronized (mLock) {
                    values = new ArrayList<TestModel>(mOriginalValues);
                }

                final int count = values.size();
                final ArrayList<TestModel> newValues = new ArrayList<TestModel>();
                for (int i = 0; i < count; i++) {
                    final TestModel value = values.get(i);
                    StringBuffer chineseKeyWord = new StringBuffer();
                    boolean match = QwertyMatchPinyinUnits.matchPinyinUnits(value.getPinyinUnit(), value.getName(), constrainString, chineseKeyWord);
                    if (match) {
                        newValues.add(value);
                        if (null != chineseKeyWord && chineseKeyWord.length() > 0) {
                            chineseKeyWord.delete(0, chineseKeyWord.length());
                        }
                    } else if (value.getMobile().contains(constrainString)) {
                        newValues.add(value);
                    }
                }

                results.values = newValues;
                results.count = newValues.size();
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            // noinspection unchecked
            mObjects = (List<TestModel>) results.values;
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    }

}
