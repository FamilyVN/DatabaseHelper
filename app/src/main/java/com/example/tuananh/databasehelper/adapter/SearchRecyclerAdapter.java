package com.example.tuananh.databasehelper.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.tuananh.databasehelper.model.ItemSearch;

import java.util.List;

/**
 * Created by framgia on 26/02/2017.
 */
public class SearchRecyclerAdapter
    extends RecyclerView.Adapter<SearchRecyclerAdapter.SearchViewHolder> {
    private List<ItemSearch> mItemSearchList;
    private Context mContext;

    public SearchRecyclerAdapter(Context context, List<ItemSearch> itemSearchList) {
        mContext = context;
        mItemSearchList = itemSearchList;
    }

    @Override
    public SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(SearchViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return mItemSearchList != null ? mItemSearchList.size() : 0;
    }

    public static class SearchViewHolder extends RecyclerView.ViewHolder {
        public SearchViewHolder(View itemView) {
            super(itemView);
        }
    }
}
