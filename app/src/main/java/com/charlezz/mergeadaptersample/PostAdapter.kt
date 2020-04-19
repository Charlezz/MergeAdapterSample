package com.charlezz.mergeadaptersample

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.charlezz.mergeadaptersample.data.PostItem
import com.charlezz.mergeadaptersample.databinding.ViewPostBinding

class PostAdapter : PagedListAdapter<PostItem, PostAdapter.ViewHolder>(diffCallback) {

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<PostItem>() {
            override fun areItemsTheSame(oldItem: PostItem, newItem: PostItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: PostItem, newItem: PostItem): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long {
        return getItem(position)?.id ?: kotlin.run { hashCode().toLong() }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ViewPostBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.title.text = getItem(position)?.title
        holder.binding.postId.text = "Post id = ${getItem(position)?.id}"
        holder.binding.body.text = getItem(position)?.body
    }

    inner class ViewHolder(val binding: ViewPostBinding) : RecyclerView.ViewHolder(binding.root)
}