package com.youstinus.crochetingguide.utilities

import android.net.Uri
import android.widget.ImageView
import android.widget.ScrollView
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.youstinus.crochetingguide.R
import com.youstinus.crochetingguide.fragments.schemes.scheme.MySchemeRecyclerViewAdapter
import com.youstinus.crochetingguide.fragments.schemes.scheme.Scheme
import java.io.File

class FireFun {
    companion object {
        fun loadSchemes(callBack: (schemes: MutableList<Scheme>) -> Unit) {
            val mFireStore = FirebaseFirestore.getInstance() //var ref = FirebaseDatabase.getInstance().getReference("schemes")
            mFireStore
                    .collection("schemes")
                    .get()
                    .addOnSuccessListener { docs ->
                        if (!docs.isEmpty) {
                            //schemes.clear();
                            val schemes = docs.toObjects(Scheme::class.java)
                            callBack.invoke(schemes)
                            //dataStatus.DataIsLoaded(schemes, keys);
                        }
                    }.addOnFailureListener {

                    }
        }

        fun getVideo(file: File, callBack: (success: Boolean) -> Unit) {
            val mFireStorage = FirebaseStorage.getInstance()
            mFireStorage
                    .reference
                    .child("videos")
                    .child("terms")
                    .child(file.name)
                    .getFile(file)
                    .addOnSuccessListener {
                        // Local temp file has been created
                        callBack.invoke(true)
                    }.addOnFailureListener {
                        // Handle any errors
                        callBack.invoke(false)
                    }
        }

        fun downloadImages(scheme: Scheme?, imagesIndex: Int, callBack: (uri: Uri?) -> Unit) {
            val ref = FirebaseStorage.getInstance().reference.child("images").child(scheme!!.images).child("$imagesIndex.jpg")//getReferenceFromUrl("gs://crocheting-guide.appspot.com/images/" + scheme!!.images + "/" + imagesIndex + ".jpg")
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
                callBack.invoke(uri)
            }.addOnFailureListener {
                // Handle any errors
                callBack.invoke(null)
            }
        }
    }
}