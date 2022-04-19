package com.binar.challenge5.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.binar.challenge5.data.api.model.Result
import com.binar.challenge5.databinding.DiscoverListLayoutBinding
import com.bumptech.glide.Glide

class DiscoverAdapter(private val onClick:(Result)->Unit)
    : ListAdapter<Result, DiscoverAdapter.ViewHolder>(ResultComparator()) {


    class ViewHolder(private val binding: DiscoverListLayoutBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(currentResult: Result,
                 onClick: (Result) -> Unit){

            binding.apply {
                Glide.with(binding.ivPosterDiscover)
                    .load("https://image.tmdb.org/t/p/w500"+currentResult.posterPath)
                    .into(ivPosterDiscover)
                root.setOnClickListener {
                    onClick(currentResult)
                }
            }

        }

    }

    class ResultComparator : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DiscoverListLayoutBinding.inflate(
            LayoutInflater.from(parent.context),parent,false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), onClick)
    }

}