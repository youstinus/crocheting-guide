package com.youstinus.crochetingguide.fragments.schemes.scheme

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast

import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.gson.Gson
import com.youstinus.crochetingguide.R
import com.youstinus.crochetingguide.fragments.schemes.SchemeViewModel
import com.youstinus.crochetingguide.utilities.FireFun.Companion.downloadImages
import devdeeds.com.changelanguage.LocaleHelper
import java.util.*
import kotlin.math.roundToInt

class SchemeFragment : Fragment() {

    private var mViewModel: SchemeViewModel? = null
    private var scheme: Scheme? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_scheme_item, container, false)
        //scheme = arguments!!.getSerializable("item") as Scheme
        arguments?.let {
            scheme = Gson().fromJson(it.getString("scheme"), Scheme::class.java)
        }
        populateLayout(view)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel = ViewModelProviders.of(this).get(SchemeViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun populateLayout(view: View) {
        val description = getDescriptionByLanguage(view, scheme!!.descriptions)
        val layout = view.findViewById<LinearLayout>(R.id.linear_layout1)
        val texts = ArrayList(Arrays.asList<String>(*description.replace("\\n", "\n").split("\\{image\\}".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()))
        var imagesIndex = 0
        var textsIndex = 0

        for (type in scheme!!.sequence.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()) {
            when (type) {
                "0" -> {
                    if (textsIndex < texts.size) {
                        val textView = TextView(activity)
                        textView.text = texts[textsIndex]
                        textView.background = resources.getDrawable(R.drawable.shape_border)
                        val dp = TypedValue.applyDimension(
                                TypedValue.COMPLEX_UNIT_DIP, 8.toFloat(), resources.displayMetrics).roundToInt()
                        textView.setPadding(dp, dp, dp, dp)
                        textsIndex++
                        layout.addView(textView)
                    }
                }
                "1" -> {
                    val image = ImageView(activity)
                    downloadImages(scheme, imagesIndex) { uri ->
                        if (uri != null) {
                            Glide.with(activity!!).load(uri).into(image)
                        }
                    }
                    imagesIndex++
                    layout.addView(image)
                    image.setPadding(16, 16, 16, 16)
                    //image.set

                }
                else -> t("something wrong")
            }
        }
    }

    private fun getDescriptionByLanguage(view: View, descriptions: ArrayList<String>): String {
        val language = LocaleHelper.getLanguage(view.context!!)
        val index = when(language) {
            "lt"-> 0
            "en"-> 1
            else -> 0
        }

        return if (descriptions.size > index) descriptions[index] else descriptions[0]
    }

    /*public void download(String name, final File file) {
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        firebaseStorage.getReference().child("images").child(name).child(file.getName()).getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                // Local temp file has been created
                addVideoFromPath(file.getPath());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
                t("Download failed");
            }
        });
    }*/

    private fun t(m: String) {
        Toast.makeText(activity, m, Toast.LENGTH_SHORT).show()
    }

    companion object {

        fun newInstance(): SchemeFragment {
            return SchemeFragment()
        }
    }
}
