package com.example.petcare.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.petcare.R
import com.example.petcare.databinding.FragmentEditProfileBinding
import java.io.File

class EditProfileFragment : Fragment() {
    lateinit var binding: FragmentEditProfileBinding
    lateinit var uri: Uri
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        binding= FragmentEditProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val actName= arguments?.getString("name").toString()
        val actAge = arguments?.getString("age").toString()
        val actPhone = arguments?.getString("phone").toString()
        val actAboutMe= arguments?.getString("aboutMe").toString()

        binding.confirmButton.setOnClickListener {
            val newName= binding.name.editText?.text.toString()
            val newAge= binding.age.editText?.text.toString()
            val newPhone= binding.telefono.editText?.text.toString()
            val newAboutMe= binding.aboutMe.editText?.text.toString()

            var name=""
            var age=""
            var phone=""
            var aboutMe=""

            if(newName=="") name= actName
            else name=newName
            if(newAge=="") age= actAge
            else age=newAge
            if(newPhone=="") phone= actPhone
            else phone=newPhone
            if(newAboutMe=="") aboutMe= actAboutMe
            else aboutMe=newAboutMe

            val action = EditProfileFragmentDirections.actionEditProfileFragmentToProfileFragment(name,age,phone,aboutMe,true)
            findNavController().navigate(action)

        }
        binding.confirmEdit.setOnClickListener {
            openGalleryForImages()
        }
    }

    private fun openGalleryForImages() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "image/*"
        resultLauncher.launch(intent)
    }

    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if(result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val image = binding.userImage

            if(data?.getClipData() != null){
                var count = data.clipData?.itemCount
                for(i in 0..count!! - 1){
                    var imageUri: Uri = data.clipData?.getItemAt(i)!!.uri
                    image.setImageURI(imageUri)
                    uri = imageUri
                }
            }
            else if(data?.getData() != null){
                var imageUri: Uri = data.data!!
                image.setImageURI(imageUri)
                uri = imageUri
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val supportActionBar: ActionBar? = (requireActivity() as AppCompatActivity).supportActionBar
        if (supportActionBar != null) supportActionBar.hide()
    }



}