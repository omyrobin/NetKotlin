package com.omyrobin.network.config

import okhttp3.*
import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.Proxy
import java.util.concurrent.atomic.AtomicLong


/**
 * @Author: omyrobin
 * @CreateDate: 2020/11/22 6:29 PM
 * @Description:
 */
class DefaultEventListener(private var callId: Long, private var callStartNanos: Long) :
    EventListener() {

    companion object Factory: EventListener.Factory {
        private val nextCallId = AtomicLong(1L)
        override fun create(call: Call): EventListener {
            val callId: Long = nextCallId.getAndIncrement()
            System.out.printf("%04d %s%n", callId, call.request().url)
            return DefaultEventListener(callId, System.nanoTime())
        }
    }

    private fun printEvent(name: String) {
        val elapsedNanos = System.nanoTime() - callStartNanos
        System.out.printf("%04d %.3f %s%n", callId, elapsedNanos / 1000000000.0, name)
    }

    /**网络请求开始**/
    override fun callStart(call: Call) {
        printEvent("callStart")
    }

    /**域名解析开始**/
    override fun dnsStart(call: Call, domainName: String) {
        printEvent("dnsStart")
    }

    /**域名解析结束**/
    override fun dnsEnd(call: Call, domainName: String, inetAddressList: List<InetAddress>) {
        printEvent("dnsEnd")
    }

    /**释放**/
    override fun connectionReleased(call: Call, connection: Connection) {
        super.connectionReleased(call, connection)
    }

    /**释放**/
    override fun connectionAcquired(call: Call, connection: Connection) {
        super.connectionAcquired(call, connection)
    }

    /**TCP握手开始**/
    override fun connectStart(call: Call, inetSocketAddress: InetSocketAddress, proxy: Proxy) {
        printEvent("connectStart")
    }

    /**TSL握手开始**/
    override fun secureConnectStart(call: Call) {
        printEvent("secureConnectStart")
    }

    /**TSL握手完成**/
    override fun secureConnectEnd(call: Call, handshake: Handshake?) {
        printEvent("secureConnectEnd")
    }

    /**TCP握手完成**/
    override fun connectEnd(
        call: Call,
        inetSocketAddress: InetSocketAddress,
        proxy: Proxy,
        protocol: Protocol?
    ) {
        printEvent("connectEnd")
    }

    override fun requestHeadersStart(call: Call) {
        printEvent("requestHeadersStart")
    }

    override fun requestHeadersEnd(call: Call, request: Request) {
        printEvent("requestHeadersEnd")
    }

    override fun requestBodyStart(call: Call) {
        printEvent("requestBodyStart")
    }

    override fun responseBodyEnd(call: Call, byteCount: Long) {
        printEvent("responseBodyEnd")
    }

    override fun responseHeadersStart(call: Call) {
        printEvent("responseHeadersStart")
    }

    override fun responseHeadersEnd(call: Call, response: Response) {
        printEvent("responseHeadersEnd")
    }

    override fun responseBodyStart(call: Call) {
        printEvent("responseBodyStart")
    }

    override fun requestBodyEnd(call: Call, byteCount: Long) {
        printEvent("requestBodyEnd")
    }

    override fun callEnd(call: Call) {
        printEvent("callEnd")
    }




}