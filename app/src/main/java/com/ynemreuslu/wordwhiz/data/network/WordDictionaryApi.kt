package com.ynemreuslu.wordwhiz.data.network

import com.ynemreuslu.wordwhiz.data.model.remote.WordData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface WordDictionaryApi {
    @GET("api/v2/entries/en/{word}")
    suspend fun fetchWordDetails(@Path("word") word: String): Response<List<WordData>>
}