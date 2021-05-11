package com.example.repoviewer.views.model

data class RepoItemModel(
    val id: String = "",
    val name: String = "",
    val fullName: String = "",
    val description: String = "",
    val ownerName: String = "",
    val ownerImageUrl: String = "",
    var time: Long = 0L
)
