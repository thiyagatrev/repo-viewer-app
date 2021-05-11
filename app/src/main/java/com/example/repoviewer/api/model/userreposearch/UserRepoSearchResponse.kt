package com.example.repoviewer.api.model.userreposearch

import com.google.gson.annotations.SerializedName

data class UserRepoSearchResponse(
    @SerializedName("total_count") val total_count: Int,
    @SerializedName("incomplete_results") val incomplete_results: Boolean,
    @SerializedName("items") val items: List<Items>
)