package com.youstinus.crochetingguide.fragments.ads

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log

import androidx.fragment.app.Fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.annotation.NonNull
import com.google.android.gms.ads.*
import com.google.android.gms.ads.formats.NativeAdOptions
import com.google.android.gms.ads.formats.UnifiedNativeAd

import com.google.android.gms.ads.reward.RewardItem
import com.google.android.gms.ads.reward.RewardedVideoAd
import com.google.android.gms.ads.reward.RewardedVideoAdListener
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdCallback
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.youstinus.crochetingguide.R

/**
 * A fragment with a Google +1 button.
 * Activities that contain this fragment must implement the
 * [AdsFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [AdsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AdsFragment : Fragment(), RewardedVideoAdListener {
    // The URL to +1.  Must be a valid URL.
    private val PLUS_ONE_URL = "http://developer.android.com"
    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null

    private var mListener: OnFragmentInteractionListener? = null

    private lateinit var mInterstitialAd: InterstitialAd
    private lateinit var mRewardedVideoAd: RewardedVideoAd
    private lateinit var rewardedAd: RewardedAd
    lateinit var adLoader: AdLoader
    lateinit var mAdView: AdView
var adRewardedCode: String = "ca-app-pub-3940256099942544/5224354917"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments!!.getString(ARG_PARAM1)
            mParam2 = arguments!!.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_ads, container, false)




        interstitialAd(view, "ca-app-pub-3940256099942544/1033173712") // my: ca-app-pub-8162832251478705/5507397185 test: "ca-app-pub-3940256099942544/1033173712"
        oldRewardedAd(view, "ca-app-pub-3940256099942544/5224354917") // my: ca-app-pub-8162832251478705/8269291890 // test: "ca-app-pub-3940256099942544/5224354917"
        newRewardedAd(view, "ca-app-pub-3940256099942544/5224354917")
        nativeAd(view, "ca-app-pub-3940256099942544/2247696110") // my: ca-app-pub-8162832251478705/4709320830 test: "ca-app-pub-3940256099942544/2247696110"
        bannerAd(view, "ca-app-pub-3940256099942544/6300978111") // my: ca-app-pub-8162832251478705/3137697797 test: "ca-app-pub-3940256099942544/6300978111"

        return view
    }

    fun bannerAd(view: View, ad:String) {

        val adView = AdView(activity)
        adView.adSize = AdSize.BANNER
        adView.adUnitId = ad
        mAdView = view.findViewById<AdView>(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
    }

    fun nativeAd(view: View, ad:String) {
        adLoader = AdLoader.Builder(context, ad)
                .forUnifiedNativeAd { ad : UnifiedNativeAd ->
                    // Show the ad.
                    if (adLoader.isLoading) {
                        // The AdLoader is still loading ads.
                        // Expect more adLoaded or onAdFailedToLoad callbacks.
                    } else {
                        // The AdLoader has finished loading ads.

                    }
                }
                .withAdListener(object : AdListener() {
                    override fun onAdFailedToLoad(errorCode: Int) {
                        // Handle the failure by logging, altering the UI, and so on.
                    }
                })
                .withNativeAdOptions(NativeAdOptions.Builder()
                        // Methods in the NativeAdOptions.Builder class can be
                        // used here to specify individual options settings.
                        .build())
                .build()
        adLoader.loadAds(AdRequest.Builder().build(), 2)
    }

    fun interstitialAd(view: View, ad: String) {
        mInterstitialAd = InterstitialAd(activity)
        mInterstitialAd.adUnitId = ad // ca-app-pub-8162832251478705/5507397185
        mInterstitialAd.loadAd(AdRequest.Builder().build())

        view.findViewById<Button>(R.id.button_interstitial).setOnClickListener {
            if (mInterstitialAd.isLoaded) {
                mInterstitialAd.show()
            } else {
                Log.d("TAG", "The interstitial wasn't loaded yet.")
            }
        }

        mInterstitialAd.adListener = object : AdListener() {
            override fun onAdClosed() {
                mInterstitialAd.loadAd(AdRequest.Builder().build())
            }
        }
    }

    fun oldRewardedAd(view: View, ad: String) {
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(activity)
        mRewardedVideoAd.rewardedVideoAdListener = this
        loadRewardedVideoAd(ad)
        view.findViewById<Button>(R.id.button_rewarded_old).setOnClickListener {
            if (mRewardedVideoAd.isLoaded) {
                mRewardedVideoAd.show()
            }
        }
    }

    fun newRewardedAd(view: View, ad: String) {
        rewardedAd = RewardedAd(context, ad)
        val adLoadCallback = object: RewardedAdLoadCallback() {
            override fun onRewardedAdLoaded() {
                // Ad successfully loaded.
            }
            override fun onRewardedAdFailedToLoad(errorCode: Int) {
                // Ad failed to load.
            }
        }
        rewardedAd.loadAd(AdRequest.Builder().build(), adLoadCallback)

        view.findViewById<Button>(R.id.button_rewarded_new).setOnClickListener {
            if (rewardedAd.isLoaded) {
                val activityContext: Activity = activity as Activity
                val adCallback = object: RewardedAdCallback() {
                    /*override fun onUserEarnedReward(p0: com.google.android.gms.ads.rewarded.RewardItem) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }*/

                    override fun onRewardedAdOpened() {
                        // Ad opened.
                    }
                    override fun onRewardedAdClosed() {
                        // Ad closed.
                        onRewardedAdClosed2(ad)
                        //rewardedAd.loadAd(AdRequest.Builder().build(), adLoadCallback)
                    }
                    override fun onUserEarnedReward(reward: com.google.android.gms.ads.rewarded.RewardItem) {
                        // User earned reward.
                    }
                    override fun onRewardedAdFailedToShow(errorCode: Int) {
                        // Ad failed to display.
                    }
                }
                rewardedAd.show(activityContext, adCallback)
            }
            else {
                Log.d("TAG", "The rewarded ad wasn't loaded yet.")
            }
        }
    }

    fun createAndLoadRewardedAd(ad: String): RewardedAd {
        val rewardedAd = RewardedAd(activity, ad)
        val adLoadCallback = object: RewardedAdLoadCallback() {
            override fun onRewardedAdLoaded() {
                // Ad successfully loaded.
            }
            override fun onRewardedAdFailedToLoad(errorCode: Int) {
                // Ad failed to load.
            }
        }
        rewardedAd.loadAd(AdRequest.Builder().build(), adLoadCallback)
        return rewardedAd
    }
    fun onRewardedAdClosed2(ad:String) {
        this.rewardedAd = createAndLoadRewardedAd(ad)
    }

    private fun loadRewardedVideoAd(ad: String) {
        mRewardedVideoAd.loadAd(ad,
                AdRequest.Builder().build())
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        if (mListener != null) {
            mListener!!.onFragmentInteraction(uri)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            mListener = context
        } else {
            //throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    override fun onRewarded(reward: RewardItem) {
        Toast.makeText(context, "onRewarded! currency: ${reward.type} amount: ${reward.amount}",
                Toast.LENGTH_SHORT).show()
        // Reward the user.
    }

    override fun onRewardedVideoAdLeftApplication() {
        Toast.makeText(context, "onRewardedVideoAdLeftApplication", Toast.LENGTH_SHORT).show()
    }

    override fun onRewardedVideoAdClosed() {
        Toast.makeText(context, "onRewardedVideoAdClosed", Toast.LENGTH_SHORT).show()
        loadRewardedVideoAd(adRewardedCode)
    }

    override fun onRewardedVideoAdFailedToLoad(errorCode: Int) {
        Toast.makeText(context, "onRewardedVideoAdFailedToLoad", Toast.LENGTH_SHORT).show()
    }

    override fun onRewardedVideoAdLoaded() {
        Toast.makeText(context, "onRewardedVideoAdLoaded", Toast.LENGTH_SHORT).show()
    }

    override fun onRewardedVideoAdOpened() {
        Toast.makeText(context, "onRewardedVideoAdOpened", Toast.LENGTH_SHORT).show()
    }

    override fun onRewardedVideoStarted() {
        Toast.makeText(context, "onRewardedVideoStarted", Toast.LENGTH_SHORT).show()
    }

    override fun onRewardedVideoCompleted() {
        Toast.makeText(context, "onRewardedVideoCompleted", Toast.LENGTH_SHORT).show()
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"
        // The request code must be 0 or greater.
        private val PLUS_ONE_REQUEST_CODE = 0

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AdsFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String, param2: String): AdsFragment {
            val fragment = AdsFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }

}// Required empty public constructor
