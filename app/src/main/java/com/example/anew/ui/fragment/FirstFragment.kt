package com.example.anew.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.anew.R
import com.example.anew.databinding.FragmentFristBinding

class FirstFragment : Fragment() {

    private lateinit var binding : FragmentFristBinding

    override fun onStart() {
        super.onStart()

        val opciones = arrayListOf<String>("Opcion 1", "Opcion 2", "Opcion 3", "Opcion 4", "Opcion 5")
        val adapter =
            ArrayAdapter<String>(requireActivity(), R.layout.simple_layout, opciones)

        binding.spinner.adapter = adapter
        binding.list.adapter = adapter
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFristBinding.inflate(
            layoutInflater,
            container,
            false
        )
        return binding.root
        //return  inflater.inflate(
        //    R.layout.fragment_frist, //cual es el layout q se va a inflar (pintar)
        //    container, // y cual es el huesped del parasito (contenedor)=
        //    false)
    }

}