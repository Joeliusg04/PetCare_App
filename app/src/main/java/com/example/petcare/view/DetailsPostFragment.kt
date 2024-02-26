package com.example.petcare.view

import android.os.Bundle
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
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.petcare.R
import com.example.petcare.databinding.FragmentDetailsPostBinding
import com.example.petcare.viewmodel.MyViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class DetailsPostFragment : Fragment() {
    lateinit var binding: FragmentDetailsPostBinding
    val repository= ApiRepository("admin", "password")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        binding= FragmentDetailsPostBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = ViewModelProvider(requireActivity())[MyViewModel::class.java]

        val post = viewModel.post.value!!

        Glide.with(requireContext())
            .load(post.postPhoto)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(binding.image)

        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getImage("posts/imagenespost/${post.postPhoto}")
            withContext(Dispatchers.Main){
                if(response.isSuccessful && response.body() != null){
                    val foto = response.body()!!.bytes()
                    context?.let {
                        Glide.with(it)
                            .load(foto)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .centerCrop()
                            .circleCrop()
                            .into(binding.image)
                    }
                }
            }
        }

        binding.tittle.text= post.tittle
        binding.desctipcion.text= post.description
        binding.localidad.text= post.location
        binding.servicio.text= post.serviceType
        val date= arguments?.getString("date")
        val time= arguments?.getString("time")
        val reward=arguments?.getString("reward")
        binding.fecha.text= "$date - $time h"
        binding.solicitar.text= "Solicitar servicio: $reward€"

        binding.chat.setOnClickListener {
            findNavController().navigate(R.id.action_detailsPostFragment_to_messageFragment)
        }
        binding.perfil.setOnClickListener {
            findNavController().navigate(R.id.action_detailsPostFragment_to_profileFakeFragment)
        }

        binding.solicitar.setOnClickListener {
            Toast.makeText(context, "Solicitud enviada", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_detailsPostFragment_to_postsFragment)
        }

    }

    override fun onResume() {
        super.onResume()
        val supportActionBar: ActionBar? = (requireActivity() as AppCompatActivity).supportActionBar
        if (supportActionBar != null) supportActionBar.hide()
    }

    override fun onStop() {
        super.onStop()
        val supportActionBar: ActionBar? = (requireActivity() as AppCompatActivity).supportActionBar
        if (supportActionBar != null) supportActionBar.show()
    }


}