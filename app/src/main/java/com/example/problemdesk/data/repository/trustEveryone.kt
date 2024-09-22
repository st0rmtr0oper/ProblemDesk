package com.example.problemdesk.data.repository

import android.util.Log
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSession
import javax.net.ssl.X509TrustManager

// пустые методы - принимаются все ssl сертификаты. Безопасность говна. Сомнительноооо... но для mvp пойдет.

fun trustEveryone() {
    try {
        HttpsURLConnection.setDefaultHostnameVerifier(object : HostnameVerifier {
            override fun verify(hostname: String?, session: SSLSession?): Boolean {
                return true
            }
        })
        val context: SSLContext = SSLContext.getInstance("TLS")
        context.init(null, arrayOf(object : X509TrustManager {
            override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {
            }
            override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
            }
            override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
        }), SecureRandom())

        HttpsURLConnection.setDefaultSSLSocketFactory(context.socketFactory)
    } catch (e: Exception) {
        Log.i("SSL exception", e.toString())
    }
}