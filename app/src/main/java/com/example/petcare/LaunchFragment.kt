package com.example.petcare

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.petcare.databinding.FragmentLaunchBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class LaunchFragment : Fragment() {
    lateinit var binding: FragmentLaunchBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        binding=FragmentLaunchBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val activity = requireActivity() as MainActivity
//        activity.setBottomNavigationVisible(false)

        CoroutineScope(Dispatchers.IO).launch {
            delay(2500L)
            withContext(Dispatchers.Main) {
                findNavController().navigate(R.id.action_launchFragment_to_loginFragment)
            }
        }
    }


}