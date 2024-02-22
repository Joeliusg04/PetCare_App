package com.example.petcare.view

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.model.Post
import com.example.petcare.R
import com.example.petcare.databinding.FragmentMenuBinding
import com.example.petcare.model.AdapterPost
import com.example.petcare.viewmodel.MyViewModel
import com.example.petcare.viewmodel.OnClickListener
import com.example.petcare.viewmodel.PostsAdapter
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

@SuppressLint("StaticFieldLeak")
class MenuFragment : Fragment(), OnClickListener {
    lateinit var binding: FragmentMenuBinding
    private lateinit var myPreferences: SharedPreferences
    private val viewModel: MyViewModel by activityViewModels()
    private lateinit var userPostsAdapter: PostsAdapter
    private lateinit var linearLayoutManager: RecyclerView.LayoutManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
       binding= FragmentMenuBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.fetchData()

        viewModel.data.observe(viewLifecycleOwner){
            setUpRecyclerView1(it as MutableList<Post>)
        }

        viewModel.data.observe(viewLifecycleOwner){
            setUpRecyclerView2(it as MutableList<Post>)
        }

        binding.logout.setOnClickListener {
            cerrarSesionYLimpiarPreferencias()
        }

        binding.deleteAccount.setOnClickListener {
            eliminarCuenta()
        }

        showAndHide()

    }

    private fun cerrarSesionYLimpiarPreferencias() {
        // Cerrar sesión en Firebase
        FirebaseAuth.getInstance().signOut()

        // Limpiar preferencias compartidas
        myPreferences = requireContext().getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE)

        val editor: SharedPreferences.Editor = myPreferences.edit()
        editor.clear()
        editor.apply()

        showToast("Sesión cerrada exitosamente")
        findNavController().navigate(R.id.action_menuFragment_to_loginFragment)
    }


    private fun eliminarCuenta() {
        val user = FirebaseAuth.getInstance().currentUser
        user?.delete()
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // La cuenta se eliminó exitosamente
                    cerrarSesionYLimpiarPreferencias()
                    showToast("Cuenta eliminada exitosamente")
                    findNavController().navigate(R.id.action_menuFragment_to_loginFragment)
                } else {
                    // Ocurrió un error al intentar eliminar la cuenta
                    showToast("Error al intentar eliminar la cuenta")
                }
            }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    fun showAndHide(){
        binding.myservice.setOnClickListener{
            if (binding.recyclerViewService.visibility == View.GONE){
                binding.recyclerViewService.visibility=View.VISIBLE
                binding.recyclerViewServiceDone.visibility=View.GONE
                binding.text3.visibility=View.GONE

            }
            else binding.recyclerViewService.visibility=View.GONE
        }
        binding.servicedone.setOnClickListener{
            if (binding.recyclerViewServiceDone.visibility == View.GONE){
                binding.recyclerViewServiceDone.visibility=View.VISIBLE
                binding.recyclerViewService.visibility=View.GONE
                binding.text3.visibility=View.GONE
            }
            else binding.recyclerViewServiceDone.visibility=View.GONE
        }
        binding.complain.setOnClickListener{
            if (binding.text3.visibility == View.GONE){
                binding.text3.visibility=View.VISIBLE
                binding.recyclerViewServiceDone.visibility=View.GONE
                binding.recyclerViewService.visibility=View.GONE
            }
            else binding.text3.visibility=View.GONE
        }
    }

    fun setUpRecyclerView1(listOfPost: MutableList<Post>){
        userPostsAdapter = PostsAdapter(listOfPost , this)
        linearLayoutManager = LinearLayoutManager(context)
        binding.recyclerViewService.apply {
            setHasFixedSize(true)
            layoutManager = linearLayoutManager
            adapter = userPostsAdapter
        }

    }

    fun setUpRecyclerView2(listOfPost: MutableList<Post>){
        userPostsAdapter = PostsAdapter(listOfPost , this)
        linearLayoutManager = LinearLayoutManager(context)
        binding.recyclerViewServiceDone.apply {
            setHasFixedSize(true)
            layoutManager = linearLayoutManager
            adapter = userPostsAdapter
        }

    }
/*
    fun setUpRecyclerView(listOfPost: MutableList<Post>){

        val myAdapter = listOfPost.let { AdapterPost(it, this) }
        binding.recyclerView.apply {
            adapter = myAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

 */

    override fun onResume() {
        super.onResume()
        val supportActionBar: ActionBar? = (requireActivity() as AppCompatActivity).supportActionBar
        if (supportActionBar != null) supportActionBar.hide()
    }

    override fun onClick(post: Post) {
        val viewModel= ViewModelProvider(requireActivity())[MyViewModel::class.java]
        viewModel.post.postValue(post)
    }


}