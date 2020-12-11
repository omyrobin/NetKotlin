package com.omyrobin.netkotlin.network.transform

import com.omyrobin.netkotlin.bean.BaseResponse
import com.omyrobin.network.exception.HandlerException
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.transform

/**
 * @Author: omyrobin
 * @CreateDate: 2020/11/18 11:06 AM
 * @Description:
 */

object NetData {

    suspend fun <T> transform(response: BaseResponse<T>): T? {
        var data: T? = null
        flow<BaseResponse<T>> {
            emit(response)
        }.transform<BaseResponse<T>, T?> {
            println(Thread.currentThread().name)
            if (it.success()) {
                emit(it.data)
            } else {
                throw HandlerException().BusinessThrowable(it.errorCode, it.errorMsg)
            }
        }.collect {
            data = it
        }
        return data
    }
}