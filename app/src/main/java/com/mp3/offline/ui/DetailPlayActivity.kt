package com.mp3.offline.ui

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.mp3.offline.databinding.ActivityDetailPlayBinding
import com.mp3.offline.model.Model
import kotlinx.android.synthetic.main.activity_detail_play.*
import java.util.concurrent.TimeUnit

class DetailPlayActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailPlayBinding
    private var dataDetail: Model? = null
    private var mMediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPlayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dataDetail = intent.getParcelableExtra("keyData") as Model

        binding.tvSongTitle.text = dataDetail!!.title
        binding.tvArtist.text = dataDetail!!.artist
        binding.imgCover.setImageResource(dataDetail!!.photo)

        setPlayer(dataDetail!!.mp3)
    }

    private fun setPlayer(mp3: Int) {
        binding.imgPlay.setOnClickListener {
            if (mMediaPlayer == null) {
                mMediaPlayer = MediaPlayer.create(this, mp3)
                initialiseSeekBar()
            }
            mMediaPlayer?.start()

            binding.imgPause.visibility = View.VISIBLE
            binding.imgPlay.visibility = View.GONE
        }

        binding.imgPause.setOnClickListener {
            if (mMediaPlayer != null) mMediaPlayer?.pause()

            binding.imgPause.visibility = View.GONE
            binding.imgPlay.visibility = View.VISIBLE
        }

        binding.imgStop.setOnClickListener {
            if (mMediaPlayer != null) {
                mMediaPlayer?.stop()
                mMediaPlayer?.reset()
                mMediaPlayer?.release()
                mMediaPlayer = null
            }

            binding.imgPause.visibility = View.GONE
            binding.imgPlay.visibility = View.VISIBLE
        }

        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) mMediaPlayer?.seekTo(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
    }

    private fun initialiseSeekBar() {
        binding.seekBar.max = mMediaPlayer!!.duration

        val handler = Handler()
        handler.postDelayed(object: Runnable {
            override fun run() {
                try {
                    binding.seekBar.progress = mMediaPlayer!!.currentPosition
                    handler.postDelayed(this, 1000)
                } catch (e: Exception){
                    binding.seekBar.progress = 0
                    e.printStackTrace()
                }
            }
        }, 0)
        val duration = mMediaPlayer?.duration
        val time = String.format("%02d:%02d",
            TimeUnit.MILLISECONDS.toMinutes(duration!!.toLong()),
            TimeUnit.MILLISECONDS.toSeconds(duration.toLong()) -
                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration.toLong())))
        binding.tvDuration.text = time
    }

    /*val duration = mMediaPlayer?.duration
    val time = String.format("%02d:%02d",
        TimeUnit.MILLISECONDS.toMinutes(duration!!.toLong()),
        TimeUnit.MILLISECONDS.toSeconds(duration.toLong()) -
                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration.toLong()))
    )
    binding.tvDuration.text = time*/
}