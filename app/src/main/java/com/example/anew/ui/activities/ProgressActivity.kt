package com.example.anew.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.anew.R
import com.example.anew.databinding.ActivityProgressBinding
import com.example.anew.ui.viewmodel.ProgressViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ProgressActivity : AppCompatActivity() {

    private  lateinit var binding: ActivityProgressBinding
    private val progressViewModel by viewModels<ProgressViewModel> ()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityProgressBinding.inflate(layoutInflater)
        setContentView(binding.root)

        progressViewModel.progressState.observe(this,Observer{
            binding.progressBar.visibility = it
        })
        progressViewModel.items.observe(this, Observer {
            Toast.makeText(this,it[0].nombre,
            Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, NotificationActivity::class.java))
        })

        binding.btnProgress3.setOnClickListener{
            lifecycleScope.launch (Dispatchers.IO){
                progressViewModel.processBackgroud(3000)
            }
        }

        binding.btnProgress6.setOnClickListener{
            lifecycleScope.launch (Dispatchers.IO){
                progressViewModel.getMarvelChars(0,90)
            }
        }

    }
}