package com.bmeng.myapplication;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class ZombieLayout extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zombie_layout);

        //toolbar - might be used as a fragment
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Zombie Layout");

    }
    public void topLeft(View v){
        android.content.Intent intent = new android.content.Intent(getApplicationContext(), ListDisplay.class);
        startActivity(intent);
    }

    public void bottomLeft(View v){
        android.widget.Toast.makeText(this, "Bottom Left Button was clicked", android.widget.Toast.LENGTH_SHORT).show();
    }

    public void topRight(View v){
        android.widget.Toast.makeText(this, "Top Right Button was clicked", android.widget.Toast.LENGTH_SHORT).show();
    }

    public void bottomRight(View v){
        android.widget.Toast.makeText(this, "Bottom Right Button was clicked", android.widget.Toast.LENGTH_SHORT).show();
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
