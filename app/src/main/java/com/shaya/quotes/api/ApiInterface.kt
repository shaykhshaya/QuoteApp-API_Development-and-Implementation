package com.shaya.quotes.api

import com.shaya.quotes.response.DeleteResponse
import com.shaya.quotes.response.GetQuoteResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiInterface {

    @GET("getquote.php")
    fun getquote(): Call<GetQuoteResponse>

    @FormUrlEncoded
    @POST("deletequote.php")
    fun deletequote(@Field("quote_id") quote_id: String): Call<DeleteResponse>

    @FormUrlEncoded
    @POST("addquote.php")
    fun addquote(@Field("quote") quote: String, @Field("quote_name") quoteName:String): Call<DeleteResponse>

    @FormUrlEncoded
    @POST("updatequote.php")
    fun updatequote(@Field("quote_data") quoteData: String, @Field("id") id: String): Call<DeleteResponse>




}