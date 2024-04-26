package com.sample.pagination.ui.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sample.pagination.data.model.PostData
import com.sample.pagination.databinding.ListItemPostBinding

class PostListAdapter : RecyclerView.Adapter<PostListAdapter.MyViewHolder>() {

    var onItemClick : ((PostData) -> Unit)? = null

    private var listOfPosts: MutableList<PostData> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ListItemPostBinding.inflate(
                android.view.LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return listOfPosts.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindUI(position)
        holder.itemView.setOnClickListener(){
            onItemClick?.invoke(listOfPosts[position])
        }
    }

    fun setListItems(listOfPosts :  List<PostData>) {
        val preSize = this.listOfPosts.size
        this.listOfPosts.addAll(listOfPosts)
        notifyItemRangeChanged(preSize, listOfPosts.size-1)
    }

    inner class MyViewHolder(val binding: ListItemPostBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindUI(position: Int) {
            binding.apply {
                listOfPosts[position].let { postData->
                    id = postData.id
                    title = postData.title
                }
            }
        }
    }

}