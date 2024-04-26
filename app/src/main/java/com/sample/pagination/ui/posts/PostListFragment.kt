package com.sample.pagination.ui.posts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.sample.pagination.databinding.FragmentPostListBinding
import com.sample.pagination.databinding.PostDetailsBottomsheetBinding
import com.sample.pagination.ui.adapters.PostListAdapter
import com.sample.pagination.ui.utils.gone
import com.sample.pagination.ui.utils.visible
import com.sample.pagination.ui.viewmodels.GetPostsViewModel
import com.sample.pagination.utils.Status
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PostListFragment : Fragment() {


    private lateinit var postListAdapter: PostListAdapter

    private var _binding: FragmentPostListBinding? = null

    private val postViewModel: GetPostsViewModel by viewModels()

    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentPostListBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.progressBar.visible()
        postViewModel.getPosts()




        postViewModel.postLiveData.observe(viewLifecycleOwner) {

            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let { list ->
                        postViewModel.fetchItems()
                    }
                }

                Status.ERROR -> {
                }

                Status.LOADING -> {}
            }

        }

        postViewModel.postListLiveData.observe(viewLifecycleOwner) {

            if (it.isNullOrEmpty().not()) {
                binding.progressBar.gone()
                postListAdapter.setListItems(it)
            }

        }

        // Set up the adapter for the RecyclerView
        setAdapter()


        // Add an OnScrollListener to the RecyclerView
        binding.postsRecyclerView.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                // Check if the recyclerView cannot be scrolled vertically in the positive direction
                if (!recyclerView.canScrollVertically(1)) {
                    // If not, call the fetchItems() function to fetch more items
                    postViewModel.fetchItems()
                }
            }
        })

        return root
    }

    /**
     * Sets up the adapter for the postsRecyclerView.
     * Creates a new instance of Adapter.
     * Sets the RecyclerView's adapter to the newly created adapter.
     */
    private fun setAdapter() {
        // Create a new instance of PostListAdapter
        postListAdapter = PostListAdapter()
        // Assign the created adapter to the RecyclerView
        binding.postsRecyclerView.adapter = postListAdapter

        postListAdapter.onItemClick = {

            val dialog = BottomSheetDialog(requireContext())

            val binding: PostDetailsBottomsheetBinding = DataBindingUtil.inflate(
                LayoutInflater.from(dialog.context),
                com.sample.pagination.R.layout.post_details_bottomsheet,
                dialog.findViewById<View>(com.sample.pagination.R.id.detailsView) as ViewGroup?,
                false
            )

            binding.postData = it
            dialog.setCancelable(true)
            dialog.setCanceledOnTouchOutside(true)
            dialog.setContentView(binding.root)
            dialog.show()
         }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}