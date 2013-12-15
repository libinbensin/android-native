package com.android.foodmark.fragment;

import com.android.foodmark.constants.AppConstants;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.ContextThemeWrapper;

public abstract class AlertDialogFragment extends DialogFragment
{
	public AlertDialogFragment()
	{	
		
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		super.onCreateDialog(savedInstanceState);
		Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(getActivity(),android.R.style.Theme_Light));
		AlertDialog dialog = builder.create();
		
		dialog.setTitle("You will be leaving the app to make call");
		dialog.setButton(DialogInterface.BUTTON_POSITIVE, AppConstants.OK,new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) 
			{	
				positiveButtonClicked();
			}
		});
		
		dialog.setButton(DialogInterface.BUTTON_NEGATIVE, AppConstants.CANCEL,new OnClickListener() {
			
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
