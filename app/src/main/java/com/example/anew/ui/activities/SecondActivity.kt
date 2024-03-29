package com.example.anew.ui.activities
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.anew.R
import com.example.anew.databinding.ActivitySecondBinding
import com.example.anew.ui.fragment.FirstFragment
import com.example.anew.ui.fragment.SecondFragment
import com.example.anew.ui.fragment.ThirdFragment
import com.example.anew.ui.utilities.FragmentsManager

class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun onStart() {
        super.onStart()
        Log.d("UCE", "Entrando a Start")
        super.onStart()
        FragmentsManager().replaceFragment(supportFragmentManager,
            binding.frmContainer.id, FirstFragment())

        initClass()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    fun initClass(){
        /*Log.d("uce", "Entrando a start")  debug en la terminal
        binding.btnRetorno.setOnClickListener{
            Log.d("UCE", "Entrando al click de retorno")
            var intent= Intent(this, MainActivity::class.java)
            startActivity(intent)

            /*Snackbar.make(
                binding.loginSegundo,"regresando",
                Snackbar.LENGTH_LONG).show()*/
        }*/

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.inicio -> {
                    FragmentsManager().replaceFragment(supportFragmentManager,
                        binding.frmContainer.id, FirstFragment())
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
        binding.btnBack.setOnClickListener{
            val backInit = Intent(this,MenuActivity::class.java)
            startActivity(backInit )
        }
    }
}