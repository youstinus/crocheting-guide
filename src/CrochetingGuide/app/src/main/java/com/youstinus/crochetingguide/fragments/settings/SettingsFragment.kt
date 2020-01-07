package com.youstinus.crochetingguide.fragments.settings

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Switch
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.youstinus.crochetingguide.R
import com.youstinus.crochetingguide.utilities.Constants
import com.youstinus.crochetingguide.utilities.H

class SettingsFragment : Fragment() {

    companion object {
        fun newInstance() = SettingsFragment()
    }

    private lateinit var viewModel: SettingsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)
        setupScreenSwitch(view)
        setupLanguageSetter(view)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SettingsViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun setupScreenSwitch(view: View) {
        val switch = view.findViewById<Switch>(R.id.switch_screen);
        var screenOn = H.cuGetPrefs("screen-on", activity)
        if (screenOn == null) {
            screenOn = "false"
        }

        switch.isChecked = screenOn == "true"

        switch.setOnCheckedChangeListener { _, checked ->
            if (checked) {
                activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
                H.cuSetPrefs("screen-on", "true", activity)
            } else {
                activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
                H.cuSetPrefs("screen-on", "false", activity)
            }
        }
    }

    private fun setupLanguageSetter(view: View) {
        val lng = getLanguagePref(view)
        if (lng != null && lng == "en") {
            view.findViewById<RadioButton>(R.id.radioButton_lt).isChecked = false
            view.findViewById<RadioButton>(R.id.radioButton_en).isChecked = true
        } else {
            view.findViewById<RadioButton>(R.id.radioButton_lt).isChecked = true
            view.findViewById<RadioButton>(R.id.radioButton_en).isChecked = false
        }

        view.findViewById<RadioGroup>(R.id.radioGroup_language).setOnCheckedChangeListener { group, checkedId ->
            var idx = 0
            try {
                idx = group.indexOfChild(group.findViewById(group.checkedRadioButtonId))
            } catch (ex: Exception) {
            }
            val lang = if (idx == 1) "en" else "lt"
            setLanguagePref(view, lang)
            activity?.recreate()
        }
    }

    private fun getLanguagePref(view: View): String? {
        val mPrefs: SharedPreferences = view.context.getSharedPreferences(
                Constants.crocheting,
                Context.MODE_PRIVATE
        )
        return mPrefs.getString(Constants.language, "lt")
    }

    private fun setLanguagePref(view: View, lng: String) {
        val mPrefs: SharedPreferences = view.context.getSharedPreferences(
                Constants.crocheting,
                Context.MODE_PRIVATE
        )
        val prefsEditor = mPrefs.edit()
        prefsEditor.putString(Constants.language, lng)
        prefsEditor.apply()
    }
}
