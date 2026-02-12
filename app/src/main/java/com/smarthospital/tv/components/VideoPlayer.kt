package com.smarthospital.tv.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.media3.ui.compose.ContentFrame
import kotlinx.coroutines.delay

@Composable
fun VideoPlayer(
    videoUrl: String,
    modifier: Modifier = Modifier,
    showControls: Boolean = true,
    onVideoEnded: () -> Unit = {}
) {
    val context = LocalContext.current

    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            val mediaItem = MediaItem.fromUri(videoUrl)
            setMediaItem(mediaItem)
            prepare()
            playWhenReady = true
        }
    }

    var isPlaying by remember { mutableStateOf(exoPlayer.isPlaying) }
    var currentPosition by remember { mutableLongStateOf(exoPlayer.currentPosition) }
    var totalDuration by remember { mutableLongStateOf(exoPlayer.duration) }
    var bufferedPercentage by remember { mutableIntStateOf(exoPlayer.bufferedPercentage) }
    var isControlsVisible by remember { mutableStateOf(showControls) }

    // Update progress periodically
    LaunchedEffect(exoPlayer) {
        while (true) {
            currentPosition = exoPlayer.currentPosition
            totalDuration = exoPlayer.duration
            bufferedPercentage = exoPlayer.bufferedPercentage
            isPlaying = exoPlayer.isPlaying
            delay(1000)
        }
    }

    DisposableEffect(Unit) {
        val listener = object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                if (playbackState == Player.STATE_ENDED) {
                    onVideoEnded()
                }
                isPlaying = exoPlayer.isPlaying
            }

            override fun onIsPlayingChanged(isPlayingState: Boolean) {
                isPlaying = isPlayingState
            }
        }
        exoPlayer.addListener(listener)

        onDispose {
            exoPlayer.removeListener(listener)
            exoPlayer.release()
        }
    }

    Box(modifier = modifier) {
        ContentFrame(
            player = exoPlayer,
            modifier = Modifier.fillMaxSize()
                .clickable {
                     if (showControls) {
                         isControlsVisible = !isControlsVisible
                     }
                }
        )

        if (showControls && isControlsVisible) {
            VideoPlayerControls(
                modifier = Modifier.fillMaxSize(),
                isPlaying = isPlaying,
                currentPosition = currentPosition,
                duration = totalDuration,
                bufferPercentage = bufferedPercentage,
                onPlayPauseToggle = {
                    if (exoPlayer.isPlaying) {
                        exoPlayer.pause()
                    } else {
                        exoPlayer.play()
                    }
                },
                onSeek = { position ->
                    exoPlayer.seekTo(position)
                    currentPosition = position
                }
            )
        }
    }
}
