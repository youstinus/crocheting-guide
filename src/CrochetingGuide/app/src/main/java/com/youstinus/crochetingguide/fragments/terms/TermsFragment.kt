package com.youstinus.crochetingguide.fragments.terms

import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.MediaController
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import android.widget.VideoView

import com.google.firebase.storage.FirebaseStorage

import java.io.File
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.youstinus.crochetingguide.R

class TermsFragment : Fragment() {

    private lateinit var videoView: VideoView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_terms, container, false) as ConstraintLayout
        //(activity as AppCompatActivity).supportActionBar!!.title = "Sutrumpinimai"
        //view.findViewById<ScrollView>(R.id.scrollView3).smoothScrollTo(0, 0)
        videoView = view.findViewById(R.id.videoView)
        setOnClickListeners(view)
        loadVideo(view, "single_crochet.mp4")
        //view.findViewById<VideoView>(R.id.videoView).pause()
        return view
        //super.onCreateView(inflater, container, savedInstanceState);
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        view?.findViewById<ScrollView>(R.id.scrollView3)?.fullScroll(ScrollView.FOCUS_UP)
    }

    private fun setOnClickListeners(view: View) {
        view.findViewById<Button>(R.id.button_mr).setOnClickListener { loadVideo(view, "magic_ring.mp4") }
        view.findViewById<Button>(R.id.button_sc).setOnClickListener { loadVideo(view, "single_crochet.mp4") }
        view.findViewById<Button>(R.id.button_inc).setOnClickListener { loadVideo(view, "increase.mp4") }
        view.findViewById<Button>(R.id.button_2tog).setOnClickListener { loadVideo(view, "two_together.mp4") }
        view.findViewById<Button>(R.id.button_hdc).setOnClickListener { loadVideo(view, "half_double_crochet.mp4") }
        view.findViewById<Button>(R.id.button_dc).setOnClickListener { loadVideo(view, "double_crochet.mp4") }
        view.findViewById<Button>(R.id.button_ch).setOnClickListener { loadVideo(view, "chain.mp4") }
        view.findViewById<Button>(R.id.button_slst).setOnClickListener { loadVideo(view, "slip_stitch.mp4") }
    }

    private fun addVideoFromResource(view: View, resource: Int) {
        val activity = activity
        val videoView = view.findViewById<VideoView>(R.id.videoView)

        videoView.setVideoPath("android.resource://" + activity!!.packageName + "/" + resource)

        val controller = MediaController(activity)
        controller.setAnchorView(videoView)
        videoView.setMediaController(controller)
        videoView.setZOrderOnTop(true)
        videoView.start()
    }

    private fun addVideoFromPath(path: String) {
        val activity = activity
        //val videoView = view.findViewById<VideoView>(R.id.videoView)

        videoView.setVideoPath(path)

        val controller = MediaController(activity)
        controller.setAnchorView(videoView)
        //videoView.setMediaController(controller);

        //videoView.setZOrderMediaOverlay(true);
        videoView.setZOrderOnTop(false)
        videoView.setBackgroundColor(Color.TRANSPARENT)

        videoView.start()
        videoView.setMediaController(null)
        videoView.setOnTouchListener { _, _ ->
            videoView.setMediaController(controller)
            controller.show()
            true
        }
        val names = path.split("/".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
        //val name = names[names.size - 1]

        //renameDemoTextView(name.substring(0, 1).toUpperCase() + name.substring(1, name.length - 4).replace("_", " "))
    }

    private fun addVideoFromUri(view: View, uri: Uri) {
        val activity = activity
        val videoView = view.findViewById<VideoView>(R.id.videoView)

        videoView.setVideoURI(uri)

        val controller = MediaController(activity)
        controller.setAnchorView(videoView)
        videoView.setMediaController(controller)
        videoView.setZOrderOnTop(true)
        videoView.start()
    }

    fun loadVideo(view: View, name: String) {
        videoView.pause()
        view.findViewById<ScrollView>(R.id.scrollView3).fullScroll(ScrollView.FOCUS_UP)
        val file = fullPath(name)
        if (file.exists()) {
            addVideoFromPath(file.path)
        } else {
            download(view, file)
        }
    }

    fun download(view: View, file: File) {
        val firebaseStorage = FirebaseStorage.getInstance()

        firebaseStorage.reference.child("videos").child("terms").child(file.name).getFile(file).addOnSuccessListener {
            // Local temp file has been created
            addVideoFromPath(file.path)
            view.findViewById<ScrollView>(R.id.scrollView3).fullScroll(ScrollView.FOCUS_UP)
        }.addOnFailureListener {
            // Handle any errors
            t(getString(R.string.download_failed))
        }
    }

    fun fileExists(name: String): Boolean {
        val filePath = activity!!.filesDir.absolutePath + "/" + name
        val file = File(filePath)
        return file.exists()
    }

    fun fullPath(fileName: String): File {
        return File(activity!!.filesDir.absolutePath + "/" + fileName)
    }

    fun t(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }
}
