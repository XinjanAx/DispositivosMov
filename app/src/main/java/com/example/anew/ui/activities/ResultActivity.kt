package com.example.anew.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.anew.R
import com.example.anew.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()

        binding.btnOK.setOnClickListener{
            val i = Intent()
            i.putExtra("result","Si, Todo Bien ok")
            setResult(RESULT_OK,i)
            finish()
        }

        binding.btnFalse.setOnClickListener{
            val i = Intent()
            i.putExtra("result","F... for pay respects")
            setResult(RESULT_CANCELED,i)
            finish()
        }
    }
}