package cn.timeplay.blog;

import android.app.Activity;
import android.content.Intent;

public class BaseAcitivity extends Activity
{
	@Override
	protected void onDestroy()
	{
		super.onDestroy();
	}
	
	@Override
	public void startActivity(Intent intent)
	{
		super.startActivity(intent);
		overridePendingTransition(R.anim.push_in_right, R.anim.push_out_left);
		
	}
}
