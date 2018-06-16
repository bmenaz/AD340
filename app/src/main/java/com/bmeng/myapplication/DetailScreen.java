package com.bmeng.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

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
        //toolbar - might be used as a fragment
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Movie detail");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId()==R.id.settings)
            Toast.makeText(getApplicationContext(), "Setting button", Toast.LENGTH_LONG).show();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.setting_menu, menu);
        return true;
    }
}
