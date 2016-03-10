package com.intedemoapp.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.intedemoapp.R;
import com.library.viewpagerindicator.CirclePageIndicator;


public class ScenarioFirstFragment extends Fragment
{

    public ScenarioFirstFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ScenarioFirstFragment.
     */
    public static ScenarioFirstFragment newInstance()
    {
        ScenarioFirstFragment fragment = new ScenarioFirstFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        View view  = inflater.inflate(R.layout.fragment_scenario_first, container, false);
        final TextView clickedtextView = (TextView)view.findViewById(R.id.clickedtextView);

        LinearLayout linearLayout = (LinearLayout)view.findViewById(R.id.horizontalScrollView_child_container);

        for (int i=0;i<5;i++)
        {
            LayoutInflater hsvInflater = LayoutInflater.from(getActivity());
            LinearLayout itemView = (LinearLayout) hsvInflater.inflate(R.layout.item_layout,null);
            linearLayout.addView(itemView);

            TextView subItem = (TextView)itemView.getChildAt(0);
            if(subItem!=null)
            {
                subItem.setText("Item"+(i+1));
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    if (v instanceof LinearLayout)
                    {
                        LinearLayout clickedItem = (LinearLayout) v;
                        TextView clickedText = (TextView)clickedItem.getChildAt(0);
                        if(clickedText!=null)
                        {
                            CharSequence text = clickedText.getText();
                            clickedtextView.setText(text);
                        }

                    }
                }
            });
        }

        final LinearLayout buttonLayout = (LinearLayout)view.findViewById(R.id.layout_button);
        Button buttonRed = (Button)view.findViewById(R.id.buttonRed);
        buttonRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                buttonLayout.setBackgroundColor(Color.RED);
                buttonLayout.invalidate();
            }
        });
        Button buttonBlue = (Button)view.findViewById(R.id.buttonBlue);
        buttonBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                buttonLayout.setBackgroundColor(Color.BLUE);
                buttonLayout.invalidate();
            }
        });
        Button buttonGreen = (Button)view.findViewById(R.id.buttonGreen);
        buttonGreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                buttonLayout.setBackgroundColor(Color.GREEN);
                buttonLayout.invalidate();
            }
        });

        ViewPager mPager = (ViewPager)view.findViewById(R.id.pager);
        TestFragmentAdapter mAdapter = new TestFragmentAdapter(getFragmentManager());
        mPager.setAdapter(mAdapter);
        CirclePageIndicator mIndicator = (CirclePageIndicator)view.findViewById(R.id.indicator);
        mIndicator.setViewPager(mPager);

        return view;
    }

}
