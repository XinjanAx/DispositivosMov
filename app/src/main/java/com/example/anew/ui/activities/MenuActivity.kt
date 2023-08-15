package com.example.anew.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.anew.R
import com.example.anew.databinding.ActivityMenuBinding

class MenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}