package com.sample.pagination.repos

import com.sample.pagination.data.model.PostData
import com.sample.pagination.data.api.ApiService
import retrofit2.Response
import javax.inject.Inject

/**
 * Repository class responsible for fetching data related to Posts from the API service.
 * @param apiService The API service used to fetch data from the network.
 */
class ApiRepository @Inject constructor(val apiService: ApiService) {

    /**
     * Fetches the list of Posts from the API service.
     * @return A response containing the list of Posts.
     */
    suspend fun getPosts() : Response<List<PostData>>
    {
        return apiService.getPosts()
    }

}