package com.minhaz.rockpaperscissors;

import com.rockpaperscissors.R;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {
	private static final String TAG="MainActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar bar=getActionBar();
        bar.setTitle("Rock Paper Scissors");
        bar.setDisplayHomeAsUpEnabled(true);
        bar.show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    /**
     * Button click event when user wish to play againes computer
     * @param view
     */
    public void computerClick(View view){
    	Log.d(TAG, "User want to play againest Computer");
    	Intent intent=new Intent(this, ComputerActivity.class);
    	startActivity(intent);
    	
    }
    /**
     * Button Click event. when user wants play againest
     * another human opponent
     * @param view
     */
    public void friendClick(View view){
    	Log.d(TAG, "User want to play againest friend");
    	Intent intent=new Intent(this, FriendActivity.class);
    	startActivity(intent);
    	
    }
}
