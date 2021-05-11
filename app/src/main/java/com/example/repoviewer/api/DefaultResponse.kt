package com.example.repoviewer.api

import com.google.gson.annotations.SerializedName

data class DefaultResponse(

    @field:SerializedName("status")
    val status: Int? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("data")
    val data: String? = null
)