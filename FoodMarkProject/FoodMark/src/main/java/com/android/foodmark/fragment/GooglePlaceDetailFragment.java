package com.android.foodmark.fragment;

import com.android.foodmark.MainApplication;
import com.android.foodmark.R;
import com.android.foodmark.activity.BaseActivity;
import com.android.foodmark.activity.MapActivity;
import com.android.foodmark.activity.WebViewActivity;
import com.android.foodmark.adapter.ReviewAdapter;
import com.android.foodmark.callbacks.GooglePlaceDetailCallback;
import com.android.foodmark.constants.AppBundle;
import com.android.foodmark.constants.ConfigConstants;
import com.android.foodmark.model.Geometry;
import com.android.foodmark.model.GooglePlaceDetail;
import com.android.foodmark.utils.AppUtil;

import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
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
                        MainApplication.getImageDownloader().download(
                                argGooglePlaceDetail.getIconUrl(),downloadImageView );
					}

                    // display the location map
                    if(argGooglePlaceDetail.getGeometry()!= null)
                    {
                        // update the map fragment
                        PlaceMapFragment mapFragment = new PlaceMapFragment();
                        final Geometry geometry = argGooglePlaceDetail.getGeometry();
                        Bundle mapInfo = new Bundle();
                        mapInfo.putSerializable(AppBundle.LOCATION,geometry);
                        mapFragment.setArguments(mapInfo);
                        getChildFragmentManager().beginTransaction().add(
                                R.id.map_detail_frame, mapFragment).commit();

                        // button to invoke map detail view
                        Button detailMap = (Button) getView().findViewById(R.id.map_detail_button);
                        detailMap.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                synchronized (this)
                                {
                                    Intent intent = new Intent(getActivity(), MapActivity.class);
                                    intent.putExtra(AppBundle.LOCATION,geometry);
                                    startActivity(intent);
                                }
                            }
                        });
                        final String webUrl = argGooglePlaceDetail.getUrl();
                        // button to invoke webView to write review
                        Button writeReview = (Button) getView().findViewById(
                                R.id.map_write_review_button);
                        writeReview.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                synchronized (this)
                                {
                                    Intent intent = new Intent(getActivity(), WebViewActivity.class);
                                    intent.putExtra(AppBundle.WEB_URL,webUrl);
                                    startActivity(intent);
                                }
                            }
                        });
                        Location current = MainApplication.getAppInstance().getLocation();
                        Geometry.AppLocation dist = geometry.getLocation();
                        // format direction uri
                        final String routeUrl = String.format(
                                ConfigConstants.GOOGLE_MAP_DIRECTION_URL,
                                current.getLatitude() + "," + current.getLongitude(),
                                dist.getLat() + "," + dist.getLng());

                        Button invokeMap = (Button) getView().findViewById(
                                R.id.map_route_button);
                        invokeMap.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                synchronized (this)
                                {
                                    Intent intent = new Intent(
                                            Intent.ACTION_VIEW,Uri.parse(routeUrl));
                                    intent.setClassName("com.google.android.apps.maps" ,
                                            "com.google.android.maps.MapsActivity");
                                    startActivity(intent);
                                }
                            }
                        });
                    }

					if(argGooglePlaceDetail.getReviews() != null)
					{
						PlaceReviewFragment reviewFragment = new PlaceReviewFragment();

						getChildFragmentManager().beginTransaction().replace(
                                R.id.review_detail_frame, reviewFragment).commit();

						// update review list
						ReviewAdapter reviewListAdapter = new ReviewAdapter(getActivity());

						for(GooglePlaceDetail.Reviews review: argGooglePlaceDetail.getReviews())
						{
							reviewListAdapter.add(review);
						}

						reviewFragment.setListAdapter(reviewListAdapter);
					}
                    else
                    {
                        EmptyFragment emptyFragment = new EmptyFragment();
                        Bundle bundle = new Bundle();

                        bundle.putString("TITLE" ,
                                getResources().getString(R.string.empty_review_list));
                        emptyFragment.setArguments(bundle);
                        getChildFragmentManager().beginTransaction().replace(
                                R.id.review_detail_frame,emptyFragment).commit();
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
