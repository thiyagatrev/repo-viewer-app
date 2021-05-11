package com.example.repoviewer.views.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.repoviewer.database.model.RepoCommentModel
import com.example.repoviewer.databinding.RepoCommentItemBinding
import com.example.repoviewer.utils.TAG
import com.example.repoviewer.utils.toDateHoursMinutesSeconds
import com.example.repoviewer.views.TouchEventListner
import com.example.repoviewer.views.adapter.diffutils.RepoCommentDiffUtil
import com.google.gson.Gson


class RepoCommentAdapter(val context: Context, val touchEventListner: TouchEventListner) :
    RecyclerView.Adapter<RepoCommentAdapter.ViewHolder>() {

    var values: ArrayList<RepoCommentModel> = ArrayList()
    private var _binding: RepoCommentItemBinding? = null
    private val binding get() = _binding!!

    inner class ViewHolder(val binding: RepoCommentItemBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        _binding =
            RepoCommentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        Log.e(TAG, "onBindViewHolder: " + Gson().toJson(item))
        holder.binding.mtxtComment.text = item.comment.toString()
        holder.binding.mtxtTimestamp.text =
            "Commented at:" + item.timestamp.toDateHoursMinutesSeconds()

        holder.binding.mcardView.setOnLongClickListener { v: View? ->
            touchEventListner.onLongClick(position, item.id)
            true
        }
    }

    override fun getItemCount(): Int = values.size
    override fun getItemId(position: Int) = position.toLong()

    override fun getItemViewType(position: Int) = position

    fun setData(newData: ArrayList<RepoCommentModel>) {
        val diffResult = DiffUtil.calculateDiff(RepoCommentDiffUtil(values, newData))
        values.clear()
        this.values = newData
        diffResult.dispatchUpdatesTo(this)

    }


}