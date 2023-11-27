package com.example.petcare.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.petcare.R
import com.example.petcare.databinding.FragmentMenuBinding

lateinit var binding: FragmentMenuBinding
class MenuFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
       binding= FragmentMenuBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showAndHide()

    }

    fun showAndHide(){
        binding.myservice.setOnClickListener{
            if (binding.text1.visibility == View.GONE){
                binding.text1.visibility=View.VISIBLE
            }
            else binding.text1.visibility=View.GONE
        }
        binding.servicedone.setOnClickListener{
            if (binding.text2.visibility == View.GONE){
                binding.text2.visibility=View.VISIBLE
            }
            else binding.text2.visibility=View.GONE
        }
        binding.complain.setOnClickListener{
            if (binding.text3.visibility == View.GONE){
                binding.text3.visibility=View.VISIBLE
            }
            else binding.text3.visibility=View.GONE
        }
    }

}