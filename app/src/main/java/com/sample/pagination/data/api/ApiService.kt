package com.sample.pagination.data.api

import com.sample.pagination.data.model.PostData
import retrofit2.Response
import retrofit2.http.GET


/**
 * Service interface for making API requests.
 */
interface ApiService {


    @GET("posts")
    suspend fun getPosts(): Response<List<PostData>>

}

