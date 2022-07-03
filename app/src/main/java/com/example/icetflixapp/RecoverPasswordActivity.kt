package com.example.icetflixapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.icetflixapp.databinding.ActivityRecoverPasswordBinding
import com.google.firebase.auth.FirebaseAuth

class RecoverPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecoverPasswordBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecoverPasswordBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.btnEnviar.setOnClickListener{
            val email = binding.inputEmail.text.toString()

            if(email.isNotEmpty()){
                firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener{
                    if(it.isSuccessful){
                        Toast.makeText(this,"verifique seu email", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this,"Ocorreu um erro", Toast.LENGTH_SHORT).show()
                    }
                }
            }else{
                Toast.makeText(this, "Informe seu e-mail !!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}