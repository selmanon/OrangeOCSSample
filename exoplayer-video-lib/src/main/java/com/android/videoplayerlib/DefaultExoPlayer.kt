package com.android.videoplayerlib

import android.content.Context
import com.android.playervideolib.Media
import com.android.playervideolib.VideoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer

class DefaultExoPlayer(var context: Context) : VideoPlayer {

    private lateinit var exoPlayer: SimpleExoPlayer

    override fun build(): Any {
        exoPlayer = SimpleExoPlayer.Builder(context).build()
        return exoPlayer
    }


    override fun getCurrentWindowIndex(): Int = exoPlayer.currentWindowIndex

    override fun getCurrentPosition(): Long =
        exoPlayer.currentPosition


    override fun setMedia(media: Media) {
        exoPlayer.setMediaItem(media.getMedia() as MediaItem)
    }

    override fun seekTo(window: Int, playBackPosition: Long) {
        exoPlayer.seekTo(window, playBackPosition)
    }

    override fun setPlayWhenReady(isPlayWhenReady: Boolean) {
        exoPlayer.playWhenReady = isPlayWhenReady
    }

    override fun getPlayWhenReady(): Boolean =
        getPlayWhenReady()


    override fun prepare() {
        exoPlayer.prepare()
    }

    override fun release() {
        exoPlayer.release()
    }
}