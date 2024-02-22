package com.example.petcare.view

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.model.Post
import com.example.model.User
import com.example.petcare.databinding.FragmentPostsBinding
import com.example.petcare.model.AdapterPost
import com.example.petcare.viewmodel.MyViewModel
import com.example.petcare.viewmodel.OnClickListener
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider

class PostsFragment : Fragment(), OnClickListener {
    private val viewModel: MyViewModel by activityViewModels()
    private lateinit var userAdapterPost: AdapterPost
    private lateinit var linearLayoutManager: RecyclerView.LayoutManager
    lateinit var binding: FragmentPostsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
       binding= FragmentPostsBinding.inflate(layoutInflater)
        val activity = requireActivity() as MainActivity
        activity.setBottomNavigationVisible(true)
        return binding.root
    }

    //@RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.fetchData()

        viewModel.data.observe(viewLifecycleOwner){
            setUpRecyclerView(it as MutableList<Post>)
        }

        binding.search.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    if (query != "") {
                        viewModel.name = query
                        viewModel.fetchPostByName(viewModel.name)
                        println(viewModel.post.value)
                    }
                    else {
                        viewModel.fetchData()
                    }
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    if (newText.length >= 2) {
                        viewModel.name = newText
                        viewModel.fetchPostByName(viewModel.name)
                    }
                    else {
                        viewModel.fetchData()
                    }
                }
                return false
            }
        })

    }

    fun setUpRecyclerView(listOfPost: MutableList<Post>){
        userAdapterPost = AdapterPost(listOfPost , this)
        linearLayoutManager = LinearLayoutManager(context)
        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = linearLayoutManager
            adapter = userAdapterPost
        }

    }

    override fun onClick(post: Post) {
        val viewModel= ViewModelProvider(requireActivity())[MyViewModel::class.java]
        viewModel.post.postValue(post)
        val action = PostsFragmentDirections.actionPostsFragmentToDetailsPostFragment(post.postId, post.postDate, post.reward, post.serviceTime)
        findNavController().navigate(action)
    }

    override fun onResume() {
        super.onResume()
        val supportActionBar: ActionBar? = (requireActivity() as AppCompatActivity).supportActionBar
        if (supportActionBar != null) supportActionBar.hide()
    }



}