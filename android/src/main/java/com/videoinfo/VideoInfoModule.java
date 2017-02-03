package com.videoinfo;

import android.content.Context;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Promise;

import android.content.Intent;
import android.app.Activity;
import android.net.Uri;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.BaseActivityEventListener;

import com.videoinfo.QueryMediaStore;


public class VideoInfoModule extends ReactContextBaseJavaModule {
	private Context context;
	private Promise mPromise;
	private WritableMap result;

	private static final int REQUEST_LAUNCH_VIDEO_LIBRARY = 467081;

	private final ActivityEventListener mActivityEventListener = new BaseActivityEventListener () {

	    @Override
		public void onActivityResult (
			Activity activity,
			int requestCode,
			int resultCode,
			Intent intent
		) {
	      	if (requestCode == REQUEST_LAUNCH_VIDEO_LIBRARY) {

          		if (resultCode == Activity.RESULT_CANCELED) {

            		mPromise.reject ("CANCELLED");

          		} else if (resultCode == Activity.RESULT_OK) {
            		Uri uri = intent.getData();

            		if (uri == null) {
              			mPromise.reject ("NOT_FOUND");
            		} else {
            			QueryMediaStore mediaStore = new QueryMediaStore (context);

              			result = mediaStore.requestInfo (uri.getPath ());
            		}
            	}

	          	mPromise.resolve (result);
	     	}
    	}
	};

	public VideoInfoModule (ReactApplicationContext reactContext) {
		super (reactContext);

		this.context = reactContext;
		reactContext.addActivityEventListener (mActivityEventListener);
	}

	/**
	 * @return the name of this module. This will be the name used to {@code require()} this module
	 * from javascript.
	 */
	@Override
	public String getName () {
		return "VideoInfo";
	}

	@ReactMethod
	public void launchVideoLibrary (
		final ReadableMap options,
		final Promise promise
	) {
		result = Arguments.createMap();

		Activity currentActivity = getCurrentActivity ();

		Intent intent = new Intent (Intent.ACTION_GET_CONTENT);
		intent.addCategory (Intent.CATEGORY_OPENABLE);
		intent.setType ("video/*");

		currentActivity.startActivityForResult (
			Intent.createChooser (intent, "SELECT VIDEO"),
			REQUEST_LAUNCH_VIDEO_LIBRARY
		);

		mPromise = promise;
	}

}