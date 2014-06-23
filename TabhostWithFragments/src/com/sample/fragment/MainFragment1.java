package com.sample.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MainFragment1 extends Fragment {
	
    @Override
    public View onCreateView(LayoutInflater inflater, 
    ViewGroup container, Bundle savedInstanceState) {
        Log.d("Fragment 1", "onCreateView");
        return inflater.inflate(
            R.layout.main_fragment1, container, false);
    }
    
}
