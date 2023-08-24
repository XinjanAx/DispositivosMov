package com.example.anew.ui.activities

import android.Manifest
import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.speech.RecognizerIntent
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import com.example.anew.R
import com.example.anew.databinding.ActivityMainBinding
import com.example.anew.ui.validator.LoginValidator
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsStatusCodes
import com.google.android.gms.location.Priority
import com.google.android.gms.location.SettingsClient
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.integrity.internal.l
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale
import java.util.UUID

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