package com.angelorobson.monitorerrorapp.utils

import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import org.koin.core.KoinComponent
import org.koin.core.get
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

abstract class WebService<T> {

    companion object : KoinComponent {

        @Volatile
        private var INSTANCE: Retrofit? = null

        fun getInstance(): Retrofit {
            val baseWebService: BaseWebService = get()
            val okHttpClient: OkHttpClient = get()
            val moshi: Moshi = get()

            if (INSTANCE != null) {
                return INSTANCE as Retrofit
            }

            synchronized(this) {
                val retrofit: Retrofit = Retrofit.Builder()
                    .baseUrl(baseWebService.baseUrl)
                    .client(okHttpClient)
                    .addConverterFactory(MoshiConverterFactory.create(moshi))
                    .build()

                INSTANCE = retrofit
                return INSTANCE as Retrofit
            }
        }
    }

    abstract fun api(): T
}
