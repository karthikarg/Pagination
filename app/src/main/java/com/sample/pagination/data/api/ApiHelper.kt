package com.sample.pagination.data.api

import com.sample.pagination.data.model.PostData
import retrofit2.Response

/**
 * Helper interface for making API requests.
 */
interface ApiHelper {

    /**
     * Fetches the list of posts from the API.
     * @return A response containing the list of posts.
     */
    suspend fun getPosts(): Response<List<PostData>>

}