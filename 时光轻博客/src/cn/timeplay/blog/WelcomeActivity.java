package cn.timeplay.blog;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

public class WelcomeActivity extends BaseAcitivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		
		new Timer().schedule(new TimerTask()
		{
			
			@Override
			public void run()
			{
				Intent intent = new Intent(WelcomeActivity.this,MainActivity.class);
				startActivity(intent);
				WelcomeActivity.this.finish();
			}
		}, 1500);
	}
}
