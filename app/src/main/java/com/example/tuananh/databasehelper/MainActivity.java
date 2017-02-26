package com.example.tuananh.databasehelper;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.tuananh.databasehelper.adapter.SearchRecyclerAdapter;
import com.example.tuananh.databasehelper.database.DatabaseManager;
import com.example.tuananh.databasehelper.enums.TypeSearch;
import com.example.tuananh.databasehelper.model.ItemSearch;
import com.tuananh.databasehelper.utils.MapParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private SearchRecyclerAdapter mSearchRecyclerAdapter;
    private List<ItemSearch> mItemSearchList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadData();
        initViews();
    }

    private void loadData() {
        List<Map> mapList = DatabaseManager.getInstance(this).getItemSearch(TypeSearch.DEGREES);
        if (mapList != null) {
            for (Map map : mapList) {
                ItemSearch itemSearch = MapParser.toObject(map, ItemSearch.class);
                mItemSearchList.add(itemSearch);
            }
        }
    }

    private void initViews() {
        mSearchRecyclerAdapter = new SearchRecyclerAdapter(this, mItemSearchList);
        RecyclerView recyclerViewSearch = (RecyclerView) findViewById(R.id.recycler_view_search);
        recyclerViewSearch.setAdapter(mSearchRecyclerAdapter);
        recyclerViewSearch.setLayoutManager(new LinearLayoutManager(this));
    }
}
