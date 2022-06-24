package com.kimdo.mybooksearchapp.data.api

import android.content.Context
import com.kimdo.mybooksearchapp.util.Constants.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.InputStream
import java.security.cert.Certificate
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

//object RetrofitInstance {
//    private val okHttpClient: OkHttpClient by lazy {
//        val httpLoggingInterceptor = HttpLoggingInterceptor()
//            .setLevel(HttpLoggingInterceptor.Level.BASIC)
//
//        val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
//            override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {
//            }
//
//            override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
//            }
//
//            override fun getAcceptedIssuers() = arrayOf<X509Certificate>()
//        })
//        val sslContext = SSLContext.getInstance("SSL")
//        sslContext.init(null, trustAllCerts, java.security.SecureRandom())
//        val sslSocketFactory = sslContext.socketFactory
//
//        OkHttpClient.Builder()
//            .addInterceptor( httpLoggingInterceptor )
//            .sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
//            .hostnameVerifier{ _, _ -> true }
//            .build()
//    }
//
//    private val retrofit: Retrofit by lazy {
//        Retrofit.Builder()
//            .addConverterFactory( MoshiConverterFactory.create())
//            .client(okHttpClient)
//            .baseUrl(BASE_URL)
//            .build()
//    }
//
//    val api: BookSearchApi by lazy {
//        retrofit.create( BookSearchApi::class.java)
//    }
//}