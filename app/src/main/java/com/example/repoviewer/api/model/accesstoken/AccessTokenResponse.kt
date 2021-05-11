package com.example.repoviewer.api.model.accesstoken

import com.google.gson.annotations.SerializedName

class AccessTokenResponse {

    @SerializedName("access_token")
    var accessToken: String? = null

    @SerializedName("scope")
    var scope: String? = null

    @SerializedName("token_type")
    var tokenType: String? = null


    @SerializedName("expires_in")
    var expireTime: Long? = null

    @SerializedName("refresh_token")
    var refreshToken: String? = null

    @SerializedName("refresh_token_expires_in")
    var refreshTokenExpireTime: Long? = null

}
