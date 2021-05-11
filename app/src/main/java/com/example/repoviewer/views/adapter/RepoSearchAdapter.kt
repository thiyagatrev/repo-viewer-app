package com.example.repoviewer.views.adapter

import android.content.Context
import android.view.*
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.repoviewer.R
import com.example.repoviewer.api.model.publicrepo.PublicRepoModel
import com.example.repoviewer.databinding.FragmentItem2Binding
import com.example.repoviewer.utils.GlideUtils
import com.example.repoviewer.views.TouchEventListner
import com.example.repoviewer.views.adapter.diffutils.RepoSearchDiffUtil


/**
 * [RecyclerView.Adapter] that can display a [DummyItem].
 */
class RepoSearchAdapter(val context: Context, val touchEventListner: TouchEventListner) :
    RecyclerView.Adapter<RepoSearchAdapter.ViewHolder>() {

    var values: ArrayList<PublicRepoModel> = ArrayList()


    private var _binding: FragmentItem2Binding? = null
    private val binding get() = _binding!!

    inner class ViewHolder(private val binding: FragmentItem2Binding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        _binding = FragmentItem2Binding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        binding.mtxtName.text = item.name
        binding.mtxtFullName.text = item.fullName
        binding.mtxtDescription.text = item.description
        GlideUtils.loadImage(
            context = context,
            item.owner.ownerImageUrl,
            binding.mimgOwnerImage,
            context.getDrawable(R.drawable.ic_profile)
        )
        binding.mcardView.setOnClickListener { v: View? ->
            touchEventListner.onClick(position)
            true
        }
//        Log.e(TAG, "onBindViewHolder: " + Gson().toJson(item))

    }

    override fun getItemCount(): Int = values.size

    fun setData(newData: ArrayList<PublicRepoModel>) {
        val diffResult = DiffUtil.calculateDiff(RepoSearchDiffUtil(values, newData))
        values.clear()
        this.values.addAll(newData)
        diffResult.dispatchUpdatesTo(this)

    }
}