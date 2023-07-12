package com.example.anew.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.anew.R
import com.example.anew.databinding.ActivityMainBinding
import com.example.anew.databinding.ActivitySecondBinding
import com.example.anew.ui.fragment.NewFragment
import com.example.anew.ui.fragment.SecondFragment
import com.example.anew.ui.fragment.ThirdFragment
import com.example.anew.ui.utilities.FragmentsManager

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        initClass()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    fun initClass(){
        /*Log.d("uce", "Entrando a start")  debug en la terminal*/


        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.inicio -> {
                    FragmentsManager().replaceFragment(supportFragmentManager,
                        binding.frmContainer.id, NewFragment())
                    true
                }
                R.id.favoritos -> {
                    FragmentsManager().replaceFragment(supportFragmentManager,
                        binding.frmContainer.id, SecondFragment())
                    true
                }
                R.id.apis -> {
                    FragmentsManager().replaceFragment(supportFragmentManager,
                        binding.frmContainer.id, ThirdFragment())
                    true
                }
                else -> false
            }
        }
    }
}