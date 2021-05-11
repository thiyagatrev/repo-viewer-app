package com.example.repoviewer

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.multidex.MultiDexApplication
import com.example.repoviewer.database.RoomInitializer
import com.example.repoviewer.di.KoinUtils
import com.example.repoviewer.utils.Constants
import com.facebook.stetho.Stetho

class Application : MultiDexApplication() {


    override fun onCreate() {
        super.onCreate()
        appContext = this.applicationContext
        Constants.setBaseUrl("https://api.github.com/")
        Stetho.initializeWithDefaults(appContext)
        KoinUtils.setupKoin(appContext, listOf(KoinUtils.injectionModule))
        RoomInitializer.initDatabase(appContext, getString(R.string.app_name))
    }

    companion object {
        lateinit var appContext: Context
        fun isNetConnected(): Boolean {
            val cm =
                appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
            var result = false
            if (activeNetwork != null) {
                result = activeNetwork.isConnectedOrConnecting
            }
            return result
        }
    }


}