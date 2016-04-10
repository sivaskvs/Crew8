package com.myapplication.siva.cubbalance;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.view.View;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onCheck(View view){
    Intent i = new Intent(this,SmsActivity.class);
        startActivity(i);

    }
    public void onTandc(View view){
        Intent i = new Intent(this,TandC.class);
        startActivity(i);

    }

    public void onAboutus(View view){
        Intent i = new Intent(this,AboutUs.class);
        startActivity(i);

    }


}
