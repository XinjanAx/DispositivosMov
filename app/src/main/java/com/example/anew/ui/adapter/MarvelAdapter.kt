package com.example.anew.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.anew.R
import com.example.anew.data.marvel.MarvelChars
import com.example.anew.databinding.MarvelCharactersBinding

class MarvelAdapter (private val items : List<MarvelChars>): RecyclerView.Adapter<MarvelAdapter.MarvelViewHolder>(){


    class MarvelViewHolder (view: View):RecyclerView.ViewHolder(view){

        private val binding : MarvelCharactersBinding = MarvelCharactersBinding.bind(view)

        fun render(item : MarvelChars){
            binding.txtName.text=item.name
            binding.txtComic.text=item.comic

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarvelViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MarvelViewHolder(inflater.inflate(R.layout.marvel_characters,parent,false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MarvelViewHolder, position: Int) {

    }
}