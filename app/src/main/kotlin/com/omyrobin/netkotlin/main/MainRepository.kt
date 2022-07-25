package com.omyrobin.netkotlin.main

import com.omyrobin.netkotlin.api.ApiService
import com.omyrobin.netkotlin.bean.BaseResponse
import com.omyrobin.netkotlin.bean.UserListBean
import com.omyrobin.netkotlin.network.calladapter.NetworkResponse
import com.omyrobin.netkotlin.network.transform.NetData
import com.omyrobin.network.Api
import com.omyrobin.network.exception.HandlerException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import java.lang.Error

/**å
 * @Author: omyrobin
 * @CreateDate: 2020/11/16 5:48 PM
 * @Description:
 */

fun String.test(i: Int) : Int{
    return i*2
}

class MainRepository {

    val b = ::productor

    suspend fun userListRepository() : NetworkResponse<BaseResponse<List<UserListBean>>, Error>{
        return Api.create(ApiService::class.java).getUserListBean()
    }



    suspend fun productor(int: Int) = flow<Int> {
        for (i in 1..10) {
            delay(100)
            emit(i)
            println("produce $i")
        }
    }

    suspend fun productor2() {
        flow<Int> {
            for (i in 1..10) {
                delay(100)
                emit(i)
                println("produce $i")
            }
        }
    }


}