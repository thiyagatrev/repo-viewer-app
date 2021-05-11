package com.example.repoviewer.utils

class Constants {

    companion object {
        private var baseUrl: String = ""
        fun getBaseUrl() = baseUrl
        fun setBaseUrl(string: String) {
            baseUrl = string
        }
    }
}