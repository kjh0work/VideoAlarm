package com.example.videoalarm.di

import android.content.Context
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ViewModelComponent::class)
object PlayerModule {

    @Provides
    fun providesPlayer(@ApplicationContext context: Context) : Player {
        return ExoPlayer.Builder(context).build()
    }

}