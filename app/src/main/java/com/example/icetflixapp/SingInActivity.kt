package com.example.icetflixapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.icetflixapp.databinding.ActivitySingInBinding

class SingInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySingInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySingInBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.RegistrarConta.setOnClickListener({
            IrParaTelaCadastro()
        })
    }

    private fun IrParaTelaCadastro(){

        val navTela = Intent(this,SingUpActivity::class.java)
        startActivity(navTela)
    }
}

