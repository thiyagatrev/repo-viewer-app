package com.example.repoviewer.repositary

import com.example.repoviewer.api.model.publicrepo.PublicRepoModel
import com.example.repoviewer.usecase.GetPublicRepoUsecase
import io.reactivex.Observable

class ApiRespositary(private val getPublicRepoUsecase: GetPublicRepoUsecase) {

    fun getPublicRepos(since: Int): Observable<List<PublicRepoModel>> {
        return getPublicRepoUsecase.execute(since)
    }
}