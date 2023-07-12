package com.example.anew.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.anew.*
import com.example.anew.logic.data.MarvelChars
import com.example.anew.databinding.FragmentNewBinding
import com.example.anew.logic.jikanlogic.JikanAnimeLogic
import com.example.anew.logic.marvelLogic.MarvelLogic
import com.example.anew.ui.activities.DetailsMarvelItem
import com.example.anew.ui.adapter.MarvelAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class   NewFragment : Fragment() {

    private lateinit var binding : FragmentNewBinding
    private lateinit var lmanager : LinearLayoutManager
    private lateinit var gManager : GridLayoutManager

    private var page = 1
    private var marvelCharsItems : MutableList<MarvelChars> = mutableListOf<MarvelChars>()

    private var rvAdapter : MarvelAdapter =
        MarvelAdapter { sendMarvelItem(it) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentNewBinding.inflate(
            layoutInflater, container, false
        )


        lmanager = LinearLayoutManager(
            requireActivity(),
            LinearLayoutManager.VERTICAL,
            false)
        return binding.root

    }
    override fun onStart(){
        super.onStart()
        val names = arrayListOf<String>(
            "A", "F", "M","B","P"
        )
        val adapter = ArrayAdapter<String>(requireActivity() ,
            android.R.layout.simple_spinner_item,
            names)

        binding.spinner .adapter = adapter
        chargeDataRv(5)

        binding.rvSwipe.setOnRefreshListener {
            chargeDataRv(5)
            binding.rvSwipe.isRefreshing=false
            lmanager.scrollToPositionWithOffset(5, 20)
        }


        binding.rvMarvelChars.addOnScrollListener(
            object: RecyclerView.OnScrollListener(){
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if(dx>0){
                        val v = lmanager.childCount//Cuantos han pasado
                        val p = lmanager.findFirstVisibleItemPosition()//Cual es mi posicion actual
                        val t = lmanager.itemCount//Cuantos tengo en total

                        if ((v + p) >= t) {
                            lifecycleScope.launch((Dispatchers.Main))
                            {
                                val x = with(Dispatchers.IO){
                                    MarvelLogic().getMarvelChars(name = "spider", page*3 )
                                    //JikanAnimeLogic().getAllAnimes()
                                }
                                rvAdapter.updateListAdapter((x))

                            }
                        }

                    }

                }
            })

        binding.txtFilter.addTextChangedListener{ filteredText ->
            val newItems = marvelCharsItems.filter {items ->
                items.name.lowercase().contains(filteredText.toString().lowercase())
            }

            rvAdapter.replaceListAdapter(newItems)
        }


    }


    fun sendMarvelItem(item: MarvelChars){
        val i = Intent(requireActivity(),DetailsMarvelItem::class.java)
        i.putExtra("name",item)
        startActivity(i)
    }


    // Serializacion: pasar de un objeto a un string para poder enviarlo por medio de la web, usa obj JSON
    // Parceables: Mucho mas eficiente que la serializacion pero su implementacion es compleja,
    // pero existen plugins que nos ayudan
    fun chargeDataRv(pos:Int) {
        lifecycleScope.launch(Dispatchers.Main) {
            //rvAdapter.items = JikanAnimeLogic().getAllAnimes()
            marvelCharsItems = withContext(Dispatchers.IO){
                return@withContext (MarvelLogic().getMarvelChars(
                    "spider", page*2
                ))
            }
            rvAdapter.items = marvelCharsItems

            binding.rvMarvelChars.apply {
                this.adapter = rvAdapter;
                this.layoutManager = lmanager;
            }
        }
    }

    fun chargeDataRVDB(pos: Int) {

        lifecycleScope.launch(Dispatchers.Main){

            marvelCharsItems = withContext(Dispatchers.IO) {

                var marvelCharsItems = MarvelLogic()
                    .getAllMarvelCharsDB()
                    .toMutableList()

                if(marvelCharsItems.isEmpty()) {
                    marvelCharsItems = (MarvelLogic().getAllMarvelChars(
                        0, page * 3))
                    MarvelLogic().insertMarvelCharsToDB(marvelCharsItems)
                }

                return@withContext marvelCharsItems
            }
        }

        rvAdapter.items = marvelCharsItems

        binding.rvMarvelChars.apply{
            this.adapter = rvAdapter
            this.layoutManager = lmanager
            gManager.scrollToPositionWithOffset(pos, 10)
        }
        page++
    }






}
