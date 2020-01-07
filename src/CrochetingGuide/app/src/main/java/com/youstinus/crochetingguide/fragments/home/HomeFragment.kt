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
import com.google.android.gms.ads.AdSize

import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.youstinus.crochetingguide.R

class HomeFragment : Fragment() {

    //var mainView: View
    private var mAdView: AdView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        //setupAd(view)

        setOnClickListeners(view)
        //(activity as AppCompatActivity).supportActionBar!!.title = getString(R.string.home)
        return view
        //super.onCreateView(inflater, container, savedInstanceState);
    }

    // ad gone
    private fun setupAd(view: View) {
        val adView = view.findViewById<AdView>(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
    }

    private fun setOnClickListeners(view: View) {
        view.findViewById<CardView>(R.id.cardView_guide).setOnClickListener { onGuideClick() }
        view.findViewById<CardView>(R.id.cardView_terms).setOnClickListener { onTermsClick() }
        view.findViewById<CardView>(R.id.cardView_tips).setOnClickListener { onTipsClick() }
        view.findViewById<CardView>(R.id.cardView_schemes).setOnClickListener { onSchemesClick() }
        view.findViewById<CardView>(R.id.cardView_settings).setOnClickListener { onSettingsClick() }
        view.findViewById<CardView>(R.id.cardView_newest).setOnClickListener { onNewestClick() }
        view.findViewById<CardView>(R.id.cardView_ads).setOnClickListener { onAdsClick() }
    }

    private fun onGuideClick() {
        view?.findNavController()?.navigate(R.id.nav_guide, null)
    }

    private fun onTermsClick() {
        view?.findNavController()?.navigate(R.id.nav_terms, null)
    }

    private fun onTipsClick() {
        view?.findNavController()?.navigate(R.id.nav_tips, null)
    }

    private fun onSchemesClick() {
        view?.findNavController()?.navigate(R.id.nav_schemes, null)
    }

    private fun onSettingsClick() {
        view?.findNavController()?.navigate(R.id.nav_settings, null)
    }

    private fun onNewestClick() {
        view?.findNavController()?.navigate(R.id.nav_schemes, null)
    }

    private fun onAdsClick() {
        view?.findNavController()?.navigate(R.id.nav_ads, null)
    }
}
