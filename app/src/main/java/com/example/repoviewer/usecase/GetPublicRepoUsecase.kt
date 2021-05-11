package com.example.repoviewer.usecase

import android.util.Log
import android.widget.Toast
import com.example.repoviewer.Application
import com.example.repoviewer.R
import com.example.repoviewer.api.ApiCalls
import com.example.repoviewer.api.model.publicrepo.PublicRepoModel
import com.example.repoviewer.utils.TAG
import io.reactivex.Observable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


interface GetPublicRepoUsecase {
    fun execute(since: Int): Observable<List<PublicRepoModel>>
}

class GetPublicRepoUsecaseImpl(private val apiCalls: ApiCalls) : GetPublicRepoUsecase {
    override fun execute(since: Int): Observable<List<PublicRepoModel>> {
        return Observable.create { emitter ->
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    if (Application.isNetConnected()) {
                        val call = apiCalls.getPublicRepo("1", "2", since.toString())
                        call.enqueue(object : Callback<List<PublicRepoModel>> {
                            override fun onResponse(
                                call: Call<List<PublicRepoModel>>,
                                response: Response<List<PublicRepoModel>>
                            ) {
                                Log.e(TAG, "onResponse: " + response.body()?.size)
                                emitter.onNext(response.body()!!)
                            }

                            override fun onFailure(
                                call: Call<List<PublicRepoModel>>,
                                t: Throwable
                            ) {
                                emitter.tryOnError(t)
                            }


                        })
                    } else {
                        Toast.makeText(
                            Application.appContext,
                            Application.appContext.getString(R.string.msg_no_internet),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "execute: " + e.message)
                }

            }
        }

    }

}