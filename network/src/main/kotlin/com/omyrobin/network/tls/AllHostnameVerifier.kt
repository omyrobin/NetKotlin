package com.omyrobin.network.tls

import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLSession

/**
 * @Author: omyrobin
 * @CreateDate: 2020/11/15 7:14 AM
 * @Description:
 */
class AllHostnameVerifier : HostnameVerifier {

    override fun verify(hostname: String?, session: SSLSession?): Boolean {
        return true
    }
}