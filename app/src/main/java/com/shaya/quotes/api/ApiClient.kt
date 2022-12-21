package com.shaya.quotes.api

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


private const val BASE_URL = "http://192.192.142.6/quoteapis/"


class ApiClient {

    companion object{

        var retrofit: Retrofit? = null

        fun getclient(): Retrofit? {

            if(retrofit == null) {


                val okHttpClient: OkHttpClient = OkHttpClient.Builder().build()
                val gson: Gson = GsonBuilder().create()


                retrofit = Retrofit.Builder().baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson)).build()
            }

            return retrofit

        }

    }
}
