package com.angelorobson.monitorerrorapp.utils

import okhttp3.OkHttpClient
import org.koin.core.KoinComponent
import org.koin.core.get
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

abstract class WebService<T> {

    companion object : KoinComponent {

        @Volatile
        private var INSTANCE: Retrofit? = null

        fun getInstance(): Retrofit {
            val baseWebService: BaseWebService = get()
            val okHttpClient: OkHttpClient = get()

            println("baseWebService " + baseWebService.baseUrl)

            if (INSTANCE != null) {
                return INSTANCE as Retrofit
            }

            synchronized(this) {

                val retrofit: Retrofit = Retrofit.Builder()
                    .baseUrl(baseWebService.baseUrl)
                    .client(okHttpClient)
                    .addConverterFactory(MoshiConverterFactory.create())
                    .build()

                INSTANCE = retrofit
                return INSTANCE as Retrofit
            }
        }
    }

    abstract fun api(): T
}
