package com.example.repoviewer.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.repoviewer.api.model.publicrepo.PublicRepoModel
import com.example.repoviewer.database.model.RepoCommentModel
import com.example.repoviewer.repositary.ApiRespositary
import com.example.repoviewer.repositary.RepoCommentRepositaryUseCase
import com.example.repoviewer.utils.TAG
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class SharedViewModel(
    private val apiRespositary: ApiRespositary,
    private val repoCommentRepositoryUseCase: RepoCommentRepositaryUseCase
) : ViewModel() {

    var publicRepoMutableList = MutableLiveData<List<PublicRepoModel>>()
    var publicRepoList: ArrayList<PublicRepoModel> = ArrayList<PublicRepoModel>()
    var sinceNumber: Int = 1

    fun getPublicRepo() {
        apiRespositary.getPublicRepos(sinceNumber)
            .subscribeOn(Schedulers.io())
            .debounce(10, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ data ->
                setOrUpdatePublicRepoList(data)
                publicRepoMutableList.value = data
                sinceNumber += 369
            },
                {
                    Log.e(TAG, "checkIntentData: " + it.message)
                })

    }

    private fun setOrUpdatePublicRepoList(data: List<PublicRepoModel>?) {
        publicRepoList = data as ArrayList<PublicRepoModel>
    }

    fun getPubliceRepoObject(position: Int): Pair<String, Any?> {
        return Pair("publicRepoModel", publicRepoList[position])
    }

    fun saveComment(repoCommentModel: RepoCommentModel) {
        CoroutineScope(Dispatchers.Default).launch {
            repoCommentRepositoryUseCase.insert(repoCommentModel)
        }
    }

    fun getRepoComments(nodeId: String): LiveData<List<RepoCommentModel>?> {

        return repoCommentRepositoryUseCase.get(nodeId)
    }

    fun deleteComment(id: Int) {
        repoCommentRepositoryUseCase.delete(id)
    }


}


