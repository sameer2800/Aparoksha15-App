package com.aparoksha.main;

import com.aparoksha.main.R;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
// import android.app.Notification.Action;
import android.app.FragmentTransaction;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Homepage extends FragmentActivity implements
		ActionBar.TabListener, OnClickListener {

	ActionBar		actionBar;
	ViewPager		viewPager;
	MyAdapter		tabsPageAdapter;
	Button			button_left;
	Button			button_right;
	TextView		textView;
	LayoutInflater	layoutInflater;
	View			view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_homepage);

		FragmentManager fragmentManager = getSupportFragmentManager();
		tabsPageAdapter = new MyAdapter(fragmentManager);
		actionBar = getActionBar();
		layoutInflater = LayoutInflater.from(this);
		view = layoutInflater.inflate(R.layout.layout_action_bar, null);

		button_left = (Button) view.findViewById(R.id.action_bar_button_left);
		button_right = (Button) view.findViewById(R.id.action_bar_button_right);
		textView = (TextView) view.findViewById(R.id.action_bar_text_view);
		viewPager = (ViewPager) findViewById(R.id.pager);

		viewPager.setAdapter(tabsPageAdapter);
		button_left.setOnClickListener(this);
		button_right.setOnClickListener(this);

		// actionBar.setHomeButtonEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		// actionBar.setDisplayShowTitleEnabled(true);
		// actionBar.setSubtitle("Tab 1");
		// actionBar.setLogo(R.drawable.ic_launcher);
		// actionBar.setDisplayShowHomeEnabled(true);
		// actionBar.setDisplayUseLogoEnabled(true);

		textView.setText("Tab 1");
		actionBar.setDisplayShowCustomEnabled(true);
		// actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		actionBar.setCustomView(view);

		for (int i = 1; i <= 3; i++) {
			actionBar.addTab(actionBar.newTab().setTabListener(this));
		}

		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			public void onPageScrollStateChanged(int arg0) {
				
				
			}

			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}

			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				actionBar.setSelectedNavigationItem(arg0);
				// actionBar.setTitle("SlideTabs - Tab" + (arg0 + 1));
				// actionBar.setSubtitle("Tab " + (arg0 + 1));
				textView.setText("Tab " + (arg0 + 1));
				
			}			
		});

	}

	class MyAdapter extends FragmentStatePagerAdapter {

		public MyAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}

		@Override
		public Fragment getItem(int arg0) {
			// TODO Auto-generated method stub
			Fragment fragment = null;

			if ((arg0 == 0) ) {
				fragment = new FragmentA();
			} else if ((arg0 == 1) ) {
				fragment = new FragmentB();
			} else if ((arg0 == 2) ) {
				fragment = new FragmentC();
			}

			return fragment;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 3;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			// TODO Auto-generated method stub
			if (position == 0) {
				return "Tab 1";
			} else if (position == 1) {
				return "Tab 2";
			} else if (position == 2) {
				return "Tab 3";
			} else if (position == 3) {
				return "Tab 4";
			} else if (position == 4) {
				return "Tab 5";
			} else if (position == 5) {
				return "Tab 6";
			} else {
				return null;
			}
		}
	}

	
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		viewPager.setCurrentItem(tab.getPosition());
		// actionBar.setTitle("SlideTabs - Tab" + (tab.getPosition() + 1));
	}

	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

	
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

	
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.action_bar_button_left) {
			if (viewPager.getCurrentItem() > 0) {
				viewPager.setCurrentItem(viewPager.getCurrentItem() - 1, true);
			}
		} else if (v.getId() == R.id.action_bar_button_right) {
			if (viewPager.getCurrentItem() < 5) {
				viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
			}
		}
	}

}
