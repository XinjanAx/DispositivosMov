package com.example.anew.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.anew.logic.data.MarvelChars
import com.example.anew.databinding.ActivityDetailsMarvelItemBinding
import com.squareup.picasso.Picasso

class DetailsMarvelItem : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsMarvelItemBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsMarvelItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        //? significa que la var puede ser null
        /*var name: String? = ""
        intent.extras?.let {
            name = it.getString("name")

        }
        if(!name.isNullOrEmpty()){
            binding.txtName.text = name
        }*/

        //con <> le pido que me regrese a la forma anterior de MarvelChars
        val item = intent.getParcelableExtra<MarvelChars>("name")

        if(item != null) {
            binding.txtName.text = item.nombre
            Picasso.get().load(item.imagen).into(binding.imgMarvel)
            binding.txtComic.text = item.comic

        }
    }
}