package com.omyrobin.netkotlin.bean

/**
 * @Author: omyrobin
 * @CreateDate: 2020/11/16 4:50 PM
 * @Description:
 */
class BaseResponse<T> {

    var errorCode = 0
    var errorMsg: String? = null
    var data: T? = null

    fun success() = errorCode == 0


}