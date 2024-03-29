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
    var items: List<MarvelChars>,
    // En Java es Void, en Kotlin es Unit
    private var fnClick: (MarvelChars) -> Unit,
    private var fnSave : (MarvelChars) -> Boolean
) :

    RecyclerView.Adapter<MarvelAdapter.MarvelViewHolder>() {
    class MarvelViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding: MarvelCharactersBinding = MarvelCharactersBinding.bind(view)

        fun render(
            item: MarvelChars,
            fnClick: (MarvelChars) -> Unit,
            fnSave : (MarvelChars) -> Boolean
        ) {
            // En esta funcion se realizan los cambios
            println("Recibiendo a ${item.nombre}")
            //binding.imageView2.bringToFront()
            binding.marvelTitle.text = item.nombre
            binding.marvelDesc.text = item.comic
            Picasso.get().load(item.imagen).into(binding.imageView2)

            // itemView: el elemento en cualquier parte del elemento
            itemView.setOnClickListener {
                /*Snackbar.make(
                    binding.imageView2,
                    item.nombre,
                    Snackbar.LENGTH_SHORT
                ).setBackgroundTint(Color.rgb(247, 147, 76)).show()
                 */
                fnClick(item)
            }

            binding.btnSave.setOnClickListener {
                fnSave(item)
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
                R.layout.marvel_characters,
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MarvelAdapter.MarvelViewHolder, position: Int) {
        holder.render(items[position], fnClick, fnSave)
    }

    override fun getItemCount(): Int = items.size

    fun updateListItem(newItems: List<MarvelChars>){
        this.items = this.items.plus(newItems)
        notifyDataSetChanged()
    }

    fun replaceListAdapter(newItems: List<MarvelChars>){
        this.items = newItems
        notifyDataSetChanged()
    }

}