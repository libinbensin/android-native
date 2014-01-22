package com.android.foodbook.fragment;

import com.android.foodbook.R;
import com.android.foodbook.constants.AppConstant;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public abstract class AlertDialogFragment extends DialogFragment
{
	public AlertDialogFragment()
	{	
		
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		super.onCreateDialog(savedInstanceState);
		Builder builder = new AlertDialog.Builder(getActivity().getActionBar().getThemedContext());
		AlertDialog dialog = builder.create();

		dialog.setTitle(getResources().getString(R.string.alert_call_title));
        dialog.setMessage(getResources().getString(R.string.alert_call_msg));
		dialog.setButton(DialogInterface.BUTTON_POSITIVE, AppConstant.OK,new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) 
			{	
				positiveButtonClicked();
			}
		});
		
		dialog.setButton(DialogInterface.BUTTON_NEGATIVE, AppConstant.CANCEL,new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) 
			{	
				negativeButtonClicked();
			}
		});
		
		return dialog;
		
	}
	
	@Override
	public void onStart()
	{
		super.onStart();
		
		/*Button pButton =  ((AlertDialog) getDialog()).getButton(DialogInterface.BUTTON_POSITIVE);
		Button nButton =  ((AlertDialog) getDialog()).getButton(DialogInterface.BUTTON_NEGATIVE);
		
		pButton.setBackgroundColor(getResources().getColor(R.color.DarkGray));
		nButton.setBackgroundColor(getResources().getColor(R.color.DarkGray));*/
	}
	
	protected abstract void positiveButtonClicked();
	
	protected abstract void negativeButtonClicked();
}
