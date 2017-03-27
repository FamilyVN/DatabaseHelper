package com.example.tuananh.databasehelper;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.tuananh.databasehelper.adapter.SearchRecyclerAdapter;
import com.example.tuananh.databasehelper.database.DatabaseManager;
import com.example.tuananh.databasehelper.enums.TypeSearch;
import com.example.tuananh.databasehelper.model.ItemSearch;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private SearchRecyclerAdapter mSearchRecyclerAdapter;
    private List<ItemSearch> mItemSearchList = new ArrayList<>();
    private TextView mTextViewTitleSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        loadData(TypeSearch.DEGREES);
    }

    private void loadData(TypeSearch typeSearch) {
        mItemSearchList.clear();
        List<ItemSearch> itemSearchList =
            DatabaseManager.getInstance(this).getItemSearch(typeSearch);
        if (itemSearchList != null) {
            mItemSearchList.addAll(itemSearchList);
        }
        mSearchRecyclerAdapter.notifyDataSetChanged();
    }

    private void initViews() {
        mTextViewTitleSearch = (TextView) findViewById(R.id.text_title_search);
        mSearchRecyclerAdapter = new SearchRecyclerAdapter(this, mItemSearchList);
        RecyclerView recyclerViewSearch = (RecyclerView) findViewById(R.id.recycler_view_search);
        recyclerViewSearch.setAdapter(mSearchRecyclerAdapter);
        recyclerViewSearch.setLayoutManager(new LinearLayoutManager(this));
        mTextViewTitleSearch.setText(R.string.text_test_title_degree);
        findViewById(R.id.button_next).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_next:
                break;
        }
    }
}
