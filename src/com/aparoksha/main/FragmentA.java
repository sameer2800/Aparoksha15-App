package com.aparoksha.main;

import com.aparoksha.main.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;

public class FragmentA extends Fragment implements OnTouchListener {
	
	ImageView image;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.fragment_a, container, false);
		 
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		image = (ImageView) getActivity(). findViewById(R.id.imageView1);
		image.setOnTouchListener(this);
	}

	public boolean onTouch(View arg0, MotionEvent arg1) {
		// TODO Auto-generated method stub
		
		Intent i = new Intent("com.aparoksha.main.mainactivity");
		startActivity(i);
		
		
		
		
		return false;
	}
	
	
}
