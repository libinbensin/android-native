package com.android.foodmark.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.foodmark.R;
import com.android.foodmark.constants.AppConstant;

/**
 * Created by libin on 12/25/13.
 */
public class EmptyFragment extends Fragment
{

    @Override
    public void onActivityCreated(Bundle bundle)
    {
        super.onActivityCreated(bundle);
        TextView textView = (TextView) getView().findViewById(R.id.empty_textView);
        textView.setText(getArguments().getString(AppConstant.TITLE));
    }

    @Override
    public View onCreateView(LayoutInflater inflater , ViewGroup parent ,
                             Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.empty_layout,parent, false);
    }
}
