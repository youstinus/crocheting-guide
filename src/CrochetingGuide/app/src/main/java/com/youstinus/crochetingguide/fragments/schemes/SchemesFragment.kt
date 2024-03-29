package com.youstinus.crochetingguide.fragments.schemes

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.youstinus.crochetingguide.R
import com.youstinus.crochetingguide.fragments.schemes.scheme.MySchemeRecyclerViewAdapter
import com.youstinus.crochetingguide.fragments.schemes.scheme.Scheme
import com.youstinus.crochetingguide.utilities.FireFun

/**
 * Mandatory empty constructor for the fragment manager to instantiate the
 * fragment (e.g. upon screen orientation changes).
 */
class SchemesFragment : Fragment() {


    // TODO: Customize parameters
    private var mColumnCount = 1
    private var mListener: OnListFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mColumnCount = arguments!!.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_scheme_list, container, false)
        //(activity as AppCompatActivity).supportActionBar!!.title = "Schemos"
        // Set the adapter
        //if (view instanceof RecyclerView) {
        //val context = view.context
        //schemes = DummyContent.ITEMS;
        readSchemes(view)
        //recyclerView.setAdapter(new MySchemeRecyclerViewAdapter(schemes, mListener));

        //DummyContent.ITEMS.add();
        //}
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */
    interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onListFragmentInteraction(item: Scheme)
    }

    private fun readSchemes(view: View) {
        val recyclerView = view.findViewById<View>(R.id.list) as RecyclerView
        if (mColumnCount <= 1) {
            recyclerView.layoutManager = LinearLayoutManager(context)
        } else {
            recyclerView.layoutManager = GridLayoutManager(context, mColumnCount)
        }

        FireFun.loadSchemes() {schemes->
            recyclerView.adapter = MySchemeRecyclerViewAdapter(schemes, mListener)
        }
    }

    companion object {
        // TODO: Customize parameter argument names
        private val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        fun newInstance(columnCount: Int): SchemesFragment {
            val fragment = SchemesFragment()
            val args = Bundle()
            args.putInt(ARG_COLUMN_COUNT, columnCount)
            fragment.arguments = args
            return fragment
        }
    }
}
