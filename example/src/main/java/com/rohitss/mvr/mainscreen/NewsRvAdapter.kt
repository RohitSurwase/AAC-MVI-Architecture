package com.rohitss.mvr.mainscreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.rohitss.mvr.R
import com.rohitss.mvr.databinding.ItemViewBinding
import com.rohitss.mvr.repository.NewsItem

class NewsRvAdapter(private val listener: (View) -> Unit) :
    ListAdapter<NewsItem, NewsRvAdapter.MyViewHolder>(NewsItemItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemViewBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), listener
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemCount() = currentList.size

    inner class MyViewHolder(
        private val itemViewBinding: ItemViewBinding,
        listener: (View) -> Unit
    ) :
        RecyclerView.ViewHolder(itemViewBinding.root) {

        init {
            itemViewBinding.root.setOnClickListener(listener)
        }

        fun bind(newsItem: NewsItem) =
            with(itemViewBinding) {
                root.tag = newsItem
                tvTitle.text = newsItem.title
                tvDescription.text = newsItem.description
                ivThumbnail.load(newsItem.imageUrl) {
                    crossfade(true)
                    placeholder(R.mipmap.ic_launcher)
                }
            }
    }

    internal class NewsItemItemCallback : DiffUtil.ItemCallback<NewsItem>() {
        override fun areItemsTheSame(oldItem: NewsItem, newItem: NewsItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: NewsItem, newItem: NewsItem): Boolean {
            return oldItem.title == newItem.title
        }
    }
}

