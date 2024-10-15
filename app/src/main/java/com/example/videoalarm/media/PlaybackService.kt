package com.example.videoalarm.media

import android.media.MediaSession2
import android.media.MediaSession2Service

class PlaybackService(

) : MediaSession2Service() {
    override fun onGetSession(controllerInfo: MediaSession2.ControllerInfo): MediaSession2? {
        TODO("Not yet implemented")
    }

    override fun onUpdateNotification(session: MediaSession2): MediaNotification? {
        TODO("Not yet implemented")
    }

}