package com.android.videoplayerlib

import com.android.playervideolib.Media
import com.google.android.exoplayer2.MediaItem

class DefaultMedia(uri: String) : Media {

    private var mediaItem: MediaItem = MediaItem.fromUri(uri)

    override fun getMedia() = mediaItem
}