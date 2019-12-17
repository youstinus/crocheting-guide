package com.youstinus.crochetingguide.fragments.guide

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.youstinus.crochetingguide.R

class GuideFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_guide, container, false)

        //(activity as AppCompatActivity).supportActionBar!!.title = "Gidas"

        return view
        //super.onCreateView(inflater, container, savedInstanceState);
    }
}
