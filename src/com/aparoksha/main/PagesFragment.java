package com.aparoksha.main;

import com.aparoksha.main.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PagesFragment extends Fragment {

	   
    public PagesFragment(){}
     
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
  
        View rootView = inflater.inflate(R.layout.pagesfragment, container, false);
          
        return rootView;
    }
}
