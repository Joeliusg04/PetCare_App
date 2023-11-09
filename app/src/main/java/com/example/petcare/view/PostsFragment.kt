package com.example.petcare.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.petcare.databinding.FragmentPostsBinding
import com.example.petcare.view.MainActivity
import com.google.android.material.navigation.NavigationView
import com.google.firebase.firestore.FirebaseFirestore

class PostsFragment : Fragment() {
    lateinit var binding: FragmentPostsBinding
    private val db = FirebaseFirestore.getInstance()
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

    /*fun userReady(){
        val email = arguments?.getString("email").toString()
        db.collection("users").document(email!!).get().addOnSuccessListener{
            val navigationView = requireActivity().findViewById(com.example.damaps.R.id.navigationView) as NavigationView
            val headerView = navigationView.getHeaderView(0)
            val navUsername = headerView.findViewById<View>(R.id.username) as TextView
            if (viewModel.userName == ""){
                viewModel.userName= it.get("name") as String
            }
            if(viewModel.usermail== ""){
                viewModel.usermail= email
            }
            val navUseremail = headerView.findViewById<View>(R.id.usermail) as TextView
            navUsername.setText(viewModel.userName)
            navUseremail.text = viewModel.usermail
        }
    }

     */


}