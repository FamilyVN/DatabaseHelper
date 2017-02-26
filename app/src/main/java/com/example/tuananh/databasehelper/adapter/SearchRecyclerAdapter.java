package com.example.tuananh.databasehelper.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tuananh.databasehelper.R;
import com.example.tuananh.databasehelper.model.ItemSearch;

import java.util.List;

/**
 * Created by framgia on 26/02/2017.
 */
public class SearchRecyclerAdapter
    extends RecyclerView.Adapter<SearchRecyclerAdapter.SearchViewHolder> {
    private List<ItemSearch> mItemSearchList;
    private LayoutInflater mLayoutInflater;

    public SearchRecyclerAdapter(Context context, List<ItemSearch> itemSearchList) {
        mItemSearchList = itemSearchList;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.item_search, parent, false);
        return new SearchViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SearchViewHolder holder, int position) {
        ItemSearch itemSearch = mItemSearchList.get(position);
        holder.mTextViewId.setText(String.valueOf(itemSearch.getId()));
        holder.mTextViewValueEn.setText(itemSearch.getEnValue());
        holder.mTextViewValueJa.setText(itemSearch.getJaValue());
    }

    @Override
    public int getItemCount() {
        return mItemSearchList != null ? mItemSearchList.size() : 0;
    }

    public static class SearchViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextViewId, mTextViewValueEn, mTextViewValueJa;

        public SearchViewHolder(View itemView) {
            super(itemView);
            mTextViewId = (TextView) itemView.findViewById(R.id.text_id);
            mTextViewValueEn = (TextView) itemView.findViewById(R.id.text_value_en);
            mTextViewValueJa = (TextView) itemView.findViewById(R.id.text_value_ja);
        }
    }
}
