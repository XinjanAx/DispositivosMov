package com.example.anew.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.anew.R
import com.example.anew.databinding.MarvelCharactersBinding
import com.example.anew.logic.data.MarvelChars
import com.squareup.picasso.Picasso

class MarvelAdapter2(
    var items: List<MarvelChars>,
) :

    RecyclerView.Adapter<MarvelAdapter2.MarvelViewHolder>() {

    class MarvelViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding: MarvelCharactersBinding = MarvelCharactersBinding.bind(view)

        fun render(item: MarvelChars) {
            binding.marvelTitle.text = item.nombre
            binding.marvelDesc.text = item.comic
            Picasso.get().load(item.imagen).into(binding.imageView2)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MarvelAdapter2.MarvelViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MarvelViewHolder(
            inflater.inflate(
                R.layout.marvel_characters_2,
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MarvelAdapter2.MarvelViewHolder, position: Int) {
        holder.render(items[position])
    }

    override fun getItemCount(): Int = items.size

}