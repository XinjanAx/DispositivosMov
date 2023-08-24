package com.example.anew.ui.activities

import android.app.SearchManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.speech.RecognizerIntent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.example.anew.R
import com.example.anew.databinding.ActivityMenuBinding
import com.google.android.material.snackbar.Snackbar
import java.util.Locale

class MenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBinding


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
    val appResultLocal =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { resultActivity ->

            val sn = Snackbar.make(binding.btnOK, "", Snackbar.LENGTH_LONG)

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
    override fun onStart() {
        super.onStart()
        initClass()
    }

    private fun initClass() {
        //boton marvel
        binding.btnMarvel.setOnClickListener{
            val intent = Intent(this,SecondActivity::class.java)
            startActivity(intent)
        }
        //boton camara
        binding.btnCamara.setOnClickListener{
            val intent = Intent(this,CameraActivity::class.java)
            startActivity(intent)
        }
        //recodatorio notificaciones
        binding.btnNotifi.setOnClickListener{
            val intent = Intent(this,NotificationActivity::class.java)
            startActivity(intent)
        }
        //boton todo bien
        binding.btnOK.setOnClickListener {
            val resIntent = Intent(this, ResultActivity::class.java)
            appResultLocal.launch(resIntent)
        }
        //boton biometric
        binding.btnBio.setOnClickListener{
            autenticateBiometric()
        }
        //boton buscar
        binding.btnMicro.setOnClickListener {
            val intentSpeech = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intentSpeech.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            intentSpeech.putExtra(RecognizerIntent.EXTRA_PROMPT, "¿Que buscamos?")
            speechToText.launch(intentSpeech)
        }
        //boton salir
        binding.btnBack.setOnClickListener{
            salir()
        }

    }

    private fun autenticateBiometric() {
        if(checkBiometric()) {
            val executor = ContextCompat.getMainExecutor(this)

            val biometricPrompt = BiometricPrompt.PromptInfo.Builder()
                .setTitle("Autenticación requerida")
                .setSubtitle("Ingrese su huella digital")
                .setAllowedAuthenticators(BIOMETRIC_STRONG)
                .setNegativeButtonText("Cancelar")
                .build()

            val biometricManager = BiometricPrompt(
                this,
                executor,
                object : BiometricPrompt.AuthenticationCallback() {
                    override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                        super.onAuthenticationError(errorCode, errString)
                    }

                    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                        super.onAuthenticationSucceeded(result)

                        startActivity(Intent(this@MenuActivity,CameraActivity::class.java))

                    }

                    override fun onAuthenticationFailed() {
                        super.onAuthenticationFailed()
                    }
                })
            biometricManager.authenticate(biometricPrompt)
        } else {
            Snackbar.make(
                binding.btnBio,
                "No existen los requisitos necesarios",
                Snackbar.LENGTH_LONG).show()
        }

    }
    private fun checkBiometric() : Boolean{
        val biometricManager = BiometricManager.from(this)
        var returnValid = true

        when(biometricManager.canAuthenticate(
            BIOMETRIC_STRONG or DEVICE_CREDENTIAL
        )) {
            //hay hw y huella
            BiometricManager.BIOMETRIC_SUCCESS -> {
                returnValid = true
            }
            // no hay hw
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                returnValid = false
            }
            // hay problema con el hw
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                returnValid = false
            }
            // hay hw pero no huella
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                val intentPrompt = Intent(Settings.ACTION_BIOMETRIC_ENROLL) // intent implicito: solicita a alguien que dé haciendo algo, no tiene punto de llegada, solo de salida
                intentPrompt.putExtra(
                    Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                    BIOMETRIC_STRONG or DEVICE_CREDENTIAL
                )
                startActivity(intentPrompt)
                returnValid = true
            }
        }
        return returnValid
    }

    private fun salir() {
        finishAffinity()
    }

}