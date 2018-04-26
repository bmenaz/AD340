package com.android.bmeng.myapplication;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ZombieLayout extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zombie_layout);

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
}
