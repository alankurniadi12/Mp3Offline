package com.mp3.offline.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Message
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

class DetailPlayActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailPlayBinding
    private var dataDetail: Model? = null
    private var mp: MediaPlayer? = null
    private var totalTime: Int = 0
    var rotateAnimation: RotateAnimation? = null
    lateinit var mAdView : AdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPlayBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //Lock screen rotation
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        //Get data mp3
        dataDetail = intent.getParcelableExtra("keyData") as Model

        //Set Ui
        binding.tvSongTitle.text = dataDetail!!.title
        binding.tvArtist.text = dataDetail!!.artist
        binding.imgCover.setImageResource(dataDetail!!.photo)

        //Media Player
        mp = MediaPlayer.create(this, dataDetail!!.mp3)
        mp!!.isLooping = true
        totalTime = mp!!.duration

        //Set progressbar
        binding.positionBar.max = totalTime
        binding.positionBar.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    if (fromUser) mp!!.seekTo(progress)
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                }
            }
        )

        Thread(Runnable {
            while (mp != null) {
                val msg = Message()
                try {
                    msg.what = mp!!.currentPosition
                    handler.sendMessage(msg)
                    Thread.sleep(1000)
                }catch (e: InterruptedException){
                }
            }
        }).start()

        //AdMob Code
        MobileAds.initialize(this) {}
        mAdView = binding.adView
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
    }

    @SuppressLint("HandlerLeak")
    var handler = object : Handler() {
        @SuppressLint("SetTextI18n")
        override fun handleMessage(msg: Message) {
            val currentPosition = msg.what

            binding.positionBar.progress = currentPosition

            val elapsedTime = createTimeLabel(currentPosition)
            binding.tvElapsedTime.text = elapsedTime

            val remainingTime = createTimeLabel(totalTime - currentPosition)
            binding.tvRemainingTime.text = "-$remainingTime"
        }
    }

    private fun createTimeLabel(time: Int): String {
        var timeLabel = ""
        val min = time / 1000 / 60
        val sec = time / 1000 % 60

        timeLabel = "$min:"
        if (sec < 10) timeLabel += "0"
        timeLabel += sec

        return timeLabel
    }

    fun playButtonClick(v: View) {
        if (mp!!.isPlaying) {
            mp!!.pause()
            binding.imgPlay.setImageResource(R.drawable.ic__play_circle_24)

            rotateAnimation!!.cancel()
        } else {
            mp!!.start()
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