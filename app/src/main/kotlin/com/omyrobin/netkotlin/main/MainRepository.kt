package com.omyrobin.netkotlin.main

import com.omyrobin.netkotlin.api.ApiService
import com.omyrobin.netkotlin.bean.UserListBean
import com.omyrobin.netkotlin.network.transform.NetData
import com.omyrobin.network.Api

/**å
 * @Author: omyrobin
 * @CreateDate: 2020/11/16 5:48 PM
 * @Description:
 */

class MainRepository {

    suspend fun userListRepository() : List<UserListBean>?{
        return NetData.transform(Api.create(ApiService::class.java).getUserListBean())
    }

}