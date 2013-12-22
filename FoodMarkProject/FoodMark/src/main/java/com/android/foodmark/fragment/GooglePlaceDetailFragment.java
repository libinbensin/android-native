package com.android.foodmark.fragment;

import com.android.foodmark.MainApplication;
import com.android.foodmark.R;
import com.android.foodmark.activity.BaseActivity;
import com.android.foodmark.adapter.ReviewAdapter;
import com.android.foodmark.callbacks.GooglePlaceDetailCallback;
import com.android.foodmark.constants.ConfigConstants;
import com.android.foodmark.model.GooglePlaceDetail;
import com.android.foodmark.utils.AppUtil;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GooglePlaceDetailFragment extends Fragment
{
	private OnResultLoadedListener mOnResultLoadedListener = null;
	
	public GooglePlaceDetailFragment() 
	{
	}

	@Override
	public void onActivityCreated(Bundle bundle)
	{
		super.onActivityCreated(bundle);
		Bundle argBundle = this.getArguments();
		fetchData(argBundle.getString("REFERENCE"));
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) 
	{
		if(container == null)
		{
			return null;
		}
		return inflater.inflate(R.layout.place_detail_item, container, false);
	}
	
	
	public void fetchData(final String argReference)
	{
		// show loading
		((BaseActivity)getActivity()).showLoading();
		
		Bundle bundle = new Bundle();
		bundle.putString("REFERENCE", argReference);
		
		getLoaderManager().initLoader(2, bundle, new GooglePlaceDetailCallback(getActivity()) 
		{
			
			@Override
			protected void onLoaderResult(GooglePlaceDetail argGooglePlaceDetail) 
			{
				if(argGooglePlaceDetail != null)
				{
					if(argGooglePlaceDetail.getName() != null)
					{
						((TextView)getView().findViewById(R.id.place_name)).setText(argGooglePlaceDetail.getName());
					}
					if(argGooglePlaceDetail.getAddress() != null)
					{
						((TextView)getView().findViewById(R.id.place_address)).setText(argGooglePlaceDetail.getAddress());
					}
					if(argGooglePlaceDetail.getPhonenumber() != null)
					{
						TextView phoneTextView = ((TextView)getView().findViewById(R.id.phone_number));
						final String phoneNumber = argGooglePlaceDetail.getPhonenumber();
						phoneTextView.setText(phoneNumber);
						ImageView phoneIcon = (ImageView) getView().findViewById(R.id.phone_icon);
						phoneIcon.setImageDrawable(getResources().getDrawable(R.drawable.phone_contact));
						
						LinearLayout makCall = (LinearLayout) getView().findViewById(R.id.phone_number_layout);
						makCall.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								
								AlertDialogFragment alertDialogFragment = new AlertDialogFragment() {
									
									@Override
									protected void positiveButtonClicked() 
									{
										Intent callIntent = new Intent(Intent.ACTION_CALL);
										callIntent.setData(Uri.parse("tel:" + phoneNumber));
										startActivity(callIntent );
									}
									
									@Override
									protected void negativeButtonClicked() 
									{
										
									}
								};
								alertDialogFragment.show(getFragmentManager(), "CallDialog");
								
							}
						});
					}
					
					boolean isImageDownloaded = false;
					ImageView downloadImageView = (ImageView)getView().findViewById(R.id.place_icon);
					// set animation
					AppUtil.setImageAnimation(downloadImageView);
					if(argGooglePlaceDetail.getPhotos() != null && argGooglePlaceDetail.getPhotos().size() > 0)
					{
						// TODO - need to find the best resolution based on the device 
						GooglePlaceDetail.Photos photo = argGooglePlaceDetail.getPhotos().get(0); 
						final String photoUrl = String.format(ConfigConstants.GOOGLE_PLACE_PHOTO_URL, photo.getWidth(),photo.getHeight(),photo.getReference(),MainApplication.getAppInstance().getGoogleApiKey());
						try
						{
							MainApplication.getImageDownloader().download(photoUrl,downloadImageView );
							isImageDownloaded = true;
						}
						catch(Exception e)
						{
							// log the exception.
						}
					}
					
					if(!isImageDownloaded)
					{
						downloadImageView.setBackgroundResource(R.drawable.no_image);
					}

					if(argGooglePlaceDetail.getReviews() != null)
					{
						PlaceReviewFragment reviewFragment = new PlaceReviewFragment();

						getChildFragmentManager().beginTransaction().replace(R.id.review_detailframe, reviewFragment).commit();

						// update review list
						ReviewAdapter reviewListAdapter = new ReviewAdapter(getActivity());

						for(GooglePlaceDetail.Reviews review: argGooglePlaceDetail.getReviews())
						{
							reviewListAdapter.add(review);
						}

						reviewFragment.setListAdapter(reviewListAdapter);
					}
					if(mOnResultLoadedListener != null)
					{
						mOnResultLoadedListener.onResultData(argGooglePlaceDetail);
					}
				}
				
				// hide loading
				((BaseActivity)getActivity()).hideLoading();
			}
		});
	}
	
	public interface OnResultLoadedListener
	{
		public void onResultData(GooglePlaceDetail argDetail);
	}
	
	public void setOnResultLoadedListener(OnResultLoadedListener listener)
	{
		this.mOnResultLoadedListener = listener;
	}
}
