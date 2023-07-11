package com.example.anew.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.anew.R
import com.example.anew.logic.data.MarvelChars
import com.example.anew.databinding.MarvelCharactersBinding
import com.squareup.picasso.Picasso

class MarvelAdapter(
    //Unit es igual al void en java, no devuelve nada
    private var fnClick: (MarvelChars) -> Unit):

    RecyclerView.Adapter<MarvelAdapter.MarvelViewHolder>() {
    var items: List<MarvelChars> = listOf()
    class MarvelViewHolder(view: View): RecyclerView.ViewHolder(view) {

        private val binding: MarvelCharactersBinding =
            MarvelCharactersBinding.bind(view)

        fun render(
            item: MarvelChars,
            fnClick: (MarvelChars) -> Unit){
            binding.imgMarvel.bringToFront()
            binding.textView.text = item.name
            binding.txtComic.text = item.comic
            Picasso.get().load(item.img).into(binding.imgMarvel)

            itemView.setOnClickListener{
                fnClick(item)
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

    fun updateListAdapter(newItems:List<MarvelChars>){
        items = items.plus(newItems)
        notifyDataSetChanged()
    }
    fun replaceListAdapter(newItems: List<MarvelChars>){
        //plus agrega a la lista los nuevos elems
        this.items = newItems
        notifyDataSetChanged()
    }
}