package com.omyrobin.netkotlin

import android.app.Application
import com.omyrobin.netkotlin.network.config.NetConfig
import com.omyrobin.network.NetworkManager

/**
 * @Author: omyrobin
 * @CreateDate: 2020/12/11 6:02 PM
 * @Description:
 */
class NetApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        //此处可以添加适合自己项目的NetConfig
//        NetworkManager.init(this, NetConfig())
    }
}