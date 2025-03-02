package com.sw.sample.network

import com.sw.sample.common.GET_CHARS
import com.sw.sample.model.CharDataResult
import retrofit2.Response
import retrofit2.http.GET

interface APIService {

    @GET(GET_CHARS)
    suspend fun getCharacters(): Response<CharDataResult>
}