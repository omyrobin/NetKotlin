package com.omyrobin.network


/**
 * @Author: omyrobin
 * @CreateDate: 2020/11/16 10:56 AM
 * @Description:
 */
object Api {

    fun <T> create(service: Class<T>): T {
        return NetworkManager.create(service)
    }

    fun <T> create(baseUrl: String, service: Class<T>): T {
        return NetworkManager.create(baseUrl, service)
    }
}