package com.omyrobin.netkotlin.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response

/**
 * @Author: omyrobin
 * @CreateDate: 2020/12/11 6:08 PM
 * @Description:
 */
class CommonHeadInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain.request().newBuilder().run {
            addHeader("header1", "value1")
            addHeader("header2", "value2")
            addHeader("header3", "value3")
            build()
        }
        return chain.proceed(newRequest)
    }
}