package com.example.petcare.model

import android.annotation.SuppressLint
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
import com.example.petcare.view.ApiRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AdapterPost (private var posts: List<Post>, private val listener: com.example.petcare.viewmodel.OnClickListener):RecyclerView.Adapter<AdapterPost.ViewHolder>() {
    private lateinit var context: Context
    lateinit var repository: ApiRepository

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val binding = PostItemBinding.bind(view)
        fun setListener(post: Post){
            binding.root.setOnClickListener{
                listener.onClick(post)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        repository= ApiRepository("admin", "password")
        val view = LayoutInflater.from(context).inflate(R.layout.post_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = posts[position]

        with(holder){
            setListener(post)
            binding.titleItem.text = post.tittle
            binding.serviceItem.text = post.serviceType
            binding.dateItem.text = post.postDate
            binding.timeItem.text = post.serviceTime
            binding.locateItem.text = post.location
            CoroutineScope(Dispatchers.IO).launch {
                val response = repository.getImage("/posts/imagenes/${post.postPhoto}")
                withContext(Dispatchers.Main){
                    if(response.isSuccessful && response.body() != null){
                        val foto = response.body()!!.bytes()
                        Glide.with(context)
                            .load(foto)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .centerCrop()
//                            .circleCrop()
                            .into(binding.imageItem)
                    }
                }
            }
        }
    }

}