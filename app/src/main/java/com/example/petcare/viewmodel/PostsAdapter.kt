package com.example.petcare.viewmodel

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.model.Post
import com.example.petcare.R
import com.example.petcare.databinding.PostItemBinding

class PostsAdapter (private val posts: List<Post>): RecyclerView.Adapter<PostsAdapter.ViewHolder>() {
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val binding = PostItemBinding.bind(view)
    }
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.post_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return posts.size
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = posts[position]
        with(holder){
            binding.titleItem.text = post.tittle
            binding.serviceItem.text = post.serviceType
            binding.dateItem.text = post.postDate
            binding.timeItem.text = post.serviceTime
            binding.locateItem.text = post.location
            Glide.with(context)
                .load(post.postPhoto)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(binding.imageItem)
        }
    }


}
