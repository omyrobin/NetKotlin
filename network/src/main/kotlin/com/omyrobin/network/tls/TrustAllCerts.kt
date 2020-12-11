package com.omyrobin.network.tls

import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

/**
 * @Author: omyrobin
 * @CreateDate: 2020/11/16 8:30 PM
 * @Description:
 */
class TrustAllCerts : X509TrustManager {

    override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {
    }

    override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
    }

    override fun getAcceptedIssuers(): Array<X509Certificate> {
        return emptyArray()
    }

    companion object {

        fun createSSLSocketFactory(): SSLSocketFactory? {
            var ssfFactory: SSLSocketFactory? = null

            try {
                val sslContext = SSLContext.getInstance("TLS")
                sslContext.init(null, arrayOf(TrustAllCerts()), SecureRandom())
                ssfFactory = sslContext.socketFactory
            } catch (e: Exception) {

            }
            return ssfFactory
        }


    }
}