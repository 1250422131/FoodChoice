package com.imcys.foodchoice

import android.app.Application
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes
import com.microsoft.appcenter.distribute.Distribute
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class FoodApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        application = this
    }
    companion object{
        lateinit var application:Application
        fun initAppCenter(state: Int){
            if (state == 1) {
                AppCenter.start(
                    application,
                    "0391335a-2bae-4bef-ae0a-c23f592a7613",
                    Analytics::class.java,
                    Crashes::class.java,
                    Distribute::class.java
                )
            } else {
                AppCenter.start(
                    application,
                    "0391335a-2bae-4bef-ae0a-c23f592a7613",
                    Distribute::class.java
                )
            }
        }
    }
}
