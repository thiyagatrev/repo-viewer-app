package com.example.repoviewer.api


import com.example.repoviewer.api.model.accesstoken.AccessTokenRequest
import com.example.repoviewer.api.model.accesstoken.AccessTokenResponse
import com.example.repoviewer.api.model.publicrepo.PublicRepoModel
import com.example.repoviewer.api.model.userreposearch.UserRepoSearchResponse
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiCalls {


    @GET("login/oauth/authorize" + "&clientId")
    suspend fun authorise(clientId: String): Deferred<Response<DefaultResponse>>


    @Headers("Accept: application/json")
    @POST("login/oauth/access_token")
    suspend fun getAccessToken(@Body accessTokenRequest: AccessTokenRequest): Deferred<Response<AccessTokenResponse>>

    // https://api.github.com/search/repositories?q=topic:android
    @Headers("Accept: application/json")
    @GET("search/repositories")
    fun getRepoSearch(@Query("q") query: String): Deferred<Response<UserRepoSearchResponse>>

    @Headers("Accept: application/vnd.github.v3+json")
    @GET("/repositories")
    fun getPublicRepo(
        @Query("page") query: String,
        @Query("per_page") page: String,
        @Query("since") since: String
    ): Call<List<PublicRepoModel>>

}