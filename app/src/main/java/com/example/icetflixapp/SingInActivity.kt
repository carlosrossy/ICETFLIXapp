package com.example.icetflixapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.example.icetflixapp.databinding.ActivitySingInBinding
import com.google.firebase.auth.FirebaseAuth

class SingInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySingInBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySingInBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.RegistrarConta.setOnClickListener {
            val navTela = Intent(this, SingUpActivity::class.java)
            startActivity(navTela)
        }
        firebaseAuth = FirebaseAuth.getInstance()

        binding.btnEntrar.setOnClickListener{
            val email = binding.inputEmail.text.toString()
            val pass = binding.inputSenha.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty()){

                firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                    if(it.isSuccessful){
                        val intent = Intent(this,MainActivity::class.java)
                        startActivity(intent)
                    }else{
                        Toast.makeText(this,"Usuario ou senha inválido", Toast.LENGTH_SHORT).show()
                    }
                }
            }else{
                Toast.makeText(this, "Campos vazios não são permitidos !!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

