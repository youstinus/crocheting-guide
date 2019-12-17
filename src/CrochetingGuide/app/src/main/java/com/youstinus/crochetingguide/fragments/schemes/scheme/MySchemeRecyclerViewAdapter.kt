package com.youstinus.crochetingguide.fragments.schemes.scheme

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.youstinus.crochetingguide.R

import com.youstinus.crochetingguide.fragments.schemes.SchemesFragment.OnListFragmentInteractionListener

/**
 * [RecyclerView.Adapter] that can display a [Scheme] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 * TODO: Replace the implementation with code for your data type.
 */
class MySchemeRecyclerViewAdapter(private val mValues: List<Scheme>, private val mListener: OnListFragmentInteractionListener?) : RecyclerView.Adapter<MySchemeRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_scheme_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mItem = mValues[position]
        holder.mIdView.text = mValues[position].title
        //holder.mContentView.setText(mValues.get(position).getText());

        holder.mView.setOnClickListener {
            mListener?.onListFragmentInteraction(holder.mItem as Scheme)
        }
    }

    override fun getItemCount(): Int {
        return mValues.size
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mIdView: TextView
        //public final TextView mContentView;
        var mItem: Scheme? = null

        init {
            mIdView = mView.findViewById<View>(R.id.scheme_title) as TextView
            //mContentView = (TextView) view.findViewById(R.id.data_type);
        }

        override fun toString(): String {
            return super.toString() + " '" + mIdView.text + "'"
        }
    }
}
