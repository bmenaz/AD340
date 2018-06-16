package com.bmeng.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class TextSender extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "android.bmeng.text";

    DrawerLayout drawer;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_sender);


        drawer = findViewById(R.id.drawer_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24px);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener(){
                    @Override
                    public boolean onNavigationItemSelected(MenuItem item){
                        item.setChecked(false);
                        switch(item.getItemId()){
                            case R.id.menu_movies:
                                Intent intent = new Intent(getApplicationContext(), ListDisplay.class);
                                startActivity(intent);
                                break;
                            case R.id.about:
                                Intent intent1 = new Intent(getApplicationContext(), About.class);
                                startActivity(intent1);
                                break;
                            case R.id.location:
                                Intent intent2 = new Intent(getApplicationContext(), LocationIdentifierActivity.class);
                                startActivity(intent2);
                                break;
                            case R.id.traffic_cam:
                                Intent intent3 = new Intent(getApplicationContext(), TrafficCamLocationsActivity.class);
                                startActivity(intent3);
                                break;
                        }
                        return true;
                    }
                });
        //access the data if it was saved before
        sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        String entry = sharedPreferences.getString(getString(R.string.shared_preference_key), "");
        EditText editText = findViewById(R.id.editText);
        editText.setText(entry);
    }
    public void sendMessage(View view) {
        Intent intent = new Intent(this, TextDisplay.class);
        EditText editText = (EditText) findViewById(R.id.editText);
        String message = editText.getText().toString();
        //Save the data and prevent from navigating
        if (!message.equals("")) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(getString(R.string.shared_preference_key), message);
            editor.commit();
            intent.putExtra(EXTRA_MESSAGE, message);
            startActivity(intent);
        }
        else {
            Toast.makeText(getApplicationContext(), "Message wasn't entered", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                drawer.openDrawer(GravityCompat.START);
                break;
            case R.id.settings:
                Toast.makeText(getApplicationContext(), "Setting button", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.setting_menu, menu);
        return true;
    }
}
