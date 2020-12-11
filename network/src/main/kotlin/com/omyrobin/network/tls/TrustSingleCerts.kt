package com.omyrobin.network.tls

import java.io.InputStream
import java.security.KeyStore
import java.security.SecureRandom
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

/**
 * @Author: omyrobin
 * @CreateDate: 2020/11/21 5:43 PM
 * @Description:
 */
class TrustSingleCerts : X509TrustManager {

    override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {
    }

    override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
    }

    override fun getAcceptedIssuers(): Array<X509Certificate> {
        return emptyArray()
    }

    companion object {

        fun createSSLSocketFactory(vararg certificates: InputStream): SSLSocketFactory? {
            var ssfFactory: SSLSocketFactory? = null

            try {
                val certificateFactory = CertificateFactory.getInstance("X.509")
                val keyStore = KeyStore.getInstance(KeyStore.getDefaultType())
                keyStore.load(null)

                for ((index, certificate) in certificates.withIndex()) {
                    val certificateAlias = index.toString()
                    keyStore.setCertificateEntry(certificateAlias, certificateFactory.generateCertificate(certificate))
                    certificate.close()
                }

                val sslContext = SSLContext.getInstance("TLS")
                val trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
                trustManagerFactory.init(keyStore)
                sslContext.init(null, trustManagerFactory.trustManagers, SecureRandom())
                ssfFactory = sslContext.socketFactory
            } catch (e: Exception) {

            }
            return ssfFactory
        }
    }
}