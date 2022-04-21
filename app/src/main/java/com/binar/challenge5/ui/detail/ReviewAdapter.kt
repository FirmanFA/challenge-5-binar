package com.binar.challenge5.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.binar.challenge5.data.api.model.ReviewResponse.Result
import com.binar.challenge5.databinding.ReviewListLayoutBinding
import com.bumptech.glide.Glide

class ReviewAdapter(private val onClick:(Result)->Unit)
    : ListAdapter<Result, ReviewAdapter.ViewHolder>(ResultComparator()) {


    class ViewHolder(private val binding: ReviewListLayoutBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(currentResult: Result,
                 onClick: (Result) -> Unit){

            binding.apply {
                tvReview.text = currentResult.content
                ivAuthor.text = currentResult.author
                Glide.with(ivAvatar)
                    .load(currentResult.authorDetails.avatarPath?.drop(1)).circleCrop()
                    .into(ivAvatar)
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
        val binding = ReviewListLayoutBinding.inflate(
            LayoutInflater.from(parent.context),parent,false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), onClick)
    }
}

