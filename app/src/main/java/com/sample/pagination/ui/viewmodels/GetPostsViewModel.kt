package com.sample.pagination.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.pagination.data.model.PostData
import com.sample.pagination.repos.ApiRepository
import com.sample.pagination.utils.AppConstants.ERROR_MSG
import com.sample.pagination.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel class responsible for providing data related to Posts to the UI.
 * This class is annotated with @HiltViewModel to allow for dependency injection using Hilt.
 * @param apiRepository The repository responsible for fetching data related to Posts.
 */
@HiltViewModel
class GetPostsViewModel @Inject constructor (val apiRepository: ApiRepository) : ViewModel() {

    private val postsData =  MutableLiveData<Resource<List<PostData>>>()
     val postLiveData : LiveData<Resource<List<PostData>>>
        get() = postsData

    val postListData =  MutableLiveData<List<PostData>>()
    val postListLiveData : LiveData<List<PostData>>
        get() = postListData

    var postList: MutableList<PostData> = mutableListOf()
    var postListLoaded: MutableList<PostData> = mutableListOf()
    private val PAGE_LIMIT = 15

    var isLastPage = false


    /**
     * Fetches a list of posts asynchronously from the repository and updates LiveData accordingly.
     */
    fun getPosts(){
        viewModelScope.launch {

            // Calls the repository to get the list of books asynchronously
            apiRepository.getPosts().let {
                // Checks if the response is successful
                if(it.isSuccessful){

                    val requestTime: Long = it.raw().sentRequestAtMillis()
                    val responseTime: Long = it.raw().receivedResponseAtMillis()
                    val apiTime = responseTime - requestTime

                    println("Log Post Api response Time(In Millis): $apiTime")

                    // Parses the response body and updates the LiveData with the successful result
                    it.body()?.let {list->
                        postList.addAll(list)
                        postsData.value = Resource.success(list)
                    }
                }else
                {
                    // If the response is not successful, handles the error
                    postsData.value =  Resource.error(data = null, message = ERROR_MSG)
                }
            }
        }

    }

    /**
     * Fetches additional items if not currently loading and not reached the last page.
     * Also updates the loading state and requests posts from the view model.
     */
    fun fetchItems() {

        val time = System.currentTimeMillis()

        // Check if not currently loading and not reached the last page
        if (!isLastPage) {
            // Calculate the last index to fetch from the URL list
            val last = if (postList.size < postListLoaded.size + PAGE_LIMIT)
                postList.size
            else
                postListLoaded.size + PAGE_LIMIT
            // Extract sublist from URL list based on current characterList size and PAGE_LIMIT
            val list = postList.subList(postListLoaded.size, last)

            postListLoaded.addAll(list)

            isLastPage = postListLoaded.size == postList.size

            postListData.value = list
        }

        println("Log Time(In Millis): ${System.currentTimeMillis() - time} ")

    }

}