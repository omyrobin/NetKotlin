package com.omyrobin.network.exception

import com.google.gson.JsonParseException
import kotlinx.coroutines.CoroutineExceptionHandler
import org.json.JSONException
import retrofit2.HttpException
import java.io.InterruptedIOException
import java.io.NotSerializableException
import java.net.*
import javax.net.ssl.SSLHandshakeException
import javax.net.ssl.SSLPeerUnverifiedException
import kotlin.coroutines.CoroutineContext

/**
 * @Author: omyrobin
 * @CreateDate: 2020/11/18 3:08 PM
 * @Description:
 */
class HandlerException constructor() : CoroutineExceptionHandler {

    var error: IError? = null

    constructor(error: IError?) : this() {
        this.error = error
    }

    companion object {

        private const val UNAUTHORIZED = 401

        private const val FORBIDDEN = 403

        private const val NOT_FOUND = 404

        private const val REQUEST_TIMEOUT = 408

        private const val INTERNAL_SERVER_ERROR = 500

        private const val BAD_GATEWAY = 502

        private const val SERVICE_UNAVAILABLE = 503

        private const val GATEWAY_TIMEOUT = 504

        /**http异常**/
        private const val HTTP_ERROR = 600

        /**JSON数据解析数据失败**/
        private const val JSON_PARSE_ERROR = 800

        /**没有网络**/
        private const val NO_NET_CONNECT = 1000

        /**URL格式错误**/
        private const val URL_ERROR = 1001

        /**网络中断 DNS服务器故障 域名解析劫持 触发该异常**/
        private const val UNKNOWN_HOST_ERROR = 1002

        /**网络中断 DNS服务器故障 域名解析劫持 触发该异常**/
        private const val UNKNOWN_SERVICE_ERROR = 1003

        /**一般出现在缓存没有进行序列化，反序列化的过程中出现异常**/
        private const val NOT_SERIALIZABLE_ERROR = 1004

        /**带宽低、延迟高 路径拥堵、服务端负载吃紧 路由节点临时异常**/
        private const val CONNECT_TIMEOUT_ERROR = 1005

        /**请求读写阶段，请求线程被中断 触发该异常**/
        private const val INTERRUPTED_ERROR = 1006

        /**Tls协议协商失败 握手失败 客户端认证证书无法通过 ---可以降级HTTP**/
        private const val SSL_HANDSHAKE_ERROR = 1007

        /**使用 HostnameVerifier 来验证 host 是否合法，如果不合法会抛出 SSLPeerUnverifiedException**/
        private const val SSL_PEER_UNVERIFIED_ERROR = 1008

        /**服务器未运行 或者 端口关闭、防火墙阻止等**/
        private const val CONNECT_ERROR = 1009

        /**其他错误**/
        private const val OTHER_ERROR = 9999
    }

    override val key: CoroutineContext.Key<*>
        get() = CoroutineExceptionHandler

    override fun handleException(context: CoroutineContext, e: Throwable) {
        if (e is HttpException) {
            when (e.code()) {
                UNAUTHORIZED -> NetThrowable(UNAUTHORIZED, e.message)
                FORBIDDEN -> NetThrowable(FORBIDDEN, e.message)
                NOT_FOUND -> NetThrowable(NOT_FOUND, e.message)
                REQUEST_TIMEOUT -> NetThrowable(REQUEST_TIMEOUT, e.message)
                INTERNAL_SERVER_ERROR -> NetThrowable(INTERNAL_SERVER_ERROR, e.message)
                BAD_GATEWAY -> NetThrowable(BAD_GATEWAY, e.message)
                SERVICE_UNAVAILABLE -> NetThrowable(SERVICE_UNAVAILABLE, e.message)
                GATEWAY_TIMEOUT -> NetThrowable(GATEWAY_TIMEOUT, e.message)
                else -> NetThrowable(HTTP_ERROR, e.message)
            }
        } else if (e is JsonParseException || e is JSONException) {
            NetThrowable(JSON_PARSE_ERROR, e.message)
        } else if (e is MalformedURLException) {
            NetThrowable(URL_ERROR, e.message)
        } else if (e is UnknownHostException) {
            NetThrowable(UNKNOWN_HOST_ERROR, e.message)
        } else if (e is UnknownServiceException) {
            NetThrowable(UNKNOWN_SERVICE_ERROR, e.message)
        } else if (e is NotSerializableException) {
            NetThrowable(NOT_SERIALIZABLE_ERROR, e.message)
        } else if (e is SocketTimeoutException) {
            NetThrowable(CONNECT_TIMEOUT_ERROR, e.message)
        } else if (e is InterruptedIOException) {
            NetThrowable(INTERRUPTED_ERROR, e.message)
        } else if (e is SSLHandshakeException) {
            NetThrowable(SSL_HANDSHAKE_ERROR, e.message)
        } else if (e is SSLPeerUnverifiedException) {
            NetThrowable(SSL_PEER_UNVERIFIED_ERROR, e.message)
        } else if (e is ConnectException) {
            NetThrowable(CONNECT_ERROR, e.message)
        } else if (e is BusinessThrowable) {
            BusinessThrowable(e.code, e.error_msg)
        } else {
            NetThrowable(OTHER_ERROR, e.message)
        }
    }

    /**
     * 网络通信过程中的异常（Retrofit的HttpException Gson的解析异常 网络过程中IO异常）
     */
    inner class NetThrowable(code: Int, override var message: String?) : Throwable() {
        init {
            error?.onFail(code, message)
            println("exception : code is $code  message is $message")
        }
    }

    /**
     * 网络通信过程中的异常（Retrofit的HttpException Gson的解析异常 网络过程中IO异常）
     */
    inner class BusinessThrowable constructor(var code: Int, var error_msg: String?) : Throwable() {
        init {
            error?.onError(code, error_msg)
            println("exception : code is $code  message is $error_msg")
        }
    }
}



