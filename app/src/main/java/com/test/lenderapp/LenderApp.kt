package com.test.lenderapp

import android.app.Application
import com.test.lenderapp.di.AppComponent
import com.test.lenderapp.di.DaggerAppComponent
import com.test.lenderapp.di.ServiceModule

class LenderApp : Application() {

    lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()

        instance = this
        component = DaggerAppComponent.builder().serviceModule(ServiceModule()).build()
    }

    companion object {
        private var instance: LenderApp? = null
        fun get(): LenderApp = instance!!
    }
}