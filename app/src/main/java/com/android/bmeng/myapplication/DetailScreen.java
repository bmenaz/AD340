package com.android.bmeng.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DetailScreen extends AppCompatActivity {
    android.support.v7.widget.RecyclerView recyclerView;
    android.support.v7.widget.LinearLayoutManager layoutManager;
    RecyclerViewAdapterTwo adapter;
    android.content.Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_screen);
        Intent intent = getIntent();
        String[] info = intent.getStringArrayExtra("info");
        recyclerView = findViewById(R.id.my_recycler_view);
        context = getApplicationContext();
        layoutManager = new android.support.v7.widget.LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerViewAdapterTwo(info, context);
        recyclerView.setAdapter(adapter);
    }
}
