package com.mp3.offline.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.mp3.offline.R
import com.mp3.offline.databinding.ActivityDetailPlayBinding
import com.mp3.offline.model.Model
import java.util.concurrent.TimeUnit

class DetailPlayActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailPlayBinding
    private var dataDetail: Model? = null
    private var mp: MediaPlayer? = null
    var rotateAnimation: RotateAnimation? = null
    lateinit var mAdView : AdView
    private lateinit var runnable: Runnable
    var mHandler: Handler? = null

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPlayBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //Lock screen rotation
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        //Get data
        dataDetail = intent.getParcelableExtra("keyData")!!

        //Set Ui
        binding.tvSongTitle.text = dataDetail!!.title
        binding.tvArtist.text = dataDetail!!.artist
        binding.imgCover.setImageResource(dataDetail!!.photo)

        //Initialize media player
        mp = MediaPlayer.create(this, dataDetail!!.mp3)
        mp!!.isLooping = true


        mHandler = Handler()
        runnable = Runnable {
            binding.positionBar.progress = mp!!.currentPosition
            mHandler!!.postDelayed(runnable, 500)
        }

        //Get duration total from mp3
        val duration = mp!!.duration
        val time = convertFormat(duration)
        binding.tvTotalTime.text = time


        binding.positionBar.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    if (fromUser) {
                        mp!!.seekTo(progress)
                    }
                    binding.tvElapsedTime.text = convertFormat(mp!!.currentPosition)
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                }
            }
        )


        //AdMob Code
        MobileAds.initialize(this) {}
        mAdView = binding.adView
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
    }

    private fun convertFormat(duration: Int): String {
        return String.format("%02d:%02d",
            TimeUnit.MILLISECONDS.toMinutes(duration.toLong()),
            TimeUnit.MILLISECONDS.toSeconds(duration.toLong()) -
            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration.toLong()))
        )
    }


    fun rewindBtn(v: View) {
        var currentPosition = mp!!.currentPosition
        if (mp!!.isPlaying && currentPosition > 5000) {
            currentPosition -= 5000
            binding.tvElapsedTime.text = convertFormat(currentPosition)
            mp!!.seekTo(currentPosition)
        }
    }

    fun fastForward(v: View) {
        var currentPosition = mp!!.currentPosition
        val duration = mp!!.duration
        if (mp!!.isPlaying && duration != currentPosition) {
            currentPosition += 5000
            binding.tvElapsedTime.text = convertFormat(currentPosition)
            mp!!.seekTo(currentPosition)
        }
    }

    fun playButtonClick(v: View) {
        if (mp!!.isPlaying) {
            mp!!.pause()

            mHandler?.removeCallbacks(runnable)
            binding.imgPlay.setImageResource(R.drawable.ic__play_circle_24)

            rotateAnimation!!.cancel()
        } else {
            mp!!.start()

            binding.positionBar.max = mp!!.duration
            mHandler?.postDelayed(runnable, 0)

            binding.imgPlay.setImageResource(R.drawable.ic_pause_circle_24)

            rotateAnimation = RotateAnimation(0f, 360f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f)
            rotateAnimation!!.duration = 15000
            rotateAnimation!!.interpolator = LinearInterpolator()
            rotateAnimation!!.repeatCount =Animation.INFINITE

            binding.imgCover.startAnimation(rotateAnimation)
        }
    }

    fun playBtnBackground(v: View) {
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        mp?.stop()
    }

}