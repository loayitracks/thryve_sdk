package com.thryve.sample

import android.app.Application
import android.util.Log
import androidx.work.Configuration
import com.thryve.connector.sdk.logger.Logger

class App : Application(), Configuration.Provider {
    override fun getWorkManagerConfiguration() =
            Configuration.Builder()
                    .setMinimumLoggingLevel(Log.VERBOSE)
                    .build()

    override fun onCreate() {
        super.onCreate()
        Logger.init(Logger.Verbosity.INFO)
    }
}