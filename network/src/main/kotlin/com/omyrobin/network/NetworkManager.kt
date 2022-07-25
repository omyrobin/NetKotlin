package com.omyrobin.network

import android.content.Context
import android.util.Log
import com.omyrobin.network.config.DefaultEventListener
import com.omyrobin.network.config.DefaultNetConfig
import com.omyrobin.network.config.INetConfig
import com.omyrobin.network.tls.SSLState
import com.omyrobin.network.tls.TrustAllCerts
import com.omyrobin.network.tls.TrustSingleCerts
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit


/**
 * @Author: omyrobin
 * @CreateDate: 2020/11/13 3:23 PM
 * @Description:
 */
object NetworkManager {

    private var client: OkHttpClient? = null

    private lateinit var retrofit: Retrofit

    private var netConfig: INetConfig = DefaultNetConfig()

    private val retrofitMap: HashMap<String, Retrofit> = hashMapOf()

    private val serviceMap: HashMap<String, Any> = hashMapOf()

    fun init(context: Context?, config: INetConfig?) {

        val okHttpBuilder = OkHttpClient.Builder()

        netConfig = config ?: DefaultNetConfig()

        client = netConfig.run {

            okHttpBuilder
                .callTimeout(callTimeout(), TimeUnit.SECONDS)
                .connectTimeout(connectTimeout(), TimeUnit.SECONDS)
                .readTimeout(readTimeout(), TimeUnit.SECONDS)
                .writeTimeout(writeTimeout(), TimeUnit.SECONDS)
                .retryOnConnectionFailure(retryOnConnectionFailure())
                .dns(dns())
                .eventListenerFactory(DefaultEventListener.Factory)
                .apply {
                    //添加拦截器
                    for (interceptor in interceptor()) {
                        addInterceptor(interceptor)
                    }

                    for (interceptor in networkInterceptor()) {
                        addNetworkInterceptor(interceptor)
                    }

                    if (BuildConfig.DEBUG) {
                        val loggingInterceptor = HttpLoggingInterceptor {
                            Log.d("Net", it)
                        }
                        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                        addNetworkInterceptor(loggingInterceptor)

                    }
                    //选择认证方式
                    when (sslState()) {
                        //支持信任所有证书https
                        SSLState.ALL -> {
                            TrustAllCerts.createSSLSocketFactory()?.let {
                                sslSocketFactory(it, TrustAllCerts())
                            }
                        }
                        //单向认证服务端自签名
                        SSLState.SINGLE -> {
                            requireNotNull(serverCer()) {
                                "ServerCer path was null"
                            }
                            val certificates = serverCer()?.let { context?.assets?.open(it) }
                            certificates?.let {
                                TrustSingleCerts.createSSLSocketFactory(certificates)?.let {
                                    sslSocketFactory(it, TrustSingleCerts())
                                }
                            }
                        }
                        //双向认证
                        SSLState.DOUBLE -> {
                            requireNotNull(clientCer()) {
                                "ClientCer path was null"
                            }
                            requireNotNull(serverCer()) {
                                "ServerCer path was null"
                            }

                            //TODO 双向认证
                        }
                    }
                }
                .hostnameVerifier(hostnameVerifier())
                .build()
        }
    }

    /**
     * 根据baseUrl构建Retrofit的实例
     *
     * @param baseUrl
     * @return
     */
    private fun createRetrofit(baseUrl: String): Retrofit {

        client ?: init(null, DefaultNetConfig())

        client?.let {
            retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addCallAdapterFactory(netConfig.responseCallAdapter())
                .addConverterFactory(netConfig.converterFactory())
                .build()
        }

        retrofitMap[baseUrl] = retrofit
        return retrofit
    }

    /**
     * 根据baseUrl 获取Retrofit的实例
     *
     * @param key
     * @return
     */
    private fun getRetrofit(baseUrl: String): Retrofit {
        retrofit = if (retrofitMap.containsKey(baseUrl)) {
            retrofitMap[baseUrl]!!
        } else {
            createRetrofit(baseUrl)
        }
        return retrofit
    }

    fun <T> create(service: Class<T>): T {
        return create(netConfig.baseUrl(), service)
    }

    fun <T> create(baseUrl: String, service: Class<T>): T {
        //根据key从缓存中查找
        return if (serviceMap.containsKey(baseUrl)) {
            serviceMap[baseUrl + service.name] as T
        } else {
            //根据key获取Retrofit
            val retrofit: Retrofit = getRetrofit(baseUrl)
            val t: T = retrofit.create(service)
            serviceMap[baseUrl + service.name] = t as Any
            t
        }
    }
}