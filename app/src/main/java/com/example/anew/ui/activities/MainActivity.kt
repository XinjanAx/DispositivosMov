package com.example.anew.ui.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.anew.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        auth = Firebase.auth
        setContentView(binding.root)
    }
    override fun onStart() {
        super.onStart()
        initClass()
    }

    private fun initClass() {
        binding.btnIngresar.setOnClickListener{
            signInWithEmailAndPassword(
                binding.txtName.text.toString(),
                binding.txtPassword.text.toString()
            )

        }
        binding.btnSignUp.setOnClickListener{
            val sUpIntent = Intent(this,SignUpActivity::class.java)
            startActivity(sUpIntent)
        }
        binding.btnfacebook.setOnClickListener{
            val linkfb = Uri.parse("https://www.facebook.com/bohemiabanda")
            val intent = Intent(Intent.ACTION_VIEW,linkfb)
            startActivity(intent)
        }
        binding.btntwitter.setOnClickListener{
            val linkT = Uri.parse("https://twitter.com/")
            val intent = Intent(Intent.ACTION_VIEW,linkT)
            startActivity(intent)
        }


    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun signInWithEmailAndPassword(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                Log.d(email,password)
                if (task.isSuccessful) {
                    Log.d("UCE", "Login Successful")
                    val menuIntent = Intent(this,MenuActivity::class.java)
                    startActivity(menuIntent)
                } else {
                    Log.w("UCE", "Error:Email or Pass", task.exception)
                    Toast.makeText(baseContext,"Authentication failed.",
                        Toast.LENGTH_SHORT,).show()
                }
            }
    }

}