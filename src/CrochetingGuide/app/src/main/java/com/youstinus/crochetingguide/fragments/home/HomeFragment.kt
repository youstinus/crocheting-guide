package com.youstinus.crochetingguide.fragments.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.cardview.widget.CardView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController

import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.youstinus.crochetingguide.R

class HomeFragment : Fragment() {

    //var mainView: View
    private var mAdView: AdView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        //mainView = view
        MobileAds.initialize(activity, "ca-app-pub-3940256099942544~3347511713") // app id ca-app-pub-8162832251478705~8436758640

        mAdView = view.findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build()
        mAdView!!.loadAd(adRequest)

        setOnClickListeners(view)
        (activity as AppCompatActivity).supportActionBar!!.title = "Titulinis"
        return view
        //super.onCreateView(inflater, container, savedInstanceState);
    }

    private fun setOnClickListeners(view: View) {
        val guide = view.findViewById<CardView>(R.id.cardView_guide)
        guide.setOnClickListener { onGuideClick() }

        val terms = view.findViewById<CardView>(R.id.cardView_terms)
        terms.setOnClickListener { onTermsClick() }

        val schemes = view.findViewById<CardView>(R.id.cardView_schemes)
        schemes.setOnClickListener { onSchemesClick() }

        val newest = view.findViewById<CardView>(R.id.cardView_newest)
        newest.setOnClickListener { onNewestClick() }
    }

    private fun onGuideClick() {
        view?.findNavController()?.navigate(R.id.nav_guide, null)
    }

    private fun onTermsClick() {
        view?.findNavController()?.navigate(R.id.nav_terms, null)
    }

    private fun onSchemesClick() {
        view?.findNavController()?.navigate(R.id.nav_schemes, null)
    }

    private fun onNewestClick() {
        view?.findNavController()?.navigate(R.id.nav_schemes, null)
    }
}
