package com.example.anew.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.anew.databinding.FragmentThridBinding


class ThridFragment : Fragment() {

    private lateinit var binding : FragmentThridBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentThridBinding.inflate(
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