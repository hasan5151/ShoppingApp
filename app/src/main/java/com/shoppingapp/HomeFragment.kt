package com.shoppingapp

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.shoppingapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    private val viewModel by activityViewModels<MainViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        database = Firebase.database

        val university = UniversityModel(1, "https://firebasestorage.googleapis.com/v0/b/ixtisas-5c890.appspot.com/o/bdu.jpg?alt=media&token=d6a7b621-8282-4ece-b6f4-a634f871", "Bakı Dovlət Universiteti")
        database.getReference("university").child(university.id.toString()).setValue(university)

        val faculty = FacultyModel(1, 1, "Komputer muhendisliyi", 650.0, 3000,true)
        database.getReference("faculty").child(faculty.id.toString()).setValue(faculty)


        auth = Firebase.auth
        if (auth.currentUser !== null){
            database.getReference(auth.currentUser?.uid!!).addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    viewModel.onSetname("${snapshot.value}")
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
        }

        binding.sinif1011.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToTenElevenFragment()
            Navigation.findNavController(it).navigate(action)
        }

        binding.sinif89.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToEightNineGradeFragment()
            Navigation.findNavController(it).navigate(action)
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater,container,false)

        // Inflate the layout for this fragment
        return binding.root


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    }



