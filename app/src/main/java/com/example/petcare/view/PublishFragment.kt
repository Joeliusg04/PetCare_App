package com.example.petcare.view
import android.annotation.SuppressLint
import android.app.Activity
import androidx.fragment.app.Fragment
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.model.Post
import com.example.petcare.R
import com.example.petcare.databinding.FragmentPublishBinding
import com.example.petcare.viewmodel.MyViewModel
import java.io.File
import java.util.Calendar
import java.net.ConnectException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.util.concurrent.TimeoutException

class PublishFragment : Fragment() {

    private lateinit var binding: FragmentPublishBinding
    private lateinit var postViewModel: MyViewModel
    private var uri = ""
    private lateinit var myPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        postViewModel = ViewModelProvider(requireActivity())[MyViewModel::class.java]
        binding = FragmentPublishBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        myPreferences = requireActivity().getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE)


        if (uri != "") {
            Glide.with(requireContext())
                .load(uri.toUri())
                .centerCrop()
                .transform(RoundedCorners(50))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.image)
        }

        binding.publish.setOnClickListener{
            val a = binding.horaInicio.selectedItem.toString()
            val b = binding.horaFinal.selectedItem.toString()
            val tiempoDeServivio = "$a - $b"
            //rememberInfo(binding.incidentNameEt.text.toString(), binding.incidentDescriptionEt.text.toString(), uri, false, lat, lon)
            val post = Post(0,
            0,
            0,
            "",
            binding.animalYRaza.editText?.text.toString(),
            "",
            binding.desctipcion.editText?.text.toString(),
            binding.tipoDeServicio.selectedItem.toString(),
            tiempoDeServivio,
            binding.dia.text.toString(),
            binding.reward.text.toString(),
            binding.localidad.selectedItem.toString()
        )
            postViewModel.addPost(post, getFileFromUri(requireContext(), uri.toUri())!!)
        }

        postViewModel.isPostAddedSuccessfully.observe(viewLifecycleOwner) {
            if (it == true) {
                findNavController().navigate(R.id.action_publishFragment_to_postsFragment)
            }
            else if (it == false) {
                findNavController().navigate(R.id.action_publishFragment_to_postsFragment)
            }
        }

        binding.dia.setOnClickListener {
            showDatePickerDialog()
        }
        binding.image.setOnClickListener {
            openGalleryForImages()
        }

    }

    /*


    private fun rememberInfo(name: String, desc: String, uri: String, aFoto: Boolean, lat:String, lon:String) {
        if(aFoto){
            myPreferences.edit {
                putString("name", name)
                putString("desc", desc)
                putString("uri", uri)
                putString("lat", lat)
                putString("lon", lon)
                apply()
            }
        }else{
            myPreferences.edit {
                putString("name", "")
                putString("desc", "")
                putString("uri", "")
                putString("lat", "")
                putString("lon", "")
                apply()
            }
        }

    }
    private fun setupForm() {
        val name = myPreferences.getString("name", "")
        val desc = myPreferences.getString("desc", "")
        uri = myPreferences.getString("uri", "").toString()
        lat = myPreferences.getString("uri", "").toString()
        lon = myPreferences.getString("uri", "").toString()
        if (name != null) {
            if(name.isNotEmpty()){
                binding.incidentNameEt.setText(name)
                binding.incidentDescriptionEt.setText(desc)
            }
        }
    }

     */

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(requireContext(), { _, year, month, day ->
            // +1 because January is zero
            val selectedDate = "$day / ${month + 1} / $year"
            binding.dia.setText(selectedDate)
        }, year, month, day)

        datePickerDialog.show()
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
            val image = binding.image

            if(data?.getClipData() != null){
                var count = data.clipData?.itemCount
                for(i in 0..count!! - 1){
                    var imageUri: Uri = data.clipData?.getItemAt(i)!!.uri
                    image.setImageURI(imageUri)
                    uri = imageUri.toString()
                }
            }
            else if(data?.getData() != null){
                var imageUri: Uri = data.data!!
                image.setImageURI(imageUri)
                uri = imageUri.toString()
            }
        }
    }

    private fun getFileFromUri(context: Context, uri: Uri): File? {
        val inputStream = context.contentResolver.openInputStream(uri) ?: return null
        val fileName = uri.lastPathSegment ?: "file"
        val directory = context.getExternalFilesDir(null)
        val file = File(directory, fileName)
        inputStream.use { input ->
            file.outputStream().use { output ->
                input.copyTo(output)
            }
        }
        return if (file.exists()) file else null
    }



    override fun onResume() {
        super.onResume()
        val supportActionBar: ActionBar? = (requireActivity() as AppCompatActivity).supportActionBar
        if (supportActionBar != null) supportActionBar.hide()
    }



}




