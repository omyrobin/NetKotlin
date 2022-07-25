package com.omyrobin.network.config

import com.omyrobin.network.tls.AllHostnameVerifier
import com.omyrobin.network.tls.SSLState
import okhttp3.Dns
import okhttp3.EventListener
import okhttp3.Interceptor
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.converter.gson.GsonConverterFactory
import javax.net.ssl.HostnameVerifier

/**
 * @Author: omyrobin
 * @CreateDate: 2020/11/13 3:21 PM
 * @Description:
 */
interface INetConfig {

    fun baseUrl(): String

    fun callTimeout(): Long {
        return 30
    }

    fun connectTimeout(): Long {
        return 5
    }

    fun writeTimeout(): Long {
        return 10
    }

    fun readTimeout(): Long {
        return 10
    }

    fun retryOnConnectionFailure(): Boolean {
        return true
    }

    fun dns(): Dns {
        return Dns.SYSTEM
    }

    fun eventListener(): EventListener? {
        return null
    }

    fun sslState(): SSLState {
        return SSLState.ALL
    }

    fun clientCer(): String? {
        return null
    }

    fun serverCer(): String? {
        return null
    }

    fun interceptor(): MutableList<Interceptor> {
        return mutableListOf()
    }

    fun networkInterceptor(): MutableList<Interceptor> {
        return mutableListOf()
    }

    fun hostnameVerifier(): HostnameVerifier {
        return AllHostnameVerifier()
    }

    fun converterFactory(): Converter.Factory {
        return GsonConverterFactory.create()
    }

    fun responseCallAdapter(): CallAdapter.Factory

}