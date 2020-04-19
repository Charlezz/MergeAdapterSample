package com.charlezz.mergeadaptersample

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.charlezz.mergeadaptersample.data.FooterItem
import com.charlezz.mergeadaptersample.data.State
import com.charlezz.mergeadaptersample.databinding.ViewFooterBinding
import kotlinx.android.synthetic.main.view_footer.view.*

class FooterAdapter(private val retryClickListener : View.OnClickListener) : RecyclerView.Adapter<FooterAdapter.ViewHolder>() {

    private val item = FooterItem(State.NONE)

    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long {
        return item.hashCode().toLong()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ViewFooterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = 1

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.progressBar.visibility = if (item.state == State.LOADING) View.VISIBLE else View.GONE
        holder.binding.retry.visibility = if (item.state == State.ERROR) View.VISIBLE else View.GONE
        holder.binding.retry.setOnClickListener(retryClickListener)
    }

    fun setState(state: State?) {
        item.state = state?:State.NONE
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ViewFooterBinding) : RecyclerView.ViewHolder(binding.root)
}