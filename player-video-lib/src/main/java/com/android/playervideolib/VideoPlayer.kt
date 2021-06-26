package com.android.playervideolib

import android.content.Context

interface VideoPlayer {

    fun build(): Any

    fun getCurrentWindowIndex(): Int


    fun getCurrentPosition(): Long


    fun setMedia(media: Media)
    fun seekTo(window: Int, playBackPosition: Long)

    fun setPlayWhenReady(isPlayWhenReady: Boolean)
    fun getPlayWhenReady(): Boolean

    fun prepare()
    fun release()
}