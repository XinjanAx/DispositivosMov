package com.example.anew.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.anew.R
import com.example.anew.databinding.ActivityNotificacionBinding

class NotificacionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotificacionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityNotificacionBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}