package com.example.icetflixapp

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.icetflixapp.databinding.ActivitySingInBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.*

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
        binding.RecuperarSenha.setOnClickListener{
            val navTela = Intent(this, RecoverPasswordActivity::class.java)
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
                    }

                }.addOnFailureListener{ exception ->

                    val mensagemErro = when(exception){
                        is FirebaseNetworkException -> "Sem conexão com a internet!"
                        else -> "Usuário ou senha inválido!"
                    }
                    val snackbar= Snackbar.make(view,mensagemErro, Snackbar.LENGTH_SHORT)
                    snackbar.setBackgroundTint(Color.RED)
                    snackbar.show()
                }
            }else{
                val snackbar= Snackbar.make(view,"Campos vazios não são permitidos!", Snackbar.LENGTH_SHORT)
                snackbar.setBackgroundTint(Color.RED)
                snackbar.show()
            }
        }
    }

    override fun onStart(){
        super.onStart()

        if(firebaseAuth.currentUser != null){
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }

}

