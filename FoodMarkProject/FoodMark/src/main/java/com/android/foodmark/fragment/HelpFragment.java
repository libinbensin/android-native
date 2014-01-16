package com.android.foodmark.fragment;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import com.android.foodmark.R;
import com.android.foodmark.adapter.HelpAdapter;
import com.android.foodmark.model.HelpData;

/**
 * Created by libin on 1/4/14.
 */
public class HelpFragment extends ListFragment
{
    private HelpAdapter mHelpAdapter;

    @Override
    public void onActivityCreated(Bundle savedInstance)
    {
        super.onActivityCreated(savedInstance);
        mHelpAdapter = new HelpAdapter(getActivity());
        setListAdapter(mHelpAdapter);
        loadData();
    }


    private void loadData()
    {
        // attach items

        HelpData helpData = null;

        TypedArray ta = getResources().obtainTypedArray(R.array.help_items);
        int n = ta.length();
        String[] array ;
        for (int i = 0; i < n; ++i) {
            int id = ta.getResourceId(i, 0);
            if (id > 0) {
                array = getResources().getStringArray(id);
                helpData = new HelpData();
                helpData.setTitle(array[0]);
                helpData.setDescription(array[1]);
                mHelpAdapter.add(helpData);

            } else {
                // something wrong with the XML
            }
        }
        ta.recycle();

    }

    @Override
    public void onResume()
    {
        super.onResume();
    }

    @Override
    public void onListItemClick(ListView listView , View view , int position , long id)
    {
        switch (position)
        {

            default:
                break;
        }
    }


}
