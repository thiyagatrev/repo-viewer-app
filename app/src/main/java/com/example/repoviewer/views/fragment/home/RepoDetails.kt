package com.example.repoviewer.views.fragment.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.repoviewer.R
import com.example.repoviewer.api.model.publicrepo.PublicRepoModel
import com.example.repoviewer.database.model.RepoCommentModel
import com.example.repoviewer.databinding.FragmentRepoDetailsBinding
import com.example.repoviewer.utils.GlideUtils
import com.example.repoviewer.utils.TAG
import com.example.repoviewer.viewmodel.SharedViewModel
import com.example.repoviewer.views.TouchEventListner
import com.example.repoviewer.views.adapter.RepoCommentAdapter
import com.google.gson.Gson
import org.koin.android.ext.android.inject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "publicRepoModel"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RepoDetails.newInstance] factory method to
 * create an instance of this fragment.
 */
class RepoDetails : Fragment(), TouchEventListner {
    // TODO: Rename and change types of parameters
    private var param1: PublicRepoModel? = null
    private var param2: String? = null

    private var _binding: FragmentRepoDetailsBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: SharedViewModel by inject()
    lateinit var mrecyclerView: RecyclerView
    lateinit var madapter: RepoCommentAdapter
    private var columnCount = 1
    private var commentList = MutableLiveData<List<RepoCommentModel>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getParcelable(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRepoDetailsBinding.inflate(inflater, container, false)
        val view = binding.root
        mrecyclerView = view.findViewById(R.id.list)
        madapter = RepoCommentAdapter(activity?.baseContext!!, this)
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
        bindviews()
        sharedViewModel.getRepoComments(param1?.node_id!!).observe(viewLifecycleOwner, {
            // Set the adapter
            // Set the adapter
            //Connect adapter with recyclerView
            madapter.apply {
                Log.e(TAG, "onViewCreated: " + values.isNullOrEmpty())
                Log.e(TAG, "onViewCreated: " + Gson().toJson(it))
                commentList.value = it
                if (values.isNullOrEmpty()) {
                    values = (it as ArrayList<RepoCommentModel>?)!!
                    notifyDataSetChanged()
                } else {
                    setData((it as ArrayList<RepoCommentModel>?)!!)
                }
            }

        })
        commentList.observe(viewLifecycleOwner, {
            if (it.isNullOrEmpty()) {
                binding.mtxtEmptyView.visibility = View.VISIBLE
            } else {
                binding.mtxtEmptyView.visibility = View.GONE

            }
        })

    }

    private fun bindviews() {
        param1?.let { it ->
            GlideUtils.loadImage(
                context = activity?.baseContext!!,
                imgUrl = it.owner.ownerImageUrl,
                imgView = binding.mimgOwnerImage,
                errorImg = context?.getDrawable(R.drawable.ic_profile)
            )
            binding.mtxtName.text = it.name
            binding.mtxtDescription.text = it.description
            binding.mtxtAdmin.text = if (it.owner.site_admin) "yes" else "no"
            binding.mtxtFork.text = if (it.fork) "yes" else "no"
            binding.mbtnSaveComment.setOnClickListener {
                if (!binding.meditUserComment.text.isNullOrEmpty() && !binding.meditUserComment.text.isNullOrBlank()) {
                    sharedViewModel.saveComment(
                        RepoCommentModel(
                            param1!!.node_id,
                            binding.meditUserComment.text.toString(),
                            System.nanoTime()
                        )
                    )
                    binding.meditUserComment.text?.clear()
                } else {
                    Toast.makeText(
                        context?.applicationContext,
                        "Cannot Save Empty Comment",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RepoDetails.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RepoDetails().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onClick(position: Int) {
        TODO("Not yet implemented")
    }

    override fun onLongClick(position: Int, additionalInfo: Any) {
        sharedViewModel.deleteComment(additionalInfo as Int)
    }
}