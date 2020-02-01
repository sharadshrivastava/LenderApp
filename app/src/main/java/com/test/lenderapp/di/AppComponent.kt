package com.test.lenderapp.di

import com.test.lenderapp.ui.home.HomeViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ServiceModule::class))
interface AppComponent {

    fun inject(vm: HomeViewModel)
}