package com.omyrobin.network.config

/**
 * @Author: omyrobin
 * @CreateDate: 2020/11/16 10:44 AM
 * @Description:
 */
class DefaultNetConfig : INetConfig {

    override fun baseUrl(): String {
        return "https://www.wanandroid.com/"
    }
}