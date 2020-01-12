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
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.firebase.storage.FirebaseStorage
import com.google.gson.Gson
import com.youstinus.crochetingguide.R
import com.youstinus.crochetingguide.fragments.schemes.SchemeViewModel
import com.youstinus.crochetingguide.utilities.Constants
import com.youstinus.crochetingguide.utilities.FireFun.Companion.downloadImages
import devdeeds.com.changelanguage.LocaleHelper
import java.io.File
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
        //setupAd(view)
        populateLayout(view)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel = ViewModelProviders.of(this).get(SchemeViewModel::class.java)
        // TODO: Use the ViewModel
    }

    /*private fun setupAd(view: View) {
        val adView = view.findViewById<AdView>(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
    }*/

    private fun populateLayout(view: View) {
        val description = getDescriptionByLanguage(view, scheme!!.descriptions)
        val layout = view.findViewById<LinearLayout>(R.id.linear_layout1)
        val texts = ArrayList(Arrays.asList<String>(*description.replace("\\n", "\n").split("\\{image\\}".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()))
        var imagesIndex = 0
        var textsIndex = 0

        val dp = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8.toFloat(), resources.displayMetrics).roundToInt()
        val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        params.setMargins(dp, dp, dp, dp)

        for (type in scheme!!.sequence.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()) {
            when (type) {
                "0" -> {
                    if (textsIndex < texts.size) {
                        val textView = TextView(activity)
                        textView.text = texts[textsIndex]
                        textView.background = resources.getDrawable(R.drawable.shape_border)
                        textView.setPadding(dp, dp, dp, dp)
                        textsIndex++
                        textView.layoutParams = params
                        layout.addView(textView)
                    }
                }
                "1" -> {
                    val image = ImageView(activity)
                    // todo old straight forward method
                    /*downloadImages(scheme, imagesIndex) { uri ->
                        if (uri != null) {
                            Glide.with(activity!!).load(uri).into(image)
                            //Picasso.get().load(uri).fit().centerCrop().into(image)
                        }
                    }*/
                    // todo new method for saving downloaded images
                    loadImage(image, scheme, imagesIndex)
                    imagesIndex++
                    image.setPadding(dp, dp, dp, dp)
                    //image.layoutParams = params
                    layout.addView(image)
                }
                else -> t("something wrong")
            }
        }
    }

    private fun getDescriptionByLanguage(view: View, descriptions: ArrayList<String>): String {
        val language = LocaleHelper.getLanguage(view.context!!)
        val index = when (language) {
            "lt" -> 0
            "en" -> 1
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

    private fun loadImage(image: ImageView, scheme: Scheme?, imageIndex: Int) {
        if (scheme == null || context == null || activity == null)
            return

        val name = "$imageIndex.jpg"//questions[qNumber].Image.substring(questions[qNumber].Image.lastIndexOf("/"))
        val myExternalFile = File(
                context?.getExternalFilesDir(Constants.schemes + "/" + scheme.images + "/" + Constants.images + "/"),
                name
        )
        when {
            myExternalFile.exists() -> {
                //Picasso.get().load(myExternalFile).into(imageView)
                Glide.with(activity!!).load(myExternalFile).into(image)

                //imageView.visibility = View.VISIBLE

                /*questions[qNumber].ImageUrl != Constants.EMPTY_STRING -> {
                    //Picasso.get().load(questions[qNumber].ImageUrl).into(imageView)
                    //imageView.visibility = View.VISIBLE
                    downloadImageAndSave(questions[qNumber].Image)
                }*/

                // todo you can use persistance here
                val storage = FirebaseStorage.getInstance().reference.child("images").child(scheme.images).child(name)
                /*storage.downloadUrl.addOnSuccessListener {
                    // triggered later. so we check if image needed later
                    //var ur = it.toString()
                    //GlideApp.with(view?.context!!).load(ur).into(imageView);
                    if (it != null) {
                        Glide.with(activity!!).load(it).into(image)
                        //Picasso.get().load(uri).fit().centerCrop().into(image)
                    }
                }.addOnFailureListener {
                    //val ex = it
                    //Timber.e(ex)
                }*/

                storage.getFile(myExternalFile).addOnSuccessListener {
                    if (activity != null && myExternalFile != null && image != null) {
                        Glide.with(activity!!).load(myExternalFile).into(image)
                    }
                    // Local temp file has been created
                    //Timber.d("File saved")
                }.addOnFailureListener {
                    // Handle any errors
                    //val ex = it
                    //Timber.e(ex, "File failed to save")
                }
            }
            else -> {
                FirebaseStorage.getInstance().reference.child("images").child(scheme.images).child(name).getFile(myExternalFile).addOnSuccessListener {
                    if (activity != null && myExternalFile != null && image != null) {
                        Glide.with(activity!!).load(myExternalFile).into(image)
                    }
                }.addOnFailureListener {
                }
            }
        }
    }

    /*private fun downloadImageAndSave(scheme: Scheme, image: String) {
        if (image == null || image == Constants.EMPTY_STRING) {
            return
        }

        val storage =
        val name = image.substring(image.lastIndexOf("/"))
        val myExternalFile =
                File(
                        context?.getExternalFilesDir(Constants.schemes + "/" + scheme.images + "/" + Constants.images + "/"),
                        name
                )//context?.getExternalFilesDir("quizzes/"+quizId+"/images/"), name)
        storage.child("images").child(scheme.images).child(name).getFile(myExternalFile).addOnSuccessListener {
            // Local temp file has been created
            Timber.d("File saved")
        }.addOnFailureListener {
            // Handle any errors
            val ex = it
            Timber.e(ex, "File failed to save")
        }
    }*/

    companion object {
        fun newInstance(): SchemeFragment {
            return SchemeFragment()
        }
    }
}
