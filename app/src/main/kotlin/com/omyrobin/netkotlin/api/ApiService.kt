package com.omyrobin.netkotlin.api

import com.omyrobin.netkotlin.bean.BaseResponse
import com.omyrobin.netkotlin.bean.UserListBean
import retrofit2.http.GET

/**
 * @Author: omyrobin
 * @CreateDate: 2020/11/16 11:56 AM
 * @Description:
 */
interface ApiService {

    @GET("wxarticle/chapters/json")
    suspend fun getUserListBean() : BaseResponse<List<UserListBean>>

}