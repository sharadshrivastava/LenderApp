package com.test.lenderapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.test.lenderapp.data.AccountDetailsRepository
import com.test.lenderapp.di.DaggerTestComponent
import com.test.lenderapp.di.TestModule
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.Buffer
import org.junit.Rule
import org.junit.rules.TestRule
import javax.inject.Inject


abstract class BaseTest {

    @Inject
    lateinit var mockWebServer : MockWebServer

    @Inject
    lateinit var accountDetailsRepository: AccountDetailsRepository

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    protected val UILOAD_DELAY = 2000L


    fun setUp() {
        val component = DaggerTestComponent.builder()
                .testModule(TestModule()).build()
        component.inject(this)
    }

    fun addDelay(millis: Long) {
        Thread.sleep(millis)
    }

    fun setResponse(fileName: String) {
        val input = this.javaClass.classLoader?.getResourceAsStream(fileName)
        mockWebServer.enqueue(MockResponse().setResponseCode(200).
            setBody(input?.bufferedReader().use {it!!.readText()}))
    }

    fun setErrorResponse() {
        mockWebServer.enqueue(MockResponse().setResponseCode(400).setBody("{}"))
    }
}
