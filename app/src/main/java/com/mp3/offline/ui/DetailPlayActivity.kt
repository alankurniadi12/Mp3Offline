package com.mp3.offline.ui

import android.content.Intent
import android.content.pm.ActivityInfo
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.view.OrientationEventListener
import android.view.View
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.widget.HorizontalScrollView
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.rotationMatrix
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.mp3.offline.databinding.ActivityDetailPlayBinding
import com.mp3.offline.model.Model
import java.util.concurrent.TimeUnit
import kotlin.system.exitProcess

class DetailPlayActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailPlayBinding
    private var dataDetail: Model? = null
    private var mp: MediaPlayer? = null
    var rotateAnimation: RotateAnimation? = null
    lateinit var mAdView : AdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPlayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //AdMob Code
        MobileAds.initialize(this) {}
        mAdView = binding.adView
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        dataDetail = intent.getParcelableExtra("keyData") as Model

        binding.tvSongTitle.text = dataDetail!!.title
        binding.tvArtist.text = dataDetail!!.artist
        binding.imgCover.setImageResource(dataDetail!!.photo)

        setPlayer(dataDetail!!.mp3)

        binding.btnPlaybackground.setOnClickListener {
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_HOME)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        mp?.release()
    }

    private fun setPlayer(mp3: Int) {
        binding.imgPlay.setOnClickListener {
            if (mp == null){
                mp = MediaPlayer.create(this, mp3)
                initialiseSeekBar()
            }
            mp?.start()

            binding.imgPause.visibility = View.VISIBLE
            binding.imgPlay.visibility = View.GONE

            rotateAnimation = RotateAnimation(0f, 360f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f)
            rotateAnimation!!.duration = 15000
            rotateAnimation!!.interpolator = LinearInterpolator()
            rotateAnimation!!.repeatCount =Animation.INFINITE

            binding.imgCover.startAnimation(rotateAnimation)
        }

        binding.imgPause.setOnClickListener {
            if (mp != null) mp?.pause()

            rotateAnimation!!.cancel()
            binding.imgPause.visibility = View.GONE
            binding.imgPlay.visibility = View.VISIBLE
        }

        binding.imgStop.setOnClickListener {
            if (mp != null) {
                mp?.stop()
                mp?.reset()
                mp?.release()
                mp = null
            }

            rotateAnimation!!.cancel()
            binding.imgPause.visibility = View.GONE
            binding.imgPlay.visibility = View.VISIBLE
        }

        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) mp?.seekTo(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
    }

    private fun initialiseSeekBar() {
        binding.seekBar.max = mp!!.duration

        val handler = Handler()
        handler.postDelayed(object: Runnable {
            override fun run() {
                try {
                    binding.seekBar.progress = mp!!.currentPosition
                    handler.postDelayed(this, 1000)
                } catch (e: Exception){
                    binding.seekBar.progress = 0
                    e.printStackTrace()
                }
            }
        }, 0)
        val duration = mp!!.duration
        val time = String.format("%02d:%02d",
            TimeUnit.MILLISECONDS.toMinutes(duration.toLong()),
            TimeUnit.MILLISECONDS.toSeconds(duration.toLong()) -
                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration.toLong())))
        binding.tvDuration.text = time
    }
}