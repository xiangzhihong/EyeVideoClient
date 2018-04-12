package com.xzh.eyevideoclient.http



import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.xzh.eyevideoclient.utils.PreferenceUtils
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object RetrofitManager {

    private var client: OkHttpClient? = null
    private var retrofit: Retrofit? = null
    val service: ApiService by lazy { getRetrofit()!!.create(ApiService::class.java) }
    private var token: String by PreferenceUtils("token", "")

    /**
     * 添加参数
     */
    private fun addQueryParameterInterceptor(): Interceptor {
        return Interceptor { chain ->
            val originalRequest = chain.request()
            val request: Request
            val modifiedUrl = originalRequest.url().newBuilder()
                    .addQueryParameter("phoneSystem", "")
                    .addQueryParameter("phoneModel", "")
                    .build()
            request = originalRequest.newBuilder().url(modifiedUrl).build()
            chain.proceed(request)
        }
    }

    /**
     * 设置请求头
     */
    private fun addHeaderInterceptor(): Interceptor {
        return Interceptor { chain ->
            val originalRequest = chain.request()
            val requestBuilder = originalRequest.newBuilder()
                    .header("token", token)
                    .method(originalRequest.method(), originalRequest.body())
            val request = requestBuilder.build()
            chain.proceed(request)
        }
    }

    /**
     * 获取Retrofit实例
     */
    private fun getRetrofit(): Retrofit? {
        if (retrofit == null) {
            synchronized(RetrofitManager::class.java) {
                if (retrofit == null) {
                    //获取Client实例
                    client = OkHttpClient.Builder()
                            .addInterceptor(addQueryParameterInterceptor())
                            .addInterceptor(addHeaderInterceptor())
                            .connectTimeout(60L, TimeUnit.SECONDS)
                            .readTimeout(60L, TimeUnit.SECONDS)
                            .writeTimeout(60L, TimeUnit.SECONDS)
                            .build()
                    // 获取retrofit的实例
                    retrofit = Retrofit.Builder()
                            .baseUrl(ApiService.BASE_URL)
                            .client(client!!)
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build()
                }
            }
        }
        return retrofit
    }

}
