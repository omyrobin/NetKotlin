package com.omyrobin.netkotlin.network.callback

import com.omyrobin.netkotlin.bean.BaseResponse
import com.omyrobin.network.exception.HandlerException
import com.omyrobin.network.exception.IError
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * @Author: omyrobin
 * @CreateDate: 2021/1/27 11:22 AM
 * @Description:
 */
abstract class OmyCallback<T> constructor() : Callback<T> {

    abstract fun onSuccess(t: T?)

    var handlerException: HandlerException? = null

    constructor(error: IError) : this() {
        handlerException = HandlerException(error)
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        handlerException?.handleException(t)
    }

    override fun onResponse(call: Call<T>, response: Response<T>) {
        val t = response.body()

        t?.let {
            if (t is BaseResponse<*>) {
                if (t.success()) {
                    onSuccess(t)
                } else {
                    handlerException?.handleException(
                        HandlerException().BusinessThrowable(
                            t.errorCode,
                            t.errorMsg
                        )
                    )
                }
            } else {
                onSuccess(t)
            }
        }


    }
}