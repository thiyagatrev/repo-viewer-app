package com.example.repoviewer.api.model.accesstoken

import com.google.gson.annotations.SerializedName

data class AccessTokenRequest(

    @SerializedName("client_id")
    private var clientId: String? = null,

    @SerializedName("client_secret")
    private var clientSecret: String? = null,

    @SerializedName("code")
    private var code: String? = null


)
