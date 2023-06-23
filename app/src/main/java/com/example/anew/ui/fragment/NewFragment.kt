package com.example.anew.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.anew.*
import com.example.anew.data.marvel.MarvelChars
import com.example.anew.databinding.FragmentNewBinding
import com.example.anew.logic.list.ListItem
import com.example.anew.ui.activities.DetailsMarvelItem
import com.example.anew.ui.adapter.MarvelAdapter

class NewFragment : Fragment() {

    private lateinit var binding : FragmentNewBinding

    override fun onStart(){
        super.onStart()
        val names = arrayListOf<String>(
            "Andres", "Fabio", "Mario","Bob","Penelope"
        )
        val adapter = ArrayAdapter<String>(requireActivity() ,
            R.layout.simple_layout,
            names)

        binding.spinner .adapter = adapter
        //binding.listwiew1.adapter = adapter

        val rvAdapter = MarvelAdapter(ListItem().returnMarvelChars())
        { sendMarvelItem(it) }
        val rvMarvel = binding.rvMarvelChars

        rvMarvel.adapter=rvAdapter
        rvMarvel.layoutManager = LinearLayoutManager(
            requireActivity(),
            LinearLayoutManager.VERTICAL,
            false
        )

    }


    fun sendMarvelItem(item: MarvelChars){
        val i = Intent(requireActivity(),DetailsMarvelItem::class.java)
        i.putExtra("name",item)
        startActivity(i)
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNewBinding.inflate(
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
