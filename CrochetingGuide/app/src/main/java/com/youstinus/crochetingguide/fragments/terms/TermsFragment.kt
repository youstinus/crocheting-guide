package com.youstinus.crochetingguide.fragments.terms

import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.youstinus.crochetingguide.R
import com.youstinus.crochetingguide.utilities.FireFun
import java.io.File

fun String.capitalizeWords(): String = split(" ").map { it.capitalize() }.joinToString(" ")

class TermsFragment : Fragment() {

    private lateinit var videoView: VideoView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_terms, container, false) as ConstraintLayout
        //(activity as AppCompatActivity).supportActionBar!!.title = "Sutrumpinimai"
        //view.findViewById<ScrollView>(R.id.scrollView3).smoothScrollTo(0, 0)
        videoView = view.findViewById(R.id.videoView)
        setOnClickListeners(view)
        loadVideo(view, "single_crochet.mp4")
        view.findViewById<TextView>(R.id.textView_term).text = "Single Crochet"

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
        var str = path.substring(path.lastIndexOf('/') + 1)
        str = str.substring(0, str.length - 4).replace("_", " ").capitalizeWords()
        view?.findViewById<TextView>(R.id.textView_term)?.text = str

        //val names = path.split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
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

    private fun loadVideo(view: View, name: String) {
        videoView.pause()
        view.findViewById<ScrollView>(R.id.scrollView3).fullScroll(ScrollView.FOCUS_UP)
        val file = fullPath(name)
        if (file.exists()) {
            addVideoFromPath(file.path)
        } else {
            download(view, file)
        }
    }

    private fun download(view: View, file: File) {
        FireFun.getVideo(file) {success ->
            if (success) {
                addVideoFromPath(file.path)
                view.findViewById<ScrollView>(R.id.scrollView3).fullScroll(ScrollView.FOCUS_UP)
            } else {
                t(getString(R.string.download_failed))
            }
        }
    }

    private fun fullPath(fileName: String): File {
        return File(activity!!.filesDir.absolutePath + "/" + fileName)
    }

    private fun t(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
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

    private fun fileExists(name: String): Boolean {
        val filePath = activity!!.filesDir.absolutePath + "/" + name
        val file = File(filePath)
        return file.exists()
    }
}
