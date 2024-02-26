package com.example.petcare.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.petcare.R
import com.example.petcare.databinding.FragmentMessageBinding

class MessageFragment : Fragment() {
    private lateinit var binding: FragmentMessageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMessageBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.sendMessage.setOnClickListener {
            sendMessage()
        }
    }

    private fun sendMessage() {
        val message = binding.msg.editText?.text.toString()
        val phoneNumber = binding.tel.editText?.text.toString()

        val uri = Uri.parse("https://api.whatsapp.com/send?phone=$phoneNumber&text=$message")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)

        // Optionally, navigate to another fragment after sending the message
        findNavController().navigate(R.id.action_messageFragment_to_detailsPostFragment)
    }
}


