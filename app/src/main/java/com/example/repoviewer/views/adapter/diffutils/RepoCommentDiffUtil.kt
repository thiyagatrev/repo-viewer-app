package com.example.repoviewer.views.adapter.diffutils

import androidx.annotation.Nullable
import androidx.recyclerview.widget.DiffUtil
import com.example.repoviewer.database.model.RepoCommentModel

class RepoCommentDiffUtil(
    private val oldList: ArrayList<RepoCommentModel>,
    private val newList: ArrayList<RepoCommentModel>
) :
    DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].comment === newList[newItemPosition].comment && oldList.size == newList.size
    }

    override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean {
        val (_, comment, timestamp) = oldList[oldPosition]
        val (_, comment1, timestamp1) = newList[newPosition]
        return comment == comment1 && timestamp == timestamp1
    }

    @Nullable
    override fun getChangePayload(oldPosition: Int, newPosition: Int): Any? {
        return super.getChangePayload(oldPosition, newPosition)
    }
}