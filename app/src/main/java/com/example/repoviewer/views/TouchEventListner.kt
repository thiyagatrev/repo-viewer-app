package com.example.repoviewer.views

interface TouchEventListner {

    fun onClick(position: Int)
    fun onLongClick(position: Int, additionalInfo: Any)

}
