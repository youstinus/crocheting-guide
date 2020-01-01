package com.youstinus.crochetingguide.fragments.settings

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.youstinus.crochetingguide.R
import android.widget.Switch
import com.youstinus.crochetingguide.utilities.H


class SettingsFragment : Fragment() {

    companion object {
        fun newInstance() = SettingsFragment()
    }

    private lateinit var viewModel: SettingsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)
        val switch = view.findViewById<Switch>(R.id.switch_screen);
        var screenOn = H.cuGetPrefs("screen-on", activity)
        if (screenOn == null) {
            screenOn = "false"
        }

        switch.isChecked = screenOn == "true"

        switch.setOnCheckedChangeListener { _,checked ->
            if (checked) {
                activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
                H.cuSetPrefs("screen-on", "true", activity)
            }else {
                activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
                H.cuSetPrefs("screen-on", "false", activity)
            }
        }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SettingsViewModel::class.java)
        // TODO: Use the ViewModel
    }
}
