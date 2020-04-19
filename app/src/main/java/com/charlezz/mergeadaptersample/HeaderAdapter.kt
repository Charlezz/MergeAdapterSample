package com.charlezz.mergeadaptersample

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.charlezz.mergeadaptersample.data.HeaderItem
import com.charlezz.mergeadaptersample.databinding.ViewHeaderBinding

class HeaderAdapter() :
    RecyclerView.Adapter<HeaderAdapter.ViewHolder>() {

    private val items=ArrayList<HeaderItem>()

    init {
        setHasStableIds(true)
        items.add(HeaderItem("Header 0"))
        items.add(HeaderItem("Header 1"))
        items.add(HeaderItem("Header 2"))
    }


    override fun getItemId(position: Int): Long {
        return items[position].hashCode().toLong()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ViewHeaderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.title.text = items[position].title
    }

    inner class ViewHolder(val binding: ViewHeaderBinding) : RecyclerView.ViewHolder(binding.root)
}