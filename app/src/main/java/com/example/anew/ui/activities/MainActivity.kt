package com.example.anew.ui.activities

import android.Manifest
import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.location.Location
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale
import java.util.UUID

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
    override fun onStart() {
        super.onStart()
        initClass()
    }

    private fun initClass() {

    }

    override fun onDestroy() {
        super.onDestroy()
    }


/*


    @SuppressLint("MissingInflated")
        onCreate
    // Para iniciar la funcion y evitar problemas con la inicializacion del Binding
    // Se ejecuta inmediatamente despues del onCreate
    override fun onStart() {
        super.onStart()
        initClass()
        /*val db=Dispositivos_moviles_proyecto_gc_es1.getDbIntance()
        db.marvelDao()*/
    }



    // Podemos crear nuestras funcionalidades en una funcion apartada e invocarla en el onCreate
    fun initClass() {
        binding.button2.setOnClickListener {

            val check = LoginValidator().checkLogin(
                binding.txtName.text.toString(),
                binding.txtPassword.text.toString()
            )
            if (check) {
                //No voy a modificar nada por eso solo necesito IO
                lifecycleScope.launch(Dispatchers.IO) {
                    saveDataStore(binding.txtName.text.toString())
                }
                var intent = Intent(
                    this,
                    ActivityWithBinding::class.java
                )

                intent.putExtra("var1", binding.txtName.text.toString())

                startActivity(intent)
            } else {
                Snackbar.make(
                    binding.button2,
                    "Usuario o contraseña incorrectos",
                    Snackbar.LENGTH_SHORT
                ).setBackgroundTint(Color.BLACK).show()
            }

        }
        binding.micro.setOnClickListener {
            //Abre una url con un boton, este intent tiene un punto de partida pero no de llegada
            //con geo: se puede mandar la latitud y longitud de una pos del mapa
            /* val intent = Intent(Intent.ACTION_VIEW,
                 //Uri.parse("geo:-0.2006288,-78.5049638")
                 Uri.parse("tel:0958615079")
             )*/
            val web = Intent(Intent.ACTION_WEB_SEARCH)
            //Los parametros para abrir una aplicacion especifica
            web.setClassName(
                "com.google.android.googlequicksearchbox",
                "com.google.android.googlequicksearchbox.SearchActivity"
            )
            web.putExtra(SearchManager.QUERY, "QUERY")
            startActivity(web)
        }
        val appResultLocal = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { resultActivity ->

            val sn = Snackbar.make(
                binding.textView9,
                "",
                Snackbar.LENGTH_LONG
            )

            var message = when (resultActivity.resultCode) {
                RESULT_OK -> {
                    sn.setBackgroundTint(resources.getColor(R.color.azul))
                    resultActivity.data?.getStringExtra("result").orEmpty()
                }

                RESULT_CANCELED -> {
                    sn.setBackgroundTint(resources.getColor(R.color.rojo_pasion))
                    resultActivity.data?.getStringExtra("result").orEmpty()
                }

                else -> {
                    "Resultado Erroneo"
                }
//
            }
            sn.setText(message)
            sn.show()

        }

        val speechToText = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
            val sn = Snackbar.make(
                binding.textView9,
                "",
                Snackbar.LENGTH_LONG
            )

            var message = ""
            when (activityResult.resultCode) {
                RESULT_OK -> {
                    val msg =
                        activityResult.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                            .toString()
                    if (msg.isNotEmpty()) {
                        val intent = Intent(
                            Intent.ACTION_WEB_SEARCH
                        )
                        intent.setClassName(
                            "com.google.android.googlequicksearchbox",
                            "com.google.android.googlequicksearchbox.SearchActivity"
                        )
                        intent.putExtra(SearchManager.QUERY, msg.toString())
                        startActivity(intent)
                    }
                }


                RESULT_CANCELED -> {
                    message = "Proceso Cancelado"
                    sn.setBackgroundTint(resources.getColor(R.color.rojo_pasion))
                }

                else -> {
                    message = "Resultado Erroneo"
                    sn.setBackgroundTint(resources.getColor(R.color.rojo_pasion))
                }
//
            }
            sn.setText(message)
            sn.show()

        }

        //Diferencia con la primera es que las 2 se van a comunicar y cuando lo hagan se va alanzar este contrato

        binding.btnResult.setOnClickListener {
            val resIntent = Intent(this, ResultActivity::class.java)
            appResultLocal.launch(resIntent)
        }


        binding.micro.setOnClickListener {
            val intentSpeech = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intentSpeech.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            intentSpeech.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            intentSpeech.putExtra(RecognizerIntent.EXTRA_PROMPT, "Di algo")
            speechToText.launch(intentSpeech)

        }
    }

    private suspend fun saveDataStore(stringData: String) {
        // it implicito cambiamos por prefs

        dataStore.edit { prefs ->
            prefs[stringPreferencesKey("usuario")] = stringData
            //universal unic identifier
            prefs[stringPreferencesKey("session")] = UUID.randomUUID().toString()
            prefs[stringPreferencesKey("email")] = "dismov@uce.edu.ec"
        }
    }

*/
}