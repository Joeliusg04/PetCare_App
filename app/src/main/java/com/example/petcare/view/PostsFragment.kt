package com.example.petcare.view

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.model.Post
import com.example.model.User
import com.example.petcare.databinding.FragmentPostsBinding
import com.example.petcare.view.MainActivity
import com.example.petcare.viewmodel.OnClickListener
import com.example.petcare.viewmodel.PostsAdapter

class PostsFragment : Fragment(), OnClickListener {
    private lateinit var userAdapter: PostsAdapter
    private lateinit var linearLayoutManager: RecyclerView.LayoutManager
    lateinit var binding: FragmentPostsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
       binding= FragmentPostsBinding.inflate(layoutInflater)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = requireActivity() as MainActivity
        activity.setBottomNavigationVisible(true)

        userAdapter = PostsAdapter(getUsers(), this)
        linearLayoutManager = LinearLayoutManager(context)

        binding.recyclerView.apply {
            setHasFixedSize(true) //Optimitza el rendiment de l’app
            layoutManager = linearLayoutManager
            adapter = userAdapter
        }


    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun getUsers(): MutableList<Post>{
        val posts = mutableListOf<Post>()
        posts.add(Post(1, User(1,"","","","","","",""), User(1,"","","","","","",""),
            listOf(),"1 Pastor aleman","","","Paseo","16:00-19:00","24/11/2023","5€","Barcelona",))
        posts.add(Post(2, User(2,"","","","","","",""), User(1,"","","","","","",""),
            listOf(),"2 Bulldogs","","","Paseo","07:00-10:00","26/11/2023","5€","Cádiz",))
        posts.add(Post(3, User(3,"","","","","","",""), User(1,"","","","","","",""),
            listOf(),"1 Gato persa","","","Veterinario","20:00-22:00","10/11/2023","5€","Madrid",))
        posts.add(Post(4, User(4,"","","","","","",""), User(1,"","","","","","",""),
            listOf(),"2 Jaskys","","","Cuidado","08:00-09:00","1/12/2023","5€","Lugo",))
        posts.add(Post(5, User(5,"","","","","","",""), User(1,"","","","","","",""),
            listOf(),"3 Gatos sphynx","","","Veterinario","14:00-16:00","12/11/2023","5€","Tarragona",))
        posts.add(Post(6, User(6,"","","","","","",""), User(1,"","","","","","",""),
            listOf(),"1 Braco de weimar","","","Peluqueria","18:00-17:00","24/12/2023","5€","Valencia",))
        posts.add(Post(7, User(7,"","","","","","",""), User(1,"","","","","","",""),
            listOf(),"1 Conejo","","","Cuidado","17:00-20:00","10/01/2024","5€","Málaga",))

        return posts
    }

    override fun onClick(post: Post) {

    }


}