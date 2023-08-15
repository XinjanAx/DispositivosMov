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
import android.speech.RecognizerIntent
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import com.example.anew.R
import com.example.anew.databinding.ActivityLogginBinding
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
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale
import java.util.UUID

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")


class LogginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLogginBinding

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var client: SettingsClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallBack: LocationCallback
    private lateinit var locationSettingsRequest: LocationSettingsRequest
    private var currentLocation: Location? = null

    private lateinit var auth: FirebaseAuth

    private val speechToText =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
            val sn = Snackbar.make(
                binding.root,
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

            }
            sn.setText(message)
            sn.show()

        }
/*
    @SuppressLint("MissingPermission")
    private val locationContract =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            when (isGranted) {
                true -> {

                    client.checkLocationSettings(locationSettingsRequest).apply {
                        addOnSuccessListener {
                            val task = fusedLocationProviderClient.lastLocation

                            task.addOnSuccessListener { location ->
                                fusedLocationProviderClient.requestLocationUpdates(
                                    locationRequest,
                                    locationCallBack,
                                    Looper.getMainLooper()
                                )
                            }
                        }

                        addOnFailureListener { ex ->
                            if (ex is ResolvableApiException) {
                                ex.startResolutionForResult(
                                    this@LogginActivity,
                                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED
                                )
                            }
//                            val alert = AlertDialog.Builder(this@EjercicioPracticoActivity).apply {
//                                setTitle("NOTIFICACION")
//                                setMessage("Por favor verifique que el GPS este activo")
//                                setPositiveButton("Verificar") { dialog, id ->
//                                    val i = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
//                                    startActivity(i)
//
//                                    dialog.dismiss()
//                                }
//                                setCancelable(false)
//                            }.show()
                        }
                    }

                }

                shouldShowRequestPermissionRationale(
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) -> {
                    Snackbar.make(
                        binding.textView3,
                        "Ayude con el permiso por fa",
                        Snackbar.LENGTH_LONG
                    ).show()
                }

                false -> {
                    Snackbar.make(
                        binding.textView3,
                        "Permiso denegado",
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
        }
*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth

        binding = ActivityLogginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        locationRequest = LocationRequest.Builder(
            //import
            Priority.PRIORITY_HIGH_ACCURACY,
            2000
        ).setMaxUpdates(3).build()
/*
        binding.btnIngresar.setOnClickListener{
            authWithFirebaseEmail(
            binding.txtName.toString(),
            binding.txtPassword.toString())
        }
*/
        locationCallBack = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)

                if (locationResult != null) {
                    locationResult.locations.forEach { location ->
                        currentLocation = location
                        Log.d(
                            "UCE",
                            "Ubicacion: ${location.latitude}, ${location.longitude}"
                        )
                    }
                }
            }
        }

    }

    private fun authWithFirebaseEmail(email:String, password:String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(Constants.TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    Toast.makeText(
                        baseContext,
                        "Authentication success.",
                        Toast.LENGTH_SHORT,
                    ).show()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(Constants.TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()

                }
            }
    }
    private fun signInWithEmailAndPassword(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("UCE", "signInWithEmail:success")
                    val user = auth.currentUser
                    //updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("UCE", "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                    // updateUI(null)
                }
            }
    }

    override fun onStart() {
        super.onStart()
        initClass()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onPause() {
        super.onPause()

        fusedLocationProviderClient.removeLocationUpdates(locationCallBack)
    }

    fun initClass() {
        binding.btnIngresar.setOnClickListener {
            authWithFirebaseEmail(
                binding.txtName.text.toString(),
                binding.txtPassword.text.toString()
            )
        }
        /*
        binding.btntwitter.setOnClickListener {

            val check = LoginValidator().checkLogin(
                binding.txtName.text.toString(),
                binding.txtPassword.text.toString()
            )

            if (check) {
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
                    binding.btntwitter,
                    "Usuario o contraseña incorrectos",
                    Snackbar.LENGTH_SHORT
                ).setBackgroundTint(Color.rgb(232, 87, 87)).show()
            }

        }
        */
        //Intent puedo mandar cualquier cosa
        binding.btnRegis.setOnClickListener {
            //Abre una url con un boton, este intent tiene un punto de partida pero no de llegada
            //con geo: se puede mandar la latitud y longitud de una pos del mapa
            /*  val intent=Intent(
                  Intent.ACTION_VIEW,
                  Uri.parse("tel:0997125604"))
              */

            //tel:0997125604
            //"geo:-0.1924028,-78.5092874"
            //https://www.google.com.ec

            //va hacer una busqueda en un paquete especifico
            //Los parametros para abrir una aplicacion especifica
            val intent = Intent(Intent.ACTION_WEB_SEARCH)
            intent.setClassName(
                "com.google.android.googlequicksearchbox",
                "com.google.android.googlequicksearchbox.SearchActivity"
            )
            //mandas extras enn los parametros en el intent
            intent.putExtra(SearchManager.QUERY, "UCE fing")
            startActivity(intent)
        }

        val appResultLocal =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { resultActivity ->

                val sn = Snackbar.make(binding.btnRegis, "", Snackbar.LENGTH_LONG)

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

        //Diferencia con la primera es que las 2 se van a comunicar y cuando lo hagan se va alanzar este contrato
        binding.btntwitter.setOnClickListener {
            val resIntent = Intent(this, ResultActivity::class.java)
            appResultLocal.launch(resIntent)
        }


        binding.txtOlvdPass.setOnClickListener {
           // locationContract.launch(Manifest.permission.ACCESS_FINE_LOCATION)


//            val intentSpeech = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
//            intentSpeech.putExtra(
//                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
//                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
//            )
//            intentSpeech.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
//            intentSpeech.putExtra(RecognizerIntent.EXTRA_PROMPT, "Di algo")
//            speechToText.launch(intentSpeech)
        }

        binding.btnRegis.setOnClickListener {
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
        dataStore.edit { prefs ->
            prefs[stringPreferencesKey("usuario")] = stringData
            prefs[stringPreferencesKey("email")] = "dimoviles@uce.edu.ec"
            prefs[stringPreferencesKey("session")] = UUID.randomUUID().toString()
        }
    }

}