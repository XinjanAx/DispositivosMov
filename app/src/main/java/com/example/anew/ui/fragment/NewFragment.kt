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
import androidx.recyclerview.widget.RecyclerView
import com.example.anew.*
import com.example.anew.data.marvel.MarvelChars
import com.example.anew.databinding.FragmentNewBinding
import com.example.anew.logic.jikanlogic.JikanAnimeLogic
import com.example.anew.logic.list.ListItem
import com.example.anew.logic.marvelLogic.MarvelLogic
import com.example.anew.ui.activities.DetailsMarvelItem
import com.example.anew.ui.adapter.MarvelAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewFragment : Fragment() {

    private lateinit var binding : FragmentNewBinding
    private lateinit var lmanager : LinearLayoutManager
    private var rvAdapter : MarvelAdapter = MarvelAdapter {
        sendMarvelItem(it)
    }


    override fun onStart(){
        super.onStart()
        val names = arrayListOf<String>(
            "Andres", "Fabio", "Mario","Bob","Penelope"
        )
        val adapter = ArrayAdapter<String>(requireActivity() ,
            R.layout.simple_layout,
            names)

        binding.spinner .adapter = adapter
        chargeDaraRv("cap")

        binding.rvSwipe.setOnRefreshListener {
            chargeDaraRv("cap")
            binding.rvSwipe.isRefreshing=false
        }
        binding.rvMarvelChars.addOnScrollListener(
            object :RecyclerView.OnScrollListener(){
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    if (dy > 0){

                        //cuantos elems han pasado
                        val v = lmanager.childCount
                        //mi posicion actual
                        val p = lmanager.findFirstVisibleItemPosition()
                        //cuantos elems tengo en total
                        val t = lmanager.itemCount

                        if ((v+p)>=t){
                            lifecycleScope.launch((Dispatchers.IO)){
                                val newItem = JikanAnimeLogic().getAllAnimes()
                                /*val newItem = MarvelLogic().getAllMarvel(
                                    name = "spider",
                                    limit = 20
                                )*/
                                withContext(Dispatchers.Main){
                                    rvAdapter.updateListItems(newItem)
                                }
                            }
                        }

                    }


                }
            }
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

        binding = FragmentNewBinding.inflate(
            layoutInflater,
            container,
            false
        )
        lmanager = LinearLayoutManager(
            requireActivity(),
            LinearLayoutManager.VERTICAL,
            false
        )
        return binding.root
    }

    fun chargeDaraRv(search:String) {
        lifecycleScope.launch(Dispatchers.IO){
            rvAdapter.items = JikanAnimeLogic().getAllAnimes()
            //ListItems().returnMarvelChars()
            //JikanAnimeLogic().getAllAnimes()
            //MarvelLogic().getMarvelChars(name = search, limit = 20)

            withContext(Dispatchers.Main){
                with(binding.rvMarvelChars){

                    this.adapter = rvAdapter
                    this.layoutManager = lmanager
                }
            }
        }
    }
}
