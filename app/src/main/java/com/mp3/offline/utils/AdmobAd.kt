package com.mp3.offline.utils

import android.app.Activity
import android.util.Log
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.mp3.offline.R

class AdmobAd(private val activity: Activity) {
    private var TAG = "AdmobAd"
    private var mInterstitialAd: InterstitialAd? = null

    fun interstitialLoad() {
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(
            activity,
            activity.resources.getString(R.string.AdMob_interstitial_id),
            adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                mInterstitialAd = null
            }

            override fun onAdLoaded(interStitialAd: InterstitialAd) {
                mInterstitialAd = interStitialAd
                Log.d(TAG, "Interstitial Loaded")
            }
        })
    }

    fun showInterstitialAd() {
        if (mInterstitialAd != null) {

            mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    Log.d(TAG, "Ad was dismissed")
                    mInterstitialAd = null
                    interstitialLoad()
                }

                override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                    Log.d(TAG, "Ad Failed to show")
                    mInterstitialAd = null
                    interstitialLoad()
                }

                override fun onAdShowedFullScreenContent() {
                    Log.d(TAG, "Ad Showed fullscreen content")
                    mInterstitialAd = null
                }
            }
            mInterstitialAd?.show(activity)
            Log.d(TAG, "The interstitial ad ready.")
        }else{
            Log.d(TAG, "The interstitial ad wasn't ready yet.")
            interstitialLoad()
        }
    }
}