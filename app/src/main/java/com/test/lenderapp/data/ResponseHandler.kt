package com.test.lenderapp.data

import retrofit2.HttpException
import java.net.SocketTimeoutException

open class ResponseHandler {

    companion object {
        fun <T : Any> handleSuccess(data: T): Resource<T> {
            return Resource.success(data)
        }

        fun <T : Any> handleException(e: Exception): Resource<T> {
            return when (e) {
                is HttpException -> Resource.error(getErrorMessage(e.code()), null)
                is SocketTimeoutException -> Resource.error("Timeout", null)
                else -> Resource.error(getErrorMessage(Int.MAX_VALUE), null)
            }
        }

        private fun getErrorMessage(code: Int): String {
            return when (code) {
                401 -> "Unauthorised"
                404 -> "Not found"
                else -> "Something went wrong"
            }
        }
    }
}