package com.example.anew.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.anew.R
import com.example.anew.data.marvel.MarvelChars
import com.example.anew.databinding.MarvelCharactersBinding
import com.squareup.picasso.Picasso

class MarvelAdapter(private val items: List<MarvelChars>,
    //Unit es igual al void en java, no devuelve nada
                    private val fnClick: (MarvelChars) -> Unit):
    RecyclerView.Adapter<MarvelAdapter.MarvelViewHolder>() {

    class MarvelViewHolder(view: View): RecyclerView.ViewHolder(view) {

        private val binding: MarvelCharactersBinding =
            MarvelCharactersBinding.bind(view)

        fun render(item: MarvelChars,
                   fnClick: (MarvelChars) -> Unit){
            binding.imgMarvel.bringToFront()
            binding.txtName.text = item.name
            binding.txtComic.text = item.comic
            Picasso.get().load(item.img).into(binding.imgMarvel)

            itemView.setOnClickListener{
                fnClick(item)
                //Snackbar.make(binding.imgMarvel, item.name, Snackbar.LENGTH_SHORT).show()
            }
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MarvelAdapter.MarvelViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return MarvelViewHolder(
            inflater.inflate(
                R.layout.marvel_characters, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MarvelAdapter.MarvelViewHolder, position: Int) {
        holder.render(items[position], fnClick)
    }

    override fun getItemCount(): Int = items.size
}