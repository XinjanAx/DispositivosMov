package com.example.anew.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.anew.R
import com.example.anew.databinding.FragmentThridBinding


class ThirdFragment : Fragment() {

    private lateinit var binding: FragmentThridBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentThridBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onStart(){
        super.onStart()

        val names = arrayListOf<String>(
            "Carlos",
            "Xavier",
            "Andrés",
            "Pepe",
            "Mariano",
            "Rosa")

        val adapter = ArrayAdapter<String>(
            requireActivity(),
            R.layout.simple_layout,
            names
        )

        binding.spinner3.adapter = adapter
        binding.listView3.adapter = adapter
    }

}