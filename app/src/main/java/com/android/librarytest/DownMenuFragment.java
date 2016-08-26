package com.android.librarytest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by lenovo on 2016/8/26.
 */
public class DownMenuFragment extends Fragment implements View.OnClickListener{
    int checked;
    LinearLayout linearLayout1;
    LinearLayout linearLayout2;
    LinearLayout linearLayout3;
    ImageView image1;
    ImageView image2;
    ImageView image3;
    int selectedColor;
    int unselectedColor;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.menu_fragment,null);
        selectedColor = getResources().getColor(R.color.selected);
        unselectedColor = getResources().getColor(R.color.unselected);
        linearLayout1 = (LinearLayout) v.findViewById(R.id.linear1);
        linearLayout2 = (LinearLayout) v.findViewById(R.id.linear2);
        linearLayout3 = (LinearLayout) v.findViewById(R.id.linear3);
        image1 = (ImageView) v.findViewById(R.id.home);
        image2 = (ImageView)v.findViewById(R.id.chat);
        image3 = (ImageView) v.findViewById(R.id.settings);
        image1.setOnClickListener(this);
        image2.setOnClickListener(this);
        image3.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.home:
                linearLayout1.setBackgroundColor(selectedColor);
                linearLayout2.setBackgroundColor(unselectedColor);
                linearLayout3.setBackgroundColor(unselectedColor);
                changePage(0);
                break;
            case R.id.chat:
                linearLayout1.setBackgroundColor(unselectedColor);
                linearLayout2.setBackgroundColor(selectedColor);
                linearLayout3.setBackgroundColor(unselectedColor);
                changePage(1);
                break;
            case R.id.settings:
                linearLayout1.setBackgroundColor(unselectedColor);
                linearLayout2.setBackgroundColor(unselectedColor);
                linearLayout3.setBackgroundColor(selectedColor);
                changePage(2);
                break;
        }
    }
    private void changePage(int current){
        ((MainActivity)getActivity()).pageChanging(current);
    }
}
