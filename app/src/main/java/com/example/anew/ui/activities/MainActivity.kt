package com.example.anew.ui.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.anew.databinding.ActivityMainBinding
import com.example.anew.databinding.ActivitySecondBinding
import com.example.anew.logic.validator.LoginValidator
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var bindindg: ActivityMainBinding;
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindindg=ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindindg.root)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
    override fun onStart() {
        super.onStart()

        /*binding.button.setOnClickListener{
            binding.textView.text="SAPA"
           //A donde me lleva el intent
            startActivity(intent)
        }*/

        initClass()
    }
    fun initClass(){
        Log.d("uce", "Entrando a start")

        bindindg.botonIngreso.setOnClickListener{
            val check = LoginValidator().checklogin(
                bindindg.inputEmail.text.toString(),
                bindindg.inputPassword.text.toString()
            )

            if(check) {
                var intent= Intent(
                    this,
                    SecondActivity ::class.java)
                intent.putExtra(
                    "var1",
                    bindindg.inputEmail.text.toString()
                )
                startActivity(intent)
            }else{
                Snackbar.make(
                    bindindg.buttonTwitter ,
                    "usuario o contrasenia invalidos",
                    Snackbar.LENGTH_LONG).show()
            }
        }
    }
}