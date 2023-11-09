package com.example.petcare.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.petcare.databinding.FragmentPostsBinding
import com.example.petcare.view.MainActivity

class PostsFragment : Fragment() {
    lateinit var binding: FragmentPostsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
       binding= FragmentPostsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = requireActivity() as MainActivity
        activity.setBottomNavigationVisible(true)

    }


}