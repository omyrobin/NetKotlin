package com.omyrobin.netkotlin.network.config

import com.omyrobin.netkotlin.network.calladapter.NetworkResponseAdapterFactory
import com.omyrobin.netkotlin.network.interceptor.CommonHeadInterceptor
import com.omyrobin.network.config.INetConfig
import okhttp3.Interceptor
import retrofit2.CallAdapter

/**
 * @Author: omyrobin
 * @CreateDate: 2020/12/11 6:01 PM
 * @Description:
 */
class NetConfig : INetConfig{

    override fun baseUrl(): String {
       return "项目自己的baseurl"
    }

    override fun interceptor(): MutableList<Interceptor> {
        val mutableList = mutableListOf<Interceptor>()
        //添加自己的拦截器
        mutableList.add(CommonHeadInterceptor())
        return mutableList
    }

    override fun responseCallAdapter(): CallAdapter.Factory {
        return NetworkResponseAdapterFactory()
    }
}