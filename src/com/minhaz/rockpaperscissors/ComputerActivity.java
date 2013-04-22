package com.minhaz.rockpaperscissors;

import com.rockpaperscissors.R;
import com.rockpaperscissors.strategy.HistoryMatchingStrategy;

import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * UI to play against Computer. It uses Naive implementation
 * of HistoryMatching strategy. 
 * @author minhaz
 *
 */
public class ComputerActivity extends Activity implements OnClickListener{
	private static final String TAG="ComputerActivity";
	private StringBuilder userMoves=new StringBuilder("");
	private ImageView rock=null;
	private ImageView paper=null;
	private ImageView scissor=null;
	private ImageView user_selection=null;
	private ImageView computer_choice=null;
	HistoryMatchingStrategy mHistoryMatchingStrategy;
	RockPaperScissor mRockPaperScissor=null;
	TextView mUserScore=null;
	TextView mComputerScore=null;
	private final int[] image={R.drawable.rock, R.drawable.paper, R.drawable.scissor};
	private int computerScore;
	private int userScore;
	char[] array={'R', 'P', 'S'};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_computer);
        rock=(ImageView)findViewById(R.id.image_rock);
        rock.setOnClickListener(this);
        paper=(ImageView)findViewById(R.id.image_paper);
        paper.setOnClickListener(this);
        scissor=(ImageView)findViewById(R.id.image_scissor);
        scissor.setOnClickListener(this);
        user_selection=(ImageView)findViewById(R.id.image_user_selection);
        computer_choice=(ImageView)findViewById(R.id.image_computer_play);
        mHistoryMatchingStrategy=new HistoryMatchingStrategy();
        mRockPaperScissor=new RockPaperScissor();
        mUserScore=(TextView)findViewById(R.id.text_user_score);
        mComputerScore=(TextView)findViewById(R.id.text_computer_score);
        ActionBar bar=getActionBar();
        bar.setTitle("Opponent Computer");
        bar.setDisplayHomeAsUpEnabled(true);
        bar.show();
        mComputerScore.setText(String.valueOf(computerScore));
		mUserScore.setText(String.valueOf(userScore));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_computer, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {       
    	 switch (item.getItemId()) {
    	    case R.id.action_friend:
    	    	startActivity(new Intent(ComputerActivity.this, FriendActivity.class)); 
    	      break;
    	    case android.R.id.home:
    	    	startActivity(new Intent(ComputerActivity.this, MainActivity.class)); ;
    	      break;

    	    default:
    	      break;
    	    }

    	    return true;
    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		makeSound();
		int user_move;
		if (v instanceof ImageView){
			ImageView iv=(ImageView)v;
			user_selection.setImageDrawable(iv.getDrawable());
		}
		//storing user move
		userMoves.append((String)v.getTag());
		mHistoryMatchingStrategy.setMoves(userMoves.toString());
		int next_move=mRockPaperScissor.getNextMove(userMoves.toString(), mHistoryMatchingStrategy);
		if (next_move>=0){
			computer_choice.setImageResource(image[next_move]);
			updateScore(((String)v.getTag()).charAt(0), array[next_move]);
		}
		
	}
	/**
	 * This method create beep sound
	 */
	private void makeSound(){
		ToneGenerator tg = new ToneGenerator(AudioManager.STREAM_NOTIFICATION, ToneGenerator.MAX_VOLUME);
    	tg.startTone(ToneGenerator.TONE_PROP_BEEP);
    	tg.release();
    	
	}
	/**
	 * Responsible for updating Score
	 * @param userMove user current move
	 * @param computerMoves move of opponent
	 */
	private void updateScore(char userMove, char computerMoves){
		if (userMove==computerMoves) return;
		switch(userMove){
		case 'R' : 
			if (computerMoves=='P') computerScore++;
			else if (computerMoves=='S') userScore++;
			break;
		case 'P' : 
			if (computerMoves=='R') userScore++;
			else if (computerMoves=='S') computerScore++;
			break;
		case 'S' : 
			if (computerMoves=='P') userScore++;
			else if (computerMoves=='R') computerScore++;
			break;
		default :
			break;
		}
		mComputerScore.setText(String.valueOf(computerScore));
		mUserScore.setText(String.valueOf(userScore));
		
	}
}
