package com.minhaz.rockpaperscissors;



import com.rockpaperscissors.R;
import com.rockpaperscissors.strategy.RandomStrategy;
import com.rockpaperscissors.strategy.SensorStrategy;

import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
/**
 * This activity handle when Gameers choose to play against another human 
 * opponent. Gamers have two choices
 * 1. Select His move by himself
 * 2. Let computer select it for him
 * 	a. shake to select: uses Accelometer value to find next move
 * 	b. touch to select: uses pseudo random number to select next move
 * 
 * @author Minhaz Rafi Chowdhury
 *
 */
public class FriendActivity extends Activity implements OnClickListener, SensorEventListener{
	private static final String TAG="FriendActivity";
	private static final String GAME_PREFERENCE="com.rockpaperscissors.preference";
	/**
	 * Storing gamers selection. one for now my be in future it grow big
	 */
	private static final String FRIEND_MOVE_CHOICE="com.rockpaperscissors.friends_move_choice";
	
	/**
	 * center images when user selecting his own 
	 * move
	 */
	private ImageView user_selection=null;
	/**
	 * Responsible for returning next move.
	 * It uses Game Strategy when needed.
	 */
	private RockPaperScissor mRockPaperScissor=null;
	private SensorManager mSensorManager=null;
	private Sensor mAccelerometer;
	private ImageView selectimage=null;
	private final int[] image={R.drawable.rock, R.drawable.paper, R.drawable.scissor};
	/**
	 * Game Strategy uses sensor value to calculate next move
	 */
	private SensorStrategy mSensorStrategy;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isUserSelectSelf()){
         	Log.d(TAG, "user select slef");
         	setContentView(R.layout.friend_self_select);
         	ImageView rock=(ImageView)findViewById(R.id.image_rock);
             rock.setOnClickListener(this);
             ImageView paper=(ImageView)findViewById(R.id.image_paper);
             paper.setOnClickListener(this);
             ImageView scissor=(ImageView)findViewById(R.id.image_scissor);
             scissor.setOnClickListener(this);
             user_selection=(ImageView)findViewById(R.id.image_user_selection);
         }
         	
        else{
         	Log.d(TAG, "user select shake");
         	/**
         	 * using two separate layout as the same reason we put layout on XML file.
         	 */
         	setContentView(R.layout.friend_shake);
         	mSensorStrategy=new SensorStrategy();
         	mSensorManager=(SensorManager)this.getSystemService(this.SENSOR_SERVICE);
         	mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
         	mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_GAME);
         	mRockPaperScissor=new RockPaperScissor();
         	selectimage=(ImageView)findViewById(R.id.imageView1);
         	selectimage.setOnClickListener(this);
         }
        ActionBar bar=getActionBar();
        bar.setTitle("Opponent Friend");
        bar.setDisplayHomeAsUpEnabled(true);
        bar.show();

        	
        	
    }
    @Override
    public void onPause(){
    	super.onPause();
    	if (!isUserSelectSelf()&&(mSensorManager!=null))
    		mSensorManager.unregisterListener(this);
    }
    @Override
    public void onResume(){
    	super.onResume();
    	//have to make sure that onResume i have everything i need
    	if (!isUserSelectSelf()){
    		if(mSensorManager!=null)
    			mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    		if (mRockPaperScissor==null)
    			mRockPaperScissor=new RockPaperScissor();
    		
    	}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_friend, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {       
    	 switch (item.getItemId()) {
    	    case R.id.menu_computer:
    	    	startActivity(new Intent(FriendActivity.this, ComputerActivity.class)); 
    	      break;
    	    case android.R.id.home:
    	    	startActivity(new Intent(FriendActivity.this, MainActivity.class)); ;
    	      break;
    	    case R.id.menu_self:
    	    	updateUserSelection(true); 
    	    	updateUI(true);
    	      break;
    	    case R.id.menu_shake:
    	    	updateUserSelection(false);
    	    	updateUI(true);
    	      break;

    	    default:
    	      break;
    	    }

    	    return true;
    }
    /**
     * This method read persistence data and find out user selection.
     * @return
     */
    private boolean isUserSelectSelf(){
    	SharedPreferences sharedPref = getSharedPreferences(GAME_PREFERENCE,MODE_PRIVATE);
    	if (sharedPref.contains(FRIEND_MOVE_CHOICE)){
    		return sharedPref.getBoolean(FRIEND_MOVE_CHOICE, false);
    	}
    	
    	return false;
    }
    private void updateUI(boolean selection){
    	if (selection)
    		setContentView(R.layout.friend_self_select);
		else
    	setContentView(R.layout.friend_self_select);
    	//just forcing activity to recreate. It blink the UI which i am not very found of.
    	this.recreate();
    }
    private void updateUserSelection(boolean selection){
    	SharedPreferences sharedPref = getSharedPreferences(GAME_PREFERENCE,MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = sharedPref.edit();
        prefEditor.putBoolean(FRIEND_MOVE_CHOICE,selection);
        prefEditor.commit();
    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId()==R.id.imageView1){
			int move=mRockPaperScissor.getNextMove(new RandomStrategy());
			Log.d(TAG, "current touch index"+move);
			if (move>=0)
				changeImage(move);
			return;
		}
		if (v instanceof ImageView){
			ImageView iv=(ImageView)v;
			user_selection.setImageDrawable(iv.getDrawable());
			makeSound();
		}
		
		
	}


	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		mSensorStrategy.setEvent(event);
	    int index=mRockPaperScissor.getNextMove(mSensorStrategy);
	    if (index>=0)
	    	changeImage(index);
	    	
	    
	}
	/**
	 * Changing UI to given image. Sole reason to make it Synchronized is to make
	 * sure that when user using Sensor event and the same touch to select 
	 * data stay consistent. Specially tone generator always take some time to 
	 * process its tone.
	 * @param index
	 */
	public synchronized void changeImage(int index){
		Log.d(TAG, "Current index Value"+ index);
    	selectimage.setImageResource(image[index]);
    	makeSound();
    	
	}
	/**
	 * Responsible for tone generating
	 * Make it synchronized as on some older
	 * devices its take some time. I do not want
	 * user shake and touch at the same and that
	 * make application crash.
	 */
	public synchronized void makeSound(){
		ToneGenerator tg = new ToneGenerator(AudioManager.STREAM_NOTIFICATION, ToneGenerator.MAX_VOLUME);
    	tg.startTone(ToneGenerator.TONE_PROP_BEEP);
    	tg.release();
    	
	}
}
