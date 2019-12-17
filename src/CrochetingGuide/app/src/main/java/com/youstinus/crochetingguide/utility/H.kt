package com.youstinus.crochetingguide.utility

import android.app.Activity
import android.content.Context

class H {
    companion object {
        fun cuSetPrefs(key: String, value: String, activity: Activity?) {
            activity?.getSharedPreferences("settings", Context.MODE_PRIVATE)?.edit()?.putString(key, key)?.apply()
        }

        fun cuGetPrefs(key: String, activity: Activity?): String? {
            return activity?.getSharedPreferences("settings", Context.MODE_PRIVATE)?.getString(key, null)
        }
    }
}
