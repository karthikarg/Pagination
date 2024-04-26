package com.sample.pagination.data.api

import com.sample.pagination.data.model.PostData
import retrofit2.Response
import javax.inject.Inject

/**
 * Implementation of the API helper interface.
 * @param apiService The service interface for making API requests.
 */
class ApiHelperImpl  @Inject constructor(private val apiService: ApiService) : ApiHelper {

    /**
     * Fetches the list of Posts from the API.
     * @return A response containing the list of Posts.
     */
    override suspend fun getPosts(): Response<List<PostData>> {
        return apiService.getPosts()
    }



}