package com.test.lenderapp.di

import com.test.lenderapp.BaseTest
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(TestModule::class))
interface TestComponent {
    fun inject(test: BaseTest)
}