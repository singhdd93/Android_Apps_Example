package com.example.trendycovers;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends Activity
{
  WebView webView;

  public void onConfigurationChanged(Configuration paramConfiguration)
  {
    super.onConfigurationChanged(paramConfiguration);
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(R.layout.activity_main);
    final TextView localTextView = (TextView)findViewById(R.id.tV1);
    final ProgressBar localProgressBar = (ProgressBar)findViewById(R.id.pB1);
    this.webView = ((WebView)findViewById(R.id.webView));
    this.webView.getSettings().setJavaScriptEnabled(true);
    this.webView.getSettings().setDomStorageEnabled(true);
    this.webView.getSettings().setSupportMultipleWindows(true);
    this.webView.getSettings().setCacheMode(-1);
    this.webView.setWebChromeClient(new WebChromeClient()
    {
      @SuppressLint({"SetJavaScriptEnabled"})
      public void onProgressChanged(WebView paramAnonymousWebView, int paramAnonymousInt)
      {
        localProgressBar.setProgress(paramAnonymousInt);
        if ((paramAnonymousInt > 60) && (localProgressBar.getVisibility() == 0))
        {
          localProgressBar.setVisibility(8);
          localTextView.setVisibility(8);
          MainActivity.this.webView.setVisibility(0);
        }
      }
    });
    this.webView.loadUrl("http://m.trendycovers.com");
    this.webView.setWebViewClient(new WebViewClient()
    {
      public boolean shouldOverrideUrlLoading(WebView paramAnonymousWebView, String paramAnonymousString)
      {
        MainActivity.this.webView.setVisibility(8);
        localProgressBar.setVisibility(0);
        localTextView.setVisibility(0);
        return false;
      }
    });
    this.webView.requestFocus(130);
    this.webView.setOnTouchListener(new View.OnTouchListener()
    {
      public boolean onTouch(View paramAnonymousView, MotionEvent paramAnonymousMotionEvent)
      {
        switch (paramAnonymousMotionEvent.getAction())
        {
        default:
        case 0:
        case 1:
        }
        while (true)
        {
          return false;
         // if (!paramAnonymousView.hasFocus())
          //  paramAnonymousView.requestFocus();
        }
      }
    });
    ((ImageView)findViewById(R.id.search_button)).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        MainActivity.this.webView.loadUrl("file:///android_asset/www/search.html");
      }
    });
    ((ImageView)findViewById(R.id.title)).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        MainActivity.this.webView.loadUrl("http://m.trendycovers.com");
      }
    });
    ((ImageView)findViewById(R.id.back_button)).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        MainActivity.this.webView.loadUrl("file:///android_asset/www/category.html");
      }
    });
  }

  public boolean onCreateOptionsMenu(Menu paramMenu)
  {
    super.onCreateOptionsMenu(paramMenu);
    getMenuInflater().inflate(R.menu.activity_main, paramMenu);
    return true;
  }

  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    if (paramKeyEvent.getAction() == 0);
    switch (paramInt)
    {
    default:
      return super.onKeyDown(paramInt, paramKeyEvent);
    case 4:
      if (this.webView.canGoBack())
      {
        this.webView.goBack();
        return true;
      }
      finish();
      return true;
    case 84:
    }
    this.webView.loadUrl("file:///android_asset/www/search.html");
    return true;
  }

  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    switch (paramMenuItem.getItemId())
    {
    default:
      return false;
    case R.id.back:
      this.webView.goBack();
      return true;
    case R.id.refresh:
      this.webView.reload();
      return true;
    case R.id.about:
      this.webView.loadUrl("file:///android_asset/www/about.html");
      return true;
    case R.id.homes:
      this.webView.loadUrl("http://m.trendycovers.com");
      return true;
    case R.id.help:
      this.webView.loadUrl("http://m.trendycovers.com/help.html");
      return true;
    case R.id.exit:
    }
    this.webView.clearCache(true);
    finish();
    return true;
  }
}