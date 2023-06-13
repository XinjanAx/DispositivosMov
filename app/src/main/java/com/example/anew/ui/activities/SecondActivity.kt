package com.example.anew.ui.activities
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.anew.R
import com.example.anew.databinding.ActivityMainBinding
import com.example.anew.databinding.ActivitySecondBinding
import com.example.anew.ui.fragment.FirstFragment
import com.example.anew.ui.fragment.SecondFragment
import com.example.anew.ui.fragment.ThridFragment
import com.example.anew.ui.utilities.FragmentsManager
import com.google.android.material.snackbar.Snackbar

class SecondActivity : AppCompatActivity(){

    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()

        /*binding.button.setOnClickListener{
            binding.textView.text="SAPA"
           //A donde me lleva el intent
            startActivity(intent)
        }*/
        //FragmentsManager().replaceFragment(supportFragmentManager,binding.frmContainer.id,FirstFragment())

        initClass()
        binding.bottomNavigation.setOnItemSelectedListener{ item ->
            when(item.itemId) {
                R.id.nav_inicio -> {
                    /*//algun proceso
                    val transaction = supportFragmentManager.beginTransaction()
                    transaction.replace(binding.frmContainer.id,FirstFragment())
                    //transaction.add(binding.frmContainer.id,FirstFragment()) se añade y no se reemplaza
                    transaction.addToBackStack(null) //todo se vuelve un flujo es para usar  el back del cell
                                                        //cada click se añade a la pila de navegacion del la app
                    transaction.commit()
                    Snackbar.make(binding.txtGracias,"entramos al inicio",Snackbar.LENGTH_LONG).show()
                    // mostar en "${name}" el valor procesado*/
                    FragmentsManager().replaceFragment(supportFragmentManager,binding.frmContainer.id,
                        FirstFragment())


                    true
                }
                R.id.nav_fav -> {
                    // Respond to navigation item 2 click
                    FragmentsManager().replaceFragment(supportFragmentManager,binding.frmContainer.id,
                        SecondFragment())
                    true
                }
                R.id.nav_us -> {
                    // Respond to navigation item 2 click
                    FragmentsManager().replaceFragment(supportFragmentManager,binding.frmContainer.id,
                        ThridFragment())
                    true
                }
                else -> false
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
    fun initClass(){

        binding.buttonGracias.setOnClickListener{

            var intent= Intent(
                this,
                MainActivity::class.java)
            startActivity(intent)
            Snackbar.make(
                binding.txtGracias,"regresando",
                Snackbar.LENGTH_LONG).show()
        }
    }


}