package com.example.petcare.view

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.model.Post
import com.example.model.User
import com.example.petcare.databinding.FragmentPostsBinding
import com.example.petcare.model.Adapter
import com.example.petcare.viewmodel.MyViewModel
import com.example.petcare.viewmodel.OnClickListener
import androidx.appcompat.widget.SearchView.OnQueryTextListener



class PostsFragment : Fragment(), OnClickListener {
    private val viewModel: MyViewModel by activityViewModels()
    private lateinit var userAdapter: Adapter
    private lateinit var linearLayoutManager: RecyclerView.LayoutManager
    lateinit var binding: FragmentPostsBinding
    //val email= arguments?.getString("email")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
       binding= FragmentPostsBinding.inflate(layoutInflater)
        val activity = requireActivity() as MainActivity
        activity.setBottomNavigationVisible(true)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.data.observe(viewLifecycleOwner){
            setUpRecyclerView(it)
        }
        binding.search.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    if (query != "") {
                        viewModel.name = query
                        viewModel.fetchPostByName(viewModel.name)
                        println(viewModel.post.value)
                    }
                    else {
                        viewModel.fetchData()
                    }
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    if (newText.length >= 2) {
                        viewModel.name = newText
                        viewModel.fetchPostByName(viewModel.name)
                    }
                    else {
                        viewModel.fetchData()
                    }
                }
                return false
            }
        })


        userAdapter = Adapter(getUsers(), this)
        linearLayoutManager = LinearLayoutManager(context)

        binding.recyclerView.apply {
            setHasFixedSize(true) //Optimitza el rendiment de l’app
            layoutManager = linearLayoutManager
            adapter = userAdapter
        }


    }

    fun setUpRecyclerView(listOfUsers: List<Post>){
        userAdapter = Adapter(listOfUsers , this)
        linearLayoutManager = LinearLayoutManager(context)
        binding.recyclerView.apply {
            setHasFixedSize(true) //Optimitza el rendiment de l’app
            layoutManager = linearLayoutManager
            adapter = userAdapter
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun getUsers(): MutableList<Post>{
        val users = mutableListOf<Post>()
        users.add(Post(1, User(1,"","","","",""), User(1,"","","","",""),
            listOf<User>(),"1 perro salchicha","","Perro salchicha: pequeño, largo, simpático. Pelaje suave, orejas caídas. Leal, juguetón. ¡Ama los abrazos!","Paseo","16:00-19:00","24/11/2023","5€","Barcelona",))
        users.add(Post(1, User(1,"","","","",""), User(1,"","","","",""),
            listOf<User>(),"2 Bulldogs","","Dos bulldogs, valientes y robustos, se destacaron en 1530. Su tenacidad y lealtad ganaron el corazón de todos, dejando un legado eterno.","Paseo","07:00-10:00","26/11/2023","5€","Cádiz",))
        users.add(Post(1, User(1,"","","","",""), User(1,"","","","",""),
            listOf<User>(),"1 Gato persa","","El gato persa es conocido por su pelaje largo y denso, nariz chata y grandes ojos redondos. Tranquilo y cariñoso, requiere cuidados constantes de su pelaje. Ideal como compañero de interior.","Veterinario","20:00-22:00","10/11/2023","5€","Madrid",))
        users.add(Post(1, User(1,"","","","",""), User(1,"","","","",""),
            listOf<User>(),"2 Jaskys","","traviesos y leales, siempre listos para jugar. Alegran mi vida con travesuras y lamidas. Amor en patas peludas","Cuidado","08:00-09:00","1/12/2023","5€","Lugo",))
        users.add(Post(1, User(1,"","","","",""), User(1,"","","","",""),
            listOf<User>(),"3 Gatos sphynx","","Mis tres Sphynx: Zafiro, traviesa y juguetona; Ónix, elegante y curiosa; y Esmeralda, la mimosa del trío. Peludas emociones en mi hogar sin pelo.","Veterinario","14:00-16:00","12/11/2023","5€","Tarragona",))
        users.add(Post(1, User(1,"","","","",""), User(1,"","","","",""),
            listOf<User>(),"1 Braco de weimar","","Leal Braco Weimar, mi sombra fiel, ojos que hablan, compañero incansable. Amor en pelaje plateado.","Peluqueria","18:00-17:00","24/12/2023","5€","Valencia",))
        users.add(Post(1, User(1,"","","","",""), User(1,"","","","",""),
            listOf<User>(),"1 Conejo","","Mi peludo amigo, orejas largas y ojos curiosos. Saltarín, suave y lleno de amor. Compañero leal de nariz rosada","Cuidado","17:00-20:00","10/01/2024","5€","Málaga",))

        return users
    }

    override fun onClick(post: Post) {
        val name= post.tittle
        val desc= post.description
        val locate= post.location
        val service= post.serviceType
        val date= post.postDate
        val time= post.serviceTime
        val reward= post.reward
        val action = PostsFragmentDirections.actionPostsFragmentToDetailsPostFragment(name,desc,locate,service,date,reward,time)
        findNavController().navigate(action)
    }

    override fun onResume() {
        super.onResume()
        val supportActionBar: ActionBar? = (requireActivity() as AppCompatActivity).supportActionBar
        if (supportActionBar != null) supportActionBar.hide()
    }



}