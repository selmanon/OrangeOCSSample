package com.android.videoplayerlib

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.android.playervideolib.Media
import com.android.playervideolib.VideoPlayer
import com.android.videoplayerlib.databinding.ActivityPlayerBinding
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.util.Util


class PlayerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlayerBinding

    private var player: VideoPlayer? = null
    private var mediaItem: Media? = null
    private var videoPlayer: View? = null

    private var playWhenReady = true
    private var currentWindow = 0
    private var playbackPosition: Long = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        if (Util.SDK_INT >= 24) {
            initializePlayer()
        }
    }

    override fun onResume() {
        super.onResume()
        if (Util.SDK_INT < 24 || player == null) {
            initializePlayer()
        }
    }

    override fun onPause() {
        super.onPause()
        if (Util.SDK_INT < 24) {
            releasePlayer()
        }
    }

    /**
     * As Android introduced multi-window mode in API 24, so that the user
     * could have two applications in the foreground which mean that the application
     * (resources) must stay active after going to onPause and only release it when onStop
     * happens.
     */
    override fun onStop() {
        super.onStop()
        if (Util.SDK_INT >= 24) {
            releasePlayer()
        }
    }

    private fun initializePlayer() {
        mediaItem = DefaultMedia(getString(R.string.url_stream_dash))
        player = DefaultExoPlayer(this)

        val exoPlayerView = PlayerView(this)
        binding.videoViewContainer.addView(exoPlayerView)
        val exoplayer = (player as DefaultExoPlayer).build() as SimpleExoPlayer
        exoPlayerView.player = exoplayer
        (videoPlayer as PlayerView).player = exoplayer
        player?.let {
            it.setMedia(mediaItem!!)
            it.setPlayWhenReady(playWhenReady)
            it.seekTo(currentWindow, playbackPosition)
            it.prepare()
        }
    }

    private fun releasePlayer() {
        player?.let {
            playWhenReady = it.getPlayWhenReady()
            playbackPosition = it.getCurrentPosition()
            currentWindow = it.getCurrentWindowIndex()
            it.release()
            player = null
        }
    }
}