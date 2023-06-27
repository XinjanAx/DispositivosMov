package com.example.anew.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.anew.*
import com.example.anew.data.marvel.MarvelChars
import com.example.anew.databinding.FragmentNewBinding
import com.example.anew.logic.jikanlogic.JikanAnimeLogic
import com.example.anew.logic.list.ListItem
import com.example.anew.ui.activities.DetailsMarvelItem
import com.example.anew.ui.adapter.MarvelAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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

        chargeDaraRv()

        binding.rvSwipe.setOnRefreshListener {
            chargeDaraRv()
            binding.rvSwipe.isRefreshing=false
        }

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

    fun chargeDaraRv() {

        lifecycleScope.launch(){
            val rvAdapter = MarvelAdapter(
                JikanAnimeLogic().getAllAnimes()
            ) {
                sendMarvelItem(it)
            }

            withContext(Dispatchers.Main){
                with(binding.rvMarvelChars){

                    this.adapter = rvAdapter
                    this.layoutManager = LinearLayoutManager(
                        requireActivity(),
                        LinearLayoutManager.VERTICAL,
                        false)
                }
            }
        }
    }


}
