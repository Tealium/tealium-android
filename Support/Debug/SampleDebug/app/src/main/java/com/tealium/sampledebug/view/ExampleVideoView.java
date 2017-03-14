package com.tealium.sampledebug.view;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.tealium.sampledebug.R;

/**
 * Created by karentamayo on 1/12/17.
 */

public class ExampleVideoView extends VideoView implements ViewTreeObserver.OnGlobalLayoutListener, MediaPlayer.OnErrorListener {

    private boolean isSetup;
    private boolean didError;

    {
        MediaController mediaController = new MediaController(this.getContext());
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

        if (isSetup) {
            return;
        }

        isSetup = true;

        final Uri videoUri = new Uri.Builder()
                .scheme("http")
                .authority("tealium.github.io")
                .appendPath("tealium-android")
                .appendPath("tmu.mp4")
                .build();

        setVideoURI(videoUri);
        setMediaController(new MediaController(getContext()));
        requestFocus();

        if (didError) {
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