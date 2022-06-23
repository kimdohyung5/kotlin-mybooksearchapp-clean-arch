package com.kimdo.mybooksearchapp.data.api

import com.kimdo.mybooksearchapp.BuildConfig
import com.kimdo.mybooksearchapp.data.model.SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface BookSearchApi {

    @Headers("Authorization: KakaoAK ${BuildConfig.apiKey}")
    @GET("v3/search/book")
    suspend fun searchBooks(
        @Query("query") query: String,
        @Query("sort") sort: String,
        @Query("page") page: Int,
        @Query("size") size: Int,
    ) : Response<SearchResponse>
}