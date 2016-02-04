package com.tealium.example.view;

import java.io.File;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnErrorListener;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.tealium.example.R;

public class ExampleVideoView extends VideoView implements OnGlobalLayoutListener, OnErrorListener {

	private boolean isSetup;
	private boolean didError;
	
	{
		MediaController mediaController  = new MediaController(this.getContext());
		mediaController.setVisibility(View.GONE);
		this.setMediaController(mediaController);
		
		getViewTreeObserver().addOnGlobalLayoutListener(this);
		setOnErrorListener(this);
		isSetup = false;	
		didError = false;
	}
	
	public ExampleVideoView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public ExampleVideoView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ExampleVideoView(Context context) {
		super(context);
	}

	
	@Override
	public void onGlobalLayout() {
	
		if(isSetup) {
			return;
		}
		
		isSetup = true;
		
		String path = "android.resource://" + getContext().getPackageName() + File.separator + R.raw.tmu;
		
		setVideoURI(Uri.parse(path));		
	    setMediaController(new MediaController(getContext()));
	    requestFocus();
	    
	    if(didError) {
	    	Toast.makeText(
	    			getContext(), 
	    			getResources().getString(R.string.error_unabletoplayvideo), 
	    			Toast.LENGTH_LONG).show();
	    	return;
	    }
	    
	    start();
	}

	@Override
	public boolean onError(MediaPlayer mediaPlayer, int what, int extra) {

		((ViewGroup) getParent()).removeView(this);
		
		didError = true;
		return true;
	}
}
