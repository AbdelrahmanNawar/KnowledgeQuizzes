package com.example.nawar.quizes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.nawar.quizes.model.Category;
import com.example.nawar.quizes.R;

public class CategoriesAdapter extends BaseAdapter {
    private Category[] mCategories;
    private Context mContext;
    public CategoriesAdapter(Context context,Category[] categories) {
        mCategories=categories;
        mContext=context;
    }

    @Override
    public int getCount() {
        return mCategories.length;

    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final Category category = mCategories[i];

        if (view == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            view = layoutInflater.inflate(R.layout.category_item, null);
        }
        final ImageView imageView = view.findViewById(R.id.categoryIV);
        imageView.setImageResource(category.getImgResId());

        return view;    }
}
