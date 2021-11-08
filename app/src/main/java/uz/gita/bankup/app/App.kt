package uz.gita.bankup.app

import android.app.Application
import dagger.Provides
import dagger.hilt.android.HiltAndroidApp
import nl.qbusict.cupboard.BuildConfig
import timber.log.Timber
import uz.gita.bankup.data.api.pref.SharedPref
@HiltAndroidApp

class App :Application () {
    companion object {
        lateinit var instance :App
        private set
    }
    override fun onCreate() {
        super.onCreate()
        instance = this

        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())


    }
}