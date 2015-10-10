package com.aparoksha.main;

import com.aparoksha.main.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings.PluginState;
import android.widget.Button;
import android.widget.ImageButton;

public class WebPage extends Activity implements OnClickListener {

	private class MyWebViewClient extends WebViewClient {

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			// TODO Auto-generated method stub
			view.loadUrl(url);

			return super.shouldOverrideUrlLoading(view, url);
		}
	}

	WebView		webView;
	Button		button_back;
	Button		button_reload;
	Button		button_next;
	ImageButton	button_facebook;
	ImageButton	button_website;
	boolean		openFacebook;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web_page);

		mapViews();
		changeSettings();
		addListeners();

		openFacebook = getIntent().getExtras().getBoolean("OpenFacebook");
		loadWebPage();
	}

	private void mapViews() {
		webView = (WebView) findViewById(R.id.webPage_webView);
		button_back = (Button) findViewById(R.id.webPage_button_Back);
		button_next = (Button) findViewById(R.id.webPage_button_Next);
		button_reload = (Button) findViewById(R.id.webPage_button_Refresh);
		button_facebook = (ImageButton) findViewById(R.id.webPage_button_Facebook);
		button_website = (ImageButton) findViewById(R.id.webPage_button_Website);
		openFacebook = false;
	}

	private void changeSettings() {
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setLoadWithOverviewMode(true);
		webView.getSettings().setUseWideViewPort(true);
		webView.getSettings().setBuiltInZoomControls(true);
		webView.getSettings().setAllowContentAccess(true);
		webView.getSettings().setAppCacheEnabled(true);
		webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
		webView.getSettings().setPluginState(PluginState.ON);
		webView.getSettings().setSaveFormData(true);
		webView.getSettings().setSavePassword(true);
	}

	private void addListeners() {
		button_back.setOnClickListener(this);
		button_next.setOnClickListener(this);
		button_reload.setOnClickListener(this);
		button_facebook.setOnClickListener(this);
		button_website.setOnClickListener(this);
		webView.setWebViewClient(new MyWebViewClient());
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.webPage_button_Back:
				if (webView.canGoBack()) {
					webView.goBack();
				}
				break;

			case R.id.webPage_button_Next:
				if (webView.canGoForward()) {
					webView.goForward();
				}
				break;

			case R.id.webPage_button_Refresh:
				webView.reload();
				break;

			case R.id.webPage_button_Website:
				webView.loadUrl("http://aparoksha.iiita.ac.in/beta/#home");
				break;

			case R.id.webPage_button_Facebook:
				webView.loadUrl("https://www.facebook.com/aparoksha?fref=ts");
				break;
			default:
				break;
		}
	}

	private void loadWebPage() {
		if (openFacebook) {
			webView.loadUrl("https://www.facebook.com/aparoksha?fref=ts");
		} else {
			webView.loadUrl("http://aparoksha.iiita.ac.in/beta/#home");
		}
	}
}
