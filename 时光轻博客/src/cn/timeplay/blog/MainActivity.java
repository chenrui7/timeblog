package cn.timeplay.blog;

import java.util.Timer;
import java.util.TimerTask;

import android.R.anim;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

@SuppressLint("SetJavaScriptEnabled")
public class MainActivity extends ActionBarActivity
{
	
	private WebView	webView;
	private int		pressedTime	= 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		webView = (WebView) this.findViewById(R.id.webView);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setLoadsImagesAutomatically(true);
		webView.setWebChromeClient(new WebChromeClient()
		{
			
		});
		webView.setWebViewClient(new WebViewClient()
		{
			
			@Override
			public void onPageFinished(WebView view, String url)
			{
				super.onPageFinished(view, url);
				
				stopLoadding();
				ActionBar bar = getSupportActionBar();
				if (!url.equals("http://www.timeplay.cn/blog/"))
				{
					bar.setDisplayHomeAsUpEnabled(true);
				}
				else
				{
					bar.setDisplayHomeAsUpEnabled(false);
				}
				String title = view.getTitle();
				if (title == null) return;
				if (title.length() > 30)
				{
					title = title.substring(0, 30) + "..";
				}
				bar.setTitle(title);
			}
			
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon)
			{
				super.onPageStarted(view, url, favicon);
				getSupportActionBar().setTitle("正在加载...");
				showLoadding();
			}
			
			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl)
			{
				super.onReceivedError(view, errorCode, description, failingUrl);
				Toast.makeText(MainActivity.this, "加载失败，请检查网络连接",
						Toast.LENGTH_LONG).show();
			}
			
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url)
			{
				if (url.contains("timeplay.cn") == true)
				{
					view.loadUrl(url);
					return true;
				}
				else
				{
					Intent in = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
					startActivity(in);
					return true;
				}
			}
			
		});
		
		webView.loadUrl(getString(R.string.url));
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.actionbar, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	public void showLoadding()
	{
		View view = findViewById(R.id.action_refresh);
		if (view == null) return;
		Animation animation = AnimationUtils.loadAnimation(this,
				R.anim.spinning);
		view.startAnimation(animation);
	}
	
	public void stopLoadding()
	{
		View view = findViewById(R.id.action_refresh);
		if (view == null) return;
		view.clearAnimation();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack())
		{
			webView.goBack();
			return true;
		}
		else if (keyCode == KeyEvent.KEYCODE_BACK && pressedTime < 1)
		{
			new Timer().schedule(new TimerTask()
			{
				
				@Override
				public void run()
				{
					pressedTime=0;					
				}
			}, 1500);
			Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
			pressedTime++;
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case android.R.id.home:
				if (webView.canGoBack()) webView.goBack();
				break;
			case R.id.action_refresh:
				webView.reload();
				break;
			default:
				break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
		ActionBar bar = super.getSupportActionBar();
		bar.setTitle("时光轻博客");
		bar.setDisplayUseLogoEnabled(true);
		bar.setDisplayShowHomeEnabled(true);
		
	}
	
}
