package com.example.icetflixapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.icetflixapp.databinding.ActivitySingUpBinding
import com.google.firebase.auth.FirebaseAuth

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
                            val intent = Intent(this,SingInActivity::class.java)
                            startActivity(intent)
                        }else{
                            Toast.makeText(this,"Insira um email válido", Toast.LENGTH_SHORT).show()
                        }
                    }
                }else{
                    Toast.makeText(this, "A senha não corresponde", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this, "Campos vazios não são permitidos !!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}