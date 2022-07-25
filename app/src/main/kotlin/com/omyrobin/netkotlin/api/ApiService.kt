package com.omyrobin.netkotlin.api

import com.omyrobin.netkotlin.bean.BaseResponse
import com.omyrobin.netkotlin.bean.UserListBean
import com.omyrobin.netkotlin.network.calladapter.NetworkResponse
import com.omyrobin.network.exception.HandlerException
import retrofit2.Call
import retrofit2.http.GET
import java.lang.Error

/**
 * @Author: omyrobin
 * @CreateDate: 2020/11/16 11:56 AM
 * @Description:
 */
interface ApiService {

    @GET("wxarticle/chapters/json")
    suspend fun getUserListBean() : NetworkResponse<BaseResponse<List<UserListBean>>, Error>

    @GET("wxarticle/chapters/json")
    fun getUserListBean2() : Call<BaseResponse<List<UserListBean>>>

}