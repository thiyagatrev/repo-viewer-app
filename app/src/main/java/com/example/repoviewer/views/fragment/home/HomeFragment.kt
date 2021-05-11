package com.example.repoviewer.views.fragment.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.repoviewer.R
import com.example.repoviewer.api.model.publicrepo.PublicRepoModel
import com.example.repoviewer.databinding.FragmentHomeBinding
import com.example.repoviewer.viewmodel.SharedViewModel
import com.example.repoviewer.views.TouchEventListner
import com.example.repoviewer.views.adapter.RepoSearchAdapter
import org.koin.android.ext.android.inject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment(), TouchEventListner {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val sharedViewModel: SharedViewModel by inject()
    lateinit var mrecyclerView: RecyclerView
    lateinit var madapter: RepoSearchAdapter
    private var columnCount = 1

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        mrecyclerView = view.findViewById(R.id.list)
        madapter = RepoSearchAdapter(activity?.baseContext!!, this)
        // Set the adapter

        with(mrecyclerView) {
            layoutManager = when {
                columnCount <= 1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, columnCount)
            }
            adapter = madapter
        }
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedViewModel.publicRepoMutableList.observe(viewLifecycleOwner, {
            //            Log.e(TAG, "initViewModelObserver: " + Gson().toJson(it))
            // Set the adapter
            // Set the adapter
            //Connect adapter with recyclerView
            madapter.apply {
                setData(it as ArrayList<PublicRepoModel>)
            }
        })
        mrecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    Log.e("-----", "end")
                    sharedViewModel.getPublicRepo()
                }
            }
        })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        sharedViewModel.getPublicRepo()

    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onClick(position: Int) {
        findNavController().navigate(
            R.id.action_homeFragment2_to_repoDetails,
            bundleOf(sharedViewModel.getPubliceRepoObject(position))
        )
    }

    override fun onLongClick(position: Int, additionalInfo: Any) {
        TODO("Not yet implemented")
    }
}