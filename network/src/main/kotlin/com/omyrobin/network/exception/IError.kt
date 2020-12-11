package com.omyrobin.network.exception

/**
 * @Author: omyrobin
 * @CreateDate: 2020/11/18 4:35 PM
 * @Description:
 */
interface IError {
    //网络失败
    fun onFail(code: Int, msg: String?)
    //业务异常
    fun onError(code: Int, msg: String?)
}