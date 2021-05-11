package com.example.repoviewer.repositary

import androidx.lifecycle.LiveData
import com.example.repoviewer.database.RoomInitializer
import com.example.repoviewer.database.model.RepoCommentModel

interface RepoCommentRepositaryUseCase {
    fun insert(repoCommentModel: RepoCommentModel)

    fun insertList(repoCommentModel: List<RepoCommentModel>)

    fun getList(): List<RepoCommentModel>
    fun get(nodeId: String): LiveData<List<RepoCommentModel>?>
    fun delete(id: Int)
}

class RepoCommentRepositaryImpl(protected val roomInitializer: RoomInitializer) :
    RepoCommentRepositaryUseCase {


    override fun insert(repoCommentModel: RepoCommentModel) {
        roomInitializer.repoCommentDao().insert(repoCommentModel)
    }

    override fun insertList(repoCommentModel: List<RepoCommentModel>) {
        roomInitializer.repoCommentDao().insertList(repoCommentModel)
    }

    override fun getList(): List<RepoCommentModel> {

        return roomInitializer.repoCommentDao().getAll()
    }

    override fun get(nodeId: String): LiveData<List<RepoCommentModel>?> {
        return roomInitializer.repoCommentDao().get(nodeId)
    }

    override fun delete(id: Int) {
        roomInitializer.repoCommentDao().delete(id)
    }
}
