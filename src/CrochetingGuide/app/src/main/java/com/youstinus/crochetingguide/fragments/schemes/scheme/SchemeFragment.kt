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
import androidx.core.view.marginBottom

import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

import java.util.ArrayList
import java.util.Arrays
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.youstinus.crochetingguide.R
import com.youstinus.crochetingguide.fragments.schemes.SchemeViewModel



class SchemeFragment : Fragment() {

    private var mViewModel: SchemeViewModel? = null
    private var scheme: Scheme? = null
    private var mainView: View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mainView = inflater.inflate(R.layout.fragment_scheme_item, container, false)
        scheme = arguments!!.getSerializable("item") as Scheme
        populateLayout()
        return mainView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel = ViewModelProviders.of(this).get(SchemeViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun populateLayout() {
        val layout = mainView!!.findViewById<LinearLayout>(R.id.linear_layout1)
        val texts = ArrayList(Arrays.asList<String>(*scheme!!.text!!.replace("\\n", "\n").split("\\{image\\}".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()))
        var imagesIndex = 0
        var textsIndex = 0

        for (type in scheme!!.sequence!!.split(",".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()) {
            when (type) {
                "0" -> {
                    val textView = TextView(activity)
                    textView.text = texts[textsIndex]
                    textView.background = resources.getDrawable(R.drawable.shape_border)
                    var dp = Math.round(TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, 8.toFloat(),resources.getDisplayMetrics()))
                    textView.setPadding(dp,dp,dp,dp)
                    textsIndex++
                    layout.addView(textView)
                }
                "1" -> {
                    val image = ImageView(activity)
                    val ref = FirebaseStorage.getInstance().getReferenceFromUrl("gs://crocheting-guide.appspot.com/images/" + scheme!!.images + "/" + imagesIndex + ".jpg")
                    downloadImages(ref, image)
                    imagesIndex++
                    layout.addView(image)
                    image.setPadding(16, 16, 16, 16)
                    //image.set

                }
                else -> t("something wrong")
            }
        }
    }

    private fun downloadImages(ref: StorageReference, image: ImageView) {
        ref.downloadUrl.addOnSuccessListener { uri ->
            /*Picasso.get().load(uri.toString()).into(image, new Callback() {
                    @Override
                    public void onSuccess() {
                        image.setScaleType(ImageView.ScaleType.FIT_CENTER);//Or ScaleType.FIT_CENTER
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });*/
            Glide.with(activity!!).load(uri).into(image)
        }.addOnFailureListener {
            // Handle any errors
        }
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

    fun t(m: String) {
        Toast.makeText(activity, m, Toast.LENGTH_SHORT).show()
    }

    companion object {

        fun newInstance(): SchemeFragment {
            return SchemeFragment()
        }
    }
}