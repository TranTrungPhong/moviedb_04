package com.framgia.moviedb.feature.video;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.framgia.moviedb.BaseView;
import com.framgia.moviedb.BuildConfig;
import com.framgia.moviedb.Constant;
import com.framgia.moviedb.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class VideoActivity extends YouTubeBaseActivity
    implements YouTubePlayer.OnInitializedListener {
    private YouTubePlayerView mYouTubePlayerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        mYouTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtube_player_view);
        mYouTubePlayerView.initialize(BuildConfig.YOUTUBE_KEY, this);
    }

    public static Intent getVideoDetailIntent(Context context, String key) {
        Intent intent = new Intent(context, VideoActivity.class);
        intent.putExtra(Constant.IntentKey.EXTRA_KEY, key);
        return intent;
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                        YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, Constant.VIDEO_ACTIVITY_DIALOG_REQUEST_CODE).show();
        } else {
            Toast.makeText(this, R.string.message_youtube_error, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constant.VIDEO_ACTIVITY_DIALOG_REQUEST_CODE) {
            mYouTubePlayerView.initialize(BuildConfig.YOUTUBE_KEY, this);
        }
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                        YouTubePlayer player, boolean wasRestored) {
        if (!wasRestored) {
            player.cueVideo(getIntent().getStringExtra(Constant.IntentKey.EXTRA_KEY));
            player.addFullscreenControlFlag(
                YouTubePlayer.FULLSCREEN_FLAG_ALWAYS_FULLSCREEN_IN_LANDSCAPE);
            player.setFullscreen(true);
            player.setShowFullscreenButton(false);
        }
    }
}
