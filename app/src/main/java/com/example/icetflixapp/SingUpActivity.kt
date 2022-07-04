package com.example.icetflixapp

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.icetflixapp.databinding.ActivitySingUpBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import java.lang.Exception as Exception

class SingUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySingUpBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySingUpBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.txtConectarConta.setOnClickListener {
            val navTela = Intent(this,SingInActivity::class.java)
            startActivity(navTela)
        }

        firebaseAuth = FirebaseAuth.getInstance()

        binding.btnCadastrar.setOnClickListener{
            val email = binding.inputEmail.text.toString()
            val pass = binding.inputSenha.text.toString()
            val confirmPass = binding.inputConfirmarSenha.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty() && confirmPass.isNotEmpty()){
                if (pass == confirmPass){

                    firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener{
                        if(it.isSuccessful){
                            val snackbar= Snackbar.make(view,"Cadastro feito com sucesso!", Snackbar.LENGTH_SHORT)
                            snackbar.setBackgroundTint(Color.GREEN)
                            snackbar.show()
                            val intent = Intent(this,SingInActivity::class.java)
                            startActivity(intent)
                        }

                    }.addOnFailureListener{ exception ->

                        val mensagemErro = when(exception){
                            is FirebaseAuthWeakPasswordException -> "Digite uma senha com no mínimo 6 caracteres!"
                            is FirebaseAuthInvalidCredentialsException -> "Insira um email válido!"
                            is FirebaseAuthUserCollisionException -> "Esta conta ja foi cadastrada!"
                            is FirebaseNetworkException -> "Sem conexão com a internet!"
                            else -> "Erro ao cadastrar usuário!"
                        }
                        val snackbar= Snackbar.make(view,mensagemErro, Snackbar.LENGTH_SHORT)
                        snackbar.setBackgroundTint(Color.RED)
                        snackbar.show()
                    }
                }else{
                    val snackbar= Snackbar.make(view,"As senhas não correspondem!", Snackbar.LENGTH_SHORT)
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
}